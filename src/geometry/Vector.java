package geometry;

public class Vector {
    public final double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector (Vector from, Vector to) {
        x = to.x - from.x;
        y = to.y - from.y;
    }

    public static Vector create(double angle, double length){
        Vector v = new Vector(Math.cos(angle), Math.sin(angle));
        return v.setLength(length);
    }

    @Override
    public String toString() {
        return "("+x+";"+y+")";
    }

    public Vector add(Vector vector) {
        return new Vector(x + vector.x, y + vector.y);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector setLength(double newLength) {
        double lengthNow = length();
        return new Vector(x / lengthNow * newLength, y / lengthNow * newLength);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public double distanceTo(Vector vector) {
        return Math.sqrt((x - vector.x) * (x - vector.x) + (y - vector.y) * (y - vector.y));
    }


    public Vector normalize() {
        double lengthNow = length();
        return new Vector(x / lengthNow, y / lengthNow);
    }

    public Vector mult(double number) {
        return new Vector(x * number, y * number);
    }

    public boolean isZero() {
        return Math.abs(this.length()) < 1e-6;
    }

    public double cosAngleTo(Vector vector) {
        if (this.isZero() || vector.isZero())
            return 1;
        double result = (x * vector.x + y*vector.y) / (this.length() * vector.length());
        return Math.round(result * 1e6) / 1e6;
    }

    public double sinAngleTo(Vector vector) {
        if (this.isZero() || vector.isZero())
            return 0;
        double result = (x * vector.y - vector.x * y) / (this.length() * vector.length());
        return Math.round(result * 1e6) / 1e6;
    }

    public Vector rotate(double angleRadian) {
        double newX = x * Math.cos(angleRadian) - y * Math.sin(angleRadian);
        double newY = x * Math.sin(angleRadian) + y * Math.cos(angleRadian);
        return new Vector(newX, newY);
    }
}
