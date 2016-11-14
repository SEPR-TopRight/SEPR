/**
 * An enumerated type used to denote the customisation type of roboticons
 * @author jcn509
 *
 */
enum RoboticonCustomisation {ORE("ore"), ENERGY("energy"), UNCUSTOMISED("uncustomised");
	private final String name; // A string denoting the name of the category (used when throwing exceptions)
	
	private RoboticonCustomisation(String s) {
		name = s;
	}
}

