/*
 * 	Samuel James Bryan - 14701935
 */

package test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import search.NegaMax;
import search.PVS;
import search.SearchResult;
import tree.Tree;

public class Test {
	private void testIterativeDeepening(Tree testTree, Boolean useModified){
		NegaMax testNegaMax = new NegaMax();
		PVS testPVS = new PVS();
		System.out.println("Num Nodes: " + testTree.getNumNodes());
		testNegaMax.iterativeSearch(1, testTree.getHeight(), testTree.getRoot(),
				-10000, 10000, useModified, true);
		System.out.println("\nTotal Static Evals: " +
				testNegaMax.getNumStaticEvals());
		testTree.getRoot().resetModifiedDaughters();
		testPVS.iterativeSearch(1, testTree.getHeight(), testTree.getRoot(),
				-10000, 10000, useModified, true);
		System.out.println("\nTotal Static Evals: " +
				testPVS.getNumStaticEvals());
	}
	
	private void testIndividualSearches(Tree testTree){
		NegaMax testNegaMax = new NegaMax();
		PVS testPVS = new PVS();
		
		SearchResult result = testNegaMax.searchTree(testTree.getRoot(), 
				testTree.getHeight(), -10000, 10000, false);
		System.out.println("\n\nNegaMax \n" + result);
		System.out.println("Num Static Evals: " +
		testNegaMax.getNumStaticEvals());
		
	    result = testPVS.searchTree(testTree.getRoot(), 
				testTree.getHeight(), -10000, 10000, false);
		System.out.println("\n\nPVS \n" + result);
		System.out.println("Num Static Evals: " +
		testPVS.getNumStaticEvals());
	}
	
	private void testTreeReordering(Tree testTree){
		NegaMax testNegaMax = new NegaMax();
		SearchResult result = testNegaMax.searchTree(testTree.getRoot(), 
				testTree.getHeight(), -10000, 10000, false);
		
		testTree.getRoot().reOrderDaughters(result.getPrincipalVariation(), 0);
		System.out.println("\n\n" + testTree.printTree(true));
		System.out.println("\n\n" + testTree.printTree(false));
		testTree.resetTree();
		System.out.println("\n\n" + testTree.printTree(true));
		System.out.println("\n\n" + testTree.printTree(false));
		testTree.getRoot().reOrderDaughters(result.getPrincipalVariation(), 0);
		System.out.println("\n\n" + testTree.printTree(true));
		System.out.println("\n\n" + testTree.printTree(false));	
	}
	
	private String runTestsOnTrees(int branchFactor, int height,
			int approx, int numTrees){
		int staticEvalsNegamax = 0, staticEvalsNegamaxReordered = 0,
				staticEvalsPVS = 0, staticEvalsPVSReordered = 0;
		NegaMax testNegaMax = new NegaMax();
		PVS testPVS = new PVS();
		for(int i=0; i<numTrees; i++){
			Tree tree = new Tree(branchFactor, height, approx);
			// Negamax WITHOUT reordering
			testNegaMax.iterativeSearch(1, tree.getHeight(), tree.getRoot(),
					-10000, 10000, false, false);
			staticEvalsNegamax += testNegaMax.getNumStaticEvals();
			testNegaMax.resetStaticEvals();
			// Negamax WITH reordering
			testNegaMax.iterativeSearch(1, tree.getHeight(), tree.getRoot(),
					-10000, 10000, true, false);
			staticEvalsNegamaxReordered += testNegaMax.getNumStaticEvals();
			testNegaMax.resetStaticEvals();
			tree.getRoot().resetModifiedDaughters();

			// PVS WITHOUT reordering
			testPVS.iterativeSearch(1, tree.getHeight(), tree.getRoot(),
					-10000, 10000, false, false);
			staticEvalsPVS += testPVS.getNumStaticEvals();
			testPVS.resetStaticEvals();
			// PVS WITH reordering
			testPVS.iterativeSearch(1, tree.getHeight(), tree.getRoot(),
					-10000, 10000, true, false);
			staticEvalsPVSReordered += testPVS.getNumStaticEvals();
			testPVS.resetStaticEvals();
			tree.getRoot().resetModifiedDaughters();
		}	
		String output = "\nTest Trees with branch " + branchFactor + 
				", height " + height + " and approx " + approx;
		
		output += " \nNEGAMAX WITHOUT REORDERING";
		output += " \nAverage Num Static Evals: " +
		staticEvalsNegamax/numTrees;
		
		output += " \nNEGAMAX WITH REORDERING";
		output += " \nAverage Num Static Evals: " +
		staticEvalsNegamaxReordered/numTrees;
		
		output += " \nPVS WITHOUT REORDERING";
		output += " \nAverage Num Static Evals: " +
		staticEvalsPVS/numTrees;		
		
		output += " \nPVS WITH REORDERING";
		output += " \nAverage Num Static Evals: " +
		staticEvalsPVSReordered/numTrees;
		
		return output;
	}
	
	// Tests done up to at most branchFactor 12 because of PC limitations.
	private void runAllTests(Test test, int numTrees){
		PrintWriter out = null;
		try {
			out = new PrintWriter("tests.txt");
		} catch (FileNotFoundException e) {
			System.err.println("Cannot write to file.");
			System.exit(0);
		}
		for(int height = 4; height <= 6; height++){
			for(int branchFactor = 3; branchFactor <= 12; branchFactor += 3){
				for(int approx = 0; approx <= 300; approx += 50){
					String output = test.runTestsOnTrees(branchFactor,
							height, approx, numTrees);
					out.println(output);
				}
			}
		}
		out.close();
	}
	
	public static void main(String[] args) {
		Test test = new Test();
		//Tree testTree = new Tree(3,3,5);
		//test.testIndividualSearches(testTree);
		//System.out.println("Num Nodes: " + testTree.getNumNodes());
		//System.out.println(testTree.printTree(false));
		//test.testIterativeDeepening(testTree, true);
		//String output = test.runTestsOnTrees(10, 6, 5, 25);
		//System.out.println(output);
		test.runAllTests(test, 25);
	}												    
}
