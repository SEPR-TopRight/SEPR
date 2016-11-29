package com.topright.roboticon;
/**
 * An enumerated type used when dispatching and receiving messages. Represents the current state of the game.
 * @author jcn509
 */
enum GameEvents {PLOTAQUIRED("plotaquired"), FINISHEDBUYINGROBOTICONS("finishedbuyingroboticons"), FINISHEDCUSTOMISINGROBOTICONS("finishedcustomisingroboticons"),FINISHEDPLACINGROBOTICONS("finishedplacingroboticons"),FINISHEDWITHTHEMARKET("finishedwiththemarket");
	private final String name; // A string denoting the name of the category (used when throwing exceptions)
	
	private GameEvents(String s) {
		name = s;
	}
}

