package noises;

import geometry.Vector;
import robot.Command;
import robot.Robot;

public class WindNoise implements IRobotNoise{
    private final Vector force;

    public WindNoise(double angle, double velocity) {
        force = Vector.create(angle, velocity);
    }

    public Robot noise(Robot robot, Command originalCommand) {
        return new Robot(robot.configuration, robot.position.add(force), robot.direction);
    }

    @Override
    public Robot noise(Robot originalRobot, double time) {
        Vector displacement = force.mult(time);
        return new Robot(originalRobot.configuration,
                originalRobot.position.add(displacement),
                originalRobot.direction);
    }
}
