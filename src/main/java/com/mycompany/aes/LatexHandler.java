/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aes;

import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author essay
 */
public class LatexHandler {
    private String text;
    public LatexHandler(String LatexCode) {
        this.text = LatexCode;
    }
    
    public void generatePDF() {
        try {
            FileWriter writer = new FileWriter("file.tex");
            File file = new File("f.tex");
            writer.write(this.text);
            writer.close();
//            Process process = Runtime.getRuntime().exec("pdflatex file.tex");
//            process.waitFor();
            ProcessBuilder pb = new ProcessBuilder(
                "C:\\Program Files\\MiKTeX\\miktex\\bin\\x64\\pdflatex.exe",
                "file.tex"
            );

            pb.directory(new File("C:\\Users\\essay\\Documents\\NetBeansProjects\\AES"));

            Process process = pb.start();
            process.waitFor();
            System.out.println("PDF created! " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
