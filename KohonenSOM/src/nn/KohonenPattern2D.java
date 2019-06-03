package nn;

import processing.core.PApplet;

import java.util.Random;
import java.security.SecureRandom;

public class KohonenPattern2D extends PApplet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PApplet.main("nn.KohonenPattern2D");
		
	}
	
	static final int windowSizeX = 600;
	static final int windowSizeY = 600;
	static final int squareHeight = 1;
	static final float scaleFactor = 300;
	static final float ellipseHeight = 1;
	static final float ellipseWidth = 1.5f;
	
	static final int dimInputSpace = 2; // non modificare
	static final int dimNeuronSpace = 2;
	
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
		
		
		
		frameRate(30);
//		fill(255);
		strokeWeight(1 / scaleFactor);
		
//		rectMode(CENTER); 
		// change random examples accordingly
		
//		translate(windowSizeX / 2, windowSizeY / 2);
		scale(scaleFactor);
//		square(0, 0, squareHeight);
		
		ellipseMode(CORNER);
		
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
//		square(0, 0, squareHeight);
		ellipse(0, 0, ellipseWidth, ellipseHeight);
		
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				
				if (i == 0 && j == 0) {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j + 1].getWeights()[0], network[i][j + 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i + 1][j].getWeights()[0], network[i + 1][j].getWeights()[1]);
				}
				
				else if (i == network.length - 1 && j == 0) {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j + 1].getWeights()[0], network[i][j + 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i - 1][j].getWeights()[0], network[i - 1][j].getWeights()[1]);
				}
				
				else if (i == 0 && j == network[i].length - 1) {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j - 1].getWeights()[0], network[i][j - 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i + 1][j].getWeights()[0], network[i + 1][j].getWeights()[1]);
				}
				
				else if (i == network.length - 1 && j == network[i].length - 1) {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j - 1].getWeights()[0], network[i][j - 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i - 1][j].getWeights()[0], network[i - 1][j].getWeights()[1]);
				}
				
				else if (i == 0) {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j + 1].getWeights()[0], network[i][j + 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i + 1][j].getWeights()[0], network[i + 1][j].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j - 1].getWeights()[0], network[i][j - 1].getWeights()[1]);
				}
				
				else if (j == 0) {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j + 1].getWeights()[0], network[i][j + 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i + 1][j].getWeights()[0], network[i + 1][j].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i + 1][j].getWeights()[0], network[i + 1][j].getWeights()[1]);
				}
				
				else if (i == network.length - 1) {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j + 1].getWeights()[0], network[i][j + 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i - 1][j].getWeights()[0], network[i - 1][j].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j - 1].getWeights()[0], network[i][j - 1].getWeights()[1]);
				}
				
				else if (j == network[i].length - 1) {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j - 1].getWeights()[0], network[i][j - 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i + 1][j].getWeights()[0], network[i + 1][j].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i - 1][j].getWeights()[0], network[i - 1][j].getWeights()[1]);
				}
				
				else {
					
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j + 1].getWeights()[0], network[i][j + 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i + 1][j].getWeights()[0], network[i + 1][j].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i][j - 1].getWeights()[0], network[i][j - 1].getWeights()[1]);
					line(network[i][j].getWeights()[0], network[i][j].getWeights()[1], network[i - 1][j].getWeights()[0], network[i - 1][j].getWeights()[1]);
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
	
	public void startLearning() throws Exception {
		
		while (epsilon > 0.01f) {
			
//			float[] randomExample = randomExampleSquare();
			float[] randomExample = randomExampleEllipse();

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
		
		Network net = new Network(dimNeuronSpace, layerNeurons, layers);
		
		Neuron[][] network = net.get2Dnetwork();
		
		for (int i = 0; i < layerNeurons; i++) {
			for (int j = 0; j < layers; j++) {
						
				network[i][j] = new Neuron(dimInputSpace, dimNeuronSpace);
				network[i][j].randomWeightsInit();
				
				network[i][j].setCoord(i, 0);
				network[i][j].setCoord(j, 1);
			}
		}
		return network;
		
	}
	
	private float[] randomExampleSquare() {
		
		Random randomN = new Random();
		float[] randomExample = new float[dimInputSpace];
		
//		randomExample[0] = (randomN.nextFloat() * squareHeight * 2) + (windowSizeX / 2) - squareHeight;
//		randomExample[1] = (randomN.nextFloat() * squareHeight * 2) + (windowSizeY / 2) - squareHeight;
		
		randomExample[0] = randomN.nextFloat() * squareHeight;
		randomExample[1] = randomN.nextFloat() * squareHeight;
		
		return randomExample;
	}
	
	private float[] randomExampleEllipse() {
		
		float[] randomExample = new float[dimInputSpace];
		Random randomN = new Random();
		
		float pointXcoord = randomN.nextFloat() * ellipseWidth;
		float pointYcoord = randomN.nextFloat() * ellipseHeight;
		float insideEllipseCond = (sq(pointXcoord - (ellipseWidth / 2)) / sq(ellipseWidth / 2)) + (sq(pointYcoord - (ellipseHeight / 2)) / sq(ellipseHeight / 2));
		
		while (insideEllipseCond > 1) {
			
			pointXcoord = randomN.nextFloat() * ellipseWidth;
			pointYcoord = randomN.nextFloat() * ellipseHeight;
			insideEllipseCond = (sq(pointXcoord - (ellipseWidth / 2)) / sq(ellipseWidth / 2)) + (sq(pointYcoord - (ellipseHeight / 2)) / sq(ellipseHeight / 2));
		}
		randomExample[0] = pointXcoord;
		randomExample[1] = pointYcoord;
		
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
				
				float distanceWinner = dist(randomExample[0], randomExample[1], winner.getWeights()[0], winner.getWeights()[1]);
				float distanceCurrent = dist(randomExample[0], randomExample[1], network[i][j].getWeights()[0], network[i][j].getWeights()[1]);
				
				if (distanceCurrent < distanceWinner) {
					winner = network[i][j];
				}
			}
		}
		return winner;
	}
	
	private void updateNetwork(Neuron winner, float[] randomExample) throws Exception {
		
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				
				float[] weightVariation = new float[2]; 
				weightVariation[0] = exp( - (((sq(i - winner.getCoord(0)) + sq(j - winner.getCoord(1))) / (2 * sq(sigma))))) * (randomExample[0] - network[i][j].getWeights()[0]);
				weightVariation[1] = exp( - (((sq(i - winner.getCoord(0)) + sq(j - winner.getCoord(1))) / (2 * sq(sigma))))) * (randomExample[1] - network[i][j].getWeights()[1]);
				
				network[i][j].getWeights()[0] = network[i][j].getWeights()[0] + epsilon * weightVariation[0];
				network[i][j].getWeights()[1] = network[i][j].getWeights()[1] + epsilon * weightVariation[1];
			}
		}
	}

}
