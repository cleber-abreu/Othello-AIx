package com.tcc.othello.model;

import java.util.Random;

public class PlayerRandom extends Player {

	Random random = new Random();
	
	@Override
	public void takeTurn() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				PlayerRandom.this.playerObservable.move(PlayerRandom.this, new Locale(random.nextInt(8), random.nextInt(8)));
			}
		}).start();
	}
}
