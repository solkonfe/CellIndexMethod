import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Cell_Index_Method {

    // scanner static data
    public static double getStaticData(Scanner reader, List<Particle> particles){
        double[] properties;
        int counter = 1;
        double rMax = 0;

        while (reader.hasNextLine()) {
            String data = reader.nextLine().trim().replaceAll("\\s+", " ");
            StringTokenizer tokenizer = new StringTokenizer(data);

            properties = new double[]{Double.parseDouble(tokenizer.nextToken()), Double.parseDouble(tokenizer.nextToken())};
            particles.add(new Particle(counter, properties[0], properties[1]));
            if(properties[0] > rMax){
                rMax = properties[0];
            }
            counter++;
        }
        return rMax;
    }

    // scanner dynamic data
    public static void getDynamicData(Scanner reader, List<Particle> particles){
        double[] position;
        int counter = 1;
        reader.nextLine(); //skip the t0

        while (reader.hasNextLine()) {
            String data = reader.nextLine().trim().replaceAll("\\s+", " ");;
            StringTokenizer tokenizer = new StringTokenizer(data);
            position = new double[]{Double.parseDouble(tokenizer.nextToken()), Double.parseDouble(tokenizer.nextToken())};
            particles.get(counter - 1).setX(position[0]);
            particles.get(counter - 1).setY(position[1]);
            counter++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        if(args.length != 5){
            throw new IllegalArgumentException("Invalid parameters");
        }
        File staticFile = new File(args[0]);
        Scanner staticData = new Scanner(staticFile);
        File dynamicFile = new File(args[1]);
        Scanner dynamicData = new Scanner(dynamicFile);

        int N = 0;
        int L = 0;
        //parse the quantity of particles(N) and the size of the square grid(L)
        if(staticData.hasNextLine()){
            N = Integer.parseInt(staticData.nextLine().trim().replaceAll("\\s+", ""));
        }
        if(staticData.hasNextLine()){
            L = Integer.parseInt(staticData.nextLine().trim().replaceAll("\\s+", ""));
        }

        int M = Integer.parseInt(args[2]);
        double rc = Double.parseDouble(args[3]);
        boolean contourProp = Boolean.parseBoolean(args[4]);

        // generate particles
        List<Particle> particles = new ArrayList<>();
        getStaticData(staticData, particles);
        getDynamicData(dynamicData, particles);

        if (particles.size() != N){
            System.err.println("Error: wrong number of particles");
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

            for (Particle p : particles) {
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

    }
}
