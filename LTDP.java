package ltdp;

public class LTDP {
	
	public static int ans[][];
	public static int prevAns[][];
	
	public static int min(int a, int b)
	{
	    return (a < b)? a : b;
	}
	public static int max(int a, int b)
	{
	    return (a > b)? a : b;
	}
		
	public static void multiply(int [][] A1){//, int [][] A2){
		int m;
		int n;
		int p;
		
		int maxVal=Integer.MIN_VALUE;
		//Test
/*			int row = A1.length;
		int column = A1[0].length;
		System.out.println("A is:");
		for(int i = 0; i < row;i++){
			for(int j = 0;j < column;j++){
				System.out.print(A1[i][j]+" ");
			}
			System.out.println();
		}*/
		//Test.
		
		if(prevAns ==null){
			m = A1.length;
			n = A1[0].length;
			//System.out.println("here in if");
		    prevAns = new int[m][n];
			for(int i = 0;i < m;i++)
				for(int j = 0;j < n;j++){
					//ans[i][j] = A1[i][j];
					prevAns[i][j] = A1[i][j];
				}
			p = prevAns[0].length;
/*			for(int i = 0;i < m;i++){
				for(int j = 0;j < p;j++){
					System.out.print(" " +prevAns[i][j]+" ");
					}
			}*/
		}
		
		else{
		//System.out.println("here in else");						
		m = prevAns.length;
		n = prevAns[0].length;
		p = A1[0].length;//ans[0].length;
		int q = A1.length;
		
		ans = new int[m][p]; 
		//prevAns = new int[m][p];
		//System.out.println("Prev dim="+m+" "+n+" A dim="+""+q+""+p);
		for(int i = 0;i < m;i++){
			for(int j = 0;j < p;j++){
				ans[i][j]=0;
				maxVal = Integer.MIN_VALUE;
				for(int k = 0;k < n;k++){
						//System.out.println("Max of "+maxVal+" and "+(prevAns[i][k] + A1[k][j])+" is = ");
						//if((prevAns[i][k] == Integer.MIN_VALUE && A1[k][j] < 0) || (prevAns[i][k] < 0 && A1[k][j] == Integer.MIN_VALUE)
						//		|| (prevAns[i][k] == Integer.MIN_VALUE && A1[k][j] == Integer.MIN_VALUE)){
						//	maxVal = maxVal;
						//}
						//else{
						//maxVal = max(maxVal,prevAns[i][k] + A1[k][j]);
						//}
						//System.out.println(maxVal);
						//System.out.println(prevAns[i][k]);
					
						if(prevAns[i][k] == Integer.MIN_VALUE || A1[k][j] == Integer.MIN_VALUE){
							maxVal = maxVal;
						}
							
						else {
							maxVal = max(maxVal,prevAns[i][k] + A1[k][j]);
						}
		         }
				//if(maxVal == Integer.MIN_VALUE){
					//ans[i][j] = 0;	
				//}
				//else{
					ans[i][j] = maxVal;	
				//}
		      }
		}
		
		prevAns = new int[m][p];
		for(int i = 0; i < m;i++){
			for(int j = 0;j < p;j++){
				prevAns[i][j] = ans[i][j];
				System.out.print(ans[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		}//End of else
		
			
		//print the multiplication:
		/*	for(int i = 0;i < m;i++){
			for(int j = 0;j < p;j++){
				System.out.print(" " +ans[i][j]+" ");
				}
		} */
	}//End of multiply function

	
	public static void main(String[] args) {

			
		//Two character arrays:
		char [] strA = {'A','A','A','A'};
		char [] strB = {'B','B','B','B'};
		
		int lenA = strA.length;
		int lenB = strB.length;
		
		int i=0, j=0, match=1, mismatch=-2;
		int gap1=-1, gap2=-1;
		int [][] SWArray = new int[lenA+1][lenB+1];
		int [][] ScoreMatrix = new int[lenA+1][lenB+1];
		
		//1. Basic LCS:
		for(i=0; i<=lenA; i++)
			for(j=0; j<=lenA; j++){
				if (i == 0 || j == 0){
					SWArray[i][j] = 0;
					ScoreMatrix[i][j] = 0;
					}
			  
			       else if (strA[i-1] == strB[j-1]){
			    	   SWArray[i][j] = SWArray[i-1][j-1] + match;
			    	   ScoreMatrix[i][j] = match;
			       }
			       else{
			    	   SWArray[i][j] = max(SWArray[i-1][j], SWArray[i][j-1]);
			    	   ScoreMatrix[i][j] = mismatch;
			       }
		}//End of LCS Loop
		
		//2. Print LCS
/*		System.out.println("SWArray is: ");
		for(i=0; i<=lenA; i++){
			for(j=0; j<=lenA; j++){
				System.out.print(SWArray[i][j]+" ");
		}
		System.out.println();
		}
*/		
		//Score matrix that gives match/mismatch
		System.out.println("Score Matrix is: ");
		for(i=0; i<=lenA; i++){
			for(j=0; j<=lenA; j++){
				System.out.print(ScoreMatrix[i][j]+" ");
		}
		System.out.println();
		}
				
		//Calculate: number of stages, array of number of problems 
		int numStages = lenA + lenB + 1;
		int [] numSubProb = new int[numStages]; //Eg: 0 to 8
		
		int count=1;
		if(numStages%2!=0){
			for(i=0; i<=numStages/2; i++){
				numSubProb[i] = i+count;
				count++;
			}
			count = numSubProb[numStages/2]-2;
			for(i=numStages/2+1; i<numStages; i++){
				numSubProb[i] = count;
				count = count-2;
			}	
		}
				
		System.out.println("\n Subproblem array is:");
		for(i=0; i<numStages; i++){
			System.out.print(numSubProb[i]+" ");
		}
		
		////////////////A-matrix generator:
		int maxJ, maxK=1,k;
		int [][] A;
		int [][] A0=null;
		int [][] A1=null;
		int [][] A2=null;
		int [][] A3=null;
		int [][] A4=null;
		int [][] A5=null;
		int [][] A6=null;
		int [][] A7=null;
		int [][] A8=null;
		int counter=0;
		System.out.println();
		for(i=0; i<numStages; i++){
			maxJ= numSubProb[i];//min((2*i)+1, 4*(lenA)-(2*i)-3);
			A = new int [maxJ][maxK];
			//System.out.println("maxJ"+maxJ);
			//System.out.println("maxK"+maxK);
			for(j=0; j<maxJ; j++){
				for(k=0; k<maxK; k++){
					if(i<=numStages/2){
						if((maxJ==1 && maxK==1)||(maxJ==3 && maxK==1)){
							A[j][k]=0;
							//System.out.println(A[j][k]);
						}
						
						else if(j==0 && k==0){
							A[j][k]=gap2;
						}
						
						else if(j%2 == 1 && k==j-1){
							A[j][j-1]=0;
						}
						
						else if(j%2==0 && j>0){
							if(k==j-2){
								A[j][j-2]=gap1;
							}
							else if(k==j-1){
								A[j][j-1]= ScoreMatrix[max(0,i-lenA)+(j/2)][min(i,lenA)-((j+1)/2)];
							}
							else if(k==j){
								A[j][j]=gap2;
							}
							else{
								A[j][k]=Integer.MIN_VALUE;
							}
						}
						else{
							A[j][k]=Integer.MIN_VALUE;
						}
					}					
					else{ //if(i>numStages/2){
						//System.out.println("None");
						if(j%2 == 1){
							if(k==j+1){
								A[j][j+1]=0;
							}
							else{
								A[j][k]=Integer.MIN_VALUE;
							}
						}
						
						else if(j%2 == 0){
							if(k==j+2){
								A[j][k]=gap2;
							}
							else if(k==j+1){
								//System.out.println("Here");
								A[j][k]= ScoreMatrix[max(0,i-lenA)+(j/2)][min(i,lenA)-((j+1)/2)];
							}
							else if(k==j){
								A[j][k]=gap1;
							}
							else{
								A[j][k]=Integer.MIN_VALUE;
							}							
							//System.out.println(A[j][k]);
						}
					}
				}//end k				
			}//end j
			
			System.out.println();
			
			//Print A matrix:
			System.out.println("A"+counter+" "+ "value is: ");
			for(int p=0; p<maxJ; p++){
				for(int q=0; q<maxK; q++){
					System.out.print(A[p][q]+" ");
			}
			System.out.println();
			}
			
			//System.out.println("Multiplication till "+" A"+counter+"=");
			//multiply(A);
			
			if(counter==0){
				A0=A;
			}
			else if(counter==1){
				A1=A;
			}
			else if(counter==2){
				A2=A;
			}
			else if(counter==3){
				A3=A;
			}
			else if(counter==4){
				A4=A;
			}
			else if(counter==5){
				A5=A;
			}
			else if(counter==6){
				A6=A;
			}
			else if(counter==7){
				A7=A;
			}
			else if(counter==8){
				A8=A;
			}
			counter++;
			maxK=maxJ;
	
		}// end i
					
		////////////////
		System.out.println("\n");
		System.out.println("Multiplication of Matrices is:");
		//ans = null;
		//System.out.println("A"+counter+" "+ "value is: ");
/*		for(int p=0; p<3; p++){
			for(int q=0; q<5; q++){
				System.out.print(A7[p][q]+" ");
		}
		System.out.println();
		}
*/		
		
		//multiply(A7);
		//multiply(A6);
		multiply(A5);
		multiply(A4);
		multiply(A3);
		multiply(A2);
		
	}

}
