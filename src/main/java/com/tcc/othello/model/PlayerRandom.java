package com.tcc.othello.model;

import java.util.ArrayList;
import java.util.Random;

public class PlayerRandom extends Player {

	Random random = new Random();
	
	@Override
	public void takeTurn(final ArrayList<Locale> moveOptions) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int move = random.nextInt(moveOptions.size());
				PlayerRandom.this.playerObservable.move(PlayerRandom.this, moveOptions.get(move));
			}
		}).start();
	}
}
