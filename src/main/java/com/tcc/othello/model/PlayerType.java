package com.tcc.othello.model;

public enum PlayerType {

	HUMAN {
		@Override
		public String toString() {
			return "Humano";
		}
	},
	
	RANDOM {
		@Override
		public String toString() {
			return "Aleatório";
		}
	},
	
	MINIMAX {
		@Override
		public String toString() {
			return "MiniMax";
		}
	},
	
	QLEARNING {
		@Override
		public String toString() {
			return "QLearning";
		}
	}
}
