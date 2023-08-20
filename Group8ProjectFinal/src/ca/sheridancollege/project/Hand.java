package ca.sheridancollege.project;

import java.util.ArrayList;

public class Hand extends ArrayList<Card>{
    
    public Hand() {
        super();
    }
    
    public Hand(Card...cards) {
        for(Card card: cards) {
            this.add(card);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hand{");
        for(Card card: this){
            sb.append(card + "|");
        }
        sb.append('}');
        return sb.toString();
    }
    
}
