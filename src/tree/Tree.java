/*
 * 	Samuel James Bryan - 14701935
 */

package tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tree {
	private int branchFactor;
	private int height; 
	private int approx;
	private Node rootNode = null;
	private int numNodes = 0;
	
	public Tree(int b, int h, int a){
		if(h<1 || b<2){
			System.err.println("Tree must have height of at least 1 and" +
					" a branching factor of at least 2.");
			System.exit(0);
		}
		branchFactor = b;
		height = h;
		approx = a;
		constructTree();
	}
	
	private void constructTree(){
		int depth = 0, T, randApprox, E;
		Map<Integer, Node> nodeDaughters;
		
		// Since root node is always interior, calculate its E using T and approx.
		T = generateRandomValue(-2500, 2500);		
		randApprox = generateRandomValue(-approx, approx);
		E = T + randApprox;
		rootNode = new Node(E);
		numNodes++;
		
		// Generate daughters of root.
		nodeDaughters = generateDaughters(rootNode, branchFactor, T, depth);		
		rootNode.setDaughters(nodeDaughters);
		numNodes += nodeDaughters.size();
		
		System.out.println("ROOT T: " + T + "\n");
	}
	
	private int randomizeBranchingFactor(){
		int roll = generateRandomValue(1,100);
		if(roll <= 90)
			return branchFactor;
		else if(roll > 90 && roll<= 95)
			return branchFactor+1;
		else
			return branchFactor-1;
	}
	
	private Map<Integer, Node> generateDaughters(Node node,
			int numDaughters, int T, int depth){
		Map<Integer, Node> daughters = new HashMap<Integer,Node>();
		
		if(T >= 10000 || T <= -10000 || depth==height)	// Won-game position
			return daughters;							// OR maximum depth reached.
				
		// Pick random daughter to be best.
		int bestDaughter = generateRandomValue(1, numDaughters); 
		
		for(int i=1; i<=numDaughters; i++){
			if(i==bestDaughter){
				if(depth==height-1){
					Node daughterNode = new Node(-T);
					daughters.put(-T, daughterNode);
				}
				else{
					int randApprox = generateRandomValue(-approx, approx);
					int E = -T + randApprox;
					Node daughterNode = new Node(E);
					daughters.put(-T, daughterNode);
				}
			}
			else{
				int newT = generateRandomValue(-T, 10000);
				if(depth==height-1 || newT == 10000){
					Node daughterNode = new Node(newT);
					daughters.put(newT, daughterNode);
				}
				else{
					int randApprox = generateRandomValue(-approx, approx);
					int E = newT + randApprox;
					Node daughterNode = new Node(E);
					daughters.put(newT, daughterNode);
				}
			}
		}
		
		// Generate daughters for the daughters, until tree is complete.
		for(Map.Entry<Integer, Node> entry : daughters.entrySet()){
			Node daughterNode = entry.getValue();
			int daughterT = entry.getKey();
			int b = randomizeBranchingFactor();
			Map<Integer, Node> newDaughters = generateDaughters(daughterNode, b,
					daughterT, depth+1);
			if(!newDaughters.isEmpty()){
				daughterNode.setDaughters(newDaughters);
				numNodes += newDaughters.size();
			}
		}
		
		return daughters;
	}
	
	// Generate true search value between min and max inclusive.
	private int generateRandomValue(int min, int max){		
		Random rand = new Random();
		int num = rand.nextInt((max+1)-min) + min;
		return num;
	}
	
	public String printTree(Boolean useModified){
		return "Root " + rootNode.printFamily(1, useModified);
	}
	
	public void resetTree(){
		rootNode.resetModifiedDaughters();
	}
	
	public Node getRoot(){
		return rootNode;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getNumNodes(){
		return numNodes;
	}
}