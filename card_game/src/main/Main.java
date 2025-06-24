package main;

import javax.swing.SwingUtilities;

import ui.GameInterface;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            GameInterface window = new GameInterface();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
