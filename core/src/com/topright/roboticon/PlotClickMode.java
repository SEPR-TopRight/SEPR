package com.topright.roboticon;

/**
 * An enumerated type used to specify what action should be performed when a plot is clicked on.
 * @author jcn509
 */
enum PlotClickMode {AQUIRE("aquire"), PLACEROBOTICON("placeroboticon"), NOACTION("noaction");
	private final String name; // A string denoting the name of the category (used when throwing exceptions)
	
	private PlotClickMode(String s) {
		name = s;
	}
}
