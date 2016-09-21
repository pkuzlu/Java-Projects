/**
 ******************************************************************************
 *                    Magic of Perfect Shuffles
 ******************************************************************************
 *
 *
 *
 * Name:         Pinar Kuzlu
 *
 *
 ******************************************************************************/

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class PerfectShuffle
{
    private int[] deck;

    PerfectShuffle(int num)
    {
        deck = new int[num];
        for(int i = 0; i< num; i++)
        {
            deck[i] = i;
        }
    }

    public int[] inShuffle(int[] input)
    {
        return shuffle("in", input);
    }

    public int perfectInShuffle()
    {
        try {
            return perfectShuffle("in");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int[] outShuffle(int[] input)
    {
        return shuffle("out", input);
    }

    public int perfectOutShuffle()
    {
        try {
            return perfectShuffle("out");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int[] shuffle(String shuffleType, int[] input)
    {
        int index1 = -1, index2 = -1;
        switch (shuffleType)
        {
            case "in":
            {
                index1 = 1;
                index2 = 0;
                break;
            }

            case "out":
            {
                index1 = 0;
                index2 = 1;
            }
        }

        ArrayList<ArrayList<Integer>> newHalvedDeck = new ArrayList<ArrayList<Integer>>(2);
        int[] newDeck = new int[input.length];
        newHalvedDeck = halveDeck(input);
        int k = 0;
        for (int i = 0; i < input.length / 2; i++)
        {
            newDeck[k] = newHalvedDeck.get(index1).get(i);
            k++;
            newDeck[k] = newHalvedDeck.get(index2).get(i);
            k++;
        }
        return newDeck;
    }

    public int perfectShuffle(String shuffleType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method m = null;
        switch (shuffleType)
        {
            case "in":
                m = PerfectShuffle.class.getMethod("inShuffle", int[].class);
                break;
            case "out":
                m = PerfectShuffle.class.getMethod("outShuffle", int[].class);
                break;
            default:
                break;
        }
        int[] originalDeck = new int[deck.length];
        System.arraycopy(deck, 0, originalDeck, 0, deck.length);
        int counter = 0;
        do
        {
            deck = (int[]) m.invoke(this, deck);
            counter ++;
        }while(!Arrays.equals(originalDeck, deck));

        return  counter;
    }

    ArrayList<ArrayList<Integer>> halveDeck(int[] deck)
    {
        ArrayList<Integer> firstHalf = new ArrayList<Integer>(deck.length/2);
        ArrayList<Integer> secondHalf = new ArrayList<Integer>(deck.length/2);
        ArrayList<ArrayList<Integer>> newHalvedDeck = new ArrayList<ArrayList<Integer>>(2);

        for(int i = 0; i < deck.length; i ++)
        {
            if(i <= (deck.length/2 - 1))
                firstHalf.add(deck[i]);
            else
                secondHalf.add(deck[i]);
        }

        newHalvedDeck.add(firstHalf);
        newHalvedDeck.add(secondHalf);
        return  newHalvedDeck;
    }

    public int[] moveTopCard(int pos)
    {
        String binaryPos = Integer.toBinaryString(pos);
        int[] newDeck = new int[deck.length];
        for (int i=0; i < binaryPos.length(); i++)
        {
            if(i == 0)
                System.arraycopy(deck, 0, newDeck, 0, deck.length);

            if(binaryPos.charAt(i) == '1')
                newDeck = inShuffle(newDeck);

            else
                newDeck = outShuffle(newDeck);
        }
        return newDeck;
    }

}