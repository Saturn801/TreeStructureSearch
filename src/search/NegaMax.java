/*
 * 	Samuel James Bryan - 14701935
 */

package search;

import java.util.ArrayList;

import tree.Node;

public class NegaMax extends Search {
	public SearchResult searchTree(Node node, int h, 
			int alpha, int beta, Boolean useModified){
		numStaticEvals++;
		if(h==0 || node.getDaughters().isEmpty()){
			return new SearchResult(node.getStaticEval());
		}
		else{
			SearchResult tempResult = null, bestResult = null;
			int value, bestValue = alpha;				
			Node bestDaughter = null;	
			Boolean first = true;
			ArrayList<Node> nodeDaughters;
			
			if(useModified)
				nodeDaughters = node.getModifiedDaughters();
			else
				nodeDaughters = node.getDaughters();
			
			for(Node daughter : nodeDaughters){
				tempResult = searchTree(daughter, h-1, -beta, -alpha, useModified);
				value = -tempResult.getValue();
				if(value > bestValue || first){
					bestValue = value;
					bestDaughter = daughter;
					bestResult = tempResult;
				}
				alpha = Max(value, alpha);
				first = false;
				if(alpha >= beta)
					break;				
			}		
			
			ArrayList<Node> newPath = new ArrayList<Node>();
			newPath.add(bestDaughter);
			newPath.addAll(bestResult.getPrincipalVariation());			
			return new SearchResult(bestValue, newPath);
		}	
	}
}
