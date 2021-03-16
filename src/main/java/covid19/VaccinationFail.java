package covid19;

import covid19.database.CitizensDao;
import covid19.database.VaccinationDao;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class VaccinationFail {
    public void vaccinationFail() {
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator();

        String taj = inputTaj(scanner, validator);

        Main main = new Main();
        CitizensDao citizensDao = new CitizensDao(main.initializeDB());
        List<Citizens> citizens = citizensDao.findCitizensByTaj(taj);
        if (citizens.isEmpty()) {
            System.out.println("Nincs regisztrálva ilyen TAJ számmal ügyfél!");
        } else if (citizens.get(0).getNumberOfVaccination() > 1 || Duration.between(citizens.get(0).getLastVaccination(), LocalDateTime.now()).toDays() < 15) {
            System.out.println("Vagy már két oltása van az ügyfélnek, vagy nem telt el 15 nap az előző oltás óta!");
        } else {
            vaccinationFailure(citizens.get(0));
        }
        main.menu();
    }

    private void vaccinationFailure(Citizens citizens) {
        Scanner scanner = new Scanner(System.in);
        String note;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = dateTime.format(formatter);


        System.out.println("A dátum: " + formatDateTime);
        System.out.print("Kérem a meghiúsulás okát: ");
        note = scanner.nextLine();

        Main main = new Main();
        VaccinationDao vaccinationDao = new VaccinationDao(main.initializeDB());
        System.out.println(citizens);
        Vaccinations vaccinations = new Vaccinations(citizens.getCitizenId(), dateTime, Status.UNSUCCESFUL.toString(), note, null);
        System.out.println(vaccinations);
        vaccinationDao.saveVaccination(vaccinations);

        main.menu();
    }

    private String inputTaj(Scanner scanner, Validator validator) {
        String taj;
        do {
            System.out.print("Kérem az oltandó TAJ számát: ");
            taj = scanner.nextLine();
        } while (!validator.taj(taj));
        return taj;
    }
}
