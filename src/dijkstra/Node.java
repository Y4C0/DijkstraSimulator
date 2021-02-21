package dijkstra;

public class Node
{
	protected int i, j;
	protected int dist;
	protected int weight;
	protected Node prev;
	protected char type;
	protected boolean flag;
	protected boolean visited;

	public Node(int i, int j, int weight)
	{
		super();
		this.i = i;
		this.j = j;
		dist = Integer.MAX_VALUE;
		prev = null;
		flag = false;
		type = 'N';
		this.weight = weight;
		visited = false;
	}

	public int getI()
	{
		return i;
	}
	
	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	@Override
	public String toString()
	{
		if (flag)
			return "" + type;
//		else if (!visited)
//			return ".";
		else
			return "" + weight;
	}

}