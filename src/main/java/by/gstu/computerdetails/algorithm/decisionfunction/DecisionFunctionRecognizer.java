package by.gstu.computerdetails.algorithm.decisionfunction;

public class DecisionFunctionRecognizer extends Recognizer {

	public DecisionFunctionRecognizer(double[] D) {
		super(D);
	}

	@Override
	int recognize() {
		double[] D = super.getD();
		if (D[0] < 0) {
			return 2;
		}
		
		if (D[0] > 0) {
			return 1;
		}
		
		return 0;
	}

}
