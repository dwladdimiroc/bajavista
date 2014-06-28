/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eda;

/**
 *
 * @author Pablo
 */
public class Hamming
{
    private String compOne;
    private String compTwo;
 
    public Hamming(String one, String two)
    {
        compOne = one;
        compTwo = two;
    }
 
    ///
    //  Calculating the Hamming Distance for two strings requires the string to be of the same length.
    ///
    public int getHammingDistance()
    {
        if (compOne.length() > compTwo.length())
        {
            int dif = compOne.length() - compTwo.length() +1;
            for (int i = 0; i < dif; i++) {
                compTwo = compTwo.concat(" ");
            }
        }
        
        if (compTwo.length() > compOne.length())
        {
            int dif = compTwo.length() - compOne.length() +1;
            for (int i = 0; i < (compTwo.length() - compOne.length()) +1 ; i++) {
                compOne = compOne.concat(" ");   
            }
        }
 
        int counter = 0;
 
        for (int i = 0; i < compOne.length(); i++)
        {
            if (compOne.charAt(i) != compTwo.charAt(i)) counter++;
        }
 
        return counter;
    }
} 