package robot;

public class Command {
    public final double linearVelocity, angularVelocity, timeOfAction;

    public Command(double linearVelocity, double angularVelocity, double timeOfAction) {
        this.linearVelocity = linearVelocity;
        this.angularVelocity = angularVelocity;
        this.timeOfAction = timeOfAction;
    }
}
