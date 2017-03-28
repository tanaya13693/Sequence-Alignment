package newDTW;

import java.util.Scanner;


//A1ch = big
//A2ch = small
//B1ch = small
//B2ch = big
//Working code
//+INF considered
//

public class NewDTWv2 {

	public static int min(int a, int b)
	{
	    return (a < b)? a : b;
	}
	
	public static int[][] scoreMatrix;
	public static int[] Il, It, Ib, Ir;
	public static int kmer, Ic;
	public static char [] kmerA;
	public static char [] kmerB;
	public static char [] kmerAch;
	public static char [] kmerBch;
	
	//1. FLB: Influence of left edge elements on bottom edge elements
		public int FLB(int x, int y){			

			char [] A1ch = new char[kmer-x];
			char [] A2ch = new char[kmer-x-1];
			char [] B1ch = new char[y+1];//kmer-y];
			char [] B2ch = new char[y+2];//kmer-y+1];
			
		/*	System.out.println("All lengths="+ A1ch.length + " " +A2ch.length + " " + 
					B1ch.length + " " + B2ch.length);
		*/	
			int c=0;
			for(c=x; c<kmer; c++){ 
				A1ch[c-x] = kmerA[c];
			}			
			
			for(c=x+1; c<kmer; c++){
				A2ch[c-x-1] = kmerA[c];
			}
			
			for(c=0; c<=y; c++){
				B1ch[c] = kmerB[c];
			}
			
			for(c=0; c<=y; c++){
				B2ch[c+1] = kmerB[c];
			}
			
			DTWv1 nv = new DTWv1();
			
			if(A2ch.length == 0){   //--->Imp case!
				if(Il[x] + nv.DTW(A1ch, B1ch) == Integer.MIN_VALUE){
					return Integer.MAX_VALUE;
				}
				else
					return Math.abs(Il[x] + nv.DTW(A1ch, B1ch));
			}
			
			else{
				if(Il[x] + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )) == Integer.MIN_VALUE){
					return Integer.MAX_VALUE;
				}
				else					
				return Math.abs(Il[x] + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )));
			}
						
		}
		
		//2. FTB: Influence of top edge elements on bottom edge elements
		public int FTB(int x, int y){
			
			char [] A1ch = new char[kmer+1];
			char [] A2ch = new char[kmer];
			char [] B1ch = new char[y-x];
			char [] B2ch = new char[y-x+1];
			
			/*System.out.println("All lengths="+ A1ch.length + " " +A2ch.length + " " + 
					B1ch.length + " " + B2ch.length);*/
			
			int c=0;
			for(c=0; c<kmer; c++){
				A1ch[c+1] = kmerA[c];
			}
			
			for(c=0; c<kmer; c++){
				A2ch[c] = kmerA[c];
			}
			
			for(c=x+1; c<=y; c++){
				B1ch[c-x-1] = kmerB[c];
			}
			
			for(c=x; c<=y; c++){
				B2ch[c-x] = kmerB[c];
			}						
			
			DTWv1 nv = new DTWv1();
			
			if(B1ch.length == 0){
				if(It[x] + nv.DTW(A2ch, B2ch) == Integer.MIN_VALUE){
					return Integer.MAX_VALUE;
				}
				else
					return Math.abs(It[x] + nv.DTW(A2ch, B2ch));
			}
			
			else{
				if(It[x] + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )) == Integer.MIN_VALUE){
					return Integer.MAX_VALUE;
				}
				else					
				return Math.abs(It[x] + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )));
			}			
		}
		
		//3. FLR: Influence of left edge elements on right edge elements
		public int FLR(int x, int y){
			
			char [] A1ch = new char[y-x+1];
			char [] A2ch = new char[y-x];
			char [] B1ch = new char[kmer];
			char [] B2ch = new char[kmer+1];
			
			int c=0;
			for(c=0; c<kmer; c++){
				B2ch[c+1] = kmerB[c];
			}
			
			for(c=0; c<kmer; c++){
				B1ch[c] = kmerB[c];
			}
			
			for(c=x+1; c<=y; c++){
				A2ch[c-x-1] = kmerA[c];
			}
			
			for(c=x; c<=y; c++){
				A1ch[c-x] = kmerA[c];
			}			
			
			DTWv1 nv = new DTWv1();
			
			if(A2ch.length == 0){
				if(Il[x] + nv.DTW(A1ch, B1ch) == Integer.MIN_VALUE){
					return Integer.MAX_VALUE;
				}
				else
					return Math.abs(Il[x] + nv.DTW(A1ch, B1ch));
			}
			
			else{
				if(Il[x] + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )) == Integer.MIN_VALUE){
					return Integer.MAX_VALUE;
				}
				else					
					return Math.abs(Il[x] + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )));
			}
		}
		
		//4. FTR: Influence of top edge elements on right edge elements
		public int FTR(int x, int y){

			char [] A1ch = new char[y+2];
			char [] A2ch = new char[y+1];			
			char [] B1ch = new char[kmer-x-1];
			char [] B2ch = new char[kmer-x];
			
			int c=0;
			for(c=0; c<=y; c++){
				A1ch[c+1] = kmerA[c];
			}
			
			for(c=0; c<=y; c++){
				A2ch[c] = kmerA[c];
			}		
			
			for(c=x+1; c<kmer; c++){
				B1ch[c-x-1] = kmerB[c];
			}
			
			for(c=x; c<kmer; c++){ 
				B2ch[c-x] = kmerB[c];
			}
			
			DTWv1 nv = new DTWv1();
			
			if(B1ch.length == 0){
				if(It[x] + nv.DTW(A2ch, B2ch) == Integer.MIN_VALUE){
					return Integer.MAX_VALUE;
				}
				else
					return Math.abs(It[x] + nv.DTW(A2ch, B2ch));
			}
			
			else{
				if(It[x] + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )) == Integer.MIN_VALUE){
					return Integer.MAX_VALUE;
				}
				else
					
				return Math.abs(It[x] + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )));
			}
		}	
		
		//5. FCB: Influence of corner elements on bottom edge elements(i.e. diagonal elements)
				public int FCB(int x){
					
					DTWv1 nv = new DTWv1();
					char [] A1ch = new char[kmer+1];
					char [] A2ch = new char[kmer];
					char [] B1ch = new char[x+1];
					char [] B2ch = new char[x+2];
					//kmerBch = new char[x+1];
					int c;
				
					for(c=0; c<kmer; c++){
						A1ch[c+1] = kmerA[c];
					}
					
					for(c=0; c<kmer; c++){
						A2ch[c] = kmerA[c];
					}
					
					for(c=0; c<=x; c++){
						B1ch[c] = kmerB[c];
					}
					
					for(c=0; c<=x; c++){
						B2ch[c+1] = kmerB[c];
					}		
					
					
					if(B1ch.length == 0){
						if(Ic + nv.DTW(A1ch, B2ch) == Integer.MIN_VALUE){
							return Integer.MAX_VALUE;
						}
						else
							return Math.abs(Ic + nv.DTW(A1ch, B2ch));
					}
					
					else{
						if(Ic + min(nv.DTW(A1ch,B2ch) , min(nv.DTW(A1ch, B1ch) , nv.DTW(A2ch, B1ch) )) == Integer.MIN_VALUE){
							return Integer.MAX_VALUE;
						}
						else
							
						return Math.abs(Ic + min(nv.DTW(A1ch,B1ch) , min(nv.DTW(A2ch, B1ch) , nv.DTW(A2ch, B2ch) )));
					}
				}
				
				//6. FCR: Influence of corner elements on right edge elements(i.e. diagonal elements)
				public int FCR(int x){
					
					DTWv1 nv = new DTWv1();
					char [] A1ch = new char[x+2];
					char [] A2ch = new char[x+1];
					char [] B1ch = new char[kmer];
					char [] B2ch = new char[kmer+1];
					int c;
				
					for(c=0; c<kmer; c++){
						B2ch[c+1] = kmerB[c];
					}
					
					for(c=0; c<kmer; c++){
						B1ch[c] = kmerB[c];
					}
					
					for(c=0; c<=x; c++){
						A2ch[c] = kmerA[c];
					}
					
					for(c=0; c<=x; c++){
						A1ch[c+1] = kmerA[c];
					}		
					
					
					if(A2ch.length == 0){
						if(Ic + nv.DTW(A1ch, B2ch) == Integer.MIN_VALUE){
							return Integer.MAX_VALUE;
						}
						else
							return Math.abs(Ic + nv.DTW(A1ch, B2ch));
					}
					
					else{
						if(Ic + min(nv.DTW(A1ch,B2ch) , min(nv.DTW(A2ch, B2ch) , nv.DTW(A2ch, B1ch) )) == Integer.MIN_VALUE){
							return Integer.MAX_VALUE;
						}
						else
							
						return Math.abs(Ic + min(nv.DTW(A1ch,B2ch) , min(nv.DTW(A2ch, B2ch) , nv.DTW(A2ch, B1ch))));
					}
				}
		
	public void newdtw(char [] StringA, char [] StringB){
		
		//*********** Variables **********//
		int lenA = StringA.length;
		int lenB = StringB.length;
		int i,j,p,q;		
				
		System.out.println("Enter kmer size: "); //Assume square kmer only!
		Scanner sc = new Scanner(System.in);
				
		kmer = sc.nextInt(); 
		
		int [][] scoreMatrix = new int[lenA][lenB];
		Il = new int[kmer]; //left vector
		It = new int[kmer]; //top vector
		Ib = new int[kmer]; //bottom vector
		Ir = new int[kmer]; //right vector
				
		DTWv1 nv = new DTWv1();
		kmerA=new char[kmer];
		kmerB=new char[kmer];
		int k;
				
		//********************************//
		
		/*System.out.println("Il");
		for(i=0; i<Il.length; i++){
		System.out.println(Il[i]);
		} //TESTONLY*/
					
		//Initialize scoreMatrix by +INF
		for(i=0; i<lenA; i++){
			for(j=0; j<lenB; j++){
				scoreMatrix[i][j] = Integer.MAX_VALUE; 
			}
		}	

		for(i=0; i<lenA; i+=kmer){
			for(j=0; j<lenB; j+=kmer){
					
					//1. Get kmers:
					for(p=i; p<i+kmer; p++){
						kmerA[p-i] = StringA[p];						
					}
					for(q=j; q<j+kmer; q++){						
						kmerB[q-j] = StringB[q];
					}
						
					//TESTONLY! (Print kmers):
					/*System.out.println("Print kmers:");
					for(p=0; p<kmer; p++){
					System.out.print(kmerA[p]);
						}
					for(p=0; p<kmer; p++){
					System.out.print(kmerB[p]);
					}
					*/				
									
					//3.1 Get It
					if(i>0){
						for(k=j; k<j+kmer; k++)
							It[k-j] = scoreMatrix[i-1][k];
					}	
					else {
						for(k=j; k<j+kmer; k++)
							It[k-j] = Integer.MAX_VALUE;
						}
					
									
					//3.2 Get Il
					if(j>0){
						for(k=i; k<i+kmer; k++)
							Il[k-i] = scoreMatrix[k][j-1];
					}
					else {
						for(k=i; k<i+kmer; k++)
							Il[k-i] = Integer.MAX_VALUE;
					}
									
					//3.3 Get Ic
					if(i>0 && j>0){
						Ic = scoreMatrix[i-1][j-1];
					}
					else {
						if(i==0 && j==0){
							Ic = 0;
						}
						else{
							Ic = Integer.MAX_VALUE;
						}
					}
									
					//4.1 Find Ib
					int i1=0, j1=0;
					for(k=0; k<kmer; k++){
						Ib[k] = Integer.MAX_VALUE;
								
						for(i1=0; i1<kmer; i1++){
							Ib[k] = min(Ib[k], FLB(i1,k));
						}
						//System.out.println("IB at FLB"+Ib[k]);
						for(i1 = 0; i1<=k; i1++){
							Ib[k] = min(Ib[k], FTB(i1,k));
						}
						//System.out.println("IB at FTB"+Ib[k]);
							Ib[k] = min(Ib[k], FCB(k));	
						//System.out.println("IB at FCB"+Ib[k]);
					}
									
					//4.2 Find Ir	
					for(k=0; k<kmer; k++){
						Ir[k] = Integer.MAX_VALUE;
										
						for(i1=0; i1<kmer; i1++){
							Ir[k] = min(Ir[k], FTR(i1,k));
						}
						//System.out.println("IR at FTR"+Ir[k]);
						for(i1 = 0; i1<=k; i1++){
							Ir[k] = min(Ir[k], FLR(i1,k));
						}
						
						//System.out.println("IR at FLR"+Ir[k]);
						Ir[k] = min(Ir[k], FCR(k));
						//System.out.println("IR at FCR"+Ir[k]);
					}								
									
					//TESTONLY!(Print kmers)
					/*System.out.println("Ic is =" + Ic);
					System.out.println();
					System.out.println("Il is:");
					for(k=0; k<kmer; k++){
						System.out.print(Il[k]+ " ");
					}
									
					System.out.println();
					System.out.println("It is:");
					for(k=0; k<kmer; k++){
						System.out.print(It[k] + " ");
					}
									
					System.out.println();
					System.out.println("Ib is:");
					for(k=0; k<kmer; k++){
						System.out.print(Ib[k]+ " ");
					}
									
					System.out.println();
					System.out.println("Ir is:");
					for(k=0; k<kmer; k++){
						System.out.print(Ir[k] + " ");
					}*/
												
					//5. Update SWArray with Ib, Ir
					for(p=j; p<j+kmer; p++){
						scoreMatrix[i+kmer-1][p] = Ib[p-j];
					}
									
					for(p=i; p<i+kmer; p++){
						scoreMatrix[p][j+kmer-1] = Ir[p-i];
					}
									
					scoreMatrix[i+kmer-1][j+kmer-1] = min(Ib[kmer-1],Ir[kmer-1]);
				}//end for kmer for1
			}//end of kmer for2
	
			//2. Print DTW
			System.out.println("DTW Score Array is: ");
			for(i=0; i<lenA; i++){
				for(j=0; j<lenB; j++){
					if(scoreMatrix[i][j] == Integer.MAX_VALUE){
						System.out.print('*' + " ");
					}
					else
					System.out.print(scoreMatrix[i][j]+" ");
				}
				System.out.println();
			}//print end
			 
	
}//End fof dtw function
}//End of class

