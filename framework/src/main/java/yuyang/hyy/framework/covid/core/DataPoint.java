package yuyang.hyy.framework.covid.core;

/**
 * A class to represent a data point on the display plot.
 */
public class DataPoint {
    private String x;
    private double y;

    /**
     * Constructor to create a data point (string, double).
     * @param x the x coordinate (string)
     * @param y the y coordinate (double)
     */
    public DataPoint(String x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X coordinate setter.
     * @param x new x coordinate
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * Y coordinate setter.
     * @param y new y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get the difference of y of point b, comparing to point a. The x label would keep as the second point.
     * @param a first point
     * @param b second point right after a
     * @return new point representing the difference between a and b, can be used on a difference plot
     */
    public static DataPoint getDifference(DataPoint a, DataPoint b) {
        return new DataPoint(b.x, b.y - a.y);
    }

    /**
     * Get the percentage rate of change comparing to the previous point.
     * The x label would keep as the second point.
     * @param a first point
     * @param b second point right after a
     * @return new point representing the percentage change between a and b, can be used on a derivative plot
     */
    public static DataPoint getPercentageChange(DataPoint a, DataPoint b) {
        if (a.y == 0) {
            return new DataPoint(b.x, 0);
        }
        double rate = (b.y - a.y) / a.y;
        return new DataPoint(b.x, rate);
    }

    /**
     * X coordinate getter.
     * @return x coordinate
     */
    public String getX() {
        return x;
    }

    /**
     * Y coordinate getter.
     * @return y coordinate
     */
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "x='" + x + '\'' +
                ", y=" + y +
                '}';
    }
}
