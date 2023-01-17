package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.*;
import static test.BlackJack_.Card.*;

import org.junit.Test;

public class BlackJack_ {
    
    @Test
    public void given_one_card_should_calculate_value() {
       assertEquals(5, createHand(_5).value());
       assertEquals(6, createHand(_6).value());
       assertEquals(10, createHand(Jack).value());
       assertEquals(10, createHand(Queen).value());
       assertEquals(10, createHand(King).value());
       assertEquals(11, createHand(Ace).value());
    }
    
    @Test
    public void given_two_cards_should_calculate_value() {
       assertEquals(11, createHand(_5, _6).value());        
       assertEquals(12, createHand(Ace, Ace).value());        
    }
    
    @Test 
    public void given_two_cards_should_determine_if_is_black_jack() {
       assertEquals(false, createHand(_5, _6).isBlackJack());        
       assertEquals(true, createHand(Ace, Queen).isBlackJack());               
    }

    @Test 
    public void given_three_cards_should_determine_that_is_not_black_jack() {
       assertEquals(false, createHand(_5, _6, Queen).isBlackJack());               
    }
    
    @Test 
    public void given_two_cards_should_determine_that_is_not_bust() {
       assertEquals(false, createHand(_4,_3).isBust());               
    }
    
    @Test 
    public void given_three_cards_should_determine_that_is_bust_or_not() {
       assertEquals(true, createHand(_4, Jack, King).isBust());               
       assertEquals(false, createHand(_4, _2, _3).isBust());               
    }
    
    @Test
    public void should_say_that_winner_is_1 () {
        List<Hand> winners = new ArrayList<Hand>();
        Hand player1 = createHand(Jack,Ace);
        Hand player2 = createHand(_10,_5,_6);
        Hand player3 = createHand(_3,_6,Ace,_3,Ace,King);
        Hand croupier = createHand(_9,_7);
        Hand deck = createHand(_5,_4,King,_2);
        winners.add(player1);
        assertEquals(winners,getWinners(player1,player2,player3,croupier,deck));
    }
    
    @Test
    public void should_be_unstacked () {
        assertEquals(10,createHand(_10,_5,_6).pop().value());
    }
    
    @Test
    public void should_be_unstacked2 () {
        Hand deck = createHand(_10,_6,Ace,_5);
        Hand stack = createHand();
        Hand result = createHand(_10,_6,Ace);
        unstack(stack,deck);
        assertEquals(result.value(),stack.value());
    }
    
    @Test 
    public void should_say_that_winners_are_1_and_3 () {
        List<Hand> winners = new ArrayList<Hand>();
        Hand player1 = createHand(_10,King);
        Hand player2 = createHand(_10,_2,_6);
        Hand player3 = createHand(_8,_8,_5);
        Hand croupier = createHand(_5,_10);
        Hand deck = createHand(Ace,_3,King,_2);
        winners.add(player1);
        winners.add(player3);

        assertEquals(winners, getWinners(player1,player2,player3,croupier,deck));
    }
    
    private List<Hand> getWinners (Hand player1, Hand player2, Hand player3, Hand Croupier, Hand deck){
        List<Hand> winners = new ArrayList<Hand>();
        List<Hand> players = new ArrayList<Hand>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        
        unstack(Croupier,deck);
        
        if (Croupier.isBlackJack()){
            return winners;
        }
        
        for(Hand hand : players){
            if(hand.isBlackJack()){
                winners.add(hand);
            }
            
            if(! hand.isBust()){
                if(Croupier.isBust()) winners.add(hand);
                if(hand.value() > Croupier.value()) winners.add(hand);
            }
 
        }
        return winners;
    }
        
    private Hand createHand(Card... cards) {
        return new Hand(cards);
    }

    private void unstack(Hand Croupier, Hand deck) {
        while(Croupier.value() < 17){
            Croupier.add(deck.pop());
        }
    }


    
    public enum Card {
        Ace, _2, _3, _4, _5, _6, _7, _8, _9, _10, Jack, Queen, King;

       
        public boolean isFace() {
            return this == Jack || this == Queen || this == King;            
        }

        public int value() {
            return isFace() ? 10 : ordinal() + 1;
        }   
    }  
}
