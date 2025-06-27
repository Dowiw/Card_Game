package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class HorizontalStackLayout implements LayoutManager {
	private int overlap;

	public HorizontalStackLayout(int overlap) {
		this.overlap = overlap;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// No-op
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// No-op
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// Compute overall width: first image full, each subsequent image only adds (width - overlap).
		int n = parent.getComponentCount();
		int width = 0;
		int height = 0;

		for (int i = 0; i < n; i++) {
			Component comp = parent.getComponent(i);
			Dimension d = comp.getPreferredSize();
			if (i == 0) {
				width += d.width;
			} else {
				width += (d.width - overlap);
			}
			height = Math.max(height, d.height);
		}
		Insets insets = parent.getInsets();
		width += insets.left + insets.right;
		height += insets.top + insets.bottom;
		return new Dimension(width, height);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int n = parent.getComponentCount();
			int x = insets.left;
			int y = insets.top;
			int maxHeight = 0;

			for (int i = 0; i < n; i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getPreferredSize();
				comp.setBounds(x, y, d.width, d.height);
				x += (d.width - overlap);
				maxHeight = Math.max(maxHeight, d.height);
			}
		}
	}
}
