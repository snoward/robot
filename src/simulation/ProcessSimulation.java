package simulation;


import ai.IRobotAI;
import geometry.Vector;
import noises.BumpNoise;
import noises.ICommandNoise;
import noises.IRobotNoise;
import robot.Command;
import robot.Robot;

import java.util.ArrayList;

public class ProcessSimulation {
    public final static double ATOM_TIME = 0.001;
    public final IRobotAI intelligence;
    public final Robot robot;
    public final Vector target;
    private double timeFromStart = 0;
    private final Iterable<ICommandNoise> commandNoises;
    private final Iterable<IRobotNoise> robotNoises;

    public double getTimeFromStart() { return timeFromStart; }
    public boolean isEndSimulation() { return intelligence.isOnTarget(robot, target); }
    public ArrayList<Vector> getBumpPosition() {
        ArrayList<Vector> result = new ArrayList<>();
        for (IRobotNoise noise : robotNoises)
            if (noise instanceof BumpNoise)
                result.add(((BumpNoise)noise).location);
        return result;
    }


    public ProcessSimulation(IRobotAI intelligence, Robot robot, Vector target, Iterable<ICommandNoise> cmdNoise, Iterable<IRobotNoise> rNoise) {
        this.intelligence = intelligence;
        this.robot = robot;
        this.target = target;
        commandNoises = cmdNoise;
        robotNoises = rNoise;
    }

    private ProcessSimulation(ProcessSimulation lastProcess, Robot robot, double addTime) {
        intelligence = lastProcess.intelligence;
        target = lastProcess.target;
        this.robot = robot;
        timeFromStart = lastProcess.timeFromStart + addTime;
        this.commandNoises = lastProcess.commandNoises;
        this.robotNoises = lastProcess.robotNoises;
    }

    public ProcessSimulation simulate(double simulationTime) {
        if (simulationTime < ATOM_TIME)
            throw new IllegalArgumentException("simulationTime can't be less then " + ATOM_TIME);

        if (isEndSimulation())
            return this;

        Robot nextRobot = robot;
        double realTime = 0;
        for(double i = 0; i < simulationTime; i += ATOM_TIME) {
            Command cmd = intelligence.nextOptimalCommand(target, robot, ATOM_TIME);
            cmd = addNoisesInCommnad(cmd, commandNoises);
            nextRobot = nextRobot.move(cmd);
            nextRobot = addNoisesInRobot(nextRobot, ATOM_TIME, robotNoises);
            realTime += cmd.timeOfAction;
        }
        return new ProcessSimulation(this, nextRobot, realTime);
    }

    private Command addNoisesInCommnad(Command originalCommand, Iterable<ICommandNoise> noises) {
        Command nextCommand = originalCommand;
        for (ICommandNoise noise : noises) {
            nextCommand = noise.noise(originalCommand);
        }
        return nextCommand;
    }

    private  Robot addNoisesInRobot(Robot originalRobot, double time, Iterable<IRobotNoise> noises) {
        Robot nextRobot = originalRobot;
        for (IRobotNoise noise : noises) {
            nextRobot = noise.noise(nextRobot, time);
        }
        return nextRobot;
    }

}
