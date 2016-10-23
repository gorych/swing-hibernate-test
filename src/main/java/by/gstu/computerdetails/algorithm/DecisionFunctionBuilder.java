package by.gstu.computerdetails.algorithm;

import by.gstu.computerdetails.entity.Cluster;
import by.gstu.computerdetails.entity.Monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionFunctionBuilder {

    public static Map<Cluster, List<Result>> build(List<Cluster> clusters, List<Monitor> monitors, Map<Cluster, List<Integer>> clusterMap) {
        final int SIGN_COUNT = monitors.get(0).signCount();

        double[] maxSignValue = AlgorithmHelper.findMaxSignValue(monitors);
        List<double[]> X = AlgorithmHelper.normalizeByMax(monitors, maxSignValue);

        List<Result> results = new ArrayList<Result>();
        for (int k = 0; k < clusters.size() - 1; k++) {
            Cluster c1 = clusters.get(k);
            for (int l = k + 1; l < clusters.size(); l++) {
                int E = 0;
                int W0 = 0;
                double[] W = new double[SIGN_COUNT];

                Cluster c2 = clusters.get(l);

                //region Build function

                List<Integer> objIndexes1 = clusterMap.get(c1);
                List<Integer> objIndexes2 = clusterMap.get(c2);

                List<NormalizeObject> obj1 = new ArrayList<NormalizeObject>();
                for (Integer index : objIndexes1) {
                    obj1.add(monitors.get(index));
                }

                List<NormalizeObject> obj2 = new ArrayList<NormalizeObject>();
                for (Integer index : objIndexes2) {
                    obj2.add(monitors.get(index));
                }

                int totalSize = obj1.size() + obj2.size();
                while (E != totalSize) {
                    for (int i = 0; i < obj1.size() && E != totalSize; i++) {
                        double D = 0;
                        for (int j = 0; j < SIGN_COUNT; j++) {
                            Monitor monitor = (Monitor) obj1.get(i);
                            int index = monitors.indexOf(monitor);
                            D += W[j] * X.get(index)[j];
                        }
                        D += W0;

                        if (D > 0) {
                            E++;
                        } else {
                            for (int j = 0; j < SIGN_COUNT; j++) {
                                Monitor monitor = (Monitor) obj1.get(i);
                                int index = monitors.indexOf(monitor);
                                W[j] += X.get(index)[j];
                            }
                            W0++;
                            E = 0;
                        }
                    }

                    for (int i = 0; i < obj2.size() && E != totalSize; i++) {
                        double D = 0;
                        for (int j = 0; j < SIGN_COUNT; j++) {
                            Monitor monitor = (Monitor) obj2.get(i);
                            int index = monitors.indexOf(monitor);
                            D += W[j] * X.get(index)[j];
                        }
                        D += W0;

                        if (D < 0) {
                            E++;
                        } else {
                            for (int j = 0; j < SIGN_COUNT; j++) {
                                Monitor monitor = (Monitor) obj2.get(i);
                                int index = monitors.indexOf(monitor);
                                W[j] -= X.get(index)[j];
                            }
                            W0--;
                            E = 0;
                        }
                    }
                }
                //endregion
                results.add(new Result(c1, c2, W0, W, true));
            }
        }

        List<Result> addResults = new ArrayList<Result>();
        for (Result result : results) {
            Cluster c1 = result.getClassNumber2();
            Cluster c2 = result.getClassNumber1();
            addResults.add(new Result(c1, c2, result.getW0(), result.getW(), false));
        }
        results.addAll(addResults);

        Map<Cluster, List<Result>> resultMap = new HashMap<Cluster, List<Result>>();
        for (Cluster cluster : clusters) {
            List<Result> resultList = new ArrayList<Result>();
            for (Result result : results) {
                if (cluster.equals(result.getClassNumber1())) {
                    resultList.add(result);
                }
            }
            resultMap.put(cluster, resultList);
        }
        return resultMap;
    }
}
