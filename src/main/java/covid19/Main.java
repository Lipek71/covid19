package covid19;

import com.mysql.cj.jdbc.MysqlDataSource;
import covid19.database.CitiesDao;
import covid19.database.CityZip;
import covid19.database.LoadZip;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class Main {

    public DataSource initializeDB(){
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/covid19?useUnicode=true");
        ds.setUser("covid19");
        ds.setPassword("covid19");
        return ds;
    }

    public void menu() {
        String menu = """
                1. Regisztráció
                2. Tömeges regisztráció
                3. Generálás
                4. Oltás
                5. Oltás meghiúsulás
                6. Riport
                X. Kilépés
                
                I. Irányítószámok feltöltése""";
        String menuPont;
        Scanner scanner = new Scanner(System.in);

        System.out.println(menu);
        System.out.print("Kérem a feladat számát: ");
        menuPont = scanner.nextLine();
        switch (menuPont.toLowerCase()) {
            case "1":
                Registration registration = new Registration();
                registration.registration();
                break;
            case "2":
                MassRegistration massRegistration = new MassRegistration();
                massRegistration.massRegistration();
                break;
            case "3":
                Generate generate = new Generate();
                generate.generate();
                break;
            case "4":
                Vaccination vaccination = new Vaccination();
                vaccination.vaccination();
                break;
            case "5":
                VaccinationFail vaccinationFail = new VaccinationFail();
                vaccinationFail.vaccinationFail();
                break;
            case "6":
                Report report = new Report();
                report.report();
                break;
            case "i":
                LoadZip loadZip = new LoadZip();
                InputStream is = LoadZip.class.getResourceAsStream("/iranyitoszamok.csv");
                List<CityZip> cityZips = loadZip.LoadZipFromFile(is);

                CitiesDao citiesDao = new CitiesDao(initializeDB());
                citiesDao.saveCityZips(cityZips);

                menu();
                break;
            case "x": break;
            default:
                System.out.println("Érvénytelen választás, kérem próbálja újra!");
                menu();
        }

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.menu();
    }
}
