package ai;

import geometry.Vector;
import robot.Command;
import robot.Robot;

public class SimpleIRobotAI implements IRobotAI {

    @Override
    public Command nextOptimalCommand(Vector target, Robot robot, double maxTime) {
        double angleOnTarget = calculateAngle(robot, target);
        if (!isZero(angleOnTarget, Math.PI / 100)) {
            return new Command(0,
                    robot.configuration.maxAngularVelocity * calclulateRotationType(robot, target).signal,
                    Math.min(maxTime, angleOnTarget / robot.configuration.maxAngularVelocity));
        }
        double distance = calculateDistance(robot, target) - robot.configuration.radius;
        if(!isZero(distance, 1e-6)) {
            return new Command(10000, 0,
                    Math.min(maxTime, distance / 10000));
        }
        return new Command(0,0,0);
    }

}
