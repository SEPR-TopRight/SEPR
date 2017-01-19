package com.topright.roboticon;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.mockito.Mockito;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


/**
 * Base class that is extended by all classes that wish to test LibGDX components
 * Obtained from: http://manabreak.eu/java/2016/10/21/unittesting-libgdx.html
 * @author jcn509
 */

public class GuiTest {
    // This is our "test" application
    private static Application application;

    // Before running any tests, initialize the application with the headless backend
    @BeforeClass
    public static void init() {
        // Note that we don't need to implement any of the listener's methods
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        },null);

        // Use Mockito to mock the OpenGL methods since we are running headlessly
       Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    // After we are done, clean up the application
    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }
    
    protected static void clickActor(Actor actor) {
	    Array<EventListener> listeners = actor.getListeners();
	    for(int listener=0;listener<listeners.size;listener++)
	    {
	    	// Ignore other types of listener
	    	if(listeners.get(listener) instanceof ClickListener){
	        	
	        	// Simulate the button click
	            ((ClickListener)listeners.get(listener)).clicked(null, 0, 0);
	        }
	    }
	}
}