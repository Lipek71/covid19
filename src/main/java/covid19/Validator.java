package covid19;

import covid19.database.CitiesDao;
import covid19.database.CityZip;

import java.util.List;
import java.util.Scanner;

public class Validator {
    public boolean citizenName(String citizenName) {
        try {
            if (citizenName == null || citizenName.isEmpty() || citizenName.length() < 2) {
                throw new IllegalArgumentException("Hibás névmegadás");
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Hibás névmegadás!");
            return false;
        }
        return true;
    }

    public boolean zip(String zip) {
        try {
            if (zip == null || 4 != zip.length()) {
                throw new IllegalArgumentException("Hibás karakterszám!");
            }
            Integer.parseInt(zip);
        } catch (NumberFormatException nfe) {
            System.out.println("Csak számjegyek lehetnek");
            return false;
        } catch (IllegalArgumentException iae) {
            System.out.println("Nem megfelelő karakterszám!");
            return false;
        }
        List<CityZip> cities;
        Main main = new Main();
        CitiesDao citiesDao = new CitiesDao(main.initializeDB());
        cities = citiesDao.findCityByZip(zip);
        if (cities.isEmpty()){
            System.out.println("Nincs ilyen település, kérem adjon meg új irányítószámot!");
            return false;
        }
        for (CityZip cityZip : cities){
            System.out.println(cityZip);
        }
            return true;
    }

    public boolean age(String age) {
        try {
            if (Integer.parseInt(age) < 10 || Integer.parseInt(age) > 150) {
                throw new IllegalArgumentException("Hibás életkor!");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Csak számjegyek lehetnek");
            return false;
        } catch (IllegalArgumentException iae) {
            System.out.println("Hibás életkor!");
            return false;
        }
        return true;
    }

    public boolean email(String email) {
        try {
            if (email == null || 3 > email.length() || !email.contains("@") || !email.contains(".")) {
                throw new IllegalArgumentException("Hibás email cím");
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Hibás email cím!");
            return false;
        }
        String passEmail;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Kérem ismételje meg az email címet: ");
            passEmail = scanner.nextLine();
            if (!email.equals(passEmail)) {
                System.out.println("A két email cím nem egyezik, kérem próbálja még egyszer! ");
            }
        } while (!email.equals(passEmail));
        return true;
    }

    public boolean taj(String taj) {
        final String NUMBERS = "0123456789";
        try {
            if (taj.length() != 10) {
                throw new IllegalArgumentException("Nem megfelelő számú karakter!");
            }
            for (int i = 0; i < taj.length(); i++) {
                if (!NUMBERS.contains(taj.substring(i, i + 1))) {
                    throw new NumberFormatException("Csak számok lehetnek!");
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Csak számjegyek lehetnek");
            return false;
        } catch (IllegalArgumentException iae) {
            System.out.println("Nem megfelelő karakterszám!");
            return false;
        }

        return tajCdvCheck(taj);
    }

    private boolean tajCdvCheck(String taj) {
        try {
            int checksum = 0;
            for (int i = 0; i < taj.length() - 1; i++) {
                checksum += Integer.parseInt(taj.substring(i, i + 1)) * (i + 1);
            }
            if (checksum % 11 == Integer.parseInt(taj.substring(9, 10))) {
                return true;
            } else {
                throw new IllegalArgumentException("Nem valós TAJ szám!");
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Nem valós TAJ szám!");
            return false;
        }
    }
}
