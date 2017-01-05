package com.tcc.othello.model;

public enum Players {

	HUMAN {
		@Override
		public String toString() {
			return "Humano";
		}
	},
	
	RANDOM {
		@Override
		public String toString() {
			return "Aleat�rio";
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
