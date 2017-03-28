package newDTW;

public class Main {

	public static void main(String[] args) {
				//Input
				String strA = "AGCAAAATTGCG";//"AGCAAAATT";
				String strB = "GCCAAAAGTGCG";//"GCCAAAAGT";		
				int i=0, j=0, k=0;
				
				int lenA = strA.length();
				int lenB = strB.length();
				
				char [] stringA = strA.toCharArray();
				char [] stringB = strB.toCharArray();	
			
				System.out.println("Sequence 1 =");
				System.out.println(strA);
				System.out.println("Sequence 2 =");
				System.out.println(strB);
				
				DTWv1 dt = new DTWv1();
				dt.DTW(stringA, stringB);
				
				NewDTWv2 nd = new NewDTWv2();
				nd.newdtw(stringA, stringB);
	}
}
