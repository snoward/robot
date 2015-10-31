package main;

import geometry.Vector;
import org.junit.Assert;
import org.junit.Test;
import robot.Command;
import robot.Robot;
import robot.RobotConfiguration;

public class RobotTest {


    Robot robot;
    public RobotTest() {
        RobotConfiguration configuration = new RobotConfiguration(10, Math.PI / 4, 10);
        robot = new Robot(configuration, new Vector(0,0), new Vector(0, -1));
    }

    public void assertVectors(Vector v1, Vector v2) {
        Assert.assertEquals(v1.x, v2.x, 1e-6);
        Assert.assertEquals(v1.y, v2.y, 1e-6);
    }

    public void assertRobotState(Robot robot, Vector positionExpected, Vector directionExpected) {
        assertVectors(robot.position, positionExpected);
        Assert.assertNotEquals(robot.direction.length(), 0, 1e-6);
        Assert.assertEquals(robot.direction.cosAngleTo(directionExpected), 1, 1e-6);
    }

    @Test
    public void rotate90Left() {
        Robot rotated90LeftRobot = robot.move(new Command(0, -robot.configuration.maxAngularVelocity, 2));
        assertRobotState(rotated90LeftRobot, robot.position, new Vector(-1, 0));
    }

    @Test
    public void rotate45Left() {
        Robot rotated45LeftRobot = robot.move(new Command(0, -robot.configuration.maxAngularVelocity, 1));
        assertRobotState(rotated45LeftRobot, robot.position, new Vector(-1 / Math.sqrt(2), -1 / Math.sqrt(2)));
    }

    @Test
    public  void rotate90Right() {
        Robot rotated90RightRobot = robot.move(new Command(0, robot.configuration.maxAngularVelocity, 2));
        assertRobotState(rotated90RightRobot, robot.position, new Vector(1, 0));
    }

    @Test
    public void rotate45Right() {
        Robot rotated45RightRobot = robot.move(new Command(0, robot.configuration.maxAngularVelocity, 1));
        assertRobotState(rotated45RightRobot, robot.position, new Vector(1 / Math.sqrt(2), -1 / Math.sqrt(2)));
    }

    @Test
    public void moveForward() {
        Robot movedForwardRobot = robot.move(new Command(robot.configuration.maxLinearVelocity, 0, 1));
        assertRobotState(movedForwardRobot, new Vector(0, -10), robot.direction);
    }

    @Test
    public void moveBackward() {
        Robot movedBackwardRobot = robot.move(new Command(-robot.configuration.maxLinearVelocity, 0, 1));
        assertRobotState(movedBackwardRobot, new Vector(0, 10), robot.direction);
    }

    @Test
    public void moveByCircle() {
        Robot moveByCircleRobot = robot.move(new Command(
                robot.configuration.maxLinearVelocity,
                robot.configuration.maxAngularVelocity, 4));
        assertRobotState(moveByCircleRobot,
                new Vector(2 * robot.configuration.maxLinearVelocity / robot.configuration.maxAngularVelocity, 0),
                new Vector(0, 1));
    }
}