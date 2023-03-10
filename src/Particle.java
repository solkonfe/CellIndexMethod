package src;

import java.util.ArrayList;
import java.util.List;

public class Particle {
    private final int id;
    private final double radius;
    private final double property;
    private double x;
    private double y;
    private List<Particle> neighbours;

    public Particle(int id, double radius, double mass) {
        this.id = id;
        this.radius = radius;
        this.property = mass;
        this.neighbours = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "src.Particle{" +
                "id=" + id +
                ", radius=" + radius +
                ", property=" + property +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public int getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public double getProperty() {
        return property;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public List<Particle> getNeighbours() {
        return neighbours;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

