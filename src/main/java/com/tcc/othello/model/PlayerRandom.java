package com.tcc.othello.model;

import java.util.Random;

public class PlayerRandom extends Player {

	Random random = new Random();
	
	@Override
	public void takeTurn() {
		Thread a = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				va();
			}
		});
		
		a.start();
	}
	
	private void va() {
		PlayerRandom.this.playerObservable.move(this, new Locale(random.nextInt(8), random.nextInt(8)));
	

	}
	
}
