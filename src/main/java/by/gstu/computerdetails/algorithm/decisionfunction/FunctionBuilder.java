package by.gstu.computerdetails.algorithm.decisionfunction;

import by.gstu.computerdetails.algorithm.NormalizeObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class FunctionBuilder {

    private List<List<NormalizeObject>> projects;
    private NormalizeObject projectedObject;

    public FunctionBuilder(List<List<NormalizeObject>> typicalProjects,
                           NormalizeObject projectedObject) {
        this.projects = typicalProjects;
        this.projectedObject = projectedObject;
    }

    public List<List<NormalizeObject>> getProjects() {
        return projects;
    }

    public NormalizeObject getProjectedObject() {
        return projectedObject;
    }

    protected int getFeaturesCount() {
        final int FEATURES_COUNT = 5;
        return FEATURES_COUNT;
    }

    protected double[] getMaxValues() {
        final int FEATURES_COUNT = getFeaturesCount();

        double[] maxValues = new double[FEATURES_COUNT];
        for (int i = 0; i < maxValues.length; i++) {
            maxValues[i] = Integer.MIN_VALUE;
        }

        for (List<NormalizeObject> projectList : projects) {
            for (NormalizeObject project : projectList) {
                double[] features = project.getSignValues();
                for (int i = 0; i < FEATURES_COUNT; i++) {
                    if (maxValues[i] < features[i]) {
                        maxValues[i] = features[i];
                    }
                }
            }
        }

        return maxValues;
    }

    protected double[][] getDimensionlessValues() {
        final int FEATURES_COUNT = getFeaturesCount();

        double[][] values = new double[projects.get(0).size() * projects.size()][FEATURES_COUNT];
        double[] maxValues = getMaxValues();

        for (int i = 0; i < projects.size(); i++) {
            for (int j = 0; j < projects.get(i).size(); j++) {
                int index = projects.get(0).size() * i + j;
                double[] features = projects.get(i).get(j).getSignValues();

                for (int k = 0; k < FEATURES_COUNT; k++) {
                    values[index][k] = features[k] / maxValues[k];
                }
            }
        }

        return values;
    }

    protected double[][] getHypotheticFeatureValues() {
        final int FEATURES_COUNT = getFeaturesCount();

        double[][] values = new double[projects.size()][FEATURES_COUNT];
        double[][] dimValues = getDimensionlessValues();

        for (int i = 0; i < projects.size(); i++) {
            double[] sums = new double[FEATURES_COUNT];

            for (int j = 0; j < projects.get(i).size(); j++) {
                int index = projects.get(0).size() * i + j;
                for (int k = 0; k < FEATURES_COUNT; k++) {
                    sums[k] += dimValues[index][k];
                }
            }

            for (int j = 0; j < sums.length; j++) {
                values[i][j] = sums[j] / projects.size();
            }
        }

        return values;
    }

    protected double[] getDimensionlessValues(NormalizeObject project) {
        final int FEATURES_COUNT = getFeaturesCount();

        double[] features = project.getSignValues();

        double[] values = new double[features.length];
        double[] maxValues = getMaxValues();

        for (int i = 0; i < FEATURES_COUNT; i++) {
            values[i] = features[i] / maxValues[i];
        }
        return values;
    }

    public List<Double> build() {
        /*double[][] hypotheticValues = getHypotheticFeatureValues();
        double[] X = getDimensionlessValues(projectedObject);

        double D[] = new double[hypotheticValues.length];
        for (int i = 0; i < hypotheticValues.length; i++) {
            double Pi = 0;
            double Di = 0;
            for (int j = 0; j < hypotheticValues[0].length; j++) {
                Di += 2 * hypotheticValues[i][j] * X[j];
                Pi += hypotheticValues[i][j] * hypotheticValues[i][j];
            }
            D[i] = Di - Pi;
        }
        return new ArrayList<Double>().add(D);*/
        throw new NotImplementedException();
    }

}
