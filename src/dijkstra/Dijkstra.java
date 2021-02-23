package dijkstra;

import java.util.ArrayList;
import java.util.Random;

public class Dijkstra
{
	private int height, width;
	private Node[][] matrix;
	private MinHeapNode heap;
	private int endx, endy;
	private ArrayList<Node> list = new ArrayList<Node>(); // Creating arraylist.

	public Dijkstra(int height, int width)
	{
		super();
		Random rand = new Random();
		heap = new MinHeapNode(height * width);
		this.height = height;
		this.width = width;
		matrix = new Node[height][width]; // Create the board
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				int weight = rand.nextInt(10 - 1) + 1;
				matrix[i][j] = new Node(i, j, weight);
				heap.insert(matrix[i][j]);
			}
		}
	}

	public void setStart(int i, int j)
	{
		matrix[i][j].dist = 0;
		matrix[i][j].type = 'S';
		matrix[i][j].flag = true;
//		matrix[i][j].prev = matrix[i][j];
	}

	public void setEnd(int i, int j)
	{
		matrix[i][j].type = 'E';
		matrix[i][j].flag = true;
		matrix[i][j].weight = 0;
		endx = i;
		endy = j;
		heap.minHeap();
	}

	public int getEndX()
	{
		return endx;
	}

	public int getEndY()
	{
		return endy;
	}

	public Node getNode(int i, int j)
	{
		return matrix[i][j];
	}

	public String get(int i, int j)
	{
		return matrix[i][j].toString();
	}

	public void setVisit(int i, int j)
	{
		matrix[i][j].visited = true;
	}

	public String toString()
	{
		StringBuilder b = new StringBuilder(); // Build a string using StringBuilder
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				b.append(get(i, j) + " ");
			}
			b.append("\n");
		}
		return b.toString();
	}

	public void findPath()
	{
		while (heap.getSize() > 0)
//		while (!list.isEmpty())
		{
//			System.out.println("k " + heap.getSize());
//			list.add(heap.remove());
			Node n = heap.remove();
			n.visited = true;

//			System.out.println(n.i + ", " + n.j);
			ArrayList<Node> lst = neighbors(n);
			for (Node i : lst)
			{
				int alt = n.dist + i.weight;
				if (alt < i.dist)
				{
					i.dist = alt;
					i.prev = n;
				}
			}
			heap.minHeap();
		}

		Node m = matrix[endx][endy];
		while (m.prev != null)
		{
			list.add(m.prev);
			m.prev.flag = true;
			m = m.prev;
		}

//		for (Node k : list)
//			System.out.println("(" + k.i + ", " + k.j + ")");

		System.out.println(matrix[endx][endy].dist);

	}

	public ArrayList<Node> neighbors(Node n)
	{
		ArrayList<Node> vertices = new ArrayList<>();
		int x = n.i;
		int y = n.j;

		if (y > 0 && !matrix[x][y - 1].visited)
			vertices.add(matrix[x][y - 1]); // add neighbor from the left
		if (x > 0 && !matrix[x - 1][y].visited)
			vertices.add(matrix[x - 1][y]); // add neighbor from above
		if (y < matrix.length - 1 && !matrix[x][y + 1].visited)
			vertices.add(matrix[x][y + 1]); // add neighbor from the right
		if (x < matrix.length - 1 && !matrix[x + 1][y].visited)
			vertices.add(matrix[x + 1][y]); // add neighbor from below
		if (y > 0 && x > 0 && !matrix[x - 1][y - 1].visited)
			vertices.add(matrix[x - 1][y - 1]); // add neighbor from the above-left
		if (y < matrix.length - 1 && x > 0 && !matrix[x - 1][y + 1].visited)
			vertices.add(matrix[x - 1][y + 1]); // add neighbor from the above-right
		if (y > 0 && x < matrix.length - 1 && !matrix[x + 1][y - 1].visited)
			vertices.add(matrix[x + 1][y - 1]); // add neighbor from the below-left
		if (y < matrix.length - 1 && x < matrix.length - 1 && !matrix[x + 1][y + 1].visited)
			vertices.add(matrix[x + 1][y + 1]); // add neighbor from the below-right
		return vertices;
	}

	public void printAnswer()
	{

		StringBuilder b = new StringBuilder("["); // Build a string using StringBuilder
		for (int i = list.size() - 1; i >= 0; i--)
			b.append("(" + list.get(i).i + ", " + list.get(i).j + ")->");
		b.append("(" + matrix[endx][endy].i + ", " + matrix[endx][endy].j + ")]");
		System.out.println(b.toString());

	}

	public static void main(String[] args)
	{
		Dijkstra d = new Dijkstra(8, 8);
		d.setStart(2, 1);
		d.setEnd(7, 7);
		System.out.println(d.toString());
		d.findPath();
		System.out.println(d.toString());

		d.printAnswer();
	}

}
