
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AVLTreeVotingBlock extends JPanel {
	private PrintWriter pwBalls;
	private String filePath;
	public static PrintWriter pwCards;
	private JFileChooser jfc;

	public AVLTreeVotingBlock() throws IOException {
		jfc = new JFileChooser();
		try {
			filePath = "";
			jfc.setFileSelectionMode(jfc.DIRECTORIES_ONLY);
			if (jfc.showDialog(this, "Select output file location") == JFileChooser.APPROVE_OPTION) {
				filePath = jfc.getSelectedFile().getAbsolutePath().toString();
			}
			System.out.println(filePath);

			// FileWriter fwBalls = new FileWriter(Balls);
			// pwBalls = new PrintWriter(fwBalls);
			File cards = new File(filePath, "AVLOutput.txt");
			FileWriter fwCards = new FileWriter(cards);
			pwCards = new PrintWriter(fwCards);
		} catch (Exception E) {
			System.out.println("error");
		}
		// jfc = new JFileChooser();
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SELECT INPUT FILE", "txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		filePath = chooser.getSelectedFile().getAbsolutePath().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println(filePath);
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
		}
		File f = new File(filePath);
		Scanner sc = new Scanner(new File(filePath));
		HashMap<Double, String> map = new HashMap<Double, String>();
		ArrayList<Double> liberalneg = new ArrayList<Double>();
		ArrayList<Double> conservativepos = new ArrayList<Double>();
		AVLTree tree = new AVLTree();
		ArrayList<Double> all = new ArrayList<Double>();
		double total = 0;
		int count = 0;
		while (sc.hasNextLine()) {
			if (sc.hasNext()) {
				String str = sc.next();
				double n = sc.nextDouble();
				if (sc.hasNextLine())
					sc.nextLine();
				total += n;
				count++;
				all.add(n);
				if (map.isEmpty()) {
					tree.root = tree.insert(tree.root, n);

				} else {
					tree.root = tree.insert(tree.root, n);
				}
				if (n > 0) {
					conservativepos.add(n);
					map.put(n, str);

				} else {
					liberalneg.add(n);
					map.put(n, str);
				}
			} else
				break;
		}
		pwCards.println("Level Order:");
		tree.levelOrder();
		pwCards.println();
		pwCards.println("Average:" + (double) (total) / count);
		pwCards.println("All Liberals:");
		for (Double i : liberalneg) {
			pwCards.println(map.get(i));
		}
		pwCards.println("All Conservatives:");
		for (Double i : conservativepos) {
			pwCards.println(map.get(i));
		}
//		for (Double i : all) {
//			System.out.println(map.get(i) + ": " + i);
//		}
		while (!all.isEmpty() && !((double) total / count < 5 && (double) total / count > -5)) {
			double next = all.remove(0);
			conservativepos.remove((Double) next);
			liberalneg.remove((Double) next);
			tree.root = tree.deleteNode(tree.root, next);
			count--;
			total -= next;
		}
		pwCards.println("AFTER REMOVING:");
		pwCards.print("In order:");
		tree.inOrder(tree.root);
		pwCards.println();

		pwCards.println("Mean:" + (double) total / count);
		pwCards.close();
	}

	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.drawString("Output done in file", 200, 200);

	}
}
