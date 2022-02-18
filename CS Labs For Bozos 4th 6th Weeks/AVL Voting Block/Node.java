import java.util.LinkedList;
import java.util.Queue;

class Node {
	double key;
	int height;
	Node left, right;

	Node(double d) {
		key = d;
		height = 1;
	}

	public int[] graphicBreadth(int maxDepth) {
		return graphicBreadth(0, maxDepth);
	}

	public int[] graphicBreadth(int depth, int maxDepth) {

		int[] maxBreadth = { 0, 0 };

		int[] breadth;

		if (depth <= maxDepth || true) {

			Node r = this.right, l = this.left;

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

class AVLTree {

	Node root;

	// A utility function to get the height of the tree
	int height(Node N) {
		if (N == null)
			return 0;

		return N.height;
	}

	
	int max(int a, int b) {
		return (a > b) ? a : b;
	}

	
	Node minValueNode(Node node) {
		Node current = node;

	
		while (current.left != null)
			current = current.left;

		return current;
	}

	Node deleteNode(Node root, double key)

	{
		
		if (root == null)
			return root;

		
		if (key < root.key)
			root.left = deleteNode(root.left, key);

		// If the key to be deleted is greater than the
		// root's key, then it lies in right subtree
		else if (key > root.key)
			root.right = deleteNode(root.right, key);

		// if key is same as root's key, then this is the node
		// to be deleted
		else {

			// node with only one child or no child
			if ((root.left == null) || (root.right == null)) {
				Node temp = null;
				if (temp == root.left)
					temp = root.right;
				else
					temp = root.left;

				// No child case
				if (temp == null) {
					temp = root;
					root = null;
				} else // One child case
					root = temp; // Copy the contents of
									// the non-empty child
			} else {

				// node with two children: Get the inorder
				// successor (smallest in the right subtree)
				Node temp = minValueNode(root.right);

				// Copy the inorder successor's data to this node
				root.key = temp.key;

				// Delete the inorder successor
				root.right = deleteNode(root.right, temp.key);
			}
		}

		// If the tree had only one node then return
		if (root == null)
			return root;

		// STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
		root.height = max(height(root.left), height(root.right)) + 1;

		// STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
		// this node became unbalanced)
		int balance = getBalance(root);

		// If this node becomes unbalanced, then there are 4 cases
		// Left Left Case
		if (balance > 1 && getBalance(root.left) >= 0)
			return rightRotate(root);

		// Left Right Case
		if (balance > 1 && getBalance(root.left) < 0) {
			root.left = leftRotate(root.left);
			return rightRotate(root);
		}

		// Right Right Case
		if (balance < -1 && getBalance(root.right) <= 0)
			return leftRotate(root);

		// Right Left Case
		if (balance < -1 && getBalance(root.right) > 0) {
			root.right = rightRotate(root.right);
			return leftRotate(root);
		}

		return root;
	}

	
	Node rightRotate(Node y) {
		Node x = y.left;
		Node T2 = x.right;

		// Perform rotation
		x.right = y;
		y.left = T2;

		// Update heights
		y.height = max(height(y.left), height(y.right)) + 1;
		x.height = max(height(x.left), height(x.right)) + 1;

		// Return new root
		return x;
	}

	
	Node leftRotate(Node x) {
		Node y = x.right;
		Node T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		x.height = max(height(x.left), height(x.right)) + 1;
		y.height = max(height(y.left), height(y.right)) + 1;

		// Return new root
		return y;
	}

	// Get Balance factor of node N
	int getBalance(Node N) {
		if (N == null)
			return 0;

		return height(N.left) - height(N.right);
	}

	Node insert(Node node, double key) {

	
		if (node == null)
			return (new Node(key));

		if (key < node.key)
			node.left = insert(node.left, key);
		else if (key > node.key)
			node.right = insert(node.right, key);
		else 
			return node;

		
		node.height = 1 + max(height(node.left), height(node.right));

	
		int balance = getBalance(node);

		
		if (balance > 1 && key < node.left.key)
			return rightRotate(node);

		// Right Right Case
		if (balance < -1 && key > node.right.key)
			return leftRotate(node);

		// Left Right Case
		if (balance > 1 && key > node.left.key) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		// Right Left Case
		if (balance < -1 && key < node.right.key) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		/* return the (unchanged) node pointer */
		return node;
	}

	// A utility function to print preorder traversal
	// of the tree.
	// The function also prints height of every node
	void levelOrder() {
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		while (!queue.isEmpty()) {

			
			Node tempNode = queue.poll();
			AVLTreeVotingBlock.pwCards.print(tempNode.key + " ");

		
			if (tempNode.left != null) {
				queue.add(tempNode.left);
			}

		
			if (tempNode.right != null) {
				queue.add(tempNode.right);
			}
		}
	}
	void inOrder(Node node) {
		if(node!=null) {
			inOrder(node.left);
			AVLTreeVotingBlock.pwCards.print(node.key+" ");
			inOrder(node.right);
		}
	}
	void preOrder(Node node) {
		if (node != null) {
			AVLTreeVotingBlock.pwCards.print(node.key + " ");
			preOrder(node.left);
			preOrder(node.right);
		}
	}

	
}