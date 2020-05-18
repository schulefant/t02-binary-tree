package binaryStringOneCharTree;

import java.util.Random;

public class DriverBinaryCharTree {

	public static void main(String[] args) {
		testTree(new BinaryTree<>("H"), 1);
//		testTree(new BinaryTree<>("H"), 2);
//		testTree(new BinaryTree<>("H"), 3);
	}

	public static void testTree(BinaryTree<String> myTree, int whichTree) {
		switch (whichTree) {
		case 1:
			myTree.add("9");
			myTree.add("i");
			myTree.add("D");
			myTree.add("U");
			myTree.add("E");
			myTree.add("$");
			myTree.add("O");
			myTree.add("W");
			myTree.add("4");
			myTree.add("6");
			myTree.add("Z");
			myTree.add("J");
			myTree.add("G");
			myTree.add("K");
			myTree.add("L");
			break;
		case 2:
			myTree.add("9");
			myTree.add("O");
			myTree.add("4");
			myTree.add("6");
			myTree.add("E");
			myTree.add("$");
			myTree.add("D");
			myTree.add("G");
			myTree.add("K");
			myTree.add("L");
			myTree.add("J");
			myTree.add("W");
			myTree.add("U");
			myTree.add("Z");
			break;
		case 3:
			Random rdm = new Random();
			for (int i = 0; i < 63; i++) {
				char c = (char) (rdm.nextInt(75) + 47);
				if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z'
						|| c >= '0' && c <= '9')
					myTree.add("" + c);
			}
			break;
		}
		myTree.printInOrder();
		System.out.println();
		System.out.println(
				"number of Elements: " + myTree.getNumberOfElements());
		System.out.println("height: " + myTree.height());
		System.out.println("width: " + myTree.width());
		System.out.println("optimal height: " + myTree.optimalHeight());
//		System.out.println("Number of storable Elements in current depth: "
//				+ myTree.getNumberOfStorableElements());
		myTree.printTree();
		myTree.rotate();
		System.out.println("height: " + myTree.height());
		myTree.printTree();
		myTree.rotate();
		System.out.println("height: " + myTree.height());
		myTree.printTree();
	}

}
