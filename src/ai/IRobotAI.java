package ai;

import geometry.Vector;
import robot.Command;
import robot.Robot;

public interface IRobotAI {
    default boolean isZero(double value, double epsilon) {
        return Math.abs(value) <= epsilon;
    }

    default double calculateAngle(Robot robot, Vector target) {
        Vector onTarget = new Vector(robot.position, target);
        return Math.acos(robot.direction.cosAngleTo(onTarget));
    }

    default double calculateDistance(Robot robot, Vector target) {
        return robot.position.distanceTo(target);
    }

    default RotationType calclulateRotationType(Robot robot, Vector target) {
        Vector onTarget = new Vector(robot.position, target);
        int signum = (int)Math.signum(robot.direction.sinAngleTo(onTarget));
        if (signum == 0 && robot.direction.cosAngleTo(onTarget) < 0)
            signum = 1;
        return RotationType.fromInteger(signum);
    }

    default boolean isOnTarget(Robot robot, Vector target) {
        return isZero(calculateAngle(robot, target), Math.PI / 100) &&
                isZero(calculateDistance(robot, target) - robot.configuration.radius, 1);
    }

    Command nextOptimalCommand(Vector target, Robot robot, double maxTime);
}
