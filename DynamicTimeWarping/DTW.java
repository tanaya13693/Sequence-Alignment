package swdtwlcs;


public class DTW{
	
	public static int min(int a, int b)
	{
	    return (a < b)? a : b;
	}
	
	public static void main(String [] args){
		
		//Input
		String strA = "AGCAAAATT";
		String strB = "GCCAAAAGT";		
		int i=0, j=0;
		
		int lenA = strA.length();
		int lenB = strB.length();
		
		char [] stringA = strA.toCharArray();
		char [] stringB = strB.toCharArray();	
	
		System.out.println("Sequence 1 =");
		System.out.println(strA);
		System.out.println("Sequence 2 =");
		System.out.println(strB);
		
		int cost = 0, score = 0;
		int match=0, mismatch = 1;	

		//modified DTW Implementation
		int [][] ScoreMatrix = new int[lenA+1][lenB+1];
		
		for(i=0; i<=lenA; i++){
			ScoreMatrix[i][0] = Integer.MAX_VALUE;
		}
		
		for(i=0; i<=lenB; i++){
			ScoreMatrix[0][i] = Integer.MAX_VALUE;
		}
		
		ScoreMatrix[0][0] = 0;
			
		for(i=1; i<=lenA; i++)
			for(j=1; j<=lenB; j++){
					
				if (stringA[i-1] == stringB[j-1]){
					cost = match;
					ScoreMatrix[i][j] = cost + min((min (ScoreMatrix[i-1][j], ScoreMatrix[i][j-1])), 
					ScoreMatrix[i-1][j-1]);
		       }
							
				else {
					cost = mismatch;
					ScoreMatrix[i][j] = cost + min((min (ScoreMatrix[i-1][j], 
					ScoreMatrix[i][j-1])), ScoreMatrix[i-1][j-1]);
		       }							
		}//End of DTW Loop
							
		//2. Print modified DTW
		System.out.println("DTW Score Array is: ");
		for(i=0; i<=lenA; i++){
			for(j=0; j<=lenB; j++){
				System.out.print(ScoreMatrix[i][j]+" ");
			}
				System.out.println();
		}
		
		
	}
}