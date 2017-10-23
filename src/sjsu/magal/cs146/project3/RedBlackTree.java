package sjsu.magal.cs146.project3;

public class RedBlackTree<Key extends Comparable<Key>> {	
	static RedBlackTree.Node<String> root; 

	public static class Node<Key extends Comparable<Key>> { //changed to static 
		
		  Key key;  		  
		  Node<String> parent;
		  Node<String> leftChild;
		  Node<String> rightChild;
		  boolean isRed;
		  int color;
		  
		  public Node(Key data){
			  this.key = data;
			  leftChild = null;
			  rightChild = null;
		  }		
		  
		  public int compareTo(Node<Key> n){ 	//this < that  <0
		 		return key.compareTo(n.key);  	//this > that  >0
		  }
		  
		  public boolean isLeaf(){
			  if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
			  if (this.equals(root)) return false;
			  if (this.leftChild == null && this.rightChild == null){
				  return true;
			  }
			  return false;
		  }
	}

	 public boolean isLeaf(RedBlackTree.Node<String> n){
		  if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
		  if (n.equals(root)) return false;
		  if (n.leftChild == null && n.rightChild == null){
			  return true;
		  }
		  return false;
	  }
	
	public interface Visitor<Key extends Comparable<Key>> {
		/**
		This method is called at each node.
		@param n the visited node
		*/
		void visit(Node<Key> n);  
	}
	
	public void visit(Node<Key> n){
		System.out.println(n.key);
	}
	
	public void printTree(){  //preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;	
		printTree(currentNode);
	}
	
	public void printTree(RedBlackTree.Node<String> node){
		System.out.print(node.key);
		if (node.isLeaf()){
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}
	
	// place a new node in the binary search tree with data the parameter and color it red. 
	public void addNode(String data){  	//this < that  <0.  this > that  >0
//		System.out.println("adding: " + data);
		RedBlackTree.Node<String> n = new RedBlackTree.Node<>(data);
		if (root == null){ //n is the root
			root = n;
			root.parent = null;
			return;
		}
		RedBlackTree.Node<String> currentNode = root;
		while(currentNode != null || !currentNode.isLeaf()){
			if (n.compareTo(currentNode) < 0){
				//if the one you're trying to add is less than the currentNode go left
				if (currentNode.leftChild != null){
					currentNode = currentNode.leftChild;
				} else {
					currentNode.leftChild = n;
					n.parent = currentNode;
					n.color = 1;
					fixTree(n);
					return;
				}
			} else if (n.compareTo(currentNode) > 0){
				//if the one you're trying to add is greater than the currentNode go right
				if (currentNode.rightChild != null){
					currentNode = currentNode.rightChild;
				} else {
					currentNode.rightChild = n;
					n.parent = currentNode;
					n.color = 1;
					fixTree(n);
					return;
				}
			} else {
//				System.out.println("else");
				return;
			}
		} 
		//now currentNode is the leaf
		if (n.compareTo(currentNode) < 0){
			//if the one you're trying to add is less than the currentNode go left
			currentNode.leftChild = n;
			n.parent = currentNode;
			n.color = 1;
			fixTree(n);
		} else if (n.compareTo(currentNode) > 0){
			//if the one you're trying to add is greater than the currentNode go right
			currentNode.rightChild = n;
			n.parent = currentNode;
			n.color = 1;
			fixTree(n);
		} else {
			fixTree(n);
			return;
		}
	}	

	public void insert(String data){
		addNode(data);	
	}
	
	public RedBlackTree.Node<String> lookup(String k){ 
		RedBlackTree.Node<String> currentNode = root;
		RedBlackTree.Node<String> newNode = new RedBlackTree.Node<>(k);
		 while (!currentNode.key.equals(k)){
		  	if (currentNode.leftChild != null && newNode.compareTo(currentNode) < 0) { //data of k < data of currentNode
		 			currentNode = currentNode.leftChild;
		  	} else if (currentNode.rightChild != null && newNode.compareTo(currentNode) > 0) {
		 			currentNode = currentNode.rightChild;
		  	}	else { //if (leftChild == null && rightChild == null && data of currentNode isn't equal to k data)
		 		//has not been found
		  			currentNode.key = k + " is not found";
		  			return currentNode;
		  	}
		 }
		 return currentNode;
	}
 	
	
	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n){  
		if(n.parent.leftChild == null) return null;
		if(n.parent.leftChild.equals(n)){  //i am left, find right
			return n.parent.rightChild;
		} else {	//i am right, find left 
			return n.parent.leftChild; 
		}
	}
	
	
	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n){
		return getSibling(n.parent);
	}
	
	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n){
		return n.parent.parent;
	}
	
	public void rotateLeft(RedBlackTree.Node<String> n){
		RedBlackTree.Node<String> currentNode = n.rightChild;
		if (currentNode == null){
			RedBlackTree.Node temp = null; //n.parent: stackoverflow
			root = n;
			root.rightChild = temp;
			return;
		}
		n.rightChild = currentNode.leftChild;
		if (currentNode.leftChild != null){
			currentNode.leftChild.parent = n;
		}
		currentNode.parent = n.parent;
		if (n.parent == null){
			root = currentNode;
		} else if (n == n.parent.leftChild){
			n.parent.leftChild = currentNode;
		} else {
			n.parent.rightChild = currentNode;
		}
		currentNode.leftChild = n;
		n.parent = currentNode;
	}
	
	public void rotateRight(RedBlackTree.Node<String> n){
		RedBlackTree.Node<String> currentNode = n.leftChild;
		n.leftChild = currentNode.rightChild;
		if (currentNode.rightChild != null){
			currentNode.rightChild.parent = n;
		}
		currentNode.parent = n.parent;
		if (n.parent == null){
			root = currentNode;
		} else if (n == n.parent.rightChild){
			n.parent.rightChild = currentNode;
		} else {
			n.parent.leftChild = currentNode;
		}
		currentNode.rightChild = n;
		n.parent = currentNode;
	}
	
	public void fixTree(RedBlackTree.Node<String> current) {
//		System.out.println("tree fixed at node: " + current.key);
		if (current.equals(root)){	//if current is the root it must be black
			current.color = 0;
			return;
		}
		if (current.parent == null) return;
		if (current.parent.color == 0){ //if parent is black current can be red. is null when they are already 
										//added and there's no parent
			return;
		}
		if (current.color == 1 && current.parent.color == 1){ //unbalanced
			RedBlackTree.Node<String> grandparent = current.parent.parent;  
			RedBlackTree.Node<String> parent = current.parent;
			if (getAunt(current) == null || getAunt(current).color == 0){ //if aunt is empty or black			
				if (isLeftChild(grandparent, parent)){ //parent is left child of grandparent
					if (isLeftChild(parent,current)){ //current is left child
						//make parent black
						parent.color = 0;
						grandparent.color = 1;
						rotateRight(grandparent);
						return;
					} else { //current is right child
						rotateLeft(parent);
						fixTree(parent);
					}
				} else { //parent is right child of grandparent
					if (isLeftChild(parent,current)){ //current is left child of parent
						rotateRight(parent);
						fixTree(parent);
					} else {						 //current is right child of parent
						parent.color = 0;
						grandparent.color = 1;
						rotateLeft(grandparent);
						return;
					}
				}
			} else if (getAunt(current).color == 1) {
				parent.color = 0;
				getAunt(current).color = 0;
				grandparent.color = 1;
				fixTree(grandparent);
			}
		}
	}
	
	public boolean isEmpty(RedBlackTree.Node<String> n){
		if (n.key == null){
			return true;
		}
		return false;
	}
	 
	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child)
	{
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor<String> v) {
	   	preOrderVisit(root, v);
	}
	 
	 
	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
	  	if (n == null) {
	  		return;
	  	}
	  	v.visit(n);
	  	preOrderVisit(n.leftChild, v);
	  	preOrderVisit(n.rightChild, v);
	}	
}

