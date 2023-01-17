package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import test.BlackJack_.Card;
import static test.BlackJack_.Card.Ace;

public class Hand {
    private List<Card> cartas; 
            
    public Hand(Card... cards){
       cartas = new ArrayList<Card>();
       for(Card carta: cards){
           cartas.add(carta);
       }
    }
    
    public int value() {
        return canUseAceExtendedValue() ? sum() + 10 : sum();
    }

    int sum() {
        return cartas.parallelStream().mapToInt(c -> c.value()).sum();
    }

    private boolean canUseAceExtendedValue() {
        return sum() <= 11 && containsAce();
    }

    private boolean containsAce() {
        return cartas.parallelStream().anyMatch(c -> c == Ace);
    }


    public boolean isBlackJack() {
        return value() == 21 && cartas.size() == 2;
    }

    public boolean isBust() {
        return value() > 21;
    }

    public Card pop() {
        BlackJack_.Card carta = cartas.get(0);
        cartas.remove(carta);
        return carta;
    }


    public void add(BlackJack_.Card card) {
        cartas.add(card);
    }
}
