package noises;

import robot.Command;

public class UniformNoise implements ICommandNoise{

    private final double sizeOfNoise;

    public UniformNoise(double sizeOfNoise) {
        this.sizeOfNoise = sizeOfNoise;
    }

    @Override
    public Command noise(Command originalCommand) {
        return new Command(originalCommand.linearVelocity * sizeOfNoise,
                originalCommand.angularVelocity * sizeOfNoise,
                originalCommand.timeOfAction * sizeOfNoise);
    }
}
