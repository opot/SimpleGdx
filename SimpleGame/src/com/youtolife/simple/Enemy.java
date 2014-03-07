package com.youtolife.simple;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
	Sprite sprite;
	float speedY,speedX;
	
	int hp = 100;
	
	public Enemy(Texture texture,float x,float y) {
		TextureRegion region = new TextureRegion(texture,0,0,128,128);
		sprite = new Sprite(region);
		sprite.setPosition(x,y);
		sprite.flip(false, true);
		sprite.setSize(0.1f, 0.12f);
		sprite.setOrigin(0.05f, 0.06f);
		
		speedY = new Random().nextFloat()+0.2f;
		speedX = new Random().nextFloat();
		
		sprite.setRotation(-(float) Math.toDegrees(Math.atan(speedX/speedY)));
	}

	public boolean update(Player player){
		sprite.setY(sprite.getY()-speedY*Gdx.graphics.getDeltaTime());
		sprite.setX(sprite.getX()-speedX*Gdx.graphics.getDeltaTime());
		if (sprite.getX() < -0.5f){
			speedX*=-1;
			sprite.setRotation(-(float) Math.toDegrees(Math.atan(speedX/speedY)));
			sprite.setX(-0.499f);
		}
		if (sprite.getX() > 0.40f){
			speedX*=-1;
			sprite.setX(0.399f);
			sprite.setRotation(-(float) Math.toDegrees(Math.atan(speedX/speedY)));
		}
		
		if(player.sprite.getBoundingRectangle().overlaps(sprite.getBoundingRectangle()))
				return true;
		return false;
	}
	
	public void render(SpriteBatch batch){
		sprite.draw(batch);
	}
	
}
