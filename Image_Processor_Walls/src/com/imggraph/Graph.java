package com.imggraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph
{
	// This is just for demo, only basic (almost none lol) methods are implemented

	ArrayList<Node[]> edges;
	HashMap<String,List<String>> subGraph= new HashMap<String,List<String>>();

	// The Graph is represented by a List of 'edges' (Node[] represents a edge, which is an array of 2 Nodes)

	
	public Graph(Node... nodes)
	{
		edges = new ArrayList<>();

		for (int i = 0; i < nodes.length - 1; i += 2)
		{
			edges.add(new Node[]
			{
				nodes[i], nodes[i + 1]
			});
		}

		if (nodes.length % 2 == 1)
		{
			edges.add(new Node[]
			{
				nodes[nodes.length - 1]
			});
		}
	}

	public Node[] PrintGraph()
	{
		
		Node[] nodes = new Node[(edges.size()-1)*edges.get(edges.size()-1).length+2];
		int counter=0;
		for (int j = 0; j < edges.size(); j++)
		{
			for (int k = 0; k < edges.get(j).length; k++)
			{
				//System.out.println(String.valueOf(counter++))
				try{
					nodes[counter++] = new Node(edges.get(j)[k].label);
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Coordonatele nu au putut fi interpretate");
				}
				
				//System.out.print(edges.get(j)[k].label + ", ");
			}
			
		}
		return nodes; 
	}
	
	public HashMap<String,List<String>> getGraphList(){
		return subGraph;
	}

}

