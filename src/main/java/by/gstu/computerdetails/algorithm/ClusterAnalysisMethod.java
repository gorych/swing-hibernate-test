package by.gstu.computerdetails.algorithm;

import java.util.ArrayList;
import java.util.List;

public class ClusterAnalysisMethod {

    protected final int OBJECT_COUNT;
    protected final int SIGN_COUNT;

    protected List<? extends NormalizeObject> objects;

    public ClusterAnalysisMethod(List<? extends NormalizeObject> objects) {
        if (objects.size() < 1) {
            throw new IllegalArgumentException("Object list is empty");
        }
        this.objects = objects;

        OBJECT_COUNT = objects.size();
        SIGN_COUNT = objects.get(0).signCount();
    }

    public double[][] normalize() {
        double[] A = new double[SIGN_COUNT];
        for (int i = 0; i < SIGN_COUNT; i++) {
            double sum = 0;
            for (NormalizeObject object : objects) {
                double[] signs = object.getSignValues();
                sum += signs[i];
            }
            A[i] = sum / OBJECT_COUNT;
        }

        double[] sigma = new double[SIGN_COUNT];
        for (int i = 0; i < SIGN_COUNT; i++) {
            double sum = 0;
            for (NormalizeObject object : objects) {
                double[] signs = object.getSignValues();
                sum += (signs[i] - A[i]) * (signs[i] - A[i]);
            }
            sigma[i] = Math.sqrt(sum / (OBJECT_COUNT - 1));
        }

        double[][] normalizeValues = new double[OBJECT_COUNT][SIGN_COUNT];
        for (int i = 0; i < OBJECT_COUNT; i++) {
            double[] signs = objects.get(i).getSignValues();
            for (int j = 0; j < SIGN_COUNT; j++) {
                normalizeValues[i][j] = (signs[j] - A[j]) / sigma[j];
            }
        }

        return normalizeValues;
    }

    protected void printMatrix(double[][] normalizeByMax) {
        for (double[] doubles : normalizeByMax) {
            for (double aDouble : doubles) {
                System.out.print(aDouble + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    protected void print(double[] maxValues) {
        for (double maxValue : maxValues) {
            System.out.println(maxValue + "; ");
        }
        System.out.println();
    }

    protected List<double[]> normalizeByMax(List<? extends NormalizeObject> objects, double[] maxValues) {
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

    protected double[] findMaxSignValue(List<? extends NormalizeObject> objects) {
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