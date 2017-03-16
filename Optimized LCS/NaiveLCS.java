package newLCS;

public class NaiveLCS {
	
	public static int min(int a, int b)
	{
	    return (a < b)? a : b;
	}
	
	public static int max(int a, int b)
	{
	    return (a > b)? a : b;
	}
	
	public int naivelcs(char [] StringA, char [] StringB){
		
		int lenA = StringA.length;
		int lenB = StringB.length;
		int i,j;
		int match = 1;
		
		int [][] SWArray = new int[lenA][lenB];
				
		//Simple LCS
		for(i=0; i<lenA; i++)
			for(j=0; j<lenB; j++){
				
				int p = Integer.MIN_VALUE,q = Integer.MIN_VALUE,r = Integer.MIN_VALUE;
				
				if(i-1<0 && j-1<0){
					p=0;
					q=0;
					r=0; 
				}
				
				else if( (i-1)<0 ){
					p=0;
					q=0;
					r = SWArray[i][j-1];
				}				
				
				else if( (j-1)< 0){
					p=0;
					q = SWArray[i-1][j];
					r=0;
				}
				
				else {					
					p = SWArray[i-1][j-1];
					q = SWArray[i-1][j];
					r = SWArray[i][j-1];	
				}			
				
	
				if (StringA[i] == StringB[j]){
					 SWArray[i][j] = p + match;
			      }
				else{
			   	   SWArray[i][j] = max(q, r);
			      }
				
			}//End of LCS Loop
		
		return SWArray[lenA-1][lenB-1];
	}
}
