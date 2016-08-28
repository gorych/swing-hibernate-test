package by.gstu.computerdetails.algorithm;

import by.gstu.computerdetails.entity.Cluster;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class K_Means extends ClusterAnalysisMethod {

    private List<Cluster> clusters;
    private List<NormalizeObject> prototypes;
    private int protoCount;

    public K_Means(List<NormalizeObject> objects, List<Cluster> clusters) {
        super(objects);
        this.clusters = clusters;

        prototypes = new ArrayList<NormalizeObject>();
        for (Cluster cluster : clusters) {
            prototypes.add(cluster.getPrototype());
        }

        this.protoCount = prototypes.size();
    }

    public Map<Cluster, List<Integer>> divide() {
        double[] X_MAX = findMaxSignValue(objects);
        double[] P_MAX = findMaxSignValue(prototypes);

        List<double[]> X = normalizeByMax(objects, X_MAX);
        List<double[]> P = normalizeByMax(prototypes, P_MAX);

        while (true) {
            double[][] distances = getDistances(X, P);
            Map<Cluster, List<Integer>> clusterMap = getClusterMap(distances);
            List<double[]> newP = findNewPrototypes(clusterMap, X);

            if (isEqual(P, newP)) {
                return clusterMap;
            }

            P.clear();
            P = newP;
        }
    }

    private boolean isEqual(List<double[]> prevP, List<double[]> curP) {
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

    private List<double[]> findNewPrototypes(Map<Cluster, List<Integer>> clusterMap, List<double[]> X) {

        //bug is here

        List<double[]> prototypes = new ArrayList<double[]>();
        for (Cluster cluster : clusterMap.keySet()) {
            List<Integer> clazz = clusterMap.get(cluster);

            double[] values_i = new double[SIGN_COUNT];
            if (clazz == null) {
                continue;
            }

            for (int objNumber : clazz) {
                for (int k = 0; k < SIGN_COUNT; k++) {
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

    private Map<Cluster, List<Integer>> getClusterMap(double[][] distances) {
        Map<Cluster, List<Integer>> clusterMap = new LinkedHashMap<Cluster, List<Integer>>();
        for (int j = 0; j < OBJECT_COUNT; j++) {
            double min = Double.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < protoCount; i++) {
                if (distances[i][j] <= min) {
                    index = i;
                    min = distances[i][j];
                }
            }
            Cluster cluster = clusters.get(index);

            List<Integer> clusterObjects = clusterMap.get(cluster);
            if (clusterObjects == null) {
                clusterObjects = new ArrayList<Integer>();
                clusterMap.put(cluster, clusterObjects);
            }

            clusterObjects.add(j);
        }
        return clusterMap;
    }

    private double[][] getDistances(List<double[]> x, List<double[]> p) {
        double[][] distances = new double[protoCount][OBJECT_COUNT];

        for (int i = 0; i < protoCount; i++) {
            for (int j = 0; j < OBJECT_COUNT; j++) {
                double sum = 0;
                for (int k = 0; k < SIGN_COUNT; k++) {
                    sum += (x.get(j)[k] - p.get(i)[k]) * (x.get(j)[k] - p.get(i)[k]);
                }
                distances[i][j] = Math.sqrt(sum);
            }
        }
        return distances;
    }


}