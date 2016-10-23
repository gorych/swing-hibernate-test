package by.gstu.computerdetails.algorithm;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmHelper {

    private AlgorithmHelper() {
    }

    public static List<double[]> normalizeByMax(List<? extends NormalizeObject> objects, double[] maxValues) {
        final int SIGN_COUNT = objects.get(0).signCount();

        List<double[]> result = new ArrayList<double[]>();
        for (NormalizeObject object : objects) {
            double[] signs = object.getSignValues();
            double[] values_i = new double[SIGN_COUNT];
            for (int j = 0; j < SIGN_COUNT; j++) {
                values_i[j] = signs[j] / maxValues[j];
            }
            result.add(values_i);
        }
        return result;
    }

    public static double[] findMaxSignValue(List<? extends NormalizeObject> objects) {
        final int SIGN_COUNT = objects.get(0).signCount();

        double[] maxValues = new double[SIGN_COUNT];
        for (int i = 0; i < SIGN_COUNT; i++) {
            maxValues[i] = Double.MIN_VALUE;
        }

        for (NormalizeObject object : objects) {
            double[] signs = object.getSignValues();
            for (int i = 0; i < SIGN_COUNT; i++) {
                if (signs[i] > maxValues[i]) {
                    maxValues[i] = signs[i];
                }
            }
        }
        return maxValues;
    }

}
