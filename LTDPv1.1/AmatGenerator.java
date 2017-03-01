package ltdp;

import java. io.*;
import java. util.*;

public class AmatGenerator {

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
	
	public static void main(String[] args) {
		
		String strA = null;
		String strB = null;
		int lenA, lenB;
		int i=0, j=0, k=0;
		
		char [] stringA =null;
		char [] stringB=null;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter length of input string1: ");
		lenA = sc.nextInt();
		
		System.out.println("Enter string1: ");
		strA = sc.next();
		
		System.out.println("Enter length of input string2: ");
		lenB = sc.nextInt();
		
		System.out.println("Enter string2: ");
		strB = sc.next();
		
		stringA = strA.toCharArray();
		stringB = strB.toCharArray();
		
		int [][] SWArray = new int[lenA+1][lenB+1];
		int [][] ScoreMatrix = new int[lenA+1][lenB+1];
		
		int match=1, mismatch=0, gap1=0, gap2=0; //TO DO: Make user input
		
		//LCS. To DO: Score matrix and print both
		//1. Basic LCS:
				for(i=0; i<=lenA; i++)
					for(j=0; j<=lenA; j++){
						if (i == 0 || j == 0){
							SWArray[i][j] = 0;
							ScoreMatrix[i][j] = 0;
							}
					  
					       else if (stringA[i-1] == stringB[j-1]){
					    	   SWArray[i][j] = SWArray[i-1][j-1] + match;
					    	   ScoreMatrix[i][j] = match;
					       }
					       else{
					    	   SWArray[i][j] = max(SWArray[i-1][j], SWArray[i][j-1]);
					    	   ScoreMatrix[i][j] = mismatch;
					       }
				}//End of LCS Loop	
			
				
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
		
		if(numStages%2!=0){
			//TO DO
		}
		
		System.out.println("\n Subproblem array is:");
		for(i=0; i<numStages; i++){
			System.out.print(numSubProb[i]+" ");
		}
		System.out.println();
		//Print LCS
		System.out.println("SWArray is: ");
			for(i=0; i<=lenA; i++){
				for(j=0; j<=lenA; j++){
					System.out.print(SWArray[i][j]+" ");
			}
		System.out.println();
		}
		System.out.println();
		
		//Score matrix that gives match/mismatch
		System.out.println("Score Matrix is: ");
		for(i=0; i<=lenA; i++){
			for(j=0; j<=lenA; j++){
				System.out.print(ScoreMatrix[i][j]+" ");
			}
		System.out.println();
		}
				
				
		//A matrix generator
		MatGenerator mg = new MatGenerator();
		mg.SWGenerator(lenA,lenB,ScoreMatrix);
		
		
	}

}//End of class AmatGenerator
