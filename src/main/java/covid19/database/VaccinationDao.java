package covid19.database;

import covid19.Citizens;
import covid19.Vaccination;
import covid19.Vaccinations;
import covid19.VaccinesType;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VaccinationDao {

    private final DataSource ds;

    public VaccinationDao(DataSource ds) {
        this.ds = ds;
    }

    public Vaccinations saveVaccination(Vaccinations vaccinations) {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO vaccinations(citizen_id, vaccination_date, status, note, vaccination_type) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, vaccinations.getCitizenId());
            stmt.setTimestamp(2, Timestamp.valueOf(vaccinations.getVaccinationDate()));
            stmt.setString(3, vaccinations.getStatus());
            stmt.setString(4, vaccinations.getNote());
            stmt.setString(5, vaccinations.getVaccinationType());
            stmt.executeUpdate();

            int returnId = getIdFromDb(stmt);

            vaccinations.setVaccinationId(returnId);
            return vaccinations;

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

    public List<Vaccinations> findVaccinationById(int citizenId) {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vaccinations WHERE citizen_id = ? ORDER BY vaccination_date DESC")
        ) {
            stmt.setInt(1, citizenId);
            return readVaccinationById(stmt);

        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
    }

    private List<Vaccinations> readVaccinationById(PreparedStatement stmt) {

        List<Vaccinations> vaccinations = new ArrayList<>();

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int vaccinationId = rs.getInt("vaccination_id");
                int citizenId = rs.getInt("citizen_id");
                LocalDateTime vaccinationDate = rs.getTimestamp("vaccination_date").toLocalDateTime();
                String status = rs.getString("status");
                String note = rs.getString("note");
                String vaccinationType = rs.getString("vaccination_type");
                vaccinations.add(new Vaccinations(vaccinationId,citizenId,vaccinationDate,status,note,vaccinationType));
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
        return vaccinations;
    }
}
