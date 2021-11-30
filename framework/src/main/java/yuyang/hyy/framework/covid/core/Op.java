package yuyang.hyy.framework.covid.core;

import java.util.List;

/**
 * Four operations that can be applied onto dataset.
 */
public enum Op implements Operation{
    PERCENTAGE_CHANGE {
        @Override
        public DataSet processData(DataSet dataSet) {
            List<DataGroup> groups = dataSet.getGroups();
            DataSet result = new DataSet();
            for (DataGroup group : groups) {
                List<DataPoint> points = group.getData();
                DataGroup newGroup = new DataGroup(group.getTopic(), group.getCategoryName());
                for (int i = 1; i < points.size(); i++) {
                    DataPoint newP = DataPoint.getPercentageChange(points.get(i-1), points.get(i));
                    newGroup.addData(newP);
                }
                result.addData(newGroup);
            }
            return result;
        }

        @Override
        public String getName() {
            return "PERCENTAGE_CHANGE";
        }
    },
    DIFFERENCE {
        @Override
        public DataSet processData(DataSet dataSet) {
            List<DataGroup> groups = dataSet.getGroups();
            DataSet result = new DataSet();
            for (DataGroup group : groups) {
                List<DataPoint> points = group.getData();
                DataGroup newGroup = new DataGroup(group.getTopic(), group.getCategoryName());
                for (int i = 1; i < points.size(); i++) {
                    DataPoint newP = DataPoint.getDifference(points.get(i-1), points.get(i));
                    newGroup.addData(newP);
                }
                result.addData(newGroup);
            }
            return result;
        }

        @Override
        public String getName() {
            return "DIFFERENCE";
        }
    },
    PERCENTAGE {
        @Override
        public DataSet processData(DataSet dataSet) {
            List<DataGroup> groups = dataSet.getGroups();
            DataSet result = new DataSet();
            for (DataGroup group : groups) {
                List<DataPoint> points = group.getData();
                DataGroup newGroup = new DataGroup(group.getTopic(), group.getCategoryName());
                int sum = 0;
                for (DataPoint p : points) {
                    sum += p.getY();
                }
                for (DataPoint p : points) {
                    DataPoint newP = new DataPoint(p.getX(), p.getY()/sum);
                    newGroup.addData(newP);
                }
                result.addData(newGroup);
            }
            return result;
        }

        @Override
        public String getName() {
            return "PERCENTAGE";
        }
    },
    ORIGIN {
        @Override
        public DataSet processData(DataSet dataSet) {
            return null;
        }

        @Override
        public String getName() {
            return "ORIGIN";
        }
    };
}
