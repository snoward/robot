package noises;

import geometry.Vector;
import robot.Robot;

public class BumpNoise implements IRobotNoise{

    private final double angle;
    public final Vector location;

    public BumpNoise(double bumpAngle, Vector location) {
        angle = bumpAngle;
        this.location = location;
    }

    @Override
    public Robot noise(Robot originalRobot, double time) {
        if (originalRobot.position.distanceTo(location) > originalRobot.configuration.radius)
            return originalRobot;
        double rotationAngle = angle;
        return new Robot(originalRobot.configuration,
                originalRobot.position.add(Vector.create(-rotationAngle, originalRobot.configuration.radius)),
                originalRobot.direction.rotate(rotationAngle));
    }
}
