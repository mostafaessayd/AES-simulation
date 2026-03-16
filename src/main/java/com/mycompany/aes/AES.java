/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.aes;

/**
 *
 * @author essay
 */
public class AES {

    public static void main(String[] args) throws Exception {
        String text = "Technology has changed the way people communicate today.\n"
                + "Computers allow us to solve complex problems quickly.\n"
                + "Programming is an important skill in the modern world.\n"
                + "Many students start learning languages like Java or Python.\n"
                + "Practice and patience are the keys to becoming a good programmer.\n"
                + "Algorithms help us design efficient solutions to problems.\n"
                + "Cybersecurity is also an important field in computer science.\n"
                + "Encryption techniques protect sensitive information.\n"
                + "Projects help students apply what they learn in practice.\n"
                + "Continuous learning is essential in the world of technology.\n";
        text = "Good morning How Are You";
        String KEY = KeyGeneratorUtil.generateKey(16);
        AESHandler handler = new AESHandler(text, KEY);
        handler.AESEncrypting();

        String solution = handler.getLatexSolution();
        System.out.println(solution);
        LatexHandler latexHandler = new LatexHandler(solution);
        latexHandler.generatePDF();
    }
}
