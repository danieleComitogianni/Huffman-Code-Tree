package ca.concordia.algos.huffman;

public class CharFrequency implements Comparable<CharFrequency>{
	
	public char character;
	public int frequency;
	public int order;
	public CharFrequency left;
	public CharFrequency right;
	
	
	
	public CharFrequency(char character, int frequency, int order) {
		this.character=character;
		this.frequency=frequency;
		this.order = order;
		this.left=null;
		this.right=null;
		
		//System.out.println("Created CharFrequency " + character + " " + frequency + " " + order);
	}
	
	
	public CharFrequency(CharFrequency left, CharFrequency right, int order) {
		this.character='\0';
		this.frequency=left.frequency+right.frequency;
		this.order = order;
		this.left=left;
		this.right=right;
		
		//System.out.println("Created combined CharFrequency: " + frequency + " " + order);
	}
	
	public CharFrequency getLeft() {
		return this.left;
	}
	
	public CharFrequency getRight() {
		return this.right;
	}
	
	public boolean isLeaf() {
		return (this.getLeft()==null&&this.getRight()==null);
	}
	
	@Override
	//to make it a stable algorithm as it is ordered based on original order
	public int compareTo(CharFrequency o) {
		int frequencyComparison = Integer.compare(this.frequency, o.frequency);
		if(frequencyComparison!=0) {
			return frequencyComparison;
		} else {
			return Integer.compare(this.order, o.order);
		}
	}

}
