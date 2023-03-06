import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int id;
    private int min_x;
    private int min_y;
    private int max_x;
    private int max_y;
    private List<Particle> particles;
    private List<Integer> neighbours;

    public Cell(int id, int min_x, int min_y, int max_x, int max_y) {
        this.id = id;
        this.min_x = min_x;
        this.min_y = min_y;
        this.max_x = max_x;
        this.max_y = max_y;
        this.particles = new ArrayList<>();
        this.neighbours = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getMin_x() {
        return min_x;
    }

    public int getMin_y() {
        return min_y;
    }


    public int getMax_x() {
        return max_x;
    }


    public int getMax_y() {
        return max_y;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public List<Integer> getNeighbours() {
        return neighbours;
    }
}
