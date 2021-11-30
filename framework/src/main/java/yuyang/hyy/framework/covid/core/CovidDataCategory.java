package yuyang.hyy.framework.covid.core;

/**
 * Three categories of the covid data.
 */
public enum CovidDataCategory {
    POSITIVE("Positive") {
        @Override
        public String getName() {
            return "POSITIVE";
        }
    }, DEATH("Death") {
        @Override
        public String getName() {
            return "DEATH";
        }
    }, HOSPITALIZED("Hospitalized") {
        @Override
        public String getName() {
            return "HOSPITALIZED";
        }
    };

    private final String name;

    CovidDataCategory(String name){
        this.name = name;
    }

    abstract String getName();

    @Override
    public String toString() {
        return name;
    }
}
