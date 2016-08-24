package by.gstu.computerdetails.algorithm;

import by.gstu.computerdetails.entity.Cluster;

import java.util.Arrays;
import java.util.List;

public class K_Means {

    public List<Cluster> divide(List<NormalizeObject> objects) {
        int objectCount = objects.size();
        if (objectCount < 1) {
            throw new IllegalArgumentException("List of objects can't be empty.");
        }

        int signCount = objects.get(0).signCount();
        double[][] normalValues = new double[objectCount][signCount];

        double[] maxSigns = new double[signCount];
        double[] signSums = new double[signCount];

        for (int i = 0; i < maxSigns.length; i++) {
            maxSigns[i] = Double.MIN_VALUE;
        }

        for (NormalizeObject object : objects) {
            double[] signValues = object.getSignValues();
            for (int i = 0; i < signValues.length; i++) {
                if (signValues[i] > maxSigns[i]) {
                    maxSigns[i] = signValues[i];
                }
                signSums[i] += signValues[i];
            }
        }

        double[] avgValues = new double[signCount];
        double[] sigma = new double[signCount];


        for (int i = 0; i < objects.size(); i++) {
            double[] signValues = objects.get(i).getSignValues();
            for (int j = 0; j < signValues.length; j++) {
                normalValues[i][j] = signValues[j] / maxSigns[j];
                avgValues[j] = signValues[j] / signSums[j];
                //sigma[j] = Math.sqrt()
            }
        }

        //Divide om max
        System.out.println(Arrays.asList(normalValues));


        for (NormalizeObject object : objects) {
            double[] signValues = object.getSignValues();
            for (int i = 0; i < signValues.length; i++) {

            }
        }


        return null;
    }

    private double[] m(List<NormalizeObject> objects, double[] avgValues) {
        double[] sums = new double[avgValues.length];

        for (NormalizeObject object : objects) {
            double[] signValues = object.getSignValues();
            for (int i = 0; i < signValues.length; i++) {
                double val = signValues[i] - avgValues[i];
                sums[i] = sums[i] + val * val;
            }
        }

        return sums;
    }

}
