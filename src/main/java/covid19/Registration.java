package covid19;

import covid19.database.CitizensDao;

import java.util.Scanner;

public class Registration {
    public void registration() {
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator();

        String citizenName = inputName(scanner, validator);
        String zip = inputZip(scanner, validator);
        int age = inputAge(scanner, validator);
        String email = inputEmail(scanner, validator);
        String taj = inputTaj(scanner, validator);

        Citizens citizens = new Citizens(citizenName, zip, age, email, taj);
        Main main = new Main();

        CitizensDao citizensDao = new CitizensDao(main.initializeDB());
        System.out.println(citizensDao.saveCitizens(citizens));

        main.menu();
    }

    private String inputName(Scanner scanner, Validator validator) {
        String citizenName;
        do {
            System.out.print("Kérem a regisztrálandó nevet: ");
            citizenName = scanner.nextLine();
        } while (!validator.citizenName(citizenName));
        return citizenName;
    }

    private String inputZip(Scanner scanner, Validator validator) {
        String zip;
        do {
            System.out.print("Kérem az értesítési hely irányítószámát: ");
            zip = scanner.nextLine();
        } while (!validator.zip(zip));
        return zip;
    }

    private int inputAge(Scanner scanner, Validator validator) {
        String age;
        do {
            System.out.print("Kérem regisztrálandó életkorát: ");
            age = scanner.nextLine();
        } while (!validator.age(age));
        return Integer.parseInt(age);
    }

    private String inputEmail(Scanner scanner, Validator validator) {
        String email;
        do {
            System.out.print("Kérem a regisztrálandó email címét: ");
            email = scanner.nextLine();
        } while (!validator.email(email));
        return email;
    }

    private String inputTaj(Scanner scanner, Validator validator) {
        String taj;
        do {
            System.out.print("Kérem a regisztrálandó TAJ számát: ");
            taj = scanner.nextLine();
        } while (!validator.taj(taj));
        return taj;
    }
}
