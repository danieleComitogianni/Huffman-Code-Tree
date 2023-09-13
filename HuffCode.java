package ca.concordia.algos.huffman;
import java.io.*;
import java.util.Scanner;

/*
 * Question 1: priority queue keeps track of the nodes of the huffman tree and prioritzes them
 * by frequency. A huffman tree processes the nodes with the lowest frequencies first and by doing this, the most frequent characters are placed near the root of the tree which results in shorter codes for the most
 * common characters. This makes the shortest possible configuration of code length which in the end provides the most optimal data compression.
 * 
 * Question 2: as stated above, the characters with the highest frequency get the shortest code and the lowest occurrence characters with the longest code. this ensures that its the shortest possible code length as the more common characters take up less space. 
 * By using shorter codes for the more frequent characters the total amount of space used to represent the data is reduced.
 * 
 * Question 3:Building a Huffman code is O(n log n), where n represents unique characters. This results from character iteration for frequency table creation (O(n)), and priority queue operations, each O(log n). 
 * The algorithm can be optimized with more efficient data structures, such as a Fibonacci heap or a pairing heap, reducing certain operations to O(1) complexity.
 * A faster frequency counting process could use an array instead of a hashmap if the character set is small and known. Further, creating a Huffman tree can be optimized using a queue of single-node trees.
 * 
 * */

public class HuffCode {
	public MinHeap queue;
	public String[] charToCode;
	public String fileContent;
	
	public HuffCode(String fileName) {
		this.charToCode=new String[128];
		this.queue= new MinHeap(128);
		this.fileContent=countFrequencies(fileName);
		CharFrequency huff = this.queue.buildHuffTree();
		buildCharToCodeMap(huff,"");
	}
	
	private void buildCharToCodeMap(CharFrequency node, String code) {
        if (node.isLeaf()) {
            charToCode[node.character] = code;
           // System.out.println("Character: " + node.character+ ", Huffman code: " + code);
        } else {
            buildCharToCodeMap(node.left, code + '0');
            buildCharToCodeMap(node.right, code + '1');
        }
    }

		
	public String encode(String text) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (charToCode[c] != null) {
                encodedText.append(charToCode[c]);
            } else {
                throw new IllegalArgumentException("The character '" + c + "' is not present in the Huffman tree");
            }
        }
        return encodedText.toString();
    }
	
	public String decode(String encodedText) {
	    StringBuilder decodedText = new StringBuilder();
	    CharFrequency currentNode = queue.root;
	    for (int i = 0; i < encodedText.length(); i++) {
	        char bit = encodedText.charAt(i);
	        if (bit == '0') {
	            currentNode = currentNode.left;
	        } else if (bit == '1') {
	            currentNode = currentNode.right;
	        } else {
	            throw new IllegalArgumentException("Invalid bit in encoded text: " + bit);
	        }

	        if (currentNode.isLeaf()) {
	            decodedText.append(currentNode.character);
	            currentNode = queue.root; // restart from the root for the next character
	        }
	    }
	    return decodedText.toString();
	}


	private String countFrequencies(String fileName) {
		int[] frequencies = new int[128];
		File file = new File(fileName);
		StringBuilder fileContent = new StringBuilder();
		
		try (FileReader fr = new FileReader(file)) {
			int c;
			while((c = fr.read())!= -1) {
				char character = (char) c;
				if (Character.isLetter(character)) {
					character = Character.toLowerCase(character);
				}
				frequencies[character]++;
				fileContent.append(character);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	
		for (char i = 0; i < frequencies.length; i++) {
	        if (frequencies[i] > 0) {
	            queue.enqueue(new CharFrequency(i, frequencies[i], queue.order++));
	        }
	    }
				return fileContent.toString();
	}
	public static void main(String[] args) {
	
		if(args.length<2) {
			System.out.println("invalid command");
			return;
		}
		
		String fileName = args[0];
		String command = args[1];
		//String customText = args[2];
		
		HuffCode huffCode = new HuffCode(fileName);
		
		Scanner scan = new Scanner(System.in);
		String customText = scan.nextLine();
		   if (command.equalsIgnoreCase("encode")) {
		       String encodedText = huffCode.encode(customText.toLowerCase());
		       System.out.println(encodedText);
		   } else if (command.equalsIgnoreCase("decode")) {
		       String decodedText = huffCode.decode(customText);
		       System.out.println(decodedText);
		   } else {
	        System.out.println("Invalid command");
	    }
		   
		  scan.close();
		
//		String fileName="C:\\Users\\danie\\Desktop\\haiku1.txt";
//		//String command = args[1];
//		
//		HuffCode huffCode = new HuffCode(fileName);
//		String customText ="Mary Jac";
//		String encodeText = huffCode.encode(customText.toLowerCase());
//		System.out.println(encodeText);
//		
//		String text = huffCode.decode(encodeText);
//		System.out.println(text);
//		if(command.equals("encode")) {
//			String fileContent =  huffCode.countFrequencies(fileName);
//			System.out.println(huffCode.encode(fileContent));
//		} else if(command.equals("decode")) {
//			//huffCode.decode();
//		}
			

		
		
		
		
		
	}

}


