package com.youtolife.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BitmapNumeric {

	Sprite[] chars = new Sprite[10];
	Texture texture;
	
	
	public BitmapNumeric(float Size,Color color) {
		texture = new Texture(Gdx.files.internal("data/font.png"));
		TextureRegion region = new TextureRegion(texture,320,32);
		TextureRegion[][] bufchars = region.split(32, 32);
		for(int i = 0;i<=9;i++){
			chars[i] = new Sprite(bufchars[0][i]);
			chars[i].setSize(Size, Size);
			chars[i].setColor(color);
		}
	}

	public void drawString(SpriteBatch batch, String str,float x, float y){
		for(int i = 0;i<=str.length()-1;i++){
			chars[str.charAt(i)-'0'].setPosition(x+chars[str.charAt(i)-'0'].getWidth()*i, y);
			chars[str.charAt(i)-'0'].draw(batch);
		}
	}
	
	public float getSize(){
		return chars[0].getWidth();
	}
	
	public void setColor(Color color){
		for(int i = 0;i<=9;i++)
			chars[i].setColor(color);
	}

	public void dispose(){
		texture.dispose();
	}
}
