package covid19.database;

import covid19.Citizens;
import covid19.ReportData;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitizensDao {

    private final DataSource ds;

    public CitizensDao(DataSource dataSource) {
        this.ds = dataSource;
    }

    public Citizens saveCitizens(Citizens citizens) {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO citizens(citizen_name, zip, age, email, taj) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, citizens.getCitizenName());
            stmt.setString(2, citizens.getZip());
            stmt.setInt(3, citizens.getAge());
            stmt.setString(4, citizens.getEmail());
            stmt.setString(5, citizens.getTaj());
            stmt.executeUpdate();

            int returnId = getIdFromDb(stmt);

            citizens.setCitizenId(returnId);
            return citizens;

        } catch (SQLException se) {
            throw new IllegalStateException("Nem sikerült beilleszteni a sort!");
        }
    }

    private int getIdFromDb(PreparedStatement stmt) throws SQLException {
        try (
                ResultSet rs = stmt.getGeneratedKeys()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new IllegalStateException("Nem kaptam vissza értéket!");
        }
    }

    public void saveCitizensMass(List<Citizens> citizens) {
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            saveCitizensMassToDB(citizens, conn);

        } catch (SQLException se) {
            throw new IllegalStateException("Nem sikerült a beillesztés!");
        }
    }

    private void saveCitizensMassToDB(List<Citizens> citizens, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO citizens(citizen_name, zip, age, email, taj) VALUES (?,?,?,?,?)")) {
            for (Citizens citizens1 : citizens) {
                if (citizens.isEmpty()) {
                    throw new IllegalStateException("Nincs adat!");
                }
                stmt.setString(1, citizens1.getCitizenName());
                stmt.setString(2, citizens1.getZip());
                stmt.setInt(3, citizens1.getAge());
                stmt.setString(4, citizens1.getEmail());
                stmt.setString(5, citizens1.getTaj());
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (IllegalArgumentException | SQLException e) {
            conn.rollback();
        }
    }

    public List<Citizens> findCitizensByZip(String zip) {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM citizens WHERE zip = ? AND number_of_vaccination < 2 AND (DATEDIFF(CURDATE(),CAST(last_vaccination AS DATE)) > 15 OR last_vaccination IS NULL) ORDER BY age DESC LIMIT 16")
        ) {
            stmt.setString(1, zip);
            return readCitizensByZip(stmt);

        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
    }

    private List<Citizens> readCitizensByZip(PreparedStatement stmt) {

        List<Citizens> citizens = new ArrayList<>();

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String citizenName = rs.getString("citizen_name");
                String zip = rs.getString("zip");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                String taj = rs.getString("taj");
                citizens.add(new Citizens(citizenName, zip, age, email, taj));
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
        return citizens;
    }

    public List<Citizens> findCitizensByTaj(String taj) {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM citizens WHERE taj = ?")
        ) {
            stmt.setString(1, taj);
            return readCitizensByTaj(stmt);

        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
    }

    private List<Citizens> readCitizensByTaj(PreparedStatement stmt) {

        List<Citizens> citizens = new ArrayList<>();

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int citizenId = rs.getInt("citizen_id");
                String citizenName = rs.getString("citizen_name");
                String zip = rs.getString("zip");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                String taj = rs.getString("taj");
                int numberOfVaccination = rs.getInt("number_of_vaccination");
                LocalDateTime lastVaccination = rs.getTimestamp("last_vaccination").toLocalDateTime();
                citizens.add(new Citizens(citizenId,citizenName, zip, age, email, taj,numberOfVaccination,lastVaccination));
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
        return citizens;
    }

    public void updateCitizens(int citizensId, LocalDateTime dateTime, int numberOfVaccination) {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE citizens SET number_of_vaccination = ?, last_vaccination = ? WHERE citizen_id = ? ")) {
            stmt.setInt(1, numberOfVaccination);
            stmt.setTimestamp(2, Timestamp.valueOf(dateTime));
            stmt.setInt(3, citizensId);
            stmt.executeUpdate();

        } catch (SQLException se) {
            throw new IllegalStateException("Nem sikerült beilleszteni a sort!");
        }
    }





    public List<ReportData> vaccinationStat() {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT zip, (SELECT COUNT(*) FROM citizens nulla WHERE number_of_vaccination = 0 AND nulla.zip = citizens.zip) nulla, (SELECT COUNT(*) FROM citizens egy WHERE number_of_vaccination = 1 AND egy.zip = citizens.zip) egy, (SELECT COUNT(*) FROM citizens tobb WHERE number_of_vaccination >= 2 AND tobb.zip = citizens.zip) tobb FROM citizens ORDER BY zip;")
        ) {
            return vaccinationStatRead(stmt);

        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
    }

    private List<ReportData> vaccinationStatRead(PreparedStatement stmt) {

        List<ReportData> reportDataList = new ArrayList<>();

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int zip = rs.getInt("zip");
                int nulla = rs.getInt("nulla");
                int egy = rs.getInt("egy");
                int tobb = rs.getInt("tobb");
                reportDataList.add(new ReportData(zip, nulla, egy, tobb));
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
        return reportDataList;
    }


}
