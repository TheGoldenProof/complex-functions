package com.thegoldenproof.complexfunctions;

import org.apache.commons.numbers.complex.Complex;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	
	Pixmap inputPlane;
	Texture inputTex;
	
	Pixmap outputPlane;
	Texture outputTex;
	
	final int planeWidth = 400;
	final int planeHeight = 400;
	final int graphGap = 100;
	final int graphAxisWeight = 2;
	
	double max = Math.PI;
	double colorScale = 1/1d;
	
	static enum GradientMode {
		LinearH, LinearV, Radial;
	}
	final GradientMode mode = GradientMode.LinearH;
	
	public Complex f(Complex x) {
		return x.sqrt();
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		inputPlane = new Pixmap(planeWidth, planeHeight, Format.RGBA8888);
		outputPlane = new Pixmap(planeWidth, planeHeight, Format.RGBA8888);
		inputTex = new Texture(inputPlane);
		outputTex = new Texture(outputPlane);
	}

	@Override
	public void render () {
		
		for (int x = 0; x < planeWidth; x++) {
			for (int y = 0; y < planeHeight; y++) {
				double _x = (x-planeWidth/2d)/(planeWidth/2d)*max;
				double _y = (y-planeWidth/2d)/(planeWidth/2d)*max;
				int rgb = isGraphAxis(_x, _y)?0:defColorAt(_x, _y);
				inputPlane.drawPixel(x, y, Color.rgba8888(((rgb>>16)&0xFF)/255f,((rgb>>8)&0xFF)/255f,(rgb&0xFF)/255f,1));
				
				Complex output = f(Complex.ofCartesian(_x, _y));
				double _x2 = output.getReal();
				double _y2 = output.getImaginary();
				rgb = isGraphAxis(_x, _y)?0:defColorAt(_x2, _y2);
				outputPlane.drawPixel(x, y, Color.rgba8888(((rgb>>16)&0xFF)/255f,((rgb>>8)&0xFF)/255f,(rgb&0xFF)/255f,1));
			}
		}
		
		//max*=0.995f;
		
		inputTex.draw(inputPlane, 0, 0);
		outputTex.draw(outputPlane, 0, 0);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(inputTex, 0, 0);
		batch.draw(outputTex, planeWidth+graphGap, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		inputTex.dispose();
		outputTex.dispose();
		inputPlane.dispose();
		outputPlane.dispose();
	}
	
	private int defColorAt(double x, double y) {
		switch (mode) {
		case LinearH: return java.awt.Color.HSBtoRGB((float)(y*colorScale), 1, 1);
		case LinearV: return java.awt.Color.HSBtoRGB((float)(x*colorScale), 1, 1);
		default: {
				Vector2 v = new Vector2((float)x, (float)y);
				return java.awt.Color.HSBtoRGB((float)(v.len()*colorScale), 1, 1);
			}
		}
	}
	
	private boolean isGraphAxis(double x, double y) {
		return x == 0 || y == 0;
	}
}
