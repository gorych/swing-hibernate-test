package by.gstu.computerdetails.algorithm;

import by.gstu.computerdetails.entity.Cluster;

import java.util.ArrayList;
import java.util.List;

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

    public void run() {
        double[] X_MAX = findMaxSignValue(objects);
        double[] P_MAX = findMaxSignValue(prototypes);

        double[][] X = normalizeByMax(objects, X_MAX);
        double[][] P = normalizeByMax(prototypes, P_MAX);

        double[][] distances = new double[protoCount][OBJECT_COUNT];
        for (int i = 0; i < protoCount; i++) {
            for (int j = 0; j < OBJECT_COUNT; j++) {
                double sum = 0;
                for (int k = 0; k < SIGN_COUNT; k++) {
                    sum += (X[j][k] - P[i][k]) * (X[j][k] - P[i][k]);
                }
                distances[i][j] = Math.sqrt(sum);
            }
        }

        printMatrix(distances);

        //double[][] normalizeValues = normalize(objects);
    }


}