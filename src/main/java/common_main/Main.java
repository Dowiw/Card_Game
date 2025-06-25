package common_main;

import javax.swing.SwingUtilities;
import ui.GameInterface;

public class Main {
    public static void main(String[] args) {
    	
    	/*
    	 * Create EDT thread.
    	 * This ensures that the code is running on a machine that is existing.
    	 * A safety thread that allows it to be an application and minimal impact when fail.
    	 */
        SwingUtilities.invokeLater(() -> {
            GameInterface window = new GameInterface();
            window.setLocationRelativeTo(null); // Set window to the center of the screen
//          window.setResizable(false); // Disable window resizing
            window.setVisible(true);
        });
    }
}
