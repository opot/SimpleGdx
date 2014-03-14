package com.youtolife.simple;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bonus {

	Sprite sprite;
	Texture texture;
	
	float speedX,speedY;
	
	public Bonus(float x, float y) {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		
		texture = new Texture(Gdx.files.internal("data/bonus.png"));
		sprite = new Sprite(texture);
		sprite.setSize(0.05f, 0.05f);
		sprite.setPosition(x,y);
		
		speedY = new Random().nextFloat()+0.2f;
		speedX = new Random().nextFloat();
	}

	public boolean update(Player player ,float h, float w){
		sprite.setY(sprite.getY()-speedY*Gdx.graphics.getDeltaTime());
		sprite.setX(sprite.getX()-speedX*Gdx.graphics.getDeltaTime());
		if (sprite.getX() < -0.5f){
			speedX*=-1;
			sprite.setX(-0.499f);
		}
		if (sprite.getX() > 0.40f){
			speedX*=-1;
			sprite.setX(0.399f);
		}
		
		if(player.sprite.getBoundingRectangle().overlaps(sprite.getBoundingRectangle()))
			return true;
		return false;
	}
	
	public void render(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	public void dispose(){
		texture.dispose();
	}
}
