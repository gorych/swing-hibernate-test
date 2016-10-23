package by.gstu.computerdetails.algorithm;

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

}