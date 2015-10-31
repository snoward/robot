package ai;

import geometry.Vector;
import robot.Command;
import robot.Robot;

public class ComplexIRobotAI implements IRobotAI {
//если точка получается на окружности или внутри окружности, сделать проверку!!!
    @Override
    public Command nextOptimalCommand(Vector target, Robot robot, double maxTime) {
        if (isOnTarget(robot, target))
            return new Command(0, 0, 0);

        double angleToTarget = calculateAngle(robot, target);
        if (angleToTarget - Math.PI / 2 > 1e-6) {
            return rotateCommand(angleToTarget - Math.PI / 2,
                    calclulateRotationType(robot, target),
                    robot.configuration.maxAngularVelocity,
                    maxTime);
        }
        if (angleToTarget < Math.PI / 100) {
            return forwardMove(calculateDistance(robot, target) - robot.configuration.radius,
                    robot.configuration.maxLinearVelocity,
                    maxTime);
        }
        double radius = robot.configuration.maxLinearVelocity / robot.configuration.maxAngularVelocity;
        double distance = calculateDistance(robot, target);
        double linearVelocity = robot.configuration.maxLinearVelocity;
        if (distance < 2 * radius)
            linearVelocity = distance * robot.configuration.maxAngularVelocity / 2;
        return new Command(linearVelocity,
                robot.configuration.maxAngularVelocity * calclulateRotationType(robot, target).signal,
                Math.min(angleToTarget / robot.configuration.maxAngularVelocity, maxTime));
    }

    public Command rotateCommand(double angle, RotationType rType, double maxAngularVelocity, double maxTime) {
        double time = angle / maxAngularVelocity;
        return new Command(0, maxAngularVelocity * rType.signal , Math.min(maxTime, time));
    }

    public Command forwardMove(double distance, double maxLinearVelocity, double maxTime) {
        double time = distance / maxLinearVelocity;
        return new Command(maxLinearVelocity, 0, Math.min(maxTime, time));
    }
}
