package covid19;

public enum VaccinesType {
    PFIZER_BIONTECH("EU", true), MODERNA("EU", true), ASTRA_ZENECA("EU", true), JANSSEN("EU", false), CURE_VAC("EU", false), SZPUTNYIK("OTHER", true), SINOPHARM("OTHER", true);

    private final String source;
    private final Boolean available;

    VaccinesType(String source, Boolean available) {
        this.source = source;
        this.available = available;
    }

    public String getSource() {
        return source;
    }

    public Boolean getAvailable() {
        return available;
    }
}
