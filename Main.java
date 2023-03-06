import java.util.ArrayList;
import java.util.List;

public class Main {
    static int totalParticles = 200 ;
    static int L = 20;
    static int M = 10;
    static int radio = 1;
    static double particleRadio = 0.25;

    public static void main(String[] args) {

        List<Particle> allParticles = new ArrayList<>();
        for(int i = 0; i < totalParticles; i++ ){
            Particle p = new Particle( (Math.random() * L), (Math.random() * L));
            allParticles.add(p);
        }

        List<Cell> cells = new ArrayList<>();
        int x = 0;
        int y = 0;
        int cellSize = L/M;
        for (int j = 1; j <= M*M; j++) {
            Cell c = new Cell(j, x, y, x+cellSize, y+cellSize);
            if (!(x==L-cellSize)){
                c.getNeighbours().add(j+1);
                if (!(y==0)){
                    c.getNeighbours().add(j-M+1);
                }
            }
            if (!(y==0)){
                c.getNeighbours().add(j-M);
            }
            if (!(y==L-cellSize)){
                if (!(x==L-cellSize)){
                    c.getNeighbours().add(j+M+1);
                }
            }

            for (Particle p : allParticles) {
                if( x <= p.getX() && p.getX() <= x+cellSize && y <= p.getY() && p.getY() <= y+cellSize){
                    c.getParticles().add(p);
                }
            }

            x = x+cellSize;
            if (x >= L){
                y = y+cellSize;
                x = 0;
            }
            cells.add(c);
        }

        for(Cell c : cells){
            if(!c.getParticles().isEmpty()){
                for(Particle p : c.getParticles()){
                }
            }
        }
    }
}
