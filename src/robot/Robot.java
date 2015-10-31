package robot;

import geometry.Vector;

public class Robot {
    public final RobotConfiguration configuration;
    public final Vector position;
    public final Vector direction;

    public Robot(RobotConfiguration configuration, Vector position, Vector direction) {
        this.configuration = configuration;
        this.position = position;
        this.direction = direction;
    }

    public Robot move(Command command) {
        Command correctCommand = configutationCorrecting(command);
        if (Math.abs(correctCommand.angularVelocity) < 1e-6)
            return straightMove(correctCommand.linearVelocity, correctCommand.timeOfAction);
        if (Math.abs(correctCommand.linearVelocity) < 1e-6)
            return rotate(correctCommand.angularVelocity, correctCommand.timeOfAction);
        return circularMove(correctCommand);
    }

    private Command configutationCorrecting(Command command) {
        int lSign = (int)Math.signum(command.linearVelocity);
        int aSign = (int)Math.signum(command.angularVelocity);
        double velocity = lSign * Math.min(Math.abs(command.linearVelocity), configuration.maxLinearVelocity);
        double angularVelocity = aSign * Math.min(Math.abs(command.angularVelocity), configuration.maxAngularVelocity);
        return new Command(velocity, angularVelocity, command.timeOfAction);
    }

    private Vector getCenterOfRotation(Vector dir, Vector pos, double radius) {
        Vector directionAcceleration = dir.rotate(Math.PI / 2);
        return directionAcceleration.setLength(radius).add(pos);
    }

    private Robot circularMove(Command command) throws IllegalArgumentException{
        if (Math.abs(command.angularVelocity) < 1e-6)
            throw new IllegalArgumentException("Angular velocity can't be zero");
        double radius = command.linearVelocity / command.angularVelocity;
        Vector centerOfRotation = getCenterOfRotation(direction, position, radius);
        double startAngle = direction.rotate(-Math.PI / 2).getAngle();
        Vector nextPosition = new Vector(
                radius * Math.cos(command.timeOfAction * command.angularVelocity + startAngle),
                radius * Math.sin(command.timeOfAction * command.angularVelocity + startAngle))
                .add(centerOfRotation);
        Vector nextDirection = direction.rotate(command.timeOfAction * command.angularVelocity);
        return new Robot(configuration, nextPosition, nextDirection);
    }

    private Robot rotate(double angularVelocity, double timeOfAction) {
        Vector rotateDirection = direction.rotate(angularVelocity * timeOfAction).normalize();
        return new Robot(configuration, position, rotateDirection);
    }

    private Robot straightMove(double velocity, double timeOfAction) {
        Vector shift = direction.normalize().mult(velocity * timeOfAction);
        Vector newPosition = position.add(shift);
        return new Robot(configuration, newPosition, direction);
    }
}
