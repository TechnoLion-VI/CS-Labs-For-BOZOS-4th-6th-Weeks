public class BSTcontroller {

    private AVLTree avl = new AVLTree();
    private Node avlNode;

    private TreeNode treeRoot;

    private UserInputPanel controls;
    private GraphicAVLTreeVisualizer view;

    public BSTcontroller(UserInputPanel controls, GraphicAVLTreeVisualizer view) {
        this.controls = controls;
        this.view = view;

        controls.setController(this);
    }

    public void addNode(double num) {

        avl.root = avl.insert(avl.root, num);

        view.update(avl);

        //return;

        //////////

        /*

        TreeNode newNode = new TreeNode(num);

        if (treeRoot == null) {
            treeRoot = newNode;
        }
        else {

            TreeNode n = treeRoot;

            while (true) {
                if (newNode.val() <= n.val()) {
                    TreeNode l = n.left();
                    if (l == null) {
                        n.left(newNode);
                        break;
                    }
                    n = l;
                    continue;
                }
                TreeNode r = n.right();
                if (r == null) {
                    n.right(newNode);
                    break;
                }
                n = r;
            }
        }

        view.update(treeRoot);

        */
    }
}

class TreeNode {

    public double val() {
        return val;
    }

    public void left(TreeNode l) {
        left = l;
    }

    public void right(TreeNode r) {
        right = r;
    }

    public TreeNode left() {
        return left;
    }

    public TreeNode right() {
        return right;
    }

    private final double val;
    private TreeNode left, right;

    public TreeNode(double val) {
        this.val = val;
    }

    public int height() {
        int height = -1;
        TreeNode n = this;
        while (n != null) {
            ++height;
            TreeNode leftChild = n.left();
            n = (leftChild == null) ? n.right() : leftChild;
        }
        return height;
    }

    public static int getMaxWidth(TreeNode node)
    {
        int maxWidth = 0;
        int width;
        int h = node.height();
        int i;

        /* Get width of each level and compare
           the width with maximum width so far */
        for (i = 1; i <= h; i++) {
            width = getWidth(node, i);
            if (width > maxWidth)
                maxWidth = width;
        }

        return maxWidth;
    }

    /* Get width of a given level */
    private static int getWidth(TreeNode node, int level)
    {
        if (node == null)
            return 0;

        if (level == 1)
            return 1;
        else if (level > 1)
            return getWidth(node.left, level - 1)
                    + getWidth(node.right, level - 1);
        return 0;
    }

    public int visualWidthInDirection(int leftOrRight, int x, int depth, int maxDepth) {

        if (depth > maxDepth) return x;

        TreeNode r = right(), l = left();
        int biggestX = x;
        if (r != null)
            biggestX = Math.max(biggestX, r.visualWidthInDirection(leftOrRight, x + leftOrRight * 2 - 1, depth + 1, maxDepth));
        if (l != null)
            biggestX = Math.max(biggestX, l.visualWidthInDirection(leftOrRight, x + (leftOrRight + 1) % 2 * 2 - 1, depth + 1, maxDepth));

        System.out.println(biggestX);

        return biggestX;
    }

    public int[] graphicBreadth(int maxDepth) {
        return graphicBreadth(0, maxDepth);
    }

    public int[] graphicBreadth(int depth, int maxDepth) {

        int[] maxBreadth = {0, 0};

        int[] breadth;

        if (depth <= maxDepth || true) {

            TreeNode r = right(), l = left();

            if (r != null) {
                breadth = r.graphicBreadth(depth + 1, maxDepth);
                maxBreadth[1] = breadth[0] + breadth[1] + 1;
            }

            if (l != null) {
                breadth = l.graphicBreadth(depth + 1, maxDepth);
                maxBreadth[0] = breadth[0] + breadth[1] + 1;
            }
        }

        return maxBreadth;
    }

}

//////////

class AVLNode extends TreeNode {

    public AVLNode (double x) {
        super(x);
    }

    public int balanceFactor(){
        return getHeight(left())-getHeight(right());
    }

    private int getHeight(TreeNode k) {
        if (k == null) return -1;
        return 1 + Math.max(getHeight(k.left()), getHeight(k.right()));
    }
}
