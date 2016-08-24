package by.gstu.computerdetails.algorithm;

import java.util.ArrayList;
import java.util.List;

abstract class ClusterAnalysisMethod {

    protected static final int FEATURES_COUNT = 5;

    private List<NormalizeObject> _X;

    protected ClusterAnalysisMethod(List<NormalizeObject> objects) {
        _X = objects;
    }

    protected static double[][] GetDistance(List<double[]> P, List<double[]> X) {
        double[][] distances = new double[P.size()][X.size()];
        for (int i = 0; i < P.size(); i++) {
            for (int j = 0; j < X.size(); j++) {
                double sum = 0;
                for (int k = 0; k < FEATURES_COUNT; k++) {
                    sum += (X.get(j)[k] - P.get(i)[k]) * (X.get(j)[k] - P.get(i)[k]);
                }
                distances[i][j] = Math.sqrt(sum);
            }
        }

        return distances;
    }

    protected static List<List<Integer>> FindObjectClasses(double[][] D) {
        List<List<Integer>> classes = new ArrayList<List<Integer>>(D.length);

        for (int j = 0; j < D[0].length; j++) {
            int number = 0;
            double minP = Double.MAX_VALUE;
            for (int i = 0; i < D.length; i++) {
                if (D[i][j] <= minP) {
                    minP = D[i][j];
                    number = i;
                }
            }

           /* if (classes.get(number) == null) {
                List<Integer> integers = classes.get(number);
                integers = new ArrayList<Integer>();
            }*/

            if (classes.get(number) == null) {

            }
            classes.get(number).add(j);
        }

        return classes;
    }

    public List<NormalizeObject> getObjects() {
        return _X;
    }

    protected double[] GetMaxValues() {
        double[] maxValues = new double[FEATURES_COUNT];

        for (int i = 0; i < maxValues.length; i++) {
            maxValues[i] = Double.MIN_VALUE;
        }
        for (NormalizeObject _object : _X) {
            double[] features = _object.getSignValues();
            for (int i = 0; i < FEATURES_COUNT; i++) {
                if (maxValues[i] < features[i]) {
                    maxValues[i] = features[i];
                }
            }
        }

        return maxValues;
    }

    protected List<double[]> Normalize(List<NormalizeObject> x) {
        List<double[]> values = new ArrayList<double[]>();

        double[] maxValues = GetMaxValues();

        for (NormalizeObject xi : x) {
            double[] features = xi.getSignValues();
            double[] values_i = new double[FEATURES_COUNT];
            for (int k = 0; k < FEATURES_COUNT; k++) {
                values_i[k] = features[k] / maxValues[k];
            }
            values.add(values_i);
        }

        return values;
    }

    public abstract List<List<Integer>> Clustering();
}