import java.util.ArrayList;
import java.util.List;

public class Particle {
    private double x;
    private double y;
    private static int num;
    private int id;

    private List<Particle> neighbours;

    public List<Particle> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Particle> neighbours) {
        this.neighbours = neighbours;
    }

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = num++;
        this.neighbours = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static int getNum() {
        return num;
    }
}

