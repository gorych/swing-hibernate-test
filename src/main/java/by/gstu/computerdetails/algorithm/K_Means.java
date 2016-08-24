package by.gstu.computerdetails.algorithm;

import by.gstu.computerdetails.entity.Cluster;

import java.util.ArrayList;
import java.util.List;

public class K_Means extends ClusterAnalysisMethod {

    private List<Cluster> clusters;

    public K_Means(List<NormalizeObject> objects, List<Cluster> clusters) {
        super(objects);
        this.clusters = clusters;
    }

    private static boolean IsEqualPrototypes(List<double[]> prevP, List<double[]> curP) {
        for (int i = 0; i < prevP.size(); i++) {
            for (int j = 0; j < prevP.get(i).length; j++) {
                final double EPS = 0.0001;
                if (Math.abs(prevP.get(i)[j] - curP.get(i)[j]) > EPS) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<double[]> GetNewPrototypes(List<List<Integer>> classes, List<double[]> X) {
        List<double[]> prototypes = new ArrayList<double[]>();
        for (List<Integer> clazz : classes) {
            double[] values_i = new double[FEATURES_COUNT];
            if (clazz == null) continue;

            for (int objNumber : clazz) {
                for (int k = 0; k < FEATURES_COUNT; k++) {
                    values_i[k] += X.get(objNumber)[k];
                }
            }

            for (int j = 0; j < values_i.length; j++) {
                values_i[j] /= clazz.size();
            }
            prototypes.add(values_i);
        }
        return prototypes;
    }

    public List<List<Integer>> Clustering() {
        List<NormalizeObject> prototypes = new ArrayList<NormalizeObject>();
        for (Cluster cluster : clusters) {
            prototypes.add(cluster.getPrototype());
        }

        List<double[]> P = Normalize(prototypes);
        List<double[]> X = Normalize(getObjects());

        while (true) {
            double[][] D = GetDistance(P, X);
            List<List<Integer>> classes = FindObjectClasses(D);
            List<double[]> newP = GetNewPrototypes(classes, X);

            if (IsEqualPrototypes(P, newP)) {
                return classes;
            }

            P.clear();
            P = newP;
        }
    }
}