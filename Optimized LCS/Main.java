package newLCS;

//------------------------------------------------------------------//
//Code: LCS (Naive and new equations implementation)
//Status: Working
//Files: Main.java, NaiveLCS.java , NewLCS.java
//Input: put kmer 3, if you are not changing the stringA and stringB
//-----------------------------------------------------------------//	
	
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		//Input
		String strA = null;
		String strB = null;
		int lenA, lenB;
		int i=0, j=0, k=0;
		
		char [] stringA = null;
		char [] stringB = null;
		Scanner sc = new Scanner(System.in);
		
		/*System.out.println("Enter length of input string1: ");
		lenA = sc.nextInt();
		
		System.out.println("Enter string1: ");
		strA = sc.next();
			
		System.out.println("Enter length of input string2: ");
		lenB = sc.nextInt();
		
		System.out.println("Enter string2: ");
		strB = sc.next();*/
		
		lenA=9; lenB=9;
		strA = "AGCAAAATT";
		strB = "GCCAAAAGT";
				
		stringA = strA.toCharArray();
		stringB = strB.toCharArray();
				
		NaiveLCS nv = new NaiveLCS();
		nv.naivelcs(stringA, stringB);
				
		NewLCSv1 nw = new NewLCSv1();
		nw.newlcs(stringA, stringB);				
	}
}
