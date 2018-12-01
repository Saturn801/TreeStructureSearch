/*
 * 	Samuel James Bryan - 14701935
 */

package search;

import tree.Node;

public abstract class Search {
	protected int numStaticEvals = 0;

	public abstract SearchResult searchTree(Node node, int h, int alpha,
			int beta, Boolean useModified);
	
	public void iterativeSearch(int minDepth, int maxDepth, Node rootNode, int alpha,
			int beta, Boolean useModified, Boolean printValues){
		for(int depth = minDepth; depth <= maxDepth; depth++){
			SearchResult result = searchTree(rootNode, depth, alpha, beta, useModified);
			rootNode.resetModifiedDaughters();
			if(printValues){
				if(useModified)
					System.out.println("\n\nWITH REORDERING");
				else
					System.out.println("\n\nWITHOUT REORDERING");
				System.out.println("NegaMax Depth " + depth + "\n" + result);
			}
			if(useModified){				
				rootNode.reOrderDaughters(result.getPrincipalVariation(), 0);
			}
		}
	}
	
	protected int Max(int a, int b){
		if(a >= b)
			return a;
		else
			return b;
	}
	
	public int getNumStaticEvals(){
		return numStaticEvals;
	}
	
	public void resetStaticEvals(){
		numStaticEvals = 0;
	}
}
