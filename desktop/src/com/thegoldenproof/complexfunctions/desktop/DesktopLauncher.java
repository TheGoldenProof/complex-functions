package com.thegoldenproof.complexfunctions.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.thegoldenproof.complexfunctions.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 900;
		config.height = 400;
		
		config.x = (1920-config.width)/2;
		config.y = (1080-config.height)/2;
		new LwjglApplication(new Main(), config);
	}
}
