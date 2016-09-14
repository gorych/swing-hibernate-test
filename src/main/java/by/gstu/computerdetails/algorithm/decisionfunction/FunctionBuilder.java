package by.gstu.computerdetails.algorithm.decisionfunction;

import by.gstu.computerdetails.algorithm.NormalizeObject;

public class FunctionBuilder {

    private NormalizeObject[][] projects;
    private NormalizeObject projectedObject;

    public FunctionBuilder(NormalizeObject[][] typicalProjects,
                           NormalizeObject projectedObject) {
        this.projects = typicalProjects;
        this.projectedObject = projectedObject;
    }

    public NormalizeObject[][] getProjects() {
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

        for (NormalizeObject[] projectList : projects) {
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

        double[][] values = new double[projects[0].length * projects.length][FEATURES_COUNT];
        double[] maxValues = getMaxValues();

        for (int i = 0; i < projects.length; i++) {
            for (int j = 0; j < projects[i].length; j++) {
                int index = projects[0].length * i + j;
                double[] features = projects[i][j].getSignValues();

                for (int k = 0; k < FEATURES_COUNT; k++) {
                    values[index][k] = features[k] / maxValues[k];
                }
            }
        }

        return values;
    }

    protected double[][] getHypotheticFeatureValues() {
        final int FEATURES_COUNT = getFeaturesCount();

        double[][] values = new double[projects.length][FEATURES_COUNT];
        double[][] dimValues = getDimensionlessValues();

        for (int i = 0; i < projects.length; i++) {
            double[] sums = new double[FEATURES_COUNT];

            for (int j = 0; j < projects[i].length; j++) {
                int index = projects[0].length * i + j;
                for (int k = 0; k < FEATURES_COUNT; k++) {
                    sums[k] += dimValues[index][k];
                }
            }

            for (int j = 0; j < sums.length; j++) {
                values[i][j] = sums[j] / projects.length;
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

    public double[] build() {
        double[][] hypotheticValues = getHypotheticFeatureValues();
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
        return D;
    }

}
