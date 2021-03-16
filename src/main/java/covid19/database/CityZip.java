package covid19.database;

public class CityZip {
    public String zip;
    private String city;

    public CityZip(String zip, String city) {
        this.zip = zip;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    @Override
    public String toString() {
        return getZip() + " " + getCity();
    }
}
