package binaryStringOneCharTree;

public class BinaryTree<T extends Comparable<T>> {

	protected Node<T> root;
	int numberOfElements;

	public BinaryTree(T rootData) {
		super();
		root = new Node<T>(rootData);
		this.numberOfElements = 1;
	}

	public void add(T data) {
		Node<T> newNode = new Node<>(data);
		root.add(newNode);
		this.numberOfElements++;
	};

/*recursive rotation cannot work, because the path of the 
 * returns differs from the new tree structure
 */
	//	public void recursiveRotate() {
//		root = root.rotateNode();
//		
//	}
	public void rotate() {
		Node<T> newRoot=root;
		// determine whether left or right side is deeper
		int rightMax = 0;
		int leftMax = 0;
		if (root.hasRightChild()) {
			rightMax = root.getRightChild().maxDepth(0);
		}
		if (root.hasLeftChild()) {
			leftMax = root.getLeftChild().maxDepth(0);
		}
		if (Math.abs(rightMax - leftMax) > 1) {
			if (rightMax - leftMax > 1) {
				if (root.hasRightChild()) {
					newRoot = root.getRightChild();
					if (newRoot.hasLeftChild())
						root.setRightChild(newRoot.getLeftChild());
					newRoot.setLeftChild(root);
				}
			}
			else if (rightMax - leftMax < -1) {
				if (root.hasLeftChild()) {
					newRoot = root.getLeftChild();
					if (root.getLeftChild().hasRightChild())
						root.setLeftChild(root.getLeftChild().getRightChild());
					newRoot.setRightChild(root);
				}
			}
			root=newRoot;
			
			if (root.hasRightChild()) {
				rightMax = root.getRightChild().maxDepth(0);
			}
			else
				rightMax = 0;
			if (root.hasLeftChild()) {
				leftMax = root.getLeftChild().maxDepth(0);
			}
			else
				leftMax = 0;
		}
	}

	public void deleteNode(T data) {
		// TODO
		// determine whether left or right side is deeper
		Node<T> left = root.getLeftChild();
		Node<T> right = root.getRightChild();
		int rightMinusLeft = right.maxDepth(1) - left.maxDepth(1);

		if (rightMinusLeft > 1) {
			Node<T> smallestGreaterNeighbour = right;
			while (smallestGreaterNeighbour.hasLeftChild()) {
				smallestGreaterNeighbour =
						smallestGreaterNeighbour.getLeftChild();
			}
			smallestGreaterNeighbour.setLeftChild(root);
			smallestGreaterNeighbour.setRightChild(root.getRightChild());
		}
		else if (rightMinusLeft < -1) {
			Node<T> largestLowerNeighbour = left;
			while (largestLowerNeighbour.hasRightChild()) {
				largestLowerNeighbour =
						largestLowerNeighbour.getRightChild();
			}
			largestLowerNeighbour.setRightChild(root);
			largestLowerNeighbour.setLeftChild(root.getLeftChild());
		}
	}

//	public boolean unbalanced() {
//		if( this.optimalHeight()-this.height()>=2)
//			return true;
//		else return false;
//	}
	public int height() {
		return root.maxDepth(0);
	}

	public int width() {
		int w = 1;
		for (int i = 1; i < this.height(); i++)
			w *= 2;
		return w;
	}

	protected double optimalHeight() {
		return lb(this.numberOfElements) + 1;
	}

	protected static double lb(double x) {
		return Math.log(x) / Math.log(2);
	}

	public static int storageWithHeight(int x) {
		int fac = 1;
		for (int i = 1; i < x; i++)
			fac = fac + 2 * fac;
		return fac;
	}

	public int getNumberOfElements() { return numberOfElements; }

	public int getNumberOfStorableElements() {
		return (int) Math.pow(2, this.height()) - 1;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public void printTree() {
		/**
		 * create String maxtrix
		 */
		int rows = this.height() * 2 - 1;
		int columns = this.width() * 3 + 1;

		// fill with spaces
		String[][] output = new String[rows][columns];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				output[i][j] = " ";

		// fill the matrix with the tree
		root.treeFormat(output, 0, columns / 2, true, columns / 4);

		// remove empty columns and center Date in matrix again
		treeCleanUp(output, rows, columns);
//		print(output, rows, columns);
		reCenter(output, rows, columns);
		print(output, rows, columns);
	}

	private void print(String[][] output, int rows, int columns) {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++)
				System.out.print(output[i][j]);
			System.out.println();
		}
	}

	/**
	 * remove empty columns
	 */
	private static void treeCleanUp(String[][] output, int rows, int columns) {

		// first empty columns that contain only empty spaces
		for (int c = 0; c < columns; c++) {
			boolean isEmpty = true;
			for (int r = 0; r < rows; r++)
				if (output[r][c] != " ") {
					isEmpty = false;
				}
			if (isEmpty) {
				for (int r = 0; r < rows; r++)
					output[r][c] = "";
			}
		}
		/**
		 * deleting empty columns by moving the filled
		 */
		for (int c = 0, insert = 0; c < columns; c++) {

			// no place to insert?
			if (!("".equals(output[0][c]))) {
				insert++;
				// something to insert and space to do it?
				if (c > insert && "".equals(output[0][insert])) {
					// move data to empty position
					for (int r = 0; r < rows; r++) {
						output[r][insert] = output[r][c];
						output[r][c] = "";
					}
				}
			}
		}
	}

	public static void reCenter(String[][] output, int rows, int columns) {
		// reCenter data
//		int offset = columns / 4;
		String leftBranch = "*", rightBranch = "*";
		String debugData = "*";
		// look at every row with data
		for (int r = 0; r < rows - 1; r += 2) {
			leftBranch = "*";
			rightBranch = "*";

			for (int c = 0; c < columns; c++) {
				int leftBranchOffset = 0, rightBranchOffset = 0;
				int leftBranchPos, rightBranchPos;
				int newDataPos = c;

				if (!(" ".equals(output[r][c]) || "".equals(output[r][c]))) {
					// Data found at c
					debugData = output[r][c];

					// find left /
					String debugEmptyLeftChar = output[r][c - 1];
					for (leftBranchPos = c; leftBranchPos > 0
							&& (" ".equals(debugEmptyLeftChar)
									|| "".equals(debugEmptyLeftChar))
							&& !"\\".equals(output[r + 1][leftBranchPos])
							&& !"/".equals(output[r + 1][leftBranchPos]);
							leftBranchPos--, leftBranchOffset++) {
						debugEmptyLeftChar = output[r][leftBranchPos - 1];
						leftBranch = output[r + 1][leftBranchPos];
					}

					// find right \
					String debugEmptyRightChar = output[r][c + 1];
					for (rightBranchPos = c; rightBranchPos < columns - 1
							&& (" ".equals(debugEmptyRightChar)
									|| "".equals(debugEmptyRightChar))
							&& !"/".equals(output[r + 1][rightBranchPos])
							&& !"\\".equals(output[r + 1][rightBranchPos]);
							rightBranchPos++, rightBranchOffset++) {
						debugEmptyRightChar = output[r][rightBranchPos + 1];
						rightBranch = output[r + 1][rightBranchPos];
					}

					newDataPos = (rightBranchPos + leftBranchPos) / 2;
					// Move Data to new Position
					if (newDataPos != c
							&& "/".equals(output[r + 1][leftBranchPos])
							&& "\\".equals(output[r + 1][rightBranchPos])) {
						output[r][newDataPos] = output[r][c];
						output[r][c] = " ";
					}
					leftBranch = "*";
					rightBranch = "*";

					if (newDataPos > c)
						c = newDataPos;
				}
			}
		}
		// fill the empty spaces again
//		for (int c = 0; c < columns; c++) {
//			for (int r = 0; r < rows; r++) {
//				if ("".equals(output[r][c]))
//					output[r][c] = "|";
//			}
//		}
	}

	public void printInOrder() {
		if (root != null)
			root.print();
		else
			System.out.println("no Tree");
	}
}
