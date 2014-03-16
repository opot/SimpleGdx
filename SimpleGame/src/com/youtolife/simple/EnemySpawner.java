package com.youtolife.simple;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EnemySpawner {

	Vector<Enemy> enemies;
	Texture texture;
	
	float nextSpawn = 3;
	
	public EnemySpawner(Vector<Enemy> enemies,Texture texture){
		this.enemies = enemies;
		this.texture = texture;
	}
	
	public void update(float w, float h){
		nextSpawn-=Gdx.graphics.getDeltaTime();
		//System.out.println(nextSpawn);
		if(nextSpawn<=0){
			Random r = new Random();
			//int scheme = r.nextInt(3);
			int scheme = 1;
			if(scheme == 0) createV(w,h,r);
			if(scheme == 1) createLine(w,h,r);
			if(scheme == 2) createRow(w,h,r);
			nextSpawn +=5;
		}
	}

	private void createLine(float w, float h, Random r) {
		float width = (1+r.nextInt(4))*0.05f;
		float height = width*Enemy.ratio;
		float delt = r.nextInt(4)*0.02f*(r.nextInt(2)*2-1);
		for(int i = 0; i<=(int)(1f/width/2);i++){
			enemies.add(new Enemy(texture,i*2*width-0.5f,h/w+delt*i,width,height,0,width*5));
		}
	}

	private void createV(float w, float h, Random r) {
		// TODO Auto-generated method stub
		
	}

	private void createRow(float w, float h, Random r) {
		// TODO Auto-generated method stub
		
	}
	
}
