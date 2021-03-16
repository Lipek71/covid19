package covid19;

import java.time.LocalDateTime;

public class Vaccinations {
    private int vaccinationId;
    private int citizenId;
    private LocalDateTime vaccinationDate;
    private String status;
    private String note;
    private String vaccinationType;

    public Vaccinations(int citizenId, LocalDateTime vaccinationDate, String status, String note, String vaccinationType) {
        this.citizenId = citizenId;
        this.vaccinationDate = vaccinationDate;
        this.status = status;
        this.note = note;
        this.vaccinationType = vaccinationType;
    }

    public Vaccinations(int vaccinationId, int citizenId, LocalDateTime vaccinationDate, String status, String note, String vaccinationType) {
        this.vaccinationId = vaccinationId;
        this.citizenId = citizenId;
        this.vaccinationDate = vaccinationDate;
        this.status = status;
        this.note = note;
        this.vaccinationType = vaccinationType;
    }

    public int getVaccinationId() {
        return vaccinationId;
    }

    public int getCitizenId() {
        return citizenId;
    }

    public LocalDateTime getVaccinationDate() {
        return vaccinationDate;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public String getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationId(int vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    @Override
    public String toString() {
        return "Vaccinations{" +
                "vaccinationId=" + vaccinationId +
                ", citizenId=" + citizenId +
                ", vaccinationDate=" + vaccinationDate +
                ", status='" + status + '\'' +
                ", note='" + note + '\'' +
                ", vaccinationType='" + vaccinationType + '\'' +
                '}';
    }
}
