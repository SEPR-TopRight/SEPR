package com.topright.roboticon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.topright.roboticon.Main;

/**
 * The class that must be run in order to actually run the game. Instantiates the main class
 * and creates a window.
 * An executable version of the game can be found here: https://sepr-topright.github.io/SEPR/documentation/assessment2/TRCGame.zip
 * @author jcn509
 *
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1680;
		config.height = 1050;
		config.fullscreen = true;
		new LwjglApplication(new Main(), config);
	}
}
