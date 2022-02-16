
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class GraphicAVLTreeWindow extends JFrame {

    final String title = "Graphic AVL Tree";

    private static JLabel getCenterAlignedJLabel(String text) {
        JLabel l = new JLabel(text, JLabel.CENTER);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    public GraphicAVLTreeWindow() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle(title);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        UserInputPanel userInputPanel = new UserInputPanel();
        GraphicAVLTreeVisualizer graphicAVLTreeVisualizer = new GraphicAVLTreeVisualizer();

        BSTcontroller controller = new BSTcontroller(userInputPanel, graphicAVLTreeVisualizer);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(getCenterAlignedJLabel("Type a decimal number in the box below and click the button to insert it as a tree node."));
        infoPanel.add(getCenterAlignedJLabel("You can also type a comma-separated series of numbers to add them to the tree, in the order that you typed them in, all in one go."));
        infoPanel.add(getCenterAlignedJLabel("Leave the box blank to insert a random number from 0 to 999."));

        add(infoPanel);
        add(userInputPanel);
        add(graphicAVLTreeVisualizer);

        pack();
        setVisible(true);
    }

}

class UserInputPanel extends JPanel implements ActionListener {

    private BSTcontroller controller;

    JTextField numberField;
    JButton addNodeButton;

    public void setController(BSTcontroller c) {
        controller = c;
    }

    public UserInputPanel() {
        numberField = new JTextField(25);
        addNodeButton = new JButton("Add Node(s)");
        addNodeButton.addActionListener(this);

        add(numberField);
        add(addNodeButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addNodeButton) {

            final String COMMA = ",";

            String text = numberField.getText();

            if (text.equals("")) {
                final double[] j = {1};
                (new Timer(33, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        j[0] *= 1.01;
                        for (int i = 0; i < j[0]; ++i) {
                            controller.addNode(Math.floor(Math.random() * 1000) + Math.random());
                        }
                    }
                })).start();
                return;
            }
            else if (text.contains(COMMA)) {
                String[] numbers = text.split(COMMA);
                for (String num : numbers) {
                    controller.addNode(Double.parseDouble(num));
                }
            }
            else {
                controller.addNode(Double.parseDouble(text));
            }
            numberField.setText(null);
        }
    }
}

class GraphicAVLTreeVisualizer extends JPanel {

    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    final int width = Math.min(1024, screenSize.width), height = Math.min(600, screenSize.height);
    int xCenter;
    final int canvasPadding = 11, nodeWidth = 35, nodeHeight = 22, horizontalGap = 15, levelGap = 11, textSize = 16;
    final Point nodePadding = new Point(2, (nodeHeight - textSize) / 2);
    final int halfNodeWidth = nodeWidth / 2, halfNodeHeight = nodeHeight / 2;

    //TreeNode tree;

    AVLTree avl;

    public GraphicAVLTreeVisualizer() {
        setPreferredSize(new Dimension(width, height));
    }

    public void update(AVLTree avl) {
        //this.tree = tree;
        this.avl = avl;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (avl == null || avl.root == null) return;

        xCenter = getWidth() / 2;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setFont(new Font("Arial", Font.PLAIN, textSize));

        AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(0, canvasPadding);

        int[] subtreeWidthBalance = avl.root.graphicBreadth(avl.height(avl.root));
        g2d.translate((coordsToPix(new Point(subtreeWidthBalance[0] - subtreeWidthBalance[1], 0)).x - xCenter + halfNodeWidth) / 2, 0);

        drawTree(avl.root, new Point(0, 1), g2d);

        g2d.setTransform(originalTransform);
    }

    private Point coordsToPix(Point p) {
        return new Point((int)(xCenter + (p.x - 1.0) * halfNodeWidth + p.x * horizontalGap), p.y * (nodeHeight + levelGap));
    }

    private void drawTree(Node node, Point loc, Graphics2D g2d) {
        AffineTransform originalTransform = g2d.getTransform();

        Point pixelLoc = coordsToPix(loc);

        g2d.drawString(GraphicAVLTreeRunner.fmt(node.key), pixelLoc.x + nodePadding.x, pixelLoc.y - nodePadding.y);

        g2d.drawRect(pixelLoc.x, pixelLoc.y - nodeHeight, nodeWidth, nodeHeight);

        Font defaultFont = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.ITALIC, textSize * 4 / 5));
        g2d.setColor(Color.BLUE);
        g2d.drawString(GraphicAVLTreeRunner.fmt(avl.getBalance(node)), pixelLoc.x + nodePadding.x + nodeWidth, pixelLoc.y - nodePadding.y);
        g2d.setFont(defaultFont);
        g2d.setColor(Color.BLACK);

        g2d.translate(halfNodeWidth, 0);

        Node lChild = node.left, rChild = node.right;
        Node[] children = {lChild, rChild};

        for (int i = children.length - 1; i >= 0; --i) {
            Node n = children[i];
            if (n != null) {
                int otherDirection = (i + 1) % 2;
                Node otherSide = children[otherDirection];
                //int shift = 1 + (false ? 0 : n.visualWidthInDirection(otherDirection, 0, 0, n.height()) * 2 /*+ otherSide.visualWidthInDirection(i, 0, 0, n.height())*/);

                int[] shift = n.graphicBreadth(Math.min(avl.height(n), otherSide == null ? 0 : avl.height(otherSide)));

                Point shiftedLoc = new Point(loc.x + (i == 0 ? -shift[1] - 1 : shift[0] + 1), loc.y + 1);

                Point shiftedLocPix = coordsToPix(shiftedLoc);

                Point connectLineStart = (Point) pixelLoc.clone(), connectLineEnd = (Point) shiftedLocPix.clone();

                int dx = shiftedLoc.x - loc.x;
                int thirdNodeWidth = nodeWidth / 3;
                int sixthNodeWidth = nodeWidth / 6;
                if (dx >= 1) {
                    connectLineStart.x += sixthNodeWidth;
                    connectLineEnd.x -= sixthNodeWidth;
                }
                if (dx <= -1) {
                    connectLineStart.x -= sixthNodeWidth;
                    connectLineEnd.x += sixthNodeWidth;
                }
                if (dx >= 10 || dx <= -10) {
                    int thirdNodeHeight = nodeHeight / 3;
                    connectLineStart.y -= thirdNodeHeight;
                    connectLineEnd.y += thirdNodeHeight;
                }
                if (dx >= 3) {
                    connectLineStart.x += thirdNodeWidth;
                    connectLineEnd.x -= thirdNodeWidth;
                }
                if (dx <= -3) {
                    connectLineStart.x -= thirdNodeWidth;
                    connectLineEnd.x += thirdNodeWidth;
                }
                g2d.drawLine(connectLineStart.x, connectLineStart.y, connectLineEnd.x, connectLineEnd.y - nodeHeight);

                g2d.setTransform(originalTransform);

                drawTree(n, shiftedLoc, g2d);
            }
        }
    }
}