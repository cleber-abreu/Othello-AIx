package com.tcc.othello.model;

import java.util.Random;

public class PlayerRandom extends Player {

	Random random = new Random();
	
	@Override
	public void takeTurn() {
		playerObservable.move(this, new Locale(random.nextInt(8), random.nextInt(8)));
	}
	
}
