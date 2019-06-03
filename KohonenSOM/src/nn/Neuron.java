
package nn;

import java.util.Random;
//import java.lang.Math;

public class Neuron {
	
	private float[] getWeights;
	private NeuronCoord[] coord;
	
	
	public Neuron(int dimInputSpace, int dimNeuronSpace /*, int dummy1, int dummy2, int dummy3 */) throws Exception {
		
		if (dimInputSpace <= 0 || dimNeuronSpace <= 0) {
			throw new Exception("[Neuron]: invalid arguments");
		}
		this.getWeights = new float[dimInputSpace + 1];
		this.coord = new NeuronCoord[dimNeuronSpace];
		
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
		return this.getWeights;
	}
	
	public void randomWeightsInit() {	
		
		Random random = new Random();
		
		for (int i = 0; i < this.getWeights.length; i++) {
			
			this.getWeights[i] = random.nextFloat();
		}
	}
	
	public double output(float[] input) throws Exception {
		
		double prod = scProd(input, this.getWeights);
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
