package com.imggraph;

//author octav
public class Node
{
	// This is just for demo. A node is basically a Point. The label field is used in the cycle finder algorithm
	// This class WILL have those two fields
	// int x;
	// int y;

	String label;

	public Node(String label)
	{
		this.label = label;
	}
}
