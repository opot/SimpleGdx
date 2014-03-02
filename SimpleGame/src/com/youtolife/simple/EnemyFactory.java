package com.youtolife.simple;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EnemyFactory implements Runnable {

	boolean isAlive = true;
	Vector<Enemy> enemies;
	Random random = new Random();
	Texture sprite;

	public EnemyFactory(Vector<Enemy> enemies, Texture sprite) {
		this.sprite = sprite;
		this.enemies = enemies;
	}

	@Override
	public void run() {
		while(isAlive){
			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(enemies){
				float x = random.nextFloat()*2-1;
				float y = Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
				enemies.add(new Enemy(sprite,x,y));
			}
		}
	}

	public void Kill() {
		isAlive = false;
	}

}
