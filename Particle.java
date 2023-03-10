import java.util.ArrayList;
import java.util.List;

public class Particle {
    private final int id;
    private final double radius;
    private final double mass;
    private double x;
    private double y;

    private List<Particle> neighbours;

    public List<Particle> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Particle> neighbours) {
        this.neighbours = neighbours;
    }

    public Particle(int id, double radius, double mass) {
        this.id = id;
        this.radius = radius;
        this.mass = mass;
        this.neighbours = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                ", radius=" + radius +
                ", mass=" + mass +
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

    public double getMass() {
        return mass;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

