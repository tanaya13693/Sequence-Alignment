package ltdp;

import java.util.*;
public class MatGenerator {

	AmatGenerator ag = new AmatGenerator();
	public void SWGenerator(int lenA, int lenB, int [][] scoreMatrix) {
		
		int match=1, mismatch=0, g_v=0, g_h=0;
		assert(lenA>0 && lenB>0 && lenA == lenB);
		assert(scoreMatrix.length == lenA+1);
		assert(scoreMatrix[0].length == lenB+1);
		
		final int N = lenA;
		final int numStages = lenA + lenB + 1;
		List <int [][]> amats = new ArrayList<int [][]>(numStages);	
		
		int [][] A = new int [1][1];
		amats.add(A);
		
		
		//Generate A matrices and store in the array list:
		int columnDim, cardAi;
		for(int i=1; i<amats.size(); i++) {
			cardAi = ag.min(2*i+1, 4*N-2*i-3); //rows
			columnDim = A.length; //columns
			A = new int[cardAi][columnDim];
			amats.add(A);
		}
	
		
		//Initilize A matrices by -INF:
		for(int i=0; i<amats.size(); i++) {
			for(int j=0; j< amats.get(i).length; j++) {
				for(int k=0; k<amats.get(i)[0].length; k++) {
					amats.get(i)[j][k] = Integer.MIN_VALUE;
				}
			}
		}
		
		for(int i=0; i<amats.size(); i++) {
			amats.get(i)[0][0] =g_h;
			for(int j=1; j<amats.get(i).length; j+=2) {
				amats.get(i)[j][j-1] = 0;
			}
			
			for(int j=2; j<amats.get(i).length; j+=2) {
				amats.get(i)[j][j-2] = g_v;
				amats.get(i)[j][j-1] = scoreMatrix(row(i,j), col(i,j));
				amats.get(j)[j][j] = g_h;
			}
		}
	}
	
	

/*	public void MatGeneratorFun(int numStages, int [] numSubProb,int [][] ScoreMatrix, int lenA, int lenB ){
		
		AmatGenerator ag = new AmatGenerator();
		int match=1, mismatch=0, gap1=0, gap2=0;
		int i,j,k;
		int maxJ, maxK=1;
		int [][] A;
		int counter=0;
		//Set <int [][]> Amats = new HashSet<int [][]>();
		
		List <int [][]> Amats = new ArrayList<int [][]>();
		
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
								A[j][j-1]= ScoreMatrix[ag.max(0,i-lenA)+(j/2)][ag.min(i,lenA)-((j+1)/2)];
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
								A[j][k]= ScoreMatrix[ag.max(0,i-lenA)+(j/2)][ag.min(i,lenA)-((j+1)/2)];
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
			Amats.add(A);
			
			counter++;
			maxK=maxJ;
		}// end i
		
	 TEST ONLY!
		A = new int[5][3];
		A = Amats.get(2);
		
		for(int p=0; p<5; p++){
			for(int q=0; q<3; q++){
				System.out.print(A[p][q]+" ");
		}
		System.out.println();
		}
		
	}//End of function
*/
	
}
