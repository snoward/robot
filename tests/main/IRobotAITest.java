package main;

import ai.ComplexIRobotAI;
import ai.IRobotAI;
import ai.SimpleIRobotAI;
import geometry.Vector;
import noises.ICommandNoise;
import noises.IRobotNoise;
import org.junit.Assert;
import org.junit.Test;
import robot.Robot;
import robot.RobotConfiguration;
import simulation.ProcessSimulation;

import java.util.ArrayList;

public class IRobotAITest {
    public final ArrayList<RobotConfiguration> configurationsSet = new ArrayList<>();
    public final ArrayList<Vector> circleVectorsSet = new ArrayList<>();

    public IRobotAITest() {
        addConfiguratons();
        addCircleVectors();
    }

    public void addConfiguratons() {
        for (int i = 1; i < 10; i++) {
            configurationsSet.add(new RobotConfiguration(50, Math.PI / i, 10));
            configurationsSet.add(new RobotConfiguration(i * 10, Math.PI / 4, 10));
            configurationsSet.add(new RobotConfiguration(50, Math.PI / 4, i * 2));
        }
    }

    public void addCircleVectors() {
        Vector startVector = new Vector(0, 200);
        for (int i = 0; i < 8; i++)
            circleVectorsSet.add(startVector.rotate(i * Math.PI / 4));
    }

    public double calculateUpperBoundTime(Robot robot, Vector target) {
        double dist = robot.position.distanceTo(target);
        double angle = Math.acos(robot.direction.cosAngleTo(target));
        double time = angle / robot.configuration.maxAngularVelocity + dist / robot.configuration.maxLinearVelocity;
        return time * 100;
    }

    public void assertCase(Robot robot, IRobotAI intellegence, Vector target) {

        ProcessSimulation simulator = new ProcessSimulation(intellegence, robot, target, new ArrayList<ICommandNoise>(), new ArrayList<IRobotNoise>());
        double time = calculateUpperBoundTime(robot, target);
        simulator = simulator.simulate(time);
        Vector pos = simulator.robot.position;
        Vector dir = simulator.robot.direction;
        Assert.assertEquals(pos.distanceTo(target), robot.configuration.radius, 1e-3);
        Assert.assertEquals(dir.cosAngleTo(new Vector(pos, target)), 1, 1e-3);
    }

    public void assertFixRobotState(IRobotAI ai) {
        Vector startPosition = new Vector(0, 0);
        Vector startDirection = new Vector(0, 10);
        for (RobotConfiguration cfg : configurationsSet)
            for (Vector target : circleVectorsSet)
                assertCase(new Robot(cfg, startPosition, startDirection), ai, target);
    }

    public void assertFixTarget(IRobotAI ai) {
        Vector target = new Vector(0, 0);
        Vector startDirection = new Vector(0, 10);
        for (RobotConfiguration cfg : configurationsSet)
            for (Vector startPosition : circleVectorsSet)
                assertCase(new Robot(cfg, startPosition, startDirection), new SimpleIRobotAI(), target);
    }

    @Test
    public void simpleAIFixRobotSate() {
        assertFixRobotState(new SimpleIRobotAI());
    }

    @Test
    public void simpleAIFixTarget() {
        assertFixTarget(new SimpleIRobotAI());
    }

    @Test
    public void simpleAITargetOnRadius() {
        RobotConfiguration cfg = new RobotConfiguration(100, Math.PI / 4, 50);
        Robot robot = new Robot(cfg, new Vector(0,0), new Vector(10, 0));
        assertCase(robot, new SimpleIRobotAI(), new Vector(0, 50));
        assertCase(robot, new SimpleIRobotAI(), new Vector(-50, 0));
    }

    @Test
    public void complexAIFixRobotState() {
        assertFixRobotState(new ComplexIRobotAI());
    }

    @Test
    public void complexAIFixTarget() {
        assertFixTarget(new ComplexIRobotAI());
    }

    @Test
    public void complexAItargetOnRadius() {
        RobotConfiguration cfg = new RobotConfiguration(100, Math.PI / 4, 50);
        Robot robot = new Robot(cfg, new Vector(0,0), new Vector(10, 0));
        assertCase(robot, new ComplexIRobotAI(), new Vector(0, 50));
        assertCase(robot, new ComplexIRobotAI(), new Vector(-50, 0));
    }
}
