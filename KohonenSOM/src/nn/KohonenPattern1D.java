package nn;

import java.util.Random;

import processing.core.PApplet;

public class KohonenPattern1D extends PApplet {

    public static void main(String[] args) {
	
	PApplet.main("nn.KohonenPattern1D");

    }
    
    private static Neuron[] network;
    
    static final int layerNeurons = 500;
    static final int dimInputSpace = 2;
    
    static final int windowSizeX = 350;
    static final int windowSizeY = 350;
    static final float scaleFactor = 300;
    static final int squareHeight = 1;
    
    static float epsilon = 0.2f;
    static float sigma = 18;
    static long iterationsCounter = 1;
    static int maxIterations = 90000;
    
    public void settings() {
	size(windowSizeX, windowSizeY);
    }
    
    public void setup() {
	
	frameRate(60);
//	fill(255);
	strokeWeight(1 / scaleFactor);
	
//	rectMode(CENTER); 
	// change random examples accordingly
	
//	translate(windowSizeX / 2, windowSizeY / 2);
	scale(scaleFactor);
	square(0, 0, squareHeight);
	
	try {
		network = networkInit();
	} catch (Exception e) {
		println(e.getMessage());
	}
	
	thread("startLearning");
	
    }
    
    public void draw() {
	
	background(200);
	scale(scaleFactor);
	square(0, 0, squareHeight);
	
	for (int i = 0; i < network.length - 1; i++) {
	   
	    line(network[i].weights[0], network[i].weights[1], network[i + 1].weights[0], network[i + 1].weights[1]);
	}
	
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

	if ((iterationsCounter % 100) == 0) {

	    println("iteration: " + iterationsCounter + "  fps: " + frameRate + "  time: " + hour() + ":" + minute()
		    + ":" + second());
	}

	iterationsCounter += 1;
	
//	delay(500);
	
    }
    
    public void startLearning() {
	
	while (epsilon >= 0.01f) {
	    
	    float[] randomExample = randomExample();

	    Neuron winner = minDistance(randomExample, network);

	    updateNetwork(winner, randomExample);

	    if ((iterationsCounter % 1000) == 0) {

		epsilon = epsilon * 0.999f;
		sigma = sigma * 0.96f;
		println("iteration: " + iterationsCounter + "  fps: " + frameRate + "  time: " + hour() + ":" + minute() + ":" + second());
	    }
	    
	    if (iterationsCounter == maxIterations) {
		
		println("stop while");
		break;
	    }
	    iterationsCounter += 1;
//	    delay(1);
	}
	
	println("stopped");
	noLoop();
	return;
    }
    
    
    private Neuron[] networkInit() throws Exception {
	
	Neuron[] network = new Neuron[layerNeurons];
	
	for (int i = 0; i < network.length; i++) {
	    
	    network[i] = new Neuron(dimInputSpace, i);
	    network[i].randomWeightsInit();
	}
	return network;
    }
    
    private Neuron minDistance(float[] randomExample, Neuron[] network) {

	Neuron winner = network[0];

	for (int i = 0; i < network.length; i++) {

	    float distanceWinner = dist(randomExample[0], randomExample[1], winner.weights[0], winner.weights[1]);
	    float distanceCurrent = dist(randomExample[0], randomExample[1], network[i].weights[0], network[i].weights[1]);

	    if (distanceCurrent < distanceWinner) {
		winner = network[i];
	    }
	}
	return winner;
    }
    
    private float[] randomExample() {

	Random randomN = new Random();
	float[] randomExample = new float[dimInputSpace];

//	randomExample[0] = (randomN.nextFloat() * squareHeight * 2) + (windowSizeX / 2) - squareHeight;
//	randomExample[1] = (randomN.nextFloat() * squareHeight * 2) + (windowSizeY / 2) - squareHeight;

	randomExample[0] = randomN.nextFloat() * squareHeight;
	randomExample[1] = randomN.nextFloat() * squareHeight;

	return randomExample;
    }
    
    private void updateNetwork(Neuron winner, float[] randomExample) {

	for (int i = 0; i < network.length; i++) {
	    
	    float[] weightVariation = new float[2];
	    
	    weightVariation[0] = exp(-((sq(i - winner.iCoord) / (2 * sq(sigma))))) * (randomExample[0] - network[i].weights[0]);
	    weightVariation[1] = exp(-((sq(i - winner.iCoord) / (2 * sq(sigma))))) * (randomExample[1] - network[i].weights[1]);

	    network[i].weights[0] = network[i].weights[0] + epsilon * weightVariation[0];
	    network[i].weights[1] = network[i].weights[1] + epsilon * weightVariation[1];

	}
    }

}
