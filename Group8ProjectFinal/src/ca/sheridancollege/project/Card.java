package ca.sheridancollege.project;

public class Card {
    
    private int value;
    private CardSuit suit;

    public Card() {
    }

    public Card(int value, CardSuit suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "Card{" + "value=" + value + ", suit=" + suit + '}';
    }
    
}
