package com.prime.example.soduku;

import java.util.HashMap;

public class PBlock {

	private PSoduku mSoduku;
	private int startX, startY, size;
	//private int[][] loc;
	private HashMap<String, Integer> block;
	
	public PBlock(PSoduku soduku) { 
		new PBlock(0, 0, 3, soduku);
	}
	
	public PBlock(int size, PSoduku soduku) {
		new PBlock(0, 0, size, soduku);
	}
	
	public PBlock(int startX, int startY, int size, PSoduku soduku) {
		mSoduku = soduku;
		this.startX = startX; this.startY = startY;
		this.size = size;
		block = new HashMap<String, Integer>();
		for(int x=0; x<size; x++) 
			for(int y=0; y<size; y++) {
				block.put(makeKey(startX+x, startY+y), -1);
			}
	}
	
	public void set(int[] vals) {
		int i = 0;
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				mSoduku.setLoc(startX+x, startY+y, vals[i]);
				markLoc(startX+x, startY+y, vals[i], true);
				if(vals[i]>0) mSoduku.setExList(startX+x, startY+y);
				i++;
			}
	}

	public boolean markLoc(int x, int y, int value, boolean initializing) 
		throws IndexOutOfBoundsException
	{
		if(initializing)
		if(!(startX<=x && x<startX+size) || !(startY<=y && y<startY+size))
			throw new IndexOutOfBoundsException();
		boolean alreadyHasValue = contains(value);
		block.remove(makeKey(x, y));
		block.put(makeKey(x,y), value);
		return alreadyHasValue;
	}
	
	private String makeKey(int x, int y) {
		return String.format("(%d,%d)", x, y);
	}
	
	public boolean contains(int value) {
		return block.containsValue(value);
	}
	
	public boolean contains(int x, int y) {
		return block.containsKey(
			makeKey(x, y));
	}
	
	public void displayValues() {
		System.out.printf("(%d, %d)\n", startX, startY);
		for(int x=startX; x<startX+size; x++) {
			for(int y=startY; y<startY+size; y++) 
				System.out.print(block.get(makeKey(x, y)) + "  ");
			System.out.println();
		}
	}
}
