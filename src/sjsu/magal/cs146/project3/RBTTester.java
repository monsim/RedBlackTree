package sjsu.magal.cs146.project3;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

//when you check if two nodes are the same do you check if they have the same color and key
//preorderVisit(v) compared with visit(node) compared with printTree()
//printTree calls preOrderVisit which calls visit
//lookup before adding

public class RBTTester {

	@Test
	public void dictionary() throws IOException{
		RedBlackTree<String> rbt1 = new RedBlackTree<>();
		//File dictionary = new File("C:/Users/Thirmal/Desktop/engmix.txt");
		File dictionary = new File("C:/Users/Thirmal/Desktop/SJSU/Sophomore/2nd sem/CS 146/Workspaces/project3/proj3/src/sjsu/magal/cs146/project3/engmix.txt");
		FileReader fr = new FileReader(dictionary);
		BufferedReader br = new BufferedReader(fr);
		String line;
		long startDict = System.currentTimeMillis();
		while ((line = br.readLine()) != null){
			rbt1.addNode(line);
		}
		long endDict = System.currentTimeMillis();
		//Scanner scan = new Scanner(new File("C:/Users/Thirmal/Desktop/test.txt")); 
		Scanner scan = new Scanner(new File("C:/Users/Thirmal/Desktop/SJSU/Sophomore/2nd sem/CS 146/Workspaces/project3/proj3/src/sjsu/magal/cs146/project3/test.txt"));
		RedBlackTree.Node<String> found;
		String line1;
		long startLookup = System.currentTimeMillis();
		while(scan.hasNext()){
			line1 = scan.next();
			found = rbt1.lookup(line1.toLowerCase());
			System.out.println(found.key);
		}
		long endLookup = System.currentTimeMillis();
		long elapsedDict = endDict - startDict;
		long elapsedLookup = endLookup - startLookup;
		System.out.println("Time to make dictionary: " + elapsedDict + " milliseconds");
		System.out.println("Time to lookup poem: " + elapsedLookup + " milliseconds");
		scan.close();
		rbt1 = null;	
	}
	
	@Test
	public void test() {
		RedBlackTree<String> rbt = new RedBlackTree<>();
        rbt.addNode("D");
        rbt.addNode("B");
        rbt.addNode("A");
        rbt.addNode("C");
        rbt.addNode("F");
        rbt.addNode("E");
        rbt.addNode("H");
        rbt.addNode("G");
        rbt.addNode("I");
        rbt.addNode("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str=     
"Color: 0, Key:D Parent: \n"+		//0 is black 1 is red NOTE THE CHANGE
"Color: 0, Key:B Parent: D\n"+
"Color: 0, Key:A Parent: B\n"+
"Color: 0, Key:C Parent: B\n"+
"Color: 0, Key:F Parent: D\n"+
"Color: 0, Key:E Parent: F\n"+
"Color: 1, Key:H Parent: F\n"+
"Color: 0, Key:G Parent: H\n"+
"Color: 0, Key:I Parent: H\n"+
"Color: 1, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
		rbt = null;
    }
    
     public static String makeString(RedBlackTree t)
    {
       class MyVisitor implements RedBlackTree.Visitor { 
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);                     
       return v.result;
    }

    
    public static String makeStringDetails(RedBlackTree t) {
    	       class MyVisitor implements RedBlackTree.Visitor {  
    	          String result = "";
    	          public void visit(RedBlackTree.Node n)  
    	          {
    	        	  if(!(n.key).equals("")){
    	        		  if(n.parent == null){
    	        			  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+"\n";
    	        		  } else {
    	        		  
    	        			  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+"\n";
    	        		  }
    	        	  }
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	      return v.result;
    }
       
    @Test
    public void testAddNode2(){
    	RedBlackTree<String> rbt7 = new RedBlackTree<String>();
    	rbt7.addNode("A");
    	rbt7.addNode("K");
    	rbt7.addNode("B");
    	rbt7.addNode("D");
    	rbt7.addNode("C");
    	assertEquals("BADCK", makeString(rbt7));
    	rbt7 = null;
    }
    
    @Test
    public void testAddNode1(){
    	RedBlackTree<String> rbt2 = new RedBlackTree<String>();
    	rbt2.addNode("D");
    	rbt2.addNode("C");
    	rbt2.addNode("B");
    	rbt2.addNode("A");
    	assertEquals("CBAD", makeString(rbt2));
    	rbt2 = null;
    }
    
    @Test
    public void testAddNode3(){	
    	RedBlackTree<String> rbt3 = new RedBlackTree<String>();
    	rbt3.addNode("A");
    	rbt3.addNode("B");
    	rbt3.addNode("D");
    	rbt3.addNode("C");
    	rbt3.addNode("E");
    	assertEquals("BADCE", makeString(rbt3));
    	rbt3 = null;
    }
    
    @Test
    public void testAddNode(){
    	RedBlackTree<String> rbt8 = new RedBlackTree<String>();
    	rbt8.addNode("A");
    	rbt8.addNode("B");
    	rbt8.addNode("C");
    	rbt8.addNode("D");
    	assertEquals("BACD", makeString(rbt8));
    	rbt8 = null;
    }    
    
    @Test
    public void testIsLeaf(){
    	RedBlackTree<String> rbt4 = new RedBlackTree<String>();
    	rbt4.addNode("A");
    	rbt4.addNode("C");
    	rbt4.addNode("B");
    	rbt4.addNode("D");
    	RedBlackTree.Node look1 = rbt4.lookup("D");
    	RedBlackTree.Node look2 = rbt4.lookup("B");
    	assertEquals(true, rbt4.isLeaf(look1)); 
    	assertEquals(false, rbt4.isLeaf(look2)); 
    }
    
    @Test
    public void testLookup(){
    	RedBlackTree<String> rbt6 = new RedBlackTree<String>();
    	rbt6.addNode("A");
    	rbt6.addNode("C");
    	rbt6.addNode("B");
    	rbt6.addNode("D");
    	RedBlackTree.Node node = rbt6.lookup("C");
    	assertEquals("C", node.key);
    	RedBlackTree.Node node1 = rbt6.lookup("D");
    	assertEquals("D", node1.key);
    	rbt6 = null;
    }
}
