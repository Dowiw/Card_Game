package ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import util.ImageManager;

/*
 * customized component for setting the background of imagePanel 
 * & more features if needed
 */
public final class EdgePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final Image edgeImage;

	public EdgePanel(int hgap, int vgap) {
		super(new BorderLayout(hgap, vgap));
		this.edgeImage = ImageManager.getEdgesImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (edgeImage != null) {
			g.drawImage(edgeImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
