import org.lwjgl.*;

public class Main {

	

	/**
	 * Method containing the main game loop. This is where the core of the game is run from.
	 * 
	 * @author andrew
	 */
	public void gameLoop(){
    }


		public void run() {
			System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		}


        /**
         * Start of the program. Calls for graphics to be loaded, windows to be drawn,
         * then finally calls for the game to start.
         *
         * @author andrew
         * 
         * @param args
         */
		public static void main(String[] args) {
			new Main().run();
		}


}
