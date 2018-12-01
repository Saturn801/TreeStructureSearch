/*
 * 	Samuel James Bryan - 14701935
 */

package tree;

import java.util.ArrayList;
import java.util.Map;

public class Node {
	private int staticEval;
	private ArrayList<Node> daughters = new ArrayList<Node>();
	private ArrayList<Node> modifiedDaughters = new ArrayList<Node>();
	
	public Node(int s){
		staticEval = s;
	}
	
	public void setDaughters(Map<Integer, Node> daughterSet){
		for(Map.Entry<Integer, Node> entry : daughterSet.entrySet()){
			Node daughter = entry.getValue();
			daughters.add(daughter);
			modifiedDaughters.add(daughter);
		}
	}
	
	public void resetModifiedDaughters(){
		modifiedDaughters.clear();
		modifiedDaughters.addAll(daughters);
		for(Node daughter : modifiedDaughters){
			daughter.resetModifiedDaughters();
		}
	}
	
	public void reOrderDaughters(ArrayList<Node> principalVariation, int currIndex){
		if(daughters.isEmpty() || currIndex > principalVariation.size()-1)
			return;
		// Because the order of the path, the first Node in the list will be
		//the 'highest', ie, the one that needs re-ordering to the front.
		Node bestDaughter = principalVariation.get(currIndex);
		int position = modifiedDaughters.indexOf(bestDaughter);
		modifiedDaughters.remove(position);
		modifiedDaughters.add(0, bestDaughter);
		modifiedDaughters.get(0).reOrderDaughters(principalVariation, currIndex+1);
	}
	
	public ArrayList<Node> getDaughters(){
		return daughters;
	}
	
	public ArrayList<Node> getModifiedDaughters(){
		return modifiedDaughters;
	}
	
	public int getStaticEval(){
		return staticEval;
	}
	
	public String printFamily(int numTabs, Boolean useModified){
		ArrayList<Node> listToPrint;
		if(useModified)
			listToPrint = modifiedDaughters;
		else
			listToPrint = daughters;
		String tabs = "";
		for(int i=0; i<numTabs; i++)
			tabs += "\t";
		String output = "Node: " + staticEval;
		for(Node node : listToPrint){
			output += "\n" + tabs + "Daughter " + 
					node.printFamily(numTabs+1, useModified);
		}
		return output;
	}
}
