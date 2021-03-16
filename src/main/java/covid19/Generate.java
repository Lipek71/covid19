package covid19;

import covid19.database.CitizensDao;
import covid19.database.ManageCitizen;

import java.util.List;
import java.util.Scanner;

public class Generate {
    public void generate() {
        Validator validator = new Validator();
        Scanner scanner = new Scanner(System.in);
        String outputFile;
        List<Citizens> citizens;
        String zip = inputZip(scanner, validator);
        System.out.print("Kérem a fájl nevét (kiterjesztés nélkül): ");
        outputFile = scanner.nextLine();

        Main main = new Main();
        CitizensDao citizensDao = new CitizensDao(main.initializeDB());
        citizens = citizensDao.findCitizensByZip(zip);

        ManageCitizen manageCitizen = new ManageCitizen();
        manageCitizen.SaveCitizensToFile(citizens, outputFile);

        main.menu();
    }

    private String inputZip(Scanner scanner, Validator validator) {
        String zip;
        do {
            System.out.print("Kérem az értesítési hely irányítószámát: ");
            zip = scanner.nextLine();
        } while (!validator.zip(zip));
        return zip;
    }
}
