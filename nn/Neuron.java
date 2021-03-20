
package nn;

import java.util.Random;

public class Neuron {
	
	private float[] weights;
	private NeuronCoord[] coord;
	private byte[] weightsDiscr;
	
	public Neuron(int dimInputSpace, int dimNeuronSpace) throws Exception {
		
		if (dimInputSpace <= 0 || dimNeuronSpace <= 0) {
			throw new Exception("[Neuron]: invalid arguments");
		}
		this.weights = new float[dimInputSpace];
		this.coord = new NeuronCoord[dimNeuronSpace];
		this.weightsDiscr = null;
		
		for (int i = 0; i < this.coord.length; i++) {
			this.coord[i] = new NeuronCoord(0);
		}
	}
	
	public Neuron(int dimInputSpace, int dimNeuronSpace, String type) throws Exception {
		
		if (dimInputSpace <= 0 || dimNeuronSpace <= 0) {
			throw new Exception("[Neuron]: invalid arguments");
		}
		this.weightsDiscr = new byte[dimInputSpace];
		this.coord = new NeuronCoord[dimNeuronSpace];
		this.weights = null;
		
		for (int i = 0; i < this.coord.length; i++) {
			this.coord[i] = new NeuronCoord(0);
		}
	}
	
	public void setCoord(int coord, int pos) throws Exception {
		
		if (pos < 0 || coord < 0) {
			throw new Exception("[setCoord]: ivalid arguments");
		}
		this.coord[pos].set(coord);
	}
	
	public int getCoord(int pos) throws Exception {
		
		if (pos < 0) {
			throw new Exception("[getCoord]: invalid argument");
		}
		return this.coord[pos].get();
	}
	
	public float[] getWeights() {
		return this.weights;
	}
	
	public void setWeight(float weight, int index) throws Exception {
		
		if (index < 0) {
			throw new Exception("[setWeight]: invalid index");
		}
		
		if (this.weights != null) {
			
			this.weights[index] = weight;
			
		} else if (this.weightsDiscr != null) {
			
			this.weightsDiscr[index] = (byte) Math.signum(weight);
			
		} else {
			
			throw new Exception("[setWeight]: weights not initialized");
		}
		
	}
	
	public void randomWeightsInit() throws Exception {	
		
		if (weights == null) {
			
			throw new Exception("[randomWeightsInit]: cont weights not initialized");
		}
		Random random = new Random();
		
		for (int i = 0; i < this.weights.length; i++) {
			
			this.weights[i] = random.nextFloat();
		}
	}
	
	public double outputCont(float[] input) throws Exception {
		
		double res = 0;
		if (this.weights != null) {
			
			double prod = scProd(input, this.weights);
			res = Math.tanh(prod);
			
		} else {
			
			throw new Exception("[outputCont]: cont weights not initialized");
		}
		
		return res;
	}
	
	public double outputDisc(float[] input) throws Exception {
		
		double res = 0;
		if (this.weights != null) {
			
			double prod = scProd(input, this.weights);
			res = Math.signum(prod);
			
		} else {
			
			throw new Exception("[outputCont]: cont weights not initialized");
		}
		
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

class NeuronCoord {
	
	private int coord;
	
	public NeuronCoord(int coord) throws Exception {
		if (coord < 0) {
			throw new Exception("[NeuronCoord]: invalid argument");
		}
		this.coord = coord;
	}
	
	public void set(int coord) throws Exception {
		if (coord < 0) {
			throw new Exception("[Coord.set]: invalid argument");
		}
		this.coord = coord;
	}
	
	public int get() {
		return this.coord;
	}
	
}
