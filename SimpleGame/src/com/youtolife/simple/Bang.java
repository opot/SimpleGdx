package com.youtolife.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bang {

	private static final int FRAME_COLS = 3;
	private static final int FRAME_ROWS = 2;
	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;
	
	private int CUR_COLS = 0;
	private int CUR_ROWS = 0;

	Sprite[][] sprites;
	TextureRegion texture;

	float swapTime = 0.04f;
	float curTime = 0.04f;

	public Bang(float x, float y, float width, float height,float swapTime){
		texture = new TextureRegion(new Texture(
				Gdx.files.internal("data/burst.png")),128,96);
		TextureRegion[][] chars;
		this.swapTime = swapTime;
		chars = texture.split(WIDTH, HEIGHT);
		sprites = new Sprite[chars.length][chars[0].length];
		for(int i = 0;i<chars.length;i++)
			for(int j = 0;j<chars[0].length;j++){
				sprites[i][j] = new Sprite(chars[i][j]);
				sprites[i][j].setSize(width, height);
				sprites[i][j].setPosition(x, y);
			}
	}
	
	public Bang(float x, float y){
		texture = new TextureRegion(new Texture(
				Gdx.files.internal("data/burst.png")),128,96);
		TextureRegion[][] chars;
		chars = texture.split(WIDTH, HEIGHT);
		sprites = new Sprite[chars.length][chars[0].length];
		for(int i = 0;i<chars.length;i++)
			for(int j = 0;j<chars[0].length;j++){
				sprites[i][j] = new Sprite(chars[i][j]);
				sprites[i][j].setSize(0.06f, 0.06f);
				sprites[i][j].setPosition(x, y);
			}
	}

	public void render(SpriteBatch batch) {
		sprites[CUR_COLS][CUR_ROWS].draw(batch);
	}

	public boolean update(float w, float h) {
		curTime -= Gdx.graphics.getDeltaTime();
		if (curTime <= 0) {
			curTime += swapTime;
			CUR_COLS++;
			if (CUR_COLS == FRAME_COLS) {
				CUR_COLS = 0;
				CUR_ROWS++;
				if(CUR_ROWS == FRAME_ROWS){
					CUR_ROWS = 0;
					return true;
				}
			}
		}
		return false;
	}

	// public void dispose(){}

}
