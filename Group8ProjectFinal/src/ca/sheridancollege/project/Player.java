package ca.sheridancollege.project;

public class Player {
    
    private String name;
    private Hand hand;
    private Card playCard;

    public Player() {
    }

    public Player(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }

    public Card getPlayCard() {
        return playCard;
    }

    public void setPlayCard(Card playCard) {
        this.playCard = playCard;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
    
    public void displayHand() {
        System.out.println("Cards in " + name + "'s hand:");
        int count = 1;
        for (Card card : hand) {
            System.out.println(count + " - " + card.getValue() + " of " + card.getSuit());
            count = count + 1;
        }
    }
    
    public boolean hasPlayableCard(){
        return true;
    }
    
    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", hand=" + hand + '}';
    }
    
}
