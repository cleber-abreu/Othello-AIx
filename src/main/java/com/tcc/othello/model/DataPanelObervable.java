package com.tcc.othello.model;

public interface DataPanelObervable {
	void onNewGame(PlayerType blackPlayer, PlayerType whitePlayer);
}
