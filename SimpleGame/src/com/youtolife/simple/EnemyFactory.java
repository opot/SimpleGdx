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
	GamePlayState game;

	public EnemyFactory(GamePlayState game, Vector<Enemy> enemies, Texture sprite) {
		this.sprite = sprite;
		this.enemies = enemies;
		this.game = game;
	}

	@Override
	public void run() {
		while(isAlive){
			try {
				Thread.sleep(random.nextInt((int) (2000-game.Score)));
				System.out.println(game.Score);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(enemies){
				float x = random.nextFloat()*0.96f-0.5f;
				float y = Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
				enemies.add(new Enemy(sprite,x,y));
			}
		}
	}

	public void Kill() {
		isAlive = false;
	}

}
