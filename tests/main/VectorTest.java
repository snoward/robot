package main;

import geometry.Vector;
import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

    public boolean isEquals(Vector v1, Vector v2, double error) {
        return Math.abs(v1.x - v2.x) <= error && Math.abs(v2.y - v1.y) <= error;
    }

    Vector vector = new Vector(1, 0);

    @Test
    public void testRotate() {
        Vector rotateVector = vector.rotate(Math.PI / 2);
        Assert.assertTrue(isEquals(rotateVector, new Vector(0, 1), 1e-6));
    }

    @Test
    public void testAdd() {
        Vector sumVector = vector.add(new Vector(1,1));
        Assert.assertTrue(isEquals(sumVector, new Vector(2, 1), 1e-6));
    }

    @Test
    public void testLength() {
        Assert.assertEquals(new Vector(1,2).length(), Math.sqrt(5), 1e-6);
    }

    @Test
    public void testDistanceTo() {
        double dist = vector.distanceTo(new Vector(0,1));
        Assert.assertEquals(dist, Math.sqrt(2), 1e-6);
    }

    @Test
    public void testNormalize() {
        Vector testVector = new Vector(2, 1);
        Vector normalaizeVector = testVector.normalize();
        Assert.assertEquals(normalaizeVector.length(), 1, 1e-6);
        Assert.assertEquals(testVector.sinAngleTo(normalaizeVector), 0, 1e-6);

    }

    @Test
    public void testMult() {
        Vector v = new Vector(4, 3);
        Vector vX2 = v.mult(2);
        Assert.assertEquals(v.length() * 2, vX2.length(), 1e-6);
    }

    @Test
    public void testIsZero() {
        Assert.assertTrue(new Vector(0, 0).isZero());
    }

    @Test
    public void testCosAngleTo() {
        Vector v1 = new Vector(1,0);
        Vector v2 = new Vector(0,1);
        Assert.assertEquals(v1.cosAngleTo(v2), 0, 1e-6);
        Assert.assertEquals(v2.cosAngleTo(v1), 0, 1e-6);
    }

    @Test
    public void testSinAngleTo() {
        Vector v1 = new Vector(1,0);
        Vector v2 = new Vector(0,1);
        Assert.assertEquals(v1.sinAngleTo(v2), 1, 1e-6);
        Assert.assertEquals(v2.sinAngleTo(v1), -1, 1e-6);
    }
}