package covid19;

import java.time.LocalDateTime;

public class Citizens {
    private int citizenId;
    private String citizenName;
    private String zip;
    private int age;
    private String email;
    private String taj;
    private int numberOfVaccination;
    private LocalDateTime lastVaccination;

    public Citizens(String citizenName, String zip, int age, String email, String taj) {
        this.citizenName = citizenName;
        this.zip = zip;
        this.age = age;
        this.email = email;
        this.taj = taj;
    }

    public Citizens(int citizenId, String citizenName, String zip, int age, String email, String taj, int numberOfVaccination, LocalDateTime lastVaccination) {
        this.citizenId = citizenId;
        this.citizenName = citizenName;
        this.zip = zip;
        this.age = age;
        this.email = email;
        this.taj = taj;
        this.numberOfVaccination = numberOfVaccination;
        this.lastVaccination = lastVaccination;
    }

    public int getCitizenId() {
        return citizenId;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public String getZip() {
        return zip;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getTaj() {
        return taj;
    }

    public int getNumberOfVaccination() {
        return numberOfVaccination;
    }

    public LocalDateTime getLastVaccination() {
        return lastVaccination;
    }

    public void setCitizenId(int citizenId) {
        this.citizenId = citizenId;
    }

    public void setNumberOfVaccination(int numberOfVaccination) {
        this.numberOfVaccination = numberOfVaccination;
    }

    public void setLastVaccination(LocalDateTime lastVaccination) {
        this.lastVaccination = lastVaccination;
    }

    @Override
    public String toString() {
        return "Citizens{" +
                "citizenId=" + citizenId +
                ", citizenName='" + citizenName + '\'' +
                ", zip='" + zip + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", taj='" + taj + '\'' +
                ", numberOfVaccination=" + numberOfVaccination +
                ", lastVaccination=" + lastVaccination +
                '}';
    }
}
