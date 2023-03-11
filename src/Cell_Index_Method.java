package src;

import java.io.*;
import java.util.*;

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

    // distance between particles
    public static double getDistance(Particle p1, Particle p2, double L, boolean walls){
        double deltaX = Math.abs(p1.getX() - p2.getX());
        double deltaY = Math.abs(p1.getY() - p2.getY());

        if(walls) {
            deltaX -= deltaX > ((L*1.0f) / 2 ) ? L : 0;
            deltaY -= deltaY > ((L*1.0f) / 2) ? L : 0;
        }

        return Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2)) - p1.getRadius() - p2.getRadius();
    }

    // input --> static.file dynamic.file M rc walls
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();

        if(args.length != 5){
            throw new IllegalArgumentException("Invalid parameters");
        }
        File staticFile = new File(args[0]);
        Scanner staticData = new Scanner(staticFile);
        File dynamicFile = new File(args[1]);
        Scanner dynamicData = new Scanner(dynamicFile);

        int N = 0;
        int L = 0;
        int M = Integer.parseInt(args[2]);
        double rc = Double.parseDouble(args[3]);
        boolean walls = Boolean.parseBoolean(args[4]);

        if(staticData.hasNextLine()){
            N = Integer.parseInt(staticData.nextLine().trim().replaceAll("\\s+", ""));
        }
        if(staticData.hasNextLine()){
            L = Integer.parseInt(staticData.nextLine().trim().replaceAll("\\s+", ""));
        }

        // generate particles
        List<Particle> particles = new ArrayList<>();
        double rMax = getStaticData(staticData, particles);
        getDynamicData(dynamicData, particles);

        if (particles.size() != N){
            System.err.println("Error: wrong number of particles");
        }

        // generate map with cells
        Cell[][] map = new Cell[M][M];
        int row;
        int col;
        for(int i = 0; i < M; i++){
            for(int j = 0; j < M; j++){
                map[i][j] = new Cell();
            }
        }
        for (Particle particle : particles) {
            row = ((int) (particle.getX() / ((float) L / M))) % M;
            col = ((int) (particle.getY() / ((float) L / M))) % M;
            map[row][col].addParticle(particle);
        }

        // get neighbours
        HashMap<Integer,List<Integer>> neighbours = new HashMap<>();

        for (int i = 0; i < M; i++){
            for (int j = 0; j < M; j++) {
                for (Particle particle : map[i][j].getParticles()) {

                    if(!neighbours.containsKey(particle.getId())) {
                        neighbours.put(particle.getId(), new ArrayList<>());
                    }

                    List<Particle> possibleNeighbours = new ArrayList<>();
                    // select possible neighbours
                    if (walls){ //with walls
                        possibleNeighbours.addAll(map[(i + M - 1) % M][(j + 1) % M].getParticles());
                        possibleNeighbours.addAll(map[i][(j+1)%M].getParticles());
                        possibleNeighbours.addAll(map[(i+1)%M][(j+1)%M].getParticles());
                        possibleNeighbours.addAll(map[(i+1)%M][j].getParticles());
                    }else {
                        if(i<M-1){
                            possibleNeighbours.addAll(map[i+1][j].getParticles());
                        }
                        if(j<M-1) {
                            possibleNeighbours.addAll(map[i][j+1].getParticles());
                            if(i<M-1){
                                possibleNeighbours.addAll(map[i+1][j+1].getParticles());
                            }
                            if (i > 0){
                                possibleNeighbours.addAll(map[i-1][j+1].getParticles());
                            }
                        }
                    }

                    //check self cell
                    for(Particle neighbour : map[i][j].getParticles()){
                        if(neighbour.getId() != particle.getId()){
                            if(getDistance(particle, neighbour, L, walls) <= rc){
                                neighbours.get(particle.getId()).add(neighbour.getId());
                            }
                        }
                    }
                    // check other cells
                    double distance;
                    for (Particle neighbour : possibleNeighbours) {
                        distance = getDistance(particle, neighbour, L, walls);
                        if(distance <= rc){
                            neighbours.get(particle.getId()).add(neighbour.getId());
                            if(!neighbours.containsKey(neighbour.getId())){
                                neighbours.put(neighbour.getId(), new ArrayList<>());
                            }
                            neighbours.get(neighbour.getId()).add(particle.getId());
                        }
                    }
                }
            }
        }

        // TODO: Generate output with neighbours
        try {
            File myObj = new File("../outputs/neighbours.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred creating output file.");
            e.printStackTrace();
        }
        FileWriter writer = new FileWriter("../outputs/neighbours.txt");

        StringBuilder str = new StringBuilder();
        for(int particleId : neighbours.keySet()){
            str.append(particleId).append("\t");
            for(int neighbourId : neighbours.get(particleId)){
                str.append(neighbourId).append("\t");
            }
            str.append("\n");
        }
        writer.write(str.toString());
        writer.close();

        long endTime = System.currentTimeMillis();
        System.out.printf("Time: %d ms\n", endTime - startTime);
    }
}
