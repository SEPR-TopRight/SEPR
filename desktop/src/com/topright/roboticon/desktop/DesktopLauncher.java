package com.topright.roboticon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.topright.roboticon.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1680;
		config.height = 1050;
		new LwjglApplication(new Main(), config);
		
		//TODO: load all resources (images etc)
		
		//TODO: call the gameloop
	}
}