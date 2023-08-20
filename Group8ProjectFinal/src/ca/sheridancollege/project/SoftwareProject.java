package ca.sheridancollege.project;

import java.util.Scanner;

public class SoftwareProject {
    
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scan = new Scanner(System.in);
        int playersCount = 2;
        
        for (int i = 1; i <= playersCount; i++) {
        System.out.print("Enter the name of player " + i + ": ");
        String playerName = scan.next();
    
            game.addPlayer(playerName);
        }

       // System.out.println(game);
        game.drawFromDeck(game.getPlayers().get(0));
        int playerTurn = 0; 
        
        
        while (game.getLastPlayerWithCards() != null) {
            game.Play(game, playerTurn);

            if (playerTurn == 0) {
                playerTurn = 1;
            } else {
                playerTurn = 0;
            }
        }
        //System.out.println(game);
        
    }
    
    
    
}
