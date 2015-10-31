package robot;

public class RobotConfiguration {
    public final double maxLinearVelocity, maxAngularVelocity, radius;

    public RobotConfiguration(double maxLinearVelocity, double maxAngularVelocity, double radius) {
        this.maxLinearVelocity = maxLinearVelocity;
        this.maxAngularVelocity = maxAngularVelocity;
        this.radius = radius;
    }
}
