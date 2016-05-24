package com.imggraph;

import java.util.ArrayList;
import java.util.List;

public class GraphCycleFinder
{
	private static Graph aGraph;
	private static List<Node[]> cycles;

	private static List<Graph> returnCycles;

	static public List<Graph> GetCycles(Graph graph)
	{
		aGraph = graph;
		cycles = new ArrayList<Node[]>();

		Start();

		returnCycles = new ArrayList<>();
		for (Node[] node : cycles)
		{
			
			returnCycles.add(new Graph(node));
		}

		return returnCycles;
	}

	private static void Start()
	{
		for (int i = 0; i < aGraph.edges.size(); i++)
		{
			for (int j = 0; j < aGraph.edges.get(i).length; j++)
			{
				findNewCycles(new Node[]
				{
					aGraph.edges.get(i)[j]
				});
			}
		}
	}

	private static void findNewCycles(Node[] path)
	{
		Node n = path[0];
		Node x;
		Node[] sub = new Node[path.length + 1];

		for (int i = 0; i < aGraph.edges.size(); i++)
		{
			for (int y = 0; y <= 1; y++)
			{
				try{
				if (aGraph.edges.get(i)[y].label.equals(n.label))	//  edge refers to our current node
				{
					x = aGraph.edges.get(i)[(y + 1) % 2];
					if (!visited(x, path))			//  neighbor node not on path yet
					{
						sub[0] = x;
						System.arraycopy(path, 0, sub, 1, path.length);		//  explore extended path
						findNewCycles(sub);
					}
					else if ((path.length > 2) && (x == path[path.length - 1]))		//  cycle found
					{
						Node[] p = normalize(path);
						Node[] inv = invert(p);
						if (isNew(p) && isNew(inv))
						{
							cycles.add(p);
						}
					}
				}
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Coordonatele nu au putut fi interpretate");
			}
			}
		}
	}

	//  check of both arrays have same lengths and contents
	private static Boolean equals(Node[] a, Node[] b)
	{
		Boolean ret = (a[0] == b[0]) && (a.length == b.length);

		for (int i = 1; ret && (i < a.length); i++)
		{
			if (a[i] != b[i])
			{
				ret = false;
			}
		}

		return ret;
	}

	//  create a path array with reversed order
	private static Node[] invert(Node[] path)
	{
		Node[] p = new Node[path.length];

		for (int i = 0; i < path.length; i++)
		{
			p[i] = path[path.length - 1 - i];
		}

		return normalize(p);
	}

	//  rotate cycle path such that it begins with the smallest node
	private static Node[] normalize(Node[] path)
	{
		Node[] p = new Node[path.length];
		Node x = smallest(path);
		Node n;

		System.arraycopy(path, 0, p, 0, path.length);

		while (p[0] != x)
		{
			n = p[0];
			System.arraycopy(p, 1, p, 0, p.length - 1);
			p[p.length - 1] = n;
		}

		return p;
	}

	//  compare path against known cycles
	//  return true, iff path is not a known cycle
	private static Boolean isNew(Node[] path)
	{
		Boolean ret = true;

		for (Node[] p : cycles)
		{
			if (equals(p, path))
			{
				ret = false;
				break;
			}
		}

		return ret;
	}

	//  return the Node of the array which is the smallest
	private static Node smallest(Node[] path)
	{
		Node min = path[0];

		for (Node p : path)
		{
			if (p.label.compareTo(min.label) < 0)
			{
				min = p;
			}
		}

		return min;
	}

	//  check if vertex n is contained in path
	private static Boolean visited(Node n, Node[] path)
	{
		Boolean ret = false;

		for (Node p : path)
		{
			if (p == n)
			{
				ret = true;
				break;
			}
		}
		return ret;
	}
}

