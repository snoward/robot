package main;

import ai.SimpleIRobotAI;
import geometry.Vector;
import noises.*;
import robot.*;
import simulation.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RobotConfiguration cfg = new RobotConfiguration(50, Math.PI / 4, 20);
        Robot robot = new Robot(cfg, new Vector(250, 250), new Vector(0, 10));

        ArrayList<ICommandNoise> commandNoises = new ArrayList<>();

        ArrayList<IRobotNoise> robotNoises = new ArrayList<>();
        robotNoises.add(new WindNoise(0, 0));
        robotNoises.add(new BumpNoise(Math.PI / 2, new Vector(250, 150)));

        ProcessSimulation simulator = new ProcessSimulation(new SimpleIRobotAI(), robot, new Vector(250, 50), commandNoises, robotNoises);
        RobotVisualisationWindow application = new RobotVisualisationWindow(simulator);
        application.start();
    }
}
