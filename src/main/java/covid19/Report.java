package covid19;

import covid19.database.CitizensDao;

public class Report {
    public void report(){
        Main main = new Main();
        CitizensDao citizensDao = new CitizensDao(main.initializeDB());
        System.out.println(citizensDao.vaccinationStat());
        main.menu();
    }
}
