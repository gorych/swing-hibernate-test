package by.gstu.computerdetails.algorithm.decisionfunction;

public abstract class Recognizer {

	private double[] D;

	public Recognizer(double[] D) {
		this.D = D;
	}

	public double[] getD() {
		return D;
	}

	abstract int recognize();

	public void printResult(String header) {
		System.out.println(header);
		
		for (int i = 0; i < D.length; i++) {
			System.out.print(String.format("D%d = %1.2f\n", (i + 1), D[i]));
		}
		
		int clazz = recognize();
		System.out.println("Class = " + clazz);
		System.out.println();
	}

}
