package com.mycompany.aes;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class interfacee extends JFrame {

    private String lastLatex = "";
    private File selectedFile = null;
    private String textToProcess = "";

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(interfacee.class.getName());

    public interfacee() {
        initComponents();
    }

    private void initComponents() {

        jButton1 = new JButton();
        jButton3 = new JButton();
        jButton4 = new JButton();

        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel4 = new JLabel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("AES");
        setSize(780, 520);
        setLocationRelativeTo(null);

        Color bg = new Color(18, 18, 18);
        Color card = new Color(30, 30, 30);

        getContentPane().setBackground(bg);

        jLabel1.setText("AES Encryption Tool");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel2.setText("Text to process");
        jLabel2.setForeground(Color.LIGHT_GRAY);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel4.setText("No file selected");
        jLabel4.setForeground(Color.GRAY);

        jTextArea1.setFont(new Font("Consolas", Font.PLAIN, 14));
        jTextArea1.setBackground(card);
        jTextArea1.setForeground(Color.WHITE);
        jTextArea1.setCaretColor(Color.WHITE);

        jScrollPane1.setViewportView(jTextArea1);

        styleButton(jButton1, "Encrypt", new Color(0,153,255));
        styleButton(jButton3, "Choose File", new Color(255,165,0));
        styleButton(jButton4, "Export PDF", new Color(0,200,100));

        jButton1.addActionListener(this::jButton1ActionPerformed);
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jButton4.addActionListener(this::jButton4ActionPerformed);

        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(bg);
        top.add(jLabel1, BorderLayout.NORTH);
        top.add(jLabel2, BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(bg);

        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(card);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        cardPanel.add(jScrollPane1, BorderLayout.CENTER);

        center.add(cardPanel, BorderLayout.CENTER);

        JPanel filePanel = new JPanel();
        filePanel.setBackground(bg);
        filePanel.add(jLabel4);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottom.setBackground(new Color(25,25,25));
        bottom.add(jButton3);
        bottom.add(jButton1);
        bottom.add(jButton4);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(filePanel, BorderLayout.WEST);
        add(bottom, BorderLayout.SOUTH);

        pack();
    }

    private void styleButton(JButton btn, String text, Color color) {
        btn.setText(text);
        btn.setFocusPainted(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private JDialog showProgress(String text) {

        JDialog dialog = new JDialog(this, true);
        dialog.setUndecorated(true);
        dialog.setSize(300, 120);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);

        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);
        bar.setForeground(new Color(0,153,255));

        panel.add(label, BorderLayout.NORTH);
        panel.add(bar, BorderLayout.CENTER);

        dialog.setContentPane(panel);
        SwingUtilities.invokeLater(() -> dialog.setVisible(true));

        return dialog;
    }

    private void showDarkMessage(String msg, String title) {

        JDialog dialog = new JDialog(this, true);
        dialog.setUndecorated(true);
        dialog.setSize(350, 180);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createLineBorder(new Color(60,60,60)));

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setForeground(new Color(0,153,255));
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel m = new JLabel(msg, SwingConstants.CENTER);
        m.setForeground(Color.WHITE);

        JButton ok = new JButton("OK");
        ok.setBackground(new Color(0,153,255));
        ok.setForeground(Color.WHITE);
        ok.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(30,30,30));
        bottom.add(ok);

        panel.add(t, BorderLayout.NORTH);
        panel.add(m, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            jLabel4.setText("File: " + selectedFile.getName());

            try (java.util.Scanner sc = new java.util.Scanner(selectedFile)) {
                StringBuilder sb = new StringBuilder();
                while (sc.hasNextLine()) sb.append(sc.nextLine()).append("\n");
                jTextArea1.setText(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        if (jTextArea1.getText().isEmpty()) {
            showDarkMessage("Enter text first!", "Warning");
            return;
        }

        JDialog progress = showProgress("Encrypting...");

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {

                String text = jTextArea1.getText();

                String KEY = KeyGeneratorUtil.generateKey(16);
                AESHandler handler = new AESHandler(text, KEY);
                handler.AESEncrypting();

                lastLatex = handler.getLatexSolution();

                return null;
            }

            @Override
            protected void done() {
                progress.dispose();
                showDarkMessage("Encryption completed!", "Success");
            }
        }.execute();
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {

        if (lastLatex.isEmpty()) {
            showDarkMessage("Click Encrypt first!", "Error");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            File fileToSave = fileChooser.getSelectedFile();

            String pathTemp = fileToSave.getAbsolutePath();
            if (!pathTemp.endsWith(".pdf")) {
                pathTemp += ".pdf";
            }

            final String path = pathTemp;

            JDialog progress = showProgress("Generating PDF...");

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {

                    File texFile = new File("temp.tex");
                    java.io.FileWriter w = new java.io.FileWriter(texFile);
                    w.write(lastLatex);
                    w.close();

                    ProcessBuilder pb = new ProcessBuilder(
                            "C:\\Program Files\\MiKTeX\\miktex\\bin\\x64\\pdflatex.exe",
                            "temp.tex"
                    );

                    pb.directory(new File(System.getProperty("user.dir")));
                    Process p = pb.start();
                    p.waitFor();

                    File pdf = new File("temp.pdf");

                    java.nio.file.Files.move(
                            pdf.toPath(),
                            new File(path).toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );

                    return null;
                }

                @Override
                protected void done() {
                    progress.dispose();
                    showDarkMessage("PDF saved successfully!", "Success");
                }
            }.execute();
        }
    }

    public static void main(String args[]) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new interfacee().setVisible(true));
    }

    private JButton jButton1;
    private JButton jButton3;
    private JButton jButton4;

    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;

    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
}