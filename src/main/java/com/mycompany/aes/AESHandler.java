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
public class AESHandler {

    private String text;
    private String KEY;
    private String latexText;
    private String HexText;
    private String KeyState;
    private ArrayList<String> blocks = new ArrayList<>();

    public AESHandler(String text, String KEY) {
        this.text = text;
        this.KEY = KEY;

        // init the latex code
        latexText
                = "\\documentclass{article}\n"
                + "\\usepackage[a4paper, margin=2cm]{geometry}\n"
                + "\\usepackage{graphicx}\n"
                + "\\begin{document}\n"
                + "\\noindent\n"
                + "\\begin{minipage}[t][1cm][b]{0.60\\textwidth}\n"
                + "\\raggedright\n"
                + "University of Science and Technology of Oran \\\\ \n"
                + "Faculty of Mathematics and Computer Science \\\\ \n"
                + "IT Department \\\\\n"
                + "Prepared by Cidi Mohamed Mostepha and Khithri Nadjet\n"
                + "\\end{minipage}%\n"
                + "\\hfill\n"
                + "\\begin{minipage}[t][1cm][b]{0.30\\textwidth}\n"
                + "\\raggedleft\n"
                + "\\includegraphics[width=3.2cm, keepaspectratio]{usto.png}\n"
                + "\\end{minipage}\n"
                + "\\vspace{0.3cm}\n"
                + "\\hrule\n"
                + "\n"
                + "\\begin{center}\n"
                + "\\Large\\textbf{Simulating Encryption Using the AES Algorithm}\n"
                + "\\end{center}\n"
                + "\n"
                + "\\hrule"
                + "\\subsection*{Exercice : You are given the next text}"
                + text
                + "\\subsection*{Required}\n"
                + "\\begin{itemize}\n"
                + "    \\item Encrypt the text using key " + this.KEY + "\n"
                + "\\end{itemize}\n"
                + "\\subsection*{Solution}\n"
                + "\\begin{itemize}\n"
                + "    \\item \\textbf{Hex text :}\\\\\n"
                + "\\end{itemize}";
    }

    public void convertTextIntoHex() {
        HexText = "";
        for (int i = 0; i < text.length(); i++) {
            String hexOf = Integer.toHexString(text.charAt(i));
            if (hexOf.length() == 1) {
                hexOf = "0" + hexOf;
            }
            HexText += hexOf;
            latexText += hexOf + " ";
        }
    }

    // convert text into blocks
    public void convertTextIntoBlocks() {
        for (int i = 0; i < HexText.length();) {
            int j = 0;
            String b = "";
            while (i < HexText.length() && j < 32) {
                b += HexText.charAt(i);
                i++;
                j++;
            }

            for (; j < 32; j++) {
                b += "0";
            }

            blocks.add(b);
        }
    }

    // convert block into state
    public void convertBlockIntoState(String block) {
        //System.out.println(block);
        latexText += "\\begin{tabular}{|c|c|c|c|}\n"
                + "\\hline ";
        for (int i = 0; i < 8; i += 2) {
            String row = "";
            for (int j = i; j < block.length(); j += 8) {
                String hexString = "";
                hexString += block.charAt(j);
                hexString += block.charAt(j + 1);
                row += hexString + " & ";
                //System.out.print(hexString + " ");
            }
            row = row.substring(0, row.length() - 2);
            //System.out.println();
            latexText += row + "\\\\ \\hline ";
        }
        latexText += "\\end{tabular}";
        latexText += "\\hspace{0.5cm}";
        latexText += "\\vspace{0.1cm}";
    }

    // convert all text into states
    public void convertTextIntoStates() {
        latexText += "\\begin{itemize}\n"
                + "    \\item \\textbf{convert text into states :}\n"
                + "\\end{itemize}";
        for (int i = 0; i < blocks.size(); i++) {
            convertBlockIntoState(blocks.get(i));
        }
    }

    // add round key
    public void addRoundKey() {
        this.KeyState = "";
        // turn key into state
        for (int i = 0; i < this.KEY.length(); i++) {
            String str = Integer.toHexString(this.KEY.charAt(i));
            if (str.length() == 1) {
                str = "0" + str;
            }
            this.KeyState += str;
        }

        latexText += "\\begin{itemize}\n"
                + "    \\item \\textbf{add round key} \\\\\n"
                + "\\end{itemize}"
                + "\\begin{itemize}\n"
                + "    \\item \\textbf{key state} \\\\\n"
                + "\\end{itemize}";
        convertBlockIntoState(this.KeyState);
        latexText += "\\\\" + "\\begin{itemize}\n"
                + "    \\item \\textbf{xor key with states :}\n"
                + "\\end{itemize}";
        // xor states with key
        //System.out.println(this.KeyState + "  88888");
        for (int i = 0; i < blocks.size(); i++) {
            char newBlock[] = "00000000000000000000000000000000".toCharArray();
            latexText += "\\begin{tabular}{|c|c|c|c|}\n"
                    + "\\hline ";
            HexBinaryConverting binaryConverting = new HexBinaryConverting();
            for (int j = 0; j < 8; j += 2) {
                String row = "";
                for (int k = j; k < 32; k += 8) {
                    // System.out.println(k);
                    String subKey = "";
                    subKey += this.KeyState.charAt(k);
                    subKey += this.KeyState.charAt(k + 1);
                    String subBlock = "";
                    subBlock += blocks.get(i).charAt(k);
                    subBlock += blocks.get(i).charAt(k + 1);
                    String binaryOfSubKey = binaryConverting.binaryOfHex(subKey);
                    String binaryOfSubBlock = binaryConverting.binaryOfHex(subBlock);
                    String xorString = binaryConverting.Xor(binaryOfSubKey, binaryOfSubBlock);
                    String hexOfXorString = binaryConverting.hexOfBinary(xorString);
                    newBlock[k] = hexOfXorString.charAt(0);
                    newBlock[k + 1] = hexOfXorString.charAt(1);
                    row += hexOfXorString + " & ";
                    //System.out.println(binaryOfSubKey + "^" + binaryOfSubBlock + "=" + xorString + "=" + hexOfXorString);
                }
                row = row.substring(0, row.length() - 2);
                latexText += row + "\\\\ \\hline ";
            }
            latexText += "\\end{tabular}";
            latexText += "\\hspace{0.5cm}";
            latexText += "\\vspace{0.1cm}";
            String newBlockString = "";
            for (char c : newBlock) {
                newBlockString += c;
            }
            blocks.set(i, newBlockString);
        }
    }

    // subByte
    public void subByte() {
        latexText += "\\begin{itemize}\n"
                + "    \\item \\textbf{sub Byte :}\n"
                + "\\end{itemize} ";
        SBOX sbox = new SBOX();
        ///////////////////////////////
        for (int i = 0; i < blocks.size(); i++) {
            char newBlock[] = "00000000000000000000000000000000".toCharArray();
            latexText += "\\begin{tabular}{|c|c|c|c|}\n"
                    + "\\hline ";
            for (int j = 0; j < 8; j += 2) {
                String row = "";
                for (int k = j; k < 32; k += 8) {
                    String subBlock = "";
                    subBlock += blocks.get(i).charAt(k);
                    subBlock += blocks.get(i).charAt(k + 1);
                    String newSubBlock = sbox.getSboxOf(subBlock);
                    newBlock[k] = newSubBlock.charAt(0);
                    newBlock[k + 1] = newSubBlock.charAt(1);
                    row += newSubBlock + " & ";
                }
                row = row.substring(0, row.length() - 2);
                latexText += row + "\\\\ \\hline ";
            }

            latexText += "\\end{tabular}";
            latexText += "\\hspace{0.5cm}";
            latexText += "\\vspace{0.1cm}";
            String newBlockString = "";
            for (char c : newBlock) {
                newBlockString += c;
            }
            blocks.set(i, newBlockString);
        }
    }

    // shift rows
    public void shiftRows() {
        System.out.println("jj");
        latexText += "\\begin{itemize}\n"
                + "    \\item \\textbf{shift rows :}\n"
                + "\\end{itemize} ";

        for (int i = 0; i < blocks.size(); i++) {
            String arr[][] = new String[4][4];
            int x = 0, y = 0;
            for (int j = 0; j < 8; j += 2) {
                for (int k = j; k < 32; k += 8) {
                    String subBlock = "";
                    subBlock += blocks.get(i).charAt(k);
                    subBlock += blocks.get(i).charAt(k + 1);
                    arr[x][y] = subBlock;
                    y++;
                    if (y == 4) {
                        x++;
                        y -= y;
                    }
                }
            }

            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < j; k++) {
                    String temp = arr[j][0];
                    for (int l = 0; l < 3; l++) {
                        arr[j][l] = arr[j][l + 1];
                    }
                    arr[j][3] = temp;
                }
            }

            String newSubString = "";
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    newSubString += arr[k][j];
                    System.out.print(arr[j][k] + " ");
                }
                System.out.println("");
            }
            System.out.println(newSubString + "=");
            blocks.set(i, newSubString);
        }

        for (int i = 0; i < blocks.size(); i++) {
            convertBlockIntoState(blocks.get(i));
        }
    }

    // mix columns
    public void mixColumns() {
        latexText += "\\begin{itemize}\n"
                + "    \\item \\textbf{mix columns :}\n"
                + "\\end{itemize} ";
        MixColumns mc = new MixColumns();
        for (int i = 0; i < blocks.size(); i++) {
            blocks.set(i, mc.multipeState(blocks.get(i)));
        }
        for (int i = 0; i < blocks.size(); i++) {
            convertBlockIntoState(blocks.get(i));
        }
    }

    public void startAES() {
        convertTextIntoHex();
        convertTextIntoBlocks();
        convertTextIntoStates();
        addRoundKey();
        subByte();
        shiftRows();
        mixColumns();
        latexText += "\\end{document}";
    }

    // aes encrypting
    public void AESEncrypting() {
        convertTextIntoHex();
        convertTextIntoBlocks();
        convertTextIntoStates();
        latexText += "\\subsection*{Round 0 : }";
        addRoundKey();
        for (int i = 1; i <= 9; i++) {
            latexText += "\\subsection*{Round " + i + " : }";
            subByte();
            shiftRows();
            mixColumns();
        }
        latexText += "\\subsection*{Round 10 : }";
        subByte();
        shiftRows();
        latexText += "\\subsection*{Encrypted text : } ";
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = 0; j < 8; j += 2) {
                for (int k = j; k < 32; k += 8) {
                    String subBlock = "";
                    subBlock += blocks.get(i).charAt(k);
                    subBlock += blocks.get(i).charAt(k + 1);
                    latexText += subBlock;
                }
            }
        }
        latexText += "\\end{document}";
    }

    public String getLatexSolution() {
        return this.latexText;
    }
}
