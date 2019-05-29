package nn;

import processing.core.PApplet;

import java.util.Random;
import java.security.SecureRandom;

public class KohonenPattern2D extends PApplet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PApplet.main("nn.KohonenPattern2D");
		
	}
	
	static final int windowSizeX = 350;
	static final int windowSizeY = 350;
	static final int squareHeight = 1;
	static final float scaleFactor = 300;
	
	static final int dimInputSpace = 2; // non modificare
//	static final double neuronTheta = 50e-3;
	
	static float epsilon = 0.2f;
	static float sigma = 18;
	static final int maxIterations = 90000;
	
	static final int layerNeurons = 15;
	static final int layers = 15;
	
	private static Neuron[][] network;
	private static long iterationsCounter = 1;
	
	public void settings() {
		
		size(windowSizeX, windowSizeY);
	//	smooth(4);
		
		//anti aliasing 4x
		
	}
	
	public void setup() {
		
		
		
		frameRate(1024);
//		fill(255);
		strokeWeight(1 / scaleFactor);
		
//		rectMode(CENTER); 
		// change random examples accordingly
		
//		translate(windowSizeX / 2, windowSizeY / 2);
		scale(scaleFactor);
		square(0, 0, squareHeight);
		
		try {
			network = networkInit();
		} catch (Exception e) {
			println(e.getMessage());
		}
		
		thread("startLearning");
		
//		delay(3000); // initial random distribution
		
		
				
	}
	
	public void draw() {
		
//		noFill();
//		beginShape(LINES);
		
		background(200);
//		translate(windowSizeX / 2, windowSizeY / 2);
		scale(scaleFactor);
		square(0, 0, squareHeight);
		
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				
				if (i == 0 && j == 0) {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j + 1].weights[0], network[i][j + 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i + 1][j].weights[0], network[i + 1][j].weights[1]);
				}
				
				else if (i == network.length - 1 && j == 0) {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j + 1].weights[0], network[i][j + 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i - 1][j].weights[0], network[i - 1][j].weights[1]);
				}
				
				else if (i == 0 && j == network[i].length - 1) {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j - 1].weights[0], network[i][j - 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i + 1][j].weights[0], network[i + 1][j].weights[1]);
				}
				
				else if (i == network.length - 1 && j == network[i].length - 1) {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j - 1].weights[0], network[i][j - 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i - 1][j].weights[0], network[i - 1][j].weights[1]);
				}
				
				else if (i == 0) {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j + 1].weights[0], network[i][j + 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i + 1][j].weights[0], network[i + 1][j].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j - 1].weights[0], network[i][j - 1].weights[1]);
				}
				
				else if (j == 0) {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j + 1].weights[0], network[i][j + 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i + 1][j].weights[0], network[i + 1][j].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i + 1][j].weights[0], network[i + 1][j].weights[1]);
				}
				
				else if (i == network.length - 1) {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j + 1].weights[0], network[i][j + 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i - 1][j].weights[0], network[i - 1][j].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j - 1].weights[0], network[i][j - 1].weights[1]);
				}
				
				else if (j == network[i].length - 1) {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j - 1].weights[0], network[i][j - 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i + 1][j].weights[0], network[i + 1][j].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i - 1][j].weights[0], network[i - 1][j].weights[1]);
				}
				
				else {
					
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j + 1].weights[0], network[i][j + 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i + 1][j].weights[0], network[i + 1][j].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i][j - 1].weights[0], network[i][j - 1].weights[1]);
					line(network[i][j].weights[0], network[i][j].weights[1], network[i - 1][j].weights[0], network[i - 1][j].weights[1]);
				}
				
			}
		}
		
//		thread("startLearning");
		
		/*
		
		float[] randomExample = randomExample();
		
		Neuron winner = minDistance(randomExample, network);
		
		updateNetwork(winner, randomExample);
		
		
		if ((iterationsCounter % 1000) == 0) {
			
			epsilon = epsilon * 0.999f;
			sigma = sigma * 0.96f;
		}
		
		if (epsilon < 0.01f) {
			
			noLoop();
			println("stopped");
		}
		
		// alternative stop
		
		if (iterationsCounter == maxIterations) {
			noLoop();
			println("stopped, maximum iterations");
		}
		
		if ((iterationsCounter % 10000) == 0) {

			println("iteration: " + iterationsCounter + "  fps: " + frameRate + "  time: " + hour() + ":" + minute() + ":" + second());
		}
			
		iterationsCounter += 1;
		
		*/
		
	//	square(mouseX, mouseY, squareHeight);
		
	}
	
	public void startLearning() {
		
		while (epsilon > 0.01f) {
			
			float[] randomExample = randomExample();

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
//			delay(50);
			// broken lines
			// thread iterations quicker than draw
			// add delay
		}
		
		println("stopped");	
//		noLoop();	
		// or continue drawing with fixed neurons
		return;
	}
	
	private Neuron[][] networkInit() throws Exception {
		
		Neuron[][] network = new Neuron[layerNeurons][layers];
		
		for (int i = 0; i < layerNeurons; i++) {
			for (int j = 0; j < layers; j++) {
						
				network[i][j] = new Neuron(dimInputSpace, i, j);
				network[i][j].randomWeightsInit();
			}
		}
		return network;
		
	}
	
	private float[] randomExample() {
		
		Random randomN = new Random();
		float[] randomExample = new float[dimInputSpace];
		
//		randomExample[0] = (randomN.nextFloat() * squareHeight * 2) + (windowSizeX / 2) - squareHeight;
//		randomExample[1] = (randomN.nextFloat() * squareHeight * 2) + (windowSizeY / 2) - squareHeight;
		
		randomExample[0] = randomN.nextFloat() * squareHeight;
		randomExample[1] = randomN.nextFloat() * squareHeight;
		
		return randomExample;
	}
	
	private float[] secureRandomExample() {
	    
	    SecureRandom randomN =  new SecureRandom();
	    
	    float[] randomExample = new float[dimInputSpace];

//		randomExample[0] = (randomN.nextFloat() * squareHeight * 2) + (windowSizeX / 2) - squareHeight;
//	randomExample[1] = (randomN.nextFloat() * squareHeight * 2) + (windowSizeY / 2) - squareHeight;

	    randomExample[0] = randomN.nextFloat() * squareHeight;
	    randomExample[1] = randomN.nextFloat() * squareHeight;

	    return randomExample;
	}
	
	private Neuron minDistance(float[] randomExample, Neuron[][] network) {
		
		Neuron winner = network[0][0];
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				
				float distanceWinner = dist(randomExample[0], randomExample[1], winner.weights[0], winner.weights[1]);
				float distanceCurrent = dist(randomExample[0], randomExample[1], network[i][j].weights[0], network[i][j].weights[1]);
				
				if (distanceCurrent < distanceWinner) {
					winner = network[i][j];
				}
			}
		}
		return winner;
	}
	
	private void updateNetwork(Neuron winner, float[] randomExample) {
		
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				
				float[] weightVariation = new float[2]; 
				weightVariation[0] = exp( - (((sq(i - winner.iCoord) + sq(j - winner.jCoord)) / (2 * sq(sigma))))) * (randomExample[0] - network[i][j].weights[0]);
				weightVariation[1] = exp( - (((sq(i - winner.iCoord) + sq(j - winner.jCoord)) / (2 * sq(sigma))))) * (randomExample[1] - network[i][j].weights[1]);
				
				network[i][j].weights[0] = network[i][j].weights[0] + epsilon * weightVariation[0];
				network[i][j].weights[1] = network[i][j].weights[1] + epsilon * weightVariation[1];
			}
		}
	}

}
