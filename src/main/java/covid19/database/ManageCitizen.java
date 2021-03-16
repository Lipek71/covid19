package covid19.database;

import covid19.Citizens;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ManageCitizen {
    public List<Citizens> LoadCitizenFromFile(InputStream is) {
        List<Citizens> citizens = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] lineParts = line.split(";");
                citizens.add(new Citizens(lineParts[0], lineParts[1], Integer.parseInt(lineParts[2]), lineParts[3], lineParts[4]));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Nem tudom megnyitni a fájlt!");
        }
        return citizens;
    }

    public void SaveCitizensToFile(List<Citizens> citizens, String outoutFile)  {
        Path path = Path.of("src/main/resources/".concat(outoutFile).concat(".csv"));

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            LocalTime time = LocalTime.of(8,0);
            LocalTime increase;
            for (Citizens citizens1 : citizens){
                bw.write(time.toString() + ";" + citizens1.getCitizenName() + ";" + citizens1.getZip() + ";" + citizens1.getAge() + ";" + citizens1.getEmail() + ";" + citizens1.getTaj() + "\n");
                increase = time.plusMinutes(30);
                time = increase;
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Nem sikerült a kiírás", ioe);
        }
    }
}
