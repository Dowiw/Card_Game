package ui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;

import util.ImageManager;

/*
 * customized component for setting the background of imagePanel 
 * & more features if needed
 */
public final class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final Image backgroundImage;

	public GamePanel(int rows, int cols, int hgap, int vgap) {
		super(new GridLayout(rows, cols, hgap, vgap));
		this.backgroundImage = ImageManager.getBackgroundImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
