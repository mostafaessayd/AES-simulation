/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aes;

/**
 *
 * @author essay
 */
public class MixColumns {

    String C[][] = {
        {"02", "03", "01", "01"},
        {"01", "02", "03", "01"},
        {"01", "01", "02", "03"},
        {"03", "01", "01", "02"}
    };

    public MixColumns() {
    }

    public String multipeState(String blockString) {
        
        String resulte = "";
        String state[][] = new String[4][4];
        int x = 0, y = 0;
        for (int j = 0; j < 8; j += 2) {
            for (int k = j; k < 32; k += 8) {
                String subBlock = "";
                subBlock += blockString.charAt(k);
                subBlock += blockString.charAt(k + 1);
                state[x][y] = subBlock;
                y++;
                if (y == 4) {
                    x++;
                    y -= y;
                }
            }
        }
        
        HexBinaryConverting binaryConverting = new HexBinaryConverting();
        String mulRes[][] = new String[4][4];
        for(int i = 0 ; i < 4 ; i++) {
            for(int j = 0 ; j < 4 ; j++) {
                String res = "00000000";
                for(int k = 0 ; k < 4 ; k++) {
                    String mul = binaryResulteOf(state[i][k], C[k][j]);
                    res = binaryConverting.Xor(res , mul);
                }
                mulRes[i][j] = binaryConverting.hexOfBinary(res);
                System.out.print(mulRes[i][j] + " ");
            }
            System.out.println("");
        }
        
        for(int i = 0 ; i < 4 ; i++) {
            for(int j = 0 ; j < 4 ; j++) {
                resulte += mulRes[j][i];
            }
        }
        
        System.out.println(resulte + "\n\n\n");
        return resulte;
    }

    public String binaryResulteOf(String a, String b) {
        HexBinaryConverting binaryConverting = new HexBinaryConverting();
        String binary = binaryConverting.binaryOfHex(a);
        String res = "";
        if (b.equals("01")) {
            res = binary;
        } else if (b.equals("02")) {
            binary += "0";
            binary = binary.substring(1);
            if (binary.charAt(0) == '1') {
                String x = binaryConverting.binaryOfHex("1b");
                binary = binaryConverting.Xor(binary , x);
            }
            res = binary;
        } else if (b.equals("03")) {
            String z = binary;
            binary += "0";
            binary = binary.substring(1);
            if (binary.charAt(0) == '1') {
                String x = binaryConverting.binaryOfHex("1b");
                binary = binaryConverting.Xor(binary , x);
            }
            binary = binaryConverting.Xor(binary , z);
            res = binary;
        }

        return res;
    }
}
