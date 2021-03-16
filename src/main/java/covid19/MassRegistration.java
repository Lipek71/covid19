package covid19;

import covid19.database.CitizensDao;
import covid19.database.ManageCitizen;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class MassRegistration {
    public void massRegistration(){
        Scanner scanner = new Scanner(System.in);
        String inputFile;

        System.out.print("Kérem a beolvasandó fájl nevét: ");
        inputFile = scanner.nextLine();
        inputFile = "/".concat(inputFile);

        InputStream is = MassRegistration.class.getResourceAsStream(inputFile);

        Main main = new Main();
        ManageCitizen loadCitizen = new ManageCitizen();
        List<Citizens> citizens = loadCitizen.LoadCitizenFromFile(is);

        CitizensDao citizensDao = new CitizensDao(main.initializeDB());
        citizensDao.saveCitizensMass(citizens);

        main.menu();
    }
}
