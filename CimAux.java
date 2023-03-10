import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CimAux {
    public static void generateNeighboursFile(HashMap<Integer, List<Integer>> neighbours, String path) throws IOException {
        File output = new File(path);
        output.createNewFile();
        StringBuilder builder;

        FileWriter writer = new FileWriter(path);

        for(int particleId : neighbours.keySet()){
            List<Integer> neighboursIds = neighbours.get(particleId);
            builder = new StringBuilder();
            builder.append(particleId).append("\t");

            for(int neighbourId : neighboursIds){
                builder.append(neighbourId).append("\t");
            }

            builder.replace(builder.length(), builder.length(), "\n");
            writer.write(builder.toString());
        }

        writer.close();
    }
}
