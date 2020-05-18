package binaryStringOneCharTree;

public class Node<T extends Comparable<T>> {
//	private Node<T> parent;
	private Node<T> leftChild = null;
	private Node<T> rightChild = null;
	private T data = null;

	public Node(T data) {
		super();
		this.data = data;
	}

//	public Node(Node<T> parent, T data) {
//		super();
//		this.parent = parent;
//		this.data = data;
//	}
	public Node(Node<T> parent, Node<T> leftChild, Node<T> rightChild,
			T data) {
		super();
//		this.parent = parent;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.data = data;
	}

	public void add(Node<T> newNode) {
		if (newNode.getData().compareTo(this.getData()) < 0)
			if (this.hasLeftChild())
				this.leftChild.add(newNode);
			else
				this.leftChild = newNode;
		else if (this.hasRightChild())
			this.rightChild.add(newNode);
		else
			this.rightChild = newNode;
	}

	public void print() {
		if (this.hasLeftChild())
			leftChild.print();
		System.out.print(" " + data + " ");
		if (this.hasRightChild())
			rightChild.print();
	}

	public boolean hasLeftChild() {
		if (leftChild != null)
			return true;
		return false;
	}

	public boolean hasRightChild() {
		if (rightChild != null)
			return true;
		return false;
	}

	public Node<T> getLeftChild() { return leftChild; }

	public void setLeftChild(Node<T> leftChild) { this.leftChild = leftChild; }

	public Node<T> getRightChild() { return rightChild; }

	public void setRightChild(Node<T> rightChild) {
		this.rightChild = rightChild;
	}

	public T getData() { return data; }

	public void setData(T data) { this.data = data; }

	public int maxDepth(int level) {
		int max = level + 1;
		int leftmax = max, rightmax = max;
		if (this.hasLeftChild())
			leftmax = leftChild.maxDepth(max);
		if (this.hasRightChild())
			rightmax = rightChild.maxDepth(max);
		if (leftmax > rightmax)
			max = leftmax;
		else
			max = rightmax;
		return max;
	}

	public Node<T> rotate() {
		Node<T> newLocalRoot = this;
		int rightMax = 0;
		int leftMax = 0;
		if (this.hasRightChild()) {
			rightMax = rightChild.maxDepth(0);
		}
		if (this.hasLeftChild()) {
			leftMax = leftChild.maxDepth(0);
		}

		  if(Math.abs(rightMax - leftMax) > 1) {

			if (this.hasLeftChild())
				leftChild = this.getLeftChild().rotate();
			if (this.hasRightChild())
				rightChild = this.getRightChild().rotate();

			if (this.hasLeftChild()) {
				leftMax = this.getLeftChild().maxDepth(0);
			}
			if (this.hasRightChild()) {
				rightMax = this.getRightChild().maxDepth(0);
			}
			if (rightMax - leftMax > 1) {
				if (this.hasRightChild()) {
					newLocalRoot = rightChild;
					if (newLocalRoot.hasLeftChild())
						rightChild = newLocalRoot.getLeftChild();
					newLocalRoot.leftChild =this;
				}
			}
			else if (rightMax - leftMax < -1) {
				if (this.hasLeftChild()) {
					newLocalRoot = leftChild;
					if (leftChild.hasRightChild())
						leftChild = leftChild.getRightChild();
					newLocalRoot.setRightChild(this);
				}
			}
		}
		return newLocalRoot;
	}
	public Node<T> rotateNode() {

		if (this.hasLeftChild())
			leftChild = this.leftChild.rotateNode();
		if (this.hasRightChild())
			rightChild = this.rightChild.rotateNode();

		Node<T> newLocalRoot = this;
		int rightMax = 0;
		int leftMax = 0;
		if (this.hasLeftChild()) {
			leftMax = leftChild.maxDepth(0);
		}
		if (this.hasRightChild()) {
			rightMax = rightChild.maxDepth(0);
		}

//		while (Math.abs(rightMax - leftMax) > 1) {
//
//			if (this.hasLeftChild()) {
//				leftMax = this.getLeftChild().maxDepth(0);
//			}
//			if (this.hasRightChild()) {
//				rightMax = this.getRightChild().maxDepth(0);
//			}
			if (rightMax - leftMax > 1) {
				if (this.hasRightChild()) {
					newLocalRoot = rightChild;
					if (newLocalRoot.hasLeftChild())
						rightChild = newLocalRoot.getLeftChild();
					newLocalRoot.leftChild =this;
				}
			}
			else if (rightMax - leftMax < -1) {
				if (this.hasLeftChild()) {
					newLocalRoot = leftChild;
					if (leftChild.hasRightChild())
						leftChild = leftChild.getRightChild();
					newLocalRoot.setRightChild(this);
				}
			}
//		}
		return newLocalRoot;
	}

	public void treeFormat(String[][] output, int currentRow,
			int currentColumn, boolean isALeftNode, int offset) {
//		int currentTreeLevel=(currentRow+2)/2;
		if (currentRow > 0)
			if (isALeftNode)
				output[currentRow - 1][currentColumn + offset] = "/";
			else
				output[currentRow - 1][currentColumn - offset] = "\\";

		output[currentRow][currentColumn] = "" + data;

		if (this.hasLeftChild())
			leftChild.treeFormat(output, currentRow + 2,
					currentColumn - offset, true, (offset + 1) / 2);
		if (this.hasRightChild())
			rightChild.treeFormat(output, currentRow + 2,
					currentColumn + offset, false, (offset + 1) / 2);
	}
}
