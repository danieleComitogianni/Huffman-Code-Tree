package ca.concordia.algos.huffman;

import java.util.NoSuchElementException;

public class MinHeap {

	public CharFrequency[] heap;
	public CharFrequency root;
	int size;
	int maxsize;
	int order=0;
	
	public MinHeap(int maxsize) {
		this.maxsize=maxsize;
		this.size=0;
		heap = new CharFrequency[this.maxsize];
	}
	
	public int size() {
		return this.size;
	}
	public int parent(int pos) {
		return (pos-1)/2;
	}
	
	public int leftChild(int pos) {
		return (pos * 2) + 1;
	}
	
	public int rightChild(int pos) {
		return (pos * 2)+2;
	}
	
	public boolean isLeaf(int pos) {
        return pos >= (size/2) && pos < size;
    }
	
	public void swap(int x, int y) {
		CharFrequency temp = heap[x];
		heap[x]=heap[y];
		heap[y]=temp;
	}
	
	public void minHeapify(int pos) {
	    if (!isLeaf(pos)) {
	        CharFrequency current = heap[pos];
	        CharFrequency left = heap[leftChild(pos)];
	        CharFrequency right = heap[rightChild(pos)];
	      
	        if ((left != null && current.compareTo(left) > 0) || 
	                (right != null && current.compareTo(right) > 0)) {
	                // Swap with the child having lesser value and call minHeapify
	                if (right == null || (left != null && left.compareTo(right) < 0)) {
	                    swap(pos, leftChild(pos));
	                    minHeapify(leftChild(pos));
	                } else {
	                    swap(pos, rightChild(pos));
	                    minHeapify(rightChild(pos));
	                }
	            }
//	        if ((left != null && current.frequency > left.frequency) || 
//	            (right != null && current.frequency > right.frequency)) {
//	            if (right == null || (left != null && left.frequency < right.frequency)) {
//	                swap(pos, leftChild(pos));
//	                minHeapify(leftChild(pos));
//	            } else {
//	                swap(pos, rightChild(pos));
//	                minHeapify(rightChild(pos));
//	            }
//	        }
	    }
	}

	
	public void enqueue(CharFrequency element) {
		if(size>=maxsize) {
			return;
		}
		element.order = order++;
		heap[size]=element;
		int current = size;
		
		while(heap[current].compareTo(heap[parent(current)])<0) {
			swap(current, parent(current));
			current = parent(current);
		}
		this.size++;
		
		//System.out.println("Enqueued: " + element.character + " " + element.frequency + " "+ element.order);
	}
	
	public CharFrequency dequeue() {
		if(size==0) {
			throw new NoSuchElementException("Empty");
		}
		CharFrequency remove = heap[0];
		heap[0]=heap[--size];
		minHeapify(0);
		
		//System.out.println("Dequeued: " + remove.character + " " + remove.frequency + " " + remove.order);
		return remove;
	}
	
	public CharFrequency getRoot(){
		return this.root;
		}
	
	public CharFrequency buildHuffTree() {
		while(this.size()>1) {
			CharFrequency node1 = this.dequeue();
			CharFrequency node2 = this.dequeue();
//			System.out.println("Combining nodes: (" + node1.character + " " + node1.frequency + " " + node1.order +
//                    ") and (" + node2.character + " " + node2.frequency + " " + node2.order + ")");
			CharFrequency combined = new CharFrequency(node1,node2, /*this.order++*/Math.min(node1.order, node2.order));
			this.enqueue(combined);
		}
		this.root=heap[0];
		return this.dequeue();
	}
}
	
