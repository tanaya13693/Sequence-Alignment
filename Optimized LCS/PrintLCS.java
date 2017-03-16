package newLCS;

public class PrintLCS {
	
	public static void printLCS(int[][] SWArray){
		//Print LCS
		System.out.println("Naive LCS is: ");
		for(int i=0; i<SWArray.length; i++){
			for(int j=0; j<SWArray[0].length; j++){
					System.out.print(SWArray[i][j]+" ");
			}
			System.out.println();
		}
				
	}

}
