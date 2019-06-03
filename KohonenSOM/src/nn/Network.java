package nn;

public class Network {
	
	private Neuron[] network1dim;
	private Neuron[][] network2dim;
	private Neuron[][][] network3dim;
	
	public Network(int dimNeuronSpace, int neuronNumber) throws Exception {
		
		if (dimNeuronSpace <= 0 || neuronNumber <= 0) {
			throw new Exception("[Network]: invalid arguments");
		}	
		else if (dimNeuronSpace == 1) {
			
			this.network2dim = null;
			this.network3dim = null;
			this.network1dim = new Neuron[neuronNumber];
		}
		else
		{
			throw new Exception("[Neuron]: invalid space dimension for constructor");
		}
	}
	
	public Network(int dimNeuronSpace, int neuronNumber, int layersNumber) throws Exception {
		
		if (dimNeuronSpace <= 0 || neuronNumber <= 0 || layersNumber <= 0) {
			throw new Exception("[Neuron]: invalid arguments");
		}
		
		else if (dimNeuronSpace == 2) {
			
			this.network1dim = null;
			this.network3dim = null;
			this.network2dim = new Neuron[neuronNumber][layersNumber];
		}
		else {
			throw new Exception("[Neuron]: invalid space dimension for constructor");
		}
	}
	
	public Network(int dimNeuronSpace, int neuronNumber, int layersNumber, int stacksNumber) throws Exception {
		
		if (dimNeuronSpace <= 0 || neuronNumber <= 0 || layersNumber <= 0 || stacksNumber < 0) {
			throw new Exception("[Neuron]: invalid arguments");
		}
		
		else if (dimNeuronSpace == 3) {
			this.network1dim = null;
			this.network2dim = null;
			this.network3dim = new Neuron[neuronNumber][layersNumber][stacksNumber];
		}
		else {
			throw new Exception("[Neuron]: invalid space dimension for constructor");
		}
	}
	
	public Neuron[] get1Dnetwork() {
		
		if (network1dim != null) {
			return network1dim;
		}
		return null;
	}
	
	public Neuron[][] get2Dnetwork() {
		
		if (network2dim != null) {
			return network2dim;
		}
		return null;
	}
	
	public Neuron[][][] get3Dnetwork() {
		
		if (network3dim != null) {
			return network3dim;
		}
		return null;
	}

}
