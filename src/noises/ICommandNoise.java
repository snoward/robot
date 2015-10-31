package noises;

import robot.Command;

public interface ICommandNoise {
    Command noise(Command originalCommand);
}
