package simulation;

import geometry.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RobotVisualisationWindow extends JFrame {
    private ProcessSimulation simulatior;

    public RobotVisualisationWindow(ProcessSimulation simulator){
        super("ROBOOT");
        this.simulatior = simulator;
        setBounds(200, 60, 500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void drawCircle(Graphics g, Vector center, double r) {
        int intR = (int)r;
        int centerX = (int)center.x;
        int centerY = (int)center.y;
        g.drawOval(centerX - intR, centerY - intR, intR * 2, intR * 2);
    }

    private void  drawRobot(Graphics g, robot.Robot robot) {
        Color colorNow = g.getColor();
        g.setColor(Color.BLACK);
        drawCircle(g, robot.position, robot.configuration.radius);
        Vector nose = robot.direction.setLength(robot.configuration.radius).add(robot.position);
        g.setColor(Color.BLUE);
        drawCircle(g, nose, robot.configuration.radius / 4);
        g.setColor(colorNow);
    }
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        drawRobot(g, simulatior.robot);
        g.setColor(Color.RED);
        drawCircle(g, simulatior.target, simulatior.robot.configuration.radius / 4);
        g.setColor(Color.BLACK);
        double time = Math.round(simulatior.getTimeFromStart() * 100) / 100.0;
        g.setFont(new Font("Verdana", Font.PLAIN, 18));
        g.drawString("Time: " + Double.toString(time), getSize().width / 2 - 50, getSize().height - 50);
        if (simulatior.isEndSimulation()) {
            g.setFont(new Font("Verdana", Font.PLAIN, 25));
            g.drawString("Charging complete", getSize().width / 2 - 120, getSize().height / 2);
        }
        for (Vector v : simulatior.getBumpPosition())
            drawCircle(g, v, 5);
    }

    public void start() throws InterruptedException {
        double lambda = 0.01;
        setVisible(true);
        while (!simulatior.isEndSimulation()) {
            simulatior = simulatior.simulate(lambda);
            TimeUnit.MILLISECONDS.sleep(10);
            repaint();
        }

    }
}
