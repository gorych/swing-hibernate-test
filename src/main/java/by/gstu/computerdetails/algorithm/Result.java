package by.gstu.computerdetails.algorithm;

import by.gstu.computerdetails.entity.Cluster;

import java.util.Arrays;

public class Result {

    private Cluster classNumber1;
    private Cluster classNumber2;
    private double W0;
    private double[] W;
    private boolean isPositive;

    public Result(Cluster classNumber1, Cluster classNumber2, double w0, double[] w, boolean isPositive) {
        this.classNumber1 = classNumber1;
        this.classNumber2 = classNumber2;
        W0 = w0;
        W = w;
        this.isPositive = isPositive;
    }

    public Cluster getClassNumber1() {
        return classNumber1;
    }

    public void setClassNumber1(Cluster classNumber1) {
        this.classNumber1 = classNumber1;
    }

    public Cluster getClassNumber2() {
        return classNumber2;
    }

    public void setClassNumber2(Cluster classNumber2) {
        this.classNumber2 = classNumber2;
    }

    public double getW0() {
        return W0;
    }

    public void setW0(double w0) {
        W0 = w0;
    }

    public double[] getW() {
        return W;
    }

    public void setW(double[] w) {
        W = w;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    @Override
    public String toString() {
        return "Result{" +
                "W0=" + W0 +
                ", W=" + Arrays.toString(W) +
                ", isPositive=" + isPositive +
                '}';
    }
}
