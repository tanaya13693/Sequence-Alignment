package newDTW;

//---------------------------------------------------------------------------------//
//Code: Implementation of LCS, Smith waterman and Dynamic Time Warping Score matrix
//Status: Code working But integer overflow in DTW. (Solution: can keep initial 
//value 100 or so for testing)
//--------------------------------------------------------------------------------//
public class DTW {
	
	public static int min(int a, int b)
	{
	    return (a < b)? a : b;
	}
	
	public static int max(int a, int b)
	{
	    return (a > b)? a : b;
	}
	
	public static int DTW(char [] stringA, char [] stringB){
		
		int cost = 0, score = 0;
		int match=0, mismatch = 1;	
		
		int lenA = stringA.length;
		int lenB = stringB.length;
		
		int i=0, j=0;
		
		if(lenA == 0){
			lenA = 1;
			stringA = new char[lenA];
			stringA[0] = '0';
		}
		
		if(lenB == 0){
			lenB = 1;
			stringB = new char[lenB];
			stringB[0] = '0';
		}

		//modified DTW Implementation
		int [][] ScoreMatrix = new int[lenA][lenB];
		//System.out.println("DTW Implementation modified: ");
		cost = 0;int val=0;
		for(i=0; i<lenB; i++){
			if(stringA[0] == stringB[i]){
				cost = match;
			}
			else{
				cost = mismatch;
			}
			
			if(i-1 < 0 ){
				val = 0;
			}
			else{
				val = ScoreMatrix[0][i-1];
			}
			ScoreMatrix[0][i] = cost + val;
		}
		
		cost = 0; val =0;
		for(i=0; i<lenA; i++){
			if(stringA[i] == stringB[0]){
				cost = match;
			}
			else{
				cost = mismatch;
			}
			
			if(i-1 < 0 ){
				val = 0;
			}
			else{
				val = ScoreMatrix[i-1][0];
			}
			
			ScoreMatrix[i][0] = cost + val;
		}
			
		for(i=1; i<lenA; i++)
			for(j=1; j<lenB; j++){
					
				if (stringA[i] == stringB[j]){
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
		for(i=0; i<lenA; i++){
			for(j=0; j<lenB; j++){
				System.out.print(ScoreMatrix[i][j]+" ");
			}
				System.out.println();
		}
			
		return ScoreMatrix[lenA-1][lenB-1];
		
	}

}

