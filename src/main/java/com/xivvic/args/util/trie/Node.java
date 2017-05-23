package com.xivvic.args.util.trie;

import static com.xivvic.args.util.trie.Constants.DIM;

class Node
{
	Object val;
	Node[] next = new Node[DIM];

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(256);
		sb.append("Node -> ");
		sb.append(val);
		sb.append("\n");
		for (int i = 0; i < DIM; i++)
		{
			Node child = next[i];
			if (child == null)
			{
				continue;
			}

			char c = Constants.VALID_CHARS.charAt(i);
			sb.append("\t");
			sb.append(i);
			sb.append(":");
			sb.append(c);
			sb.append(" -> ");
			sb.append(child);
			sb.append("\n");
		}

		return sb.toString();
	}
}