package covid19;

public class ReportData {
    int zip;
    int nulla;
    int egy;
    int tobb;

    public ReportData(int zip, int nulla, int egy, int tobb) {
        this.zip = zip;
        this.nulla = nulla;
        this.egy = egy;
        this.tobb = tobb;
    }

    @Override
    public String toString() {
        return  "zip=" + zip +
                ", nulla=" + nulla +
                ", egy=" + egy +
                ", tobb=" + tobb +
                "\n";
    }
}
