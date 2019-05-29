package nn;

import processing.core.PApplet;

import java.util.Random;

public class KohonenColours extends PApplet {

	public static void main(String[] args) {
		
		PApplet.main("nn.KohonenColours");
	}
	
	private static Neuron[][] network;
	static final int examplesN = 5;
	
	static final int dimInputSpace = 3;
	
	static final int windowSizeX = 600;
	static final int windowSizeY = 600;
	static final int squareHeight = 10; 
	
	static float epsilon = 0.2f;
	static float sigma = 18;
	
	static long iterationsCounter = 1;
	static final int maxIterations = 10000;
	
	// (windowSizeX / squareHeight) * (windowSizeY / squareHeight) neurons in network
	
	public void settings() {
		
		size(windowSizeX, windowSizeY);
		
	}
	
	public void setup() {
		
		frameRate(5);
		try {
			network = networkInit();
		} catch(Exception e) {
			println(e.getMessage());
		}
		
//		thread("startLearning");
	}
	
	public void draw() {
		
//		background(200);
		squarePlot();
		iterate();
		
	}
	
	private Neuron[][] networkInit() throws Exception {
		
		Neuron[][] network = new Neuron[windowSizeX / squareHeight][windowSizeY / squareHeight];
		
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				
				network[i][j] = new Neuron(dimInputSpace, i, j);
				network[i][j].randomWeightsInit();
			}
		}
		return network;
		
	}
	
	private void squarePlot() {
		
		int r = 0;
		int k = 0;
		
		for (int i = 0; i < windowSizeX; i += squareHeight) {
			for (int j = 0; j < windowSizeY; j += squareHeight) {
				
				float red = network[r][k].weights[0] * 255;
				float green = network[r][k].weights[1] * 255;
				float blue = network[r][k].weights[2] * 255;
				
				k += 1;
				
				fill(red, green, blue);
				square(j, i, squareHeight);
			}
			k = 0;
			r += 1;
		}
	}
	
	private float[] randomExampleFromSet() {

		Random randomN = new Random();
		float[][] randomExampleSet = { { 0, 128, 0 }, { 128, 0, 0 }, { 0, 0, 128 }, { 255, 255, 255 }, { 0, 0, 0 } };

		int randomExampleIndex = randomN.nextInt(randomExampleSet.length);

		return randomExampleSet[randomExampleIndex];
	}
	
	private float[] normalizeExample(float[] example) {
		
		for (int i = 0; i < example.length; i++) {
			example[i] = example[i] / 255;
		}
		return example;
	}
	
	private Neuron minDistance(float[] randomExample, Neuron[][] network) {
		
		Neuron winner = network[0][0];
		
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				
				float winnerDist = distance(randomExample, winner);
				float currentDist = distance(randomExample, network[i][j]);
				
				if (currentDist < winnerDist) {
					winner = network[i][j];
				}
			}
		}
		return winner;
	}
	
	private float distance(float[] example, Neuron neuron) {
		
		float neuronWeight1 = neuron.weights[0] * 255;
		float neuronWeight2 = neuron.weights[1] * 255;
		float neuronWeight3 = neuron.weights[2] * 255;
		
		float dist = sqrt(sq(example[0] - neuronWeight1) + sq(example[1] - neuronWeight2) + sq(example[2] - neuronWeight3));
		return dist;
	}
	
	private void updateNetwork(Neuron winner, float[] randomExample) {

		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {

				float[] weightVariation = new float[3];
				weightVariation[0] = exp(-(((sq(i - winner.iCoord) + sq(j - winner.jCoord)) / (2 * sq(sigma))))) * (randomExample[0] - network[i][j].weights[0]);
				weightVariation[1] = exp(-(((sq(i - winner.iCoord) + sq(j - winner.jCoord)) / (2 * sq(sigma))))) * (randomExample[1] - network[i][j].weights[1]);
				weightVariation[2] = exp(-(((sq(i - winner.iCoord) + sq(j - winner.jCoord)) / (2 * sq(sigma))))) * (randomExample[2] - network[i][j].weights[2]);

				network[i][j].weights[0] = network[i][j].weights[0] + epsilon * weightVariation[0];
				network[i][j].weights[1] = network[i][j].weights[1] + epsilon * weightVariation[1];
				network[i][j].weights[2] = network[i][j].weights[2] + epsilon * weightVariation[2];
			}
		}
	}
	
	public void startLearning() {

		while (epsilon > 0.01f) {

			float[] randomExample = randomExampleFromSet();

			Neuron winner = minDistance(randomExample, network);

			updateNetwork(winner, randomExample);

			if ((iterationsCounter % 1000) == 0) {

				epsilon = epsilon * 0.999f;
				sigma = sigma * 0.96f;
			}

			if ((iterationsCounter % 10000) == 0) {

				println("iteration: " + iterationsCounter + "  fps: " + frameRate + "  time: " + hour() + ":" + minute() + ":" + second());
			}

			if (iterationsCounter == maxIterations) {

				println("stop while");
				break;
			}
			iterationsCounter += 1;
		}

		println("stopped");
		noLoop();
		return;
	}
	
	private void iterate() {
		
		float[] randomExample = normalizeExample(randomExampleFromSet());

		Neuron winner = minDistance(randomExample, network);

		updateNetwork(winner, randomExample);
		

		if ((iterationsCounter % 1000) == 0) {

			epsilon = epsilon * 0.999f;
			sigma = sigma * 0.96f;
		}

		if ((iterationsCounter % 10000) == 0) {

			println("iteration: " + iterationsCounter + "  fps: " + frameRate + "  time: " + hour() + ":" + minute() + ":" + second());
		}

		if (iterationsCounter == maxIterations) {

			println("stop iterating");
			noLoop();
			return;
		}
		
		if (epsilon < 0.01f) {
			println("stopped");
			noLoop();
			return;
		}
		iterationsCounter += 1;
		
	}



}
