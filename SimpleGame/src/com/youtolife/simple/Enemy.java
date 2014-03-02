package com.youtolife.simple;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
	Sprite sprite;
	float speed;
	
	public Enemy(Texture texture,float x,float y) {
		TextureRegion region = new TextureRegion(texture,0,0,50,64);
		sprite = new Sprite(region);
		sprite.setPosition(x,y);
		sprite.flip(false, true);
		sprite.setSize(0.1f, 0.2f);

		speed = new Random().nextFloat()+0.2f;
	}

	public boolean update(Player player){
		sprite.setY(sprite.getY()-speed*Gdx.graphics.getDeltaTime());
		if(player.sprite.getBoundingRectangle().overlaps(sprite.getBoundingRectangle()))
				return true;
		return false;
	}
	
	public void render(SpriteBatch batch){
		sprite.draw(batch);
	}
	
}
