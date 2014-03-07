package com.youtolife.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
	
	Sprite sprite;
	Texture texture;
	float speedX = 0f;
	float speedY = 2f;
	
	int damage = 40;
	
	public Bullet(float angle ,float x, float y){
		texture = new Texture(Gdx.files.internal("data/bullet.png"));
		sprite = new Sprite(texture);
		sprite.setSize(0.02f, 0.03f);
		sprite.setPosition(x, y);
		
		speedX = (float) (2f*Math.sin(Math.toRadians(angle)));
		speedY = (float) (2f*Math.cos(Math.toRadians(angle)));
		
	}
	
	public void dispose(){
		texture.dispose();
	}
	
	public void render(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	public void update(float h, float w){
		sprite.setY(sprite.getY()+speedY*Gdx.graphics.getDeltaTime());
		sprite.setX(sprite.getX()-speedX*Gdx.graphics.getDeltaTime());
	}
	
}
