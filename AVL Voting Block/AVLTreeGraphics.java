import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class AVLTreeGraphics extends JFrame {
	private static final int width = 1400;
	private static final int height = 1000;

	public AVLTreeGraphics(String name) throws IOException {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		
		AVLTreeVotingBlock p1 = new AVLTreeVotingBlock();
		add(p1, BorderLayout.CENTER);
		
		// this.pack();
		// setLocationRelativeTo(null);
		// this.setResizable(false);
		// this.setLocationByPlatform(true);
		// p1.
		this.setBounds(0, 0, 1400, 1040);
		// p1.
		// p1.
		setVisible(true);
		// p1.transferFocus();
	}

}
