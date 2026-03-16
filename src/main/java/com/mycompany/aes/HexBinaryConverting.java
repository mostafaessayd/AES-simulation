/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aes;

import java.util.ArrayList;

/**
 *
 * @author essay
 */
public class HexBinaryConverting {
    public ArrayList<String> hexChars = new ArrayList<>();
    public ArrayList<String> binaryStrings = new ArrayList<>();

    public HexBinaryConverting() {
        hexChars.add("0"); binaryStrings.add("0000");
        hexChars.add("1"); binaryStrings.add("0001");
        hexChars.add("2"); binaryStrings.add("0010");
        hexChars.add("3"); binaryStrings.add("0011");
        hexChars.add("4"); binaryStrings.add("0100");
        hexChars.add("5"); binaryStrings.add("0101");
        hexChars.add("6"); binaryStrings.add("0110");
        hexChars.add("7"); binaryStrings.add("0111");
        hexChars.add("8"); binaryStrings.add("1000");
        hexChars.add("9"); binaryStrings.add("1001");

        hexChars.add("a"); binaryStrings.add("1010");
        hexChars.add("b"); binaryStrings.add("1011");
        hexChars.add("c"); binaryStrings.add("1100");
        hexChars.add("d"); binaryStrings.add("1101");
        hexChars.add("e"); binaryStrings.add("1110");
        hexChars.add("f"); binaryStrings.add("1111");
    }
    
    public String binaryOfHex(String h) {
        String binary = "";
        String firstLetter = "";
        String secondLetter = "";
        firstLetter += h.charAt(0) + "";
        secondLetter += h.charAt(1) + "";
        for(int i = 0 ; i < hexChars.size() ; i++) {
            if(hexChars.get(i).equals(firstLetter)) {
                binary += binaryStrings.get(i) + "";
            }
        }
        for(int i = 0 ; i < hexChars.size() ; i++) {
            if(hexChars.get(i).equals(secondLetter)) {
                binary += binaryStrings.get(i) + "";
            }
        }
        return binary;
    }
    
    
    public String Xor(String a , String b) {
        String res = "";
        for(int i = 0 ; i < a.length() ; i++) {
            int x = Integer.parseInt(a.charAt(i) + "");
            int y = Integer.parseInt(b.charAt(i) + "");
            x ^= y;
            String newX = x + "";
            res += newX;
        }
        return res;
    }
    
    public String hexOfBinary(String b) {
        String res = "";
        String firstHalf = b.substring(0 , 4);
        String secondHalf = b.substring(4 , 8);
        for(int i = 0 ; i < binaryStrings.size() ; i++) {
            if(binaryStrings.get(i).equals(firstHalf)) {
                res += hexChars.get(i);
                break;
            }
        }
        for(int i = 0 ; i < binaryStrings.size() ; i++) {
            if(binaryStrings.get(i).equals(secondHalf)) {
                res += hexChars.get(i);
                break;
            }
        }
        
        return res;
    }
    
}
