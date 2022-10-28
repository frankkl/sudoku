package sudoku;

import java.util.*;
import java.io.*;


public class Sudoku
{
	private int feld[][] =new int[10][10];
	//private Integer[] dig = new Integer[] {1,2,3,4,5,6,7,8,9};
	//private HashSet<Integer> alldigits = new HashSet<Integer>(Arrays.asList(dig));
	private HashSet<Integer> alldigits = new HashSet<Integer>(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
	int geloeste=0;
	
	Sudoku() {
		for (int z=1;z<=9;z++) 
			for (int s=1;s<=9;s++)
				feld[z][s]=0;		
	}
	
	Sudoku(Sudoku sud) {
		for (int z=1;z<=9;z++) 
			for (int s=1;s<=9;s++)
				feld[z][s]=sud.getFeld(z,s);
	}
	
	Sudoku(String fn) {
		
		char[] c = new char[100];
		
		try {
			FileReader f = new FileReader(fn);
			f.read(c);
		}
		catch(IOException e) {
			System.out.println(e);
			System.exit(0);
		}
	
		String sus = new String(c);
		sus=sus.replace("\r","").replace("\n","").trim();
		if (sus.length()<81) {
				System.out.println("Falsche Länge: zu kurz" + sus.length());
				System.exit(0);
		} else {
			for (int z=1;z<=9;z++) {
				for (int s=1; s<=9; s++) {
					setFeld(z,s,Character.getNumericValue(sus.charAt((z-1)*9+s-1)));
				}
			}
		}
	}
	

	public void ausgabe() {
		for (int z=1;z<=9;z++) { 
			for (int s=1;s<=9;s++) {
				if (feld[z][s]==0)	
					System.out.print(". ");
				else
					System.out.print(feld[z][s]+" ");
			}
			System.out.println();
		}
	}
	
	public int getFeld(int z, int s) {
		return feld[z][s];
	}
	
	public void setFeld(int z,int s, int v) {
		feld[z][s]=v;
	}
	
	
	public HashSet<Integer> numbers_col(int s) {
		HashSet<Integer> numv = new HashSet<Integer> ();
		
		for(int z=1;z<=9;z++) {
			if (feld[z][s]>0) {
				numv.add(getFeld(z,s));
			}
		}
		return numv;
	}
	
	public HashSet<Integer> numbers_row(int z) {
		HashSet<Integer> numv = new HashSet<Integer> ();
		
		for(int s=1;s<=9;s++) {
			if (feld[z][s]>0) {
				numv.add(getFeld(z,s));
			}
		}
		return numv;
	}
	
	public HashSet<Integer> numbers_square(int zs, int ss) {
		HashSet<Integer> numv = new HashSet<Integer> ();
		
		int z_ecke, s_ecke;
		z_ecke= ((zs-1)/3)*3+1;
		s_ecke= ((ss-1)/3)*3+1;
		for(int z=z_ecke;z<=z_ecke+2;z++) {
			for (int s=s_ecke;s<=s_ecke+2;s++) {
				if (feld[z][s]>0) {
					numv.add(feld[z][s]);
				}
			}
		}
		return numv;
	}
	
	public boolean check_col(int s) {
		HashSet<Integer> numv = new HashSet<Integer> ();
		
		for(int z=1;z<=9;z++) {
			if (feld[z][s]>0) {
				if (!numv.contains(feld[z][s])) {
					numv.add(feld[z][s]);
				}
				else {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean check_row(int z) {
		HashSet<Integer> numv = new HashSet<Integer> ();
		for(int s=1;s<=9;s++) {
			if (feld[z][s]>0) {
				if (!numv.contains(feld[z][s])) {
					numv.add(feld[z][s]);
				}
				else {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean check_square(int zs, int ss) {
		HashSet<Integer> numv = new HashSet<Integer> ();
		int z_ecke, s_ecke;
		z_ecke= ((zs-1)/3)*3+1;
		s_ecke= ((ss-1)/3)*3+1;
		for(int z=z_ecke;z<=z_ecke+2;z++) {
			for (int s=s_ecke;s<=s_ecke+2;s++) {
				if (feld[z][s]>0) {
					if (!numv.contains(feld[z][s])) {
						numv.add(feld[z][s]);
					}
					else {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean is_complete() {
		
		for (int z=1; z<=9; z++) {
			for (int s=1; s<=9; s++) {
				if (getFeld(z,s)==0)
					return false;
				if (!check_col(s) || !check_row(z) || !check_square(z,s))
					return false;
			}
		}	
		return true;
	}
	
	public HashSet<Integer> valid_numbers(int z, int s) {
		HashSet<Integer> numv = new HashSet<Integer> ();
		HashSet<Integer> vn = new HashSet<Integer> (alldigits);
		numv = numbers_square(z,s);
		numv.addAll(numbers_row(z));
		numv.addAll(numbers_col(s));
		vn.removeAll(numv);
		return vn;
	}
	
	public boolean check_sudoku() {
		HashSet<Integer> vn = new HashSet<Integer> ();
		
		for (int z=1; z<=9; z++) {
			for (int s=1; s<=9; s++) {
				if (!(check_col(s) && check_row(z) && check_square(z,s)))
					return false;
				if (feld[z][s]==0 && valid_numbers(z,s).size()==0)
					return false;
			}
		}
		return true;
	}
	
	public void fill_obvious() {
		HashSet<Integer> vn = new HashSet<Integer> ();
		boolean hit;
		do {
			hit = false;
			for (int z=1; z<=9; z++) {
				for (int s=1; s<=9; s++) {
					if ((feld[z][s]==0)) {
						vn = valid_numbers(z,s);
						//System.out.println("Valid: " + vn);
						if (vn.size()==1) {
							feld[z][s]=vn.iterator().next();
							//System.out.println("Eingesetzt " + z + " " + s + " " + feld[z][s]);
							hit = true;
						} 
					}
				}
			}
		} while (hit);
	}

	private String ls(int l) {
		String s="";
		for (int i=0;i<l;i++)
			s=s+"   ";
		return s;
	}

	public boolean solve_sudoku(int level) {
		HashSet<Integer> vn = new HashSet<Integer> ();
		Sudoku s2;
		int z, s;
		boolean solved;
		
		//System.out.println(ls(level) + "Tiefe: " + level);
		
		fill_obvious();
		
		if (!check_sudoku()) {
			return false;
		}
		if (is_complete()) {
			ausgabe();
			System.out.println();
			geloeste++;
			return true;
		}
		
		z=1;
		s=1;
		//Mindestens eine leere Zelle muss an dieser Stelle eigentlich dabei sein...
		while (z<=9 && feld[z][s]>0) {
			s++;
			if (s==10) {
				s=1;
				z++;
			}
		}
		
		vn = valid_numbers(z,s);
		//System.out.println(ls(level) + "Valid " + z + " " + s + ": " + vn);
		solved=false;
		for (int n: vn) {
			s2=new Sudoku(this);
			s2.feld[z][s]=n;
			//System.out.println(ls(level) + "Eingesetzt " + z + " " + s + " " + n);
			//if (s2.solve_sudoku(level+1))
			//	return true;
			if (s2.solve_sudoku(level+1)) {
				solved=true;
				geloeste += s2.geloeste;
			}
		}
		// Wenn keine der eingesetzten Zahlen für dieses Feld erfolgreich war, ist keine Lösung möglich
		return solved;
	}
}
	