package com.youtolife.simple;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EnemySpawner {

	Vector<Enemy> enemies;
	Texture texture;
	
	float nextSpawn = 3;
	int level = 0;
	
	
	public EnemySpawner(Vector<Enemy> enemies,Texture texture){
		this.enemies = enemies;
		this.texture = texture;
	}
	
	public void update(GamePlayState game,float w, float h){
		nextSpawn-=Gdx.graphics.getDeltaTime();
		if(nextSpawn<=0){
			Random r = new Random();
			int scheme = r.nextInt(3);
			if(scheme == 0) createV(w,h,r);
			if(scheme == 1) createLine(w,h,r);
			if(scheme == 2) createRow(w,h,r);
			nextSpawn +=r.nextInt(6)+0.5f;
		}
		level = (int)(game.Score/20);
	}

	private void createLine(float w, float h, Random r) {
		int n = 1+r.nextInt(4);
		float width = n*0.05f;
		float height = width*Enemy.ratio;
		float delt = r.nextInt(4)*0.02f*(r.nextInt(2)*2-1);
		for(int i = 0; i<(int)(1f/width/2);i++){
			enemies.add(new Enemy(texture,i*2*width-0.5f,h/w+delt*i,width,height,0,1/width*0.05f));
			enemies.get(enemies.size()-1).hp = (n+level)*25;
		}
	}

	private void createV(float w, float h, Random r) {
		int n = 1+r.nextInt(4);
		float width = n*0.05f;
		float height = width*Enemy.ratio;
		float delt = (r.nextInt(4)+1)*0.03f*(r.nextInt(2)*2-1)+height;
		for(int i = 0; i<=(int)(1f/width/4);i++){
			enemies.add(new Enemy(texture,i*2*width-0.5f,h/w+delt*i,width,height,1/width*0.02f,1/width*0.04f));
			enemies.add(new Enemy(texture,-i*2*width+0.5f,h/w+delt*i,width,height,-1/width*0.02f,1/width*0.04f));
			enemies.get(enemies.size()-1).hp = (n+level)*25;
		}
	}

	private void createRow(float w, float h, Random r) {
		int n = 1+r.nextInt(4);
		float width = n*0.05f;
		float height = width*Enemy.ratio;
		float delt = (r.nextInt(4)+1)*0.03f*(r.nextInt(2)*2-1)+height*2;
		float xd = r.nextInt((int) (5-n))*width*2+width;
		float lineVel = r.nextInt(4)*0.07f;
		for(int i = 0; i<=(int)(1f/width/4);i++){
			enemies.add(new Enemy(texture,-xd,h/w+delt*i,width,height,lineVel,1/width*0.04f));
			enemies.add(new Enemy(texture,xd-width,h/w+delt*i,width,height,-lineVel,1/width*0.04f));
			enemies.get(enemies.size()-1).hp = (n+level)*25;
		}
	}
	
}
