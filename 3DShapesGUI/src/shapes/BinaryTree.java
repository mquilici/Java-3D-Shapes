package shapes;

public class BinaryTree { 
	
    Node root; // Root node in the binary tree
	
    /* Class that defines nodes in the binary tree */
	public static class Node {    
		
	    double[][] face;  // polygon face
	    double[] normal;  // polygon normal
	    double depth;     // polygon distance
		Node left, right; // child nodes
	    
		// default constructor takes a polygon face and a depth value
	    public Node(double[][] arr, double[] norm, double dist) { 
	        this.face = arr;
	        this.normal = norm;
	        this.depth = dist;
	        this.left = null;
	        this.right = null;
	    }
	} 

	// Method to add a new node to the BST
	public Node addRecursive(Node current, Node newNode) {
		
		// if the tree is empty add the new node as the root
	    if (current == null) {
	        return newNode;
	        
	    // if the new node is less than the current node go left
	    } else if (newNode.depth < current.depth) {
	        current.left = addRecursive(current.left, newNode);
	        
	    // if the new node is greater than the current node go right
	    } else {
	        current.right = addRecursive(current.right, newNode);
	    }
	 
	    // done recursing, add the new node to the correct position
	    return current;
	}
	
	// Method to add a new node starting at the root
	public void add(Node newNode) {
	    root = addRecursive(root, newNode);
	}

	// Method to iteratively traverse the tree in order and draw polygon faces
	public void traverseInOrder(Node node) {
		
		// if the tree is not empty begin traversing
	    if (node != null) {
	    	
	    	// step left 
	        traverseInOrder(node.left);
	        
	        // draw the face at the current node
	        mainDraw.drawFace(node.face, node.normal);
	        
	        // step right
	        traverseInOrder(node.right);
	    }
	}
	
	// Method to begin in-order traversal starting at the root node
	public void InOrder() {
	    this.traverseInOrder(root);
	}
	
	
}