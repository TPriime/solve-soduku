package com.prime.example.soduku;

import java.util.ArrayList;

public class PSoduku {
	private PBlock[][] block ;
	private int[][] sLoc;
	private int size;
	private int cacheX, cacheY, cacheVal;
	private long count;
	private ArrayList<String> excludedList;
	
	public PSoduku (int size) {
		this.size = size;
		excludedList = new ArrayList<String>();
		block = new PBlock[size][size];
		sLoc = new int[size*size][size*size];
		for(int r=0; r<size; r++)
			for(int c=0; c<size; c++)
				block[r][c] = new PBlock(r*size, c*size, size, this);
	} 
	
	public void set(int r, int c, int... values)
		throws IllegalArgumentException
	{
		if(values.length != size*size) 
			throw new IllegalArgumentException("arg must be "+(size*size+2));
		block[r][c].set(values);
	}
	
	public void setLoc(int x, int y, int val) {
		sLoc[x][y] = val;
	}
		
	public void setExList(int x, int y) {
		excludedList.add(String.format("%d,%d", x, y));
	}
	
	public boolean inList(int x, int y) {
		return excludedList.contains(
			String.format("%d,%d", x, y));
	}
	
	public void displayValues() {
		int i = 1;
		for(int r=0; r<size; r++)
			for(int c=0; c<size; c++) {
				System.out.println(i++);
				block[r][c].displayValues();
				System.out.println();
			}	
	}
	
	public void displayValues2() {
		for(int x=0; x<size*size; x++) {
			for(int y=0; y<size*size; y++) {
				if(y%size==0) System.out.printf("   %02d   ", sLoc[x][y]);
				else System.out.printf("%02d   ", sLoc[x][y]);
			}
			if((x+1)%size==0) System.out.println();
			System.out.println();
		}
	}
	
	public void fillUp() throws IndexOutOfBoundsException {
		int[] xy = new int[2];
		for(int x=0; x<size*size; x++)
			for(int y=0; y<size*size; y++) {
				if(inList(x,y)) continue;
				xy[0] = x; xy[1] = y;
				xy = insertValue(xy);
				//adjust from feedback
				x = xy[0]; y=xy[1];
				count++;
			}
		System.out.printf("\tloops = %d\n", count);   
	}
	
	private int[] insertValue(int[] xy) throws IndexOutOfBoundsException {
		int x = xy[0], y = xy[1];
		int prev = getCache(x, y);
		//System.out.printf("cache = %dx, %dy, %dprev\n", x, y, prev); //d
		for(int i=prev; i<=size*size; i++)
			if(!canBeInserted(x, y, i)) {
				sLoc[x][y] = 0; markCorrespBlock(x, y, 0); //clean if occupied
				continue;
			} else {
				sLoc[x][y] = i; 
				markCorrespBlock(x, y, i); //update block
				//System.out.printf("%dx, %dy, tried %dval\n\n", x, y, i); //d
				return new int[] {x, y};
			}
		//if no value can be inserted
		int[] decr = getDecreament(xy);
		while(inList(decr[0], decr[1])) decr = getDecreament(decr);
		if(decr[0] < 0) throw new IndexOutOfBoundsException("no way");
		cacheVal = sLoc[decr[0]] [decr[1]]; //store prev value 
		cacheX = decr[0]; cacheY = decr[1]; //store prev(cached) position
		return getDecreament(decr);
	}
	
	private int getCache(int x, int y) {
		int val = 0;
		if(x==cacheX && y==cacheY) {
			val = cacheVal; cacheVal = -1;
			return val;
		} return val;
	}
	
	private boolean canBeInserted(int x, int y, int val) {
		return freeBlock(x, y, val) && freeCross(x, y, val);
	}
	
	private boolean freeBlock(int x, int y, int val) {
		if(x==0) x =1; if(y==0) y = 1;
		return !block[x/size][y/size].contains(val);
	}
	
	private boolean freeCross(int x, int y, int val) {
		for(int ys=0; ys<size*size; ys++)
			if(sLoc[x][ys] == val) return false;
		for(int xs=0; xs<size*size; xs++)
			if(sLoc[xs][y] == val) return false;
		return true;
	}
	
	private void markCorrespBlock(int x, int y, int value) 
		throws IndexOutOfBoundsException
	{
		for(int r=0; r<size; r++)
			for(int c=0; c<size; c++)
				if(block[r][c].contains(x, y))
					block[r][c].markLoc(x, y, value, false);
	}
	
	private int[] getDecreament(int[] xy) {
		if(xy[0]<=0 && xy[1] <=0) return new int[] {-1, -1};
		if(xy[1] == 0) return new int[] {xy[0]-1, (size*size)-1};
		return new int[] {xy[0], xy[1] - 1};
	}
}
