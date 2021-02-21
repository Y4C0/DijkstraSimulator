package dijkstra;

public class MinHeapNode
{

	private Node[] Heap;
	private int size;
	private int maxsize;

	private static final int FRONT = 1;

	public MinHeapNode(int maxsize)
	{
		this.maxsize = maxsize;
		this.size = 0;
		Heap = new Node[this.maxsize + 1];
		Heap[0] = new Node(-1, -1, 0);
	}

	// Function to return the position of
	// the parent for the node currently
	// at pos
	private int parent(int pos)
	{
		return pos / 2;
	}

	// Function to return the position of the
	// left child for the node currently at pos
	private int leftChild(int pos)
	{
		if (pos>size)
			return 0;
		return (2 * pos);
	}

	// Function to return the position of
	// the right child for the node currently
	// at pos
	private int rightChild(int pos)
	{
		if (pos>size)
			return 0;
		return (2 * pos) + 1;
	}

	// Function that returns true if the passed
	// node is a leaf node
	private boolean isLeaf(int pos)
	{
		if (pos >= (size / 2) && pos <= size)
		{
			return true;
		}
		return false;
	}

	// Function to swap two nodes of the heap
	private void swap(int fpos, int spos)
	{
		Node tmp;
		tmp = Heap[fpos];
		Heap[fpos] = Heap[spos];
		Heap[spos] = tmp;
	}

	// Function to heapify the node at pos
	private void minHeapify(int pos)
	{
//		System.out.println(" hine! " + pos);

        // If the node is a non-leaf node and greater 
        // than any of its child 
        if (!isLeaf(pos)) { 
            if (Heap[pos].dist > Heap[leftChild(pos)] .dist
                || Heap[pos].dist > Heap[rightChild(pos)].dist) { 
  
                // Swap with the left child and heapify 
                // the left child 
                if (Heap[leftChild(pos)].dist < Heap[rightChild(pos)].dist) { 
                    swap(pos, leftChild(pos)); 
                    minHeapify(leftChild(pos)); 
                } 
  
                // Swap with the right child and heapify 
                // the right child 
                else { 
                    swap(pos, rightChild(pos)); 
                    minHeapify(rightChild(pos)); 
                } 
            } 
        } 
	}

	// Function to insert a node into the heap
	public void insert(Node element)
	{
		if (size >= maxsize)
		{
			return;
		}
		Heap[++size] = element;
		int current = size;
//		System.out.println(" minHeap: " + size);

		while (Heap[current].dist < Heap[parent(current)].dist)
		{
			swap(current, parent(current));
			current = parent(current);
		}
	}

	// Function to print the contents of the heap
	public void print()
	{
		for (int i = 1; i <= size / 2; i++)
		{
			System.out.print(
					" PARENT : " + Heap[i] + " LEFT CHILD : " + Heap[2 * i] + " RIGHT CHILD :" + Heap[2 * i + 1]);
			System.out.println();
		}
	}

	// Function to build the min heap using
	// the minHeapify
	public void minHeap()
	{
		for (int pos = (size / 2); pos >= 1; pos--)
		{
			minHeapify(pos);
		}
	}

	// Function to remove and return the minimum
	// element from the heap
	public Node remove()
	{
		Node popped = Heap[FRONT];
		Heap[FRONT] = Heap[size--];
		minHeapify(FRONT);
		return popped;
	}

	public int getSize()
	{
		return size;
	}

	// Driver code
	public static void main(String[] arg)
	{
		System.out.println("The Min Heap is ");
		MinHeapNode minHeap = new MinHeapNode(15);
		Node a = new Node(4, 1, 7);
		minHeap.insert(a);
		minHeap.insert(new Node(1, 1, 8));
		minHeap.insert(new Node(1, 1, 6));
		minHeap.insert(new Node(1, 1, 10));
		minHeap.insert(new Node(5, 1, 4));
		minHeap.insert(new Node(1, 1, 19));
		Node b = new Node(7, 70, 8);
		minHeap.insert(b);
		minHeap.insert(new Node(1, 1, 6));
		minHeap.insert(new Node(1, 1, 9));
		minHeap.minHeap();

		minHeap.print();
		Node n = minHeap.remove();
		System.out.println("The Min val is " + n.dist + ", " + n.i);
		b.dist = 5;
//		minHeap.minHeap();
		Node m = minHeap.remove();
		System.out.println("The Min val is " + m.dist + ", " + m.i);

	}
}
