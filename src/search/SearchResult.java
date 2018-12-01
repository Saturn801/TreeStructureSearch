/*
 * 	Samuel James Bryan - 14701935
 */

package search;

import java.util.ArrayList;

import tree.Node;

public class SearchResult {
	int value;
	private ArrayList<Node> principalVariation = new ArrayList<Node>();

	public SearchResult(int v){
		value = v;
	}
	
	public SearchResult(int v, ArrayList<Node> path){
		value = v;
		principalVariation.addAll(path);
	}

	public int getValue(){
		return value;
	}
	
	public ArrayList<Node> getPrincipalVariation(){
		return principalVariation;
	}
	
	public String toString(){
		String output = "\tValue: " + value;
		output += "\n\tPath: [";
		int count = 0;
		for(Node node : principalVariation){
			if(count==principalVariation.size()-1){
				output += node.getStaticEval();
			}
			else
				output += node.getStaticEval() + ", ";
			count++;
		}
		return output += "]";
	}
}