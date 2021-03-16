package covid19;

import covid19.database.CitizensDao;
import covid19.database.VaccinationDao;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vaccination {

    public void vaccination() {
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
        } else if (citizens.get(0).getNumberOfVaccination() == 1) {
            vaccinationDosageSecond(citizens.get(0));
        } else {
            vaccinationDosage(citizens.get(0));
        }
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

    public void vaccinationDosageSecond(Citizens citizens) {

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = dateTime.format(formatter);

        Main main = new Main();
        VaccinationDao vaccinationDao = new VaccinationDao(main.initializeDB());
        List<Vaccinations> vaccinationsList = vaccinationDao.findVaccinationById(citizens.getCitizenId());

        System.out.println("A dátum: " + formatDateTime);
        String vaccinationType = vaccinationsList.get(vaccinationsList.size() - 1).getVaccinationType();
        System.out.println("A beadott oltás típusa: " + vaccinationType);
        System.out.println("A mai nappal rögzítettem a 2. " + vaccinationType +" típusú oltás beadását!");

        Vaccinations vaccinations = new Vaccinations(citizens.getCitizenId(), dateTime, Status.SUCCESFUL.toString(), "", vaccinationType);
        vaccinationDao.saveVaccination(vaccinations);

        CitizensDao citizensDao = new CitizensDao(main.initializeDB());
        citizensDao.updateCitizens(citizens.getCitizenId(), dateTime, citizens.getNumberOfVaccination() + 1);
        main.menu();
    }

    public void vaccinationDosage(Citizens citizens) {

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = dateTime.format(formatter);

        VaccinesType vaccinesType;

        vaccinesType = chooseVaccine();
        System.out.println("A dátum: " + formatDateTime);
        System.out.println("A beadott oltás típusa: " + vaccinesType);

        Main main = new Main();
        VaccinationDao vaccinationDao = new VaccinationDao(main.initializeDB());
        Vaccinations vaccinations = new Vaccinations(citizens.getCitizenId(), dateTime, Status.SUCCESFUL.toString(), "", vaccinesType.toString());
        vaccinationDao.saveVaccination(vaccinations);

        CitizensDao citizensDao = new CitizensDao(main.initializeDB());
        citizensDao.updateCitizens(citizens.getCitizenId(), dateTime, citizens.getNumberOfVaccination() + 1);
        main.menu();
    }

    private VaccinesType chooseVaccine() {
        Scanner scanner = new Scanner(System.in);
        List<VaccinesType> vaccinesTypes = new ArrayList<>();
        int i = 1;
        int type;

        for (VaccinesType vaccinesType : VaccinesType.values()) {
            if (vaccinesType.getAvailable()) {
                System.out.println(i + ". - " + vaccinesType);
                vaccinesTypes.add(vaccinesType);
                i++;
            }
        }
        System.out.println("Kérem az oltóanyag típusát: ");
        type = scanner.nextInt();
        scanner.nextLine();
        return vaccinesTypes.get(type - 1);
    }
}
