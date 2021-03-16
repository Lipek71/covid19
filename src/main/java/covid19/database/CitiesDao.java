package covid19.database;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitiesDao {

    private final DataSource ds;

    public CitiesDao(DataSource ds) {
        this.ds = ds;
    }

    public void saveCityZips(List<CityZip> cityZips) {
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            deleteCityZipsFromDB(conn);
            setAutoIncrementInCities(conn);
            saveCityZipsToDB(cityZips, conn);

        } catch (SQLException se) {
            throw new IllegalStateException("Nem sikerült a beillesztés!");
        }
    }

    private void deleteCityZipsFromDB(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM `covid19`.`cities` WHERE 1 = 1")) {
            stmt.executeUpdate();
            conn.commit();
        } catch (IllegalArgumentException | SQLException e) {
            conn.rollback();
        }
    }

    private void setAutoIncrementInCities(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("ALTER TABLE cities  AUTO_INCREMENT=1")) {
            stmt.executeUpdate();
            conn.commit();
        } catch (IllegalArgumentException | SQLException e) {
            conn.rollback();
        }
    }

    private void saveCityZipsToDB(List<CityZip> cityZips, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO cities(zip, city) VALUES (?,?)")) {
            for (CityZip cityZip : cityZips) {
                if (cityZips.isEmpty()) {
                    throw new IllegalStateException("Nincs adat!");
                }
                stmt.setString(1, cityZip.getZip());
                stmt.setString(2, cityZip.getCity());
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (IllegalArgumentException | SQLException e) {
            conn.rollback();
        }
    }

    public List<CityZip> findCityByZip(String zip) {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cities WHERE zip = ?")
        ) {
            stmt.setString(1, zip);
            return readCitiesFromDb(stmt);

        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
    }

    private List<CityZip> readCitiesFromDb(PreparedStatement stmt) {

        List<CityZip> cities = new ArrayList<>();

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String zip = rs.getString("zip");
                String city = rs.getString("city");
                cities.add(new CityZip(zip, city));
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can't query", sqle);
        }
        return cities;

    }

}
