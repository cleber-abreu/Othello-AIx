package com.tcc.othello.global;

import javax.swing.JOptionPane;

import com.tcc.othello.gui.MainWindow;
import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Player;
import com.tcc.othello.model.PlayerHuman;
import com.tcc.othello.model.PlayerRandom;
import com.tcc.othello.model.Players;

public class Game {
	private static Player[] player;
	private static int mathCount = 0;
	private static int playCount = 0;
	private static int playerInAction = 1;
	
	public void start() {
		MainWindow.getGameboard().clearAll();
		mathCount++;
		playCount = 0;
		alterPlayerInAction();
		MainWindow.getDataPanel().setNumDiscs(2, 2);
		setPlayers();
		getPlayerInAction().addDisc(4, 5);
		getPlayerInAction().addDisc(5, 4);
		getOpponentPlayer().addDisc(4, 4);
		getOpponentPlayer().addDisc(5, 5);
		MainWindow.getGameboard().drawDiscs(getPlayerInAction().getDiscs());
		MainWindow.getGameboard().drawDiscs(getOpponentPlayer().getDiscs());
		run();
	}
	
	public void run() {
		while (true) {
			
			Rules.setMoveOptions(getPlayerInAction().getDiscs(), getOpponentPlayer().getDiscs());
			
			if (Rules.getMoveOptions().size() > 0) {
				MainWindow.getGameboard().drawMoveOptions(Rules.getMoveOptions());
				playCount++;
				System.out.println("Jogada "+playCount+" jogador "+playerInAction);
				
				if (getPlayerInAction().is(Players.HUMAN)) {
					((PlayerHuman) getPlayerInAction()).play(Rules.getMoveOptions(), MainWindow.getGameboard());
//					suspended = true;
//					synchronized (threadGame) {
//						while (suspended) {
//							System.out.println("ESPERANDO");
//							try {
//								threadGame.wait();
//								System.out.println(System.currentTimeMillis());
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							System.out.println("CLICOU!!");
//						}
//					}
					break;
				} else { 
					//Thread.sleep(3000);
					getPlayerInAction().play(Rules.getMoveOptions());
				}
				changeTurn();
				
				System.out.println("Placar "+player[0].getDiscs().size()+" x "+player[1].getDiscs().size());
			}
			else {
				Rules.setMoveOptions(getOpponentPlayer().getDiscs(), getPlayerInAction().getDiscs());
				if (Rules.getMoveOptions().size() > 0) {
//					JOptionPane.showMessageDialog(null, "Não há jogadas possiveis", "Passou a vez", JOptionPane.PLAIN_MESSAGE);
					System.out.println("Passou a vez");
					changeTurn();
				} else {
					JOptionPane.showMessageDialog(null, getWinningPlayer() + " venceu!");
					MainWindow.getDataPanel().enableSelectionPlayer(true);
					break;
				}
			}
			
		}
		
	}
	
	private static String getWinningPlayer() {
		if (player[0].getDiscs().size() > player[1].getDiscs().size())
			return "Jogador1(" + MainWindow.getDataPanel().getStringPlayer1() + ")";
		else	
			return "Jogador2(" + MainWindow.getDataPanel().getStringPlayer2() + ")";
	}

	public static void changeTurn() {
		MainWindow.getDataPanel().setNumDiscs(player[0].getDiscs().size(), player[1].getDiscs().size());
		MainWindow.getGameboard().clearMoveOptions(Rules.getMoveOptions());
		MainWindow.getGameboard().drawDiscs(getPlayerInAction().getDiscs());
		MainWindow.getGameboard().drawDiscs(getOpponentPlayer().getDiscs());
		alterPlayerInAction();
	}
	
	private static void setPlayers() {
		player = new Player[2];
		if (mathCount % 2 != 0) {
			player[0] = getTypePlayer(MainWindow.getDataPanel().getPlayer1(), FieldStatus.BLACK);
			player[1] = getTypePlayer(MainWindow.getDataPanel().getPlayer2(), FieldStatus.WHITE);
		} else {
			player[0] = getTypePlayer(MainWindow.getDataPanel().getPlayer1(), FieldStatus.WHITE);
			player[1] = getTypePlayer(MainWindow.getDataPanel().getPlayer2(), FieldStatus.BLACK);
		}
		MainWindow.getDataPanel().setColorsPlayersDiscs(player[0].getColorDisc(), player[1].getColorDisc());
	}
	
	private static Player getTypePlayer(Players typePlayer, FieldStatus colorDisc) {
		switch (typePlayer) {
		case RANDOM:
			return new PlayerRandom(colorDisc);
		default:
			return new PlayerHuman(colorDisc);
		}
	}
		
	private static void alterPlayerInAction() {
		playerInAction = 1 - 1 * playerInAction;
	}
	
	private static Player getPlayerInAction() {
		return player[playerInAction];
	}
	
	public static Player getOpponentPlayer() {
		return player[1 - 1 * playerInAction];
	}

}
