package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Game {
    
    private ArrayList<Player> players;
    private ArrayList<Card> deck;
    private ArrayList<Card> discardDeck;
    private Card rulerCard;

    public Game() {
        this.players = new ArrayList<>();
        this.deck = new ArrayList<>();
        generateDeck();
        shuffleDeck();
        this.discardDeck = new ArrayList<>();
        generateRulerCard();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getDiscardDeck() {
        return discardDeck;
    }

    public void setDiscardDeck(ArrayList<Card> discardDeck) {
        this.discardDeck = discardDeck;
    }

    public Card getRulerCard() {
        return rulerCard;
    }

    public void setRulerCard(Card rulerCard) {
        this.rulerCard = rulerCard;
    }
    
    private void generateDeck(){
        for(int i=0; i<4; i++){
            for(int j=1; j<=12; j++){
                if(j==8 || j==9)
                    continue;
                switch(i){
                    case 0:
                        deck.add(new Card(j, CardSuit.SWORD));
                        break;
                    case 1:
                        deck.add(new Card(j, CardSuit.CUP));
                        break;
                    case 2:
                        deck.add(new Card(j, CardSuit.COIN));
                        break;
                    case 3:
                        deck.add(new Card(j, CardSuit.CLUB));
                        break;
                }
            }
        }
    }
    
    private void shuffleDeck(){
        Collections.shuffle(deck);
    }
    
    private void generateRulerCard(){
        Random random = new Random();
        int index = random.nextInt(0, this.deck.size());
        this.rulerCard = deck.remove(index);
    }
    
    public void addPlayer(String name){
        Player player = new Player();
        player.setName(name);
        player.setHand(new Hand());
        generatePlayerHand(player);
        this.players.add(player);
    }
    
    private void generatePlayerHand(Player player){
        Random random = new Random();
        int index = 0;
        for(int i=0; i<5; i++){
            index = random.nextInt(0, this.deck.size());
            player.getHand().add(deck.remove(index));
        }
    }
    
    public boolean drawFromDeck(Player player){
        if(!deck.isEmpty()){
            player.getHand().add(deck.remove(0));
            return true;
        }
        return false;
    }
    
    public boolean drawFormDiscard(Player player){
        if(!discardDeck.isEmpty()){
            player.getHand().addAll(discardDeck);
            cleanDiscard();
            return true;
        }
        return false;
    }
    
    public void cleanDiscard(){
        discardDeck.clear();
    }
    
    public static void Play(Game game, int playerTurn) {
    Player currentPlayer = game.getPlayers().get(playerTurn);
    Player otherPlayer = game.getPlayers().get(1 - playerTurn);
    Scanner scanner = new Scanner(System.in);

    while (true) {
        System.out.println("-------------------------------------------");
        System.out.println("Current card on the table: " + game.getRulerCard());
        System.out.println("-------------------------------------------");
        currentPlayer.displayHand();

        boolean foundMatchingSuit = currentPlayer.getHand().stream()
                    .anyMatch(card -> card.getSuit() == game.getRulerCard().getSuit());

            if (!foundMatchingSuit) {
                System.out.println("No matching suit in hand. Drawing from deck...");
                while (!foundMatchingSuit && game.drawFromDeck(currentPlayer)) {
                    currentPlayer.displayHand();
                    foundMatchingSuit = currentPlayer.getHand().stream()
                            .anyMatch(card -> card.getSuit() == game.getRulerCard().getSuit());
                }
            }

            int cardIndex = -1;
            while (cardIndex < 1 || cardIndex > currentPlayer.getHand().size()) {
                System.out.print(currentPlayer.getName() + ", enter the index of the card to play: ");
                try {
                    cardIndex = scanner.nextInt();
                    if (cardIndex < 1 || cardIndex > currentPlayer.getHand().size()) {
                        System.out.println("Invalid card index. Please enter a valid index.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid index.");
                    scanner.next(); // Clear the invalid input from the scanner
                }
            }

            Card playCard;
            try {
                playCard = currentPlayer.getHand().get(cardIndex - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid card index. Please select a valid card.");
                continue;
            }

            if (playCard.getSuit() != game.getRulerCard().getSuit()) {
                System.out.println("Selected card does not match the suit of the card on the table. Please select a valid card.");
            } else {
            currentPlayer.getHand().remove(cardIndex - 1);
            System.out.println("-------------------------------------------");
            System.out.println(currentPlayer.getName() + "Selected card: " + playCard);
            System.out.println("-------------------------------------------");
            currentPlayer.setPlayCard(playCard);

            System.out.println("Switching to " + otherPlayer.getName());
            System.out.println("Current card on the table: " + game.getRulerCard());
            otherPlayer.displayHand();

            foundMatchingSuit = otherPlayer.getHand().stream()
                    .anyMatch(card -> card.getSuit() == game.getRulerCard().getSuit());

            if (!foundMatchingSuit) {
                System.out.println("No matching suit in hand. Drawing from deck...");
                while (!foundMatchingSuit && game.drawFromDeck(otherPlayer)) {
                    otherPlayer.displayHand();
                    foundMatchingSuit = otherPlayer.getHand().stream()
                            .anyMatch(card -> card.getSuit() == game.getRulerCard().getSuit());
                }
            }

            System.out.print(otherPlayer.getName() + ", enter the index of the card to play: ");
            int otherCardIndex = scanner.nextInt();
            Card otherPlayCard = otherPlayer.getHand().get(otherCardIndex - 1);

            if (otherPlayCard.getSuit() != game.getRulerCard().getSuit()) {
                System.out.println("Selected card does not match the suit of the card on the table. Please select a valid card.");
            } else {
                otherPlayer.getHand().remove(otherCardIndex - 1);
                System.out.println("-------------------------------------------");
                System.out.println(otherPlayer.getName() + "Selected card: " + otherPlayCard);
                System.out.println("-------------------------------------------");
                otherPlayer.setPlayCard(otherPlayCard);

                int comparisonResult = playCard.getValue() - otherPlayCard.getValue();

                System.out.println("Bigger Card: " + (comparisonResult > 0 ? playCard : otherPlayCard));
                System.out.println("Smaller Card placed in " + (comparisonResult > 0 ? otherPlayer.getName() : currentPlayer.getName()) + "'s hand");
                if (comparisonResult > 0) {
                    System.out.println(currentPlayer.getName() + "'s hand:");
                    currentPlayer.displayHand();

                    System.out.print(currentPlayer.getName() + ", enter the index of the card to Throw: ");
                    int keepCardIndex = scanner.nextInt();

                    Card keepCard = currentPlayer.getHand().remove(keepCardIndex - 1);
                    game.setRulerCard(keepCard);
                    //currentPlayer.getHand().add(0, keepCard);
                } else if (comparisonResult < 0) {
                    System.out.println(otherPlayer.getName() + "'s hand:");
                    otherPlayer.displayHand();
                    System.out.print(otherPlayer.getName() + ", enter the index of the card to Throw: ");
                    int keepCardIndex = scanner.nextInt();
                    Card keepCard = otherPlayer.getHand().remove(keepCardIndex - 1);
                    otherPlayer.getHand().add(0, keepCard);
                    game.setRulerCard(keepCard);
                } else {
                    System.out.println("It's a tie!");
                }

                break; // Exit the loop as the turn is completed
            }
        }
    }
}

    public Player getLastPlayerWithCards() {
        for (int i = players.size() - 1; i >= 0; i--) {
            if (!players.get(i).getHand().isEmpty()) {
                return players.get(i);
            }
        }
        return null; // Si no hay jugadores con cartas, devuelve null
    }

    @Override
    public String toString() {
        return "Game{" + "players=" + players + ", deck=" + deck + ", discardDeck=" + discardDeck + ", rulerCard=" + rulerCard + '}';
    }
}
