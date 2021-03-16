package covid19.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoadZip {
    public List<CityZip> LoadZipFromFile(InputStream is) {
        List<CityZip> cities = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(is))) {
            String line = bf.readLine();
            while ((line = bf.readLine()) != null) {
                String[] lineParts = line.split(";");
                for (int i = 1; i < lineParts.length; i++) {
                    cities.add(new CityZip(lineParts[0], lineParts[i]));
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Nem tudom megnyitni a fájlt!");
        }
        return cities;
    }
}
