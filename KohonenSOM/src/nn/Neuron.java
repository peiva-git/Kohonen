
package nn;

import java.util.Random;
//import java.lang.Math;

public class Neuron {
	
	public float[] weights;
	public int iCoord;
	public int jCoord;
	public int rCoord;
//	private double theta;
//	private int dimWeightsSpace;
	
	public Neuron(int dimInputSpace, int iCoord, int jCoord, int rCoord) throws Exception {
		
		if (dimInputSpace < 0 || iCoord < 0 || jCoord < 0 || rCoord < 0) {
			throw new Exception("[Neuron]: invalid arguments");
		}
		
		this.weights = new float[dimInputSpace + 1];
		this.iCoord = iCoord;
		this.jCoord = jCoord;
		this.rCoord = rCoord;
	}
	
	public Neuron(int dimInputSpace, int iCoord, int jCoord /* , int dimWeightsSpace */) throws Exception {		
		
		if (dimInputSpace < 0 || iCoord < 0 || jCoord < 0) {
			throw new Exception("[Neuron]: invalid arguments");
		}
		
		this.weights = new float[dimInputSpace + 1];
		this.iCoord = iCoord;
		this.jCoord = jCoord;
		this.rCoord = -1;
//		this.theta = theta;
//		this.dimWeightsSpace = dimWeightsSpace;
	}
	
	public Neuron(int dimInputSpace, int iCoord) throws Exception {
	    
	    if (dimInputSpace < 0 || iCoord < 0) {
		throw new Exception("[Neuron]: invalid arguments");
	    }
	    
	    this.weights = new float[dimInputSpace + 1];
	    this.iCoord = iCoord;
	    this.jCoord = -1;
	    this.rCoord = -1;
	}
	
	public Neuron(int dimInputSpace) throws Exception {
		if (dimInputSpace < 0) {
			throw new Exception("[Neuron]: invalid argument");
		}
	}
	
	public void randomWeightsInit() {	
		
		Random random = new Random();
		
		for (int i = 0; i < this.weights.length; i++) {
			
			this.weights[i] = random.nextFloat();
		}
	}
	
	public double output(float[] input) throws Exception {
		
		double prod = scProd(input, this.weights);
		double res = Math.tanh(prod);
		return res;
	}
	
	private double scProd(float[] input, float[] weights) throws Exception {
		
		if (input.length != weights.length) {
			
			throw new Exception("[scProd]: different length");
		}
		
		double res = 0;
		
		int i;
		for (i = 0; i < input.length - 1; i++) {
			
			res += input[i] * weights[i];
		}
		res += -1 * weights[i];
		return res;
	}

}
