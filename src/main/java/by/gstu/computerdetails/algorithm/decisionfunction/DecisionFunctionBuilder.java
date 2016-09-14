package by.gstu.computerdetails.algorithm.decisionfunction;

import by.gstu.computerdetails.algorithm.NormalizeObject;

public class DecisionFunctionBuilder extends FunctionBuilder {

	public DecisionFunctionBuilder(NormalizeObject[][] typicalProjects,
			NormalizeObject projectedObject) {
		super(typicalProjects, projectedObject);
	}

	@Override
	public double[] build() {
		final int FEATURES_COUNT = getFeaturesCount();

		NormalizeObject[] c1 = getProjects()[0];
		NormalizeObject[] c2 = getProjects()[1];

		final int COUNT = c1.length + c2.length;

		double[][] X = getDimensionlessValues();
		double[] W = new double[FEATURES_COUNT];
		int W0 = 0;

		int E = 0;
		while (E != COUNT) {
			for (int i = 0; i < c1.length && E != COUNT; i++) {
				double D = 0;
				for (int j = 0; j < FEATURES_COUNT; j++) {
					D += W[j] * X[i][j];
				}
				D += W0;

				if (D > 0) {
					E++;
				} else {
					for (int j = 0; j < FEATURES_COUNT; j++) {
						W[j] += X[i][j];
					}
					W0++;
					E = 0;
				}
			}

			for (int i = c1.length; i < COUNT && E != COUNT; i++) {
				double D = 0;
				for (int j = 0; j < FEATURES_COUNT; j++) {
					D += W[j] * X[i][j];
				}
				D += W0;

				if (D < 0) {
					E++;
				} else {
					for (int j = 0; j < FEATURES_COUNT; j++) {
						W[j] -= X[i][j];
					}
					W0--;
					E = 0;
				}
			}

		}

		double[] x = getDimensionlessValues(super.getProjectedObject());
		double resD = W0;
		for (int i = 0; i < x.length; i++) {
			resD += W[i] * x[i];
		}
		return new double[] { resD };
	}
}
