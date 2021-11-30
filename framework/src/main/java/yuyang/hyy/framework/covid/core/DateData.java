package yuyang.hyy.framework.covid.core;

/**
 * Daily Covid Data from source
 */
public class DateData {
    private final int date;
    private final int positiveDaily;
    private final int hospitalizedDaily;
    private final int deathDaily;

    /**
     * Constructor, taking in all integers necessary.
     * @param date int representing a date
     * @param p positive cases on this day
     * @param h hospitalized cases on this day
     * @param d death cases on this day
     */
    public DateData(int date, int p, int h, int d) {
        this.date = date;
        positiveDaily = p;
        hospitalizedDaily = h;
        deathDaily = d;
    }

    /**
     * Get date of the data
     * @return date of the data
     */
    public int getDate() {
        return date;
    }

    /**
     * get daily death number
     * @return daily number of deaths
     */
    public int getDeathDaily() {
        return deathDaily;
    }

    /**
     * get daily hospitalized number
     * @return daily hospitalized number
     */
    public int getHospitalizedDaily() {
        return hospitalizedDaily;
    }

    /**
     * get positive cases number
     * @return daily positive number of cases
     */
    public int getPositiveDaily() {
        return positiveDaily;
    }

    @Override
    public String toString() {
        return "DateData{" +
                "date(month)=" + date +
                ", positiveDaily=" + positiveDaily +
                ", hospitalizedDaily=" + hospitalizedDaily +
                ", deathDaily=" + deathDaily +
                '}';
    }
}
