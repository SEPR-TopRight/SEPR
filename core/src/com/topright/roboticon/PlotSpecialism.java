package com.topright.roboticon;
/**
 * An enumerated type used to denote the type of resource a plot is best at producing
 * @author jcn509
 */
enum PlotSpecialism {ORE("ore"), ENERGY("energy");
	private final String name; // A string denoting the name of the category (used when throwing exceptions)
	
	private PlotSpecialism(String s) {
		name = s;
	}
}

