package newLCS;

import java.util.Scanner;

public class NewLCSv1 {

	public static int max(int a, int b)
	{
	    return (a > b)? a : b;
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
		
		char [] kmerAch = new char[kmer - x];
		char [] kmerBch = new char[y+1];
		NaiveLCS nv = new NaiveLCS();
		int c;
		
		int ki=0;
		for(c=x; c<kmer; c++){
			kmerAch[ki++] = kmerA[x];
		}
		
		for(c=0; c<=y; c++){
			kmerBch[c] =  kmerB[c];
		}
		
		if(x==kmer){
			return Il[x-1];
		}
		else
			return Il[x-1] + nv.naivelcs(kmerAch, kmerBch);
	}
	
	//2. FTB: Influence of top edge elements on bottom edge elements
	public int FTB(int x, int y){
		
		NaiveLCS nv = new NaiveLCS();	
		int c=0;
		char [] kmerBch = new char[y-x];
		int ki=0;
		for(c=x+1; c<y+1; c++){
			kmerBch[ki++] = kmerB[c];
		}
		
		if(kmerBch.length == 0){
			return It[x];
		}
		return It[x] + nv.naivelcs(kmerA, kmerBch);
		
	}
	
	//3. FLR: Influence of left edge elements on right edge elements
	public int FLR(int x, int y){
		
		NaiveLCS nv = new NaiveLCS();	
		int c=0;
		char [] kmerAch = new char[y-x];
		int ki=0;
		for(c=x+1; c<y+1; c++){
			kmerAch[ki++] = kmerA[c];
		}
		
		if(kmerAch.length == 0){
			return Il[x];
		}
		else{
			
			return Il[x] + nv.naivelcs(kmerAch, kmerB);			
		}
				
	
	}
	
	//4. FTR: Influence of top edge elements on right edge elements
	public int FTR(int x, int y){

		char [] kmerBch = new char[kmer - x];
		char [] kmerAch = new char[y+1];
		NaiveLCS nv = new NaiveLCS();
		int c;
		
		int ki=0;
		for(c=x; c<kmer; c++){
			kmerBch[ki++] = kmerB[x];
		}
		
		for(c=0; c<=y; c++){
			kmerAch[c] =  kmerA[c];
		}
		
		if(x==kmer){
			return It[x-1];
		}
		else
			return It[x-1] + nv.naivelcs(kmerAch, kmerBch);

	}
	
	//5. FLT_B: Influence of corner elements on bottom edge elements(i.e. diagonal elements)
	public int FCB(int x){
		
		NaiveLCS nv = new NaiveLCS();
		kmerBch = new char[x+1];
		int c;
	
		for(c=0; c<=x; c++){
			kmerBch[c] = kmerB[c];
		}
		
		return Ic + nv.naivelcs(kmerA,kmerBch );
	}
	
	//6. F: Influence of corner elements on right edge elements(i.e. diagonal elements)
	public int FCR(int x){
		
		NaiveLCS nv = new NaiveLCS();
		kmerAch = new char[x+1];
		int c;
	
		for(c=0; c<=x; c++){
			kmerAch[c] = kmerA[c];
		}
		
		return Ic + nv.naivelcs(kmerAch,kmerB);
	}
	
	public void newlcs(char [] StringA, char [] StringB){
	
		//*********** Variables **********//
		int lenA = StringA.length;
		int lenB = StringB.length;
		int i,j,p,q;		
		
		System.out.println("Enter kmer size: "); //Assume square kmer only!
		Scanner sc = new Scanner(System.in);
		
		kmer = sc.nextInt(); 
		
		PrintLCS pr = new PrintLCS();
		int [][] SWArray = new int[lenA][lenB];
		Il = new int[kmer]; //left vector
		It = new int[kmer]; //top vector
		Ib = new int[kmer]; //bottom vector
		Ir = new int[kmer]; //right vector
		
		NaiveLCS nv = new NaiveLCS();
		kmerA=new char[kmer];
		kmerB=new char[kmer];
		int k;
		
		//********************************//
		
		/*System.out.println("Il");
		for(i=0; i<Il.length; i++){
			System.out.println(Il[i]);
		} //TESTONLY*/
			
		//Initialize SWArray by -INF
		for(i=0; i<lenA; i++){
			for(j=0; j<lenB; j++){
				SWArray[i][j] = Integer.MIN_VALUE; 
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
/*				System.out.println("Print kmers:");
				for(p=0; p<kmer; p++){
					System.out.print(kmerA[p]);
				}
				for(p=0; p<kmer; p++){
					System.out.print(kmerB[p]);
				}
*/				
				
				//2. Calculate Naive LCS
				System.out.println("Naive LCS kmer is:");
				nv.naivelcs(kmerA, kmerB);
				
				//3.1 Get It
				if(i>0){
					for(k=j; k<j+kmer; k++)
						It[k-j] = SWArray[i-1][k];
				}	
				else {
					for(k=j; k<j+kmer; k++)
						It[k-j] = 0;
				}
				
				//3.2 Get Il
				if(j>0){
					for(k=i; k<i+kmer; k++)
						Il[k-i] = SWArray[k][j-1];
				}
				else {
					for(k=i; k<i+kmer; k++)
						Il[k-i] = 0;
				}
				
				//TEST:(Print)
				//3.3 Get Ic
				if(i>0 && j>0){
					Ic = SWArray[i-1][j-1];
				}
				else {
					Ic = 0;
				}
				
				//4.1 Find Ib
				int i1=0, j1=0;
				for(k=0; k<kmer; k++){
					Ib[k] = Integer.MIN_VALUE;
					
					for(i1=1; i1<=kmer; i1++){
						Ib[k] = max(Ib[k], FLB(i1,k));
					}
					System.out.println("IB at FLB"+Ib[k]);
					for(i1 = 0; i1<=k; i1++){
						Ib[k] = max(Ib[k], FTB(i1,k));
					}
					System.out.println("IB at FTB"+Ib[k]);
					Ib[k] = max(Ib[k], FCB(k));	
					System.out.println("IB at FCB"+Ib[k]);
				}
				
				//4.2 Find Ir	
				for(k=0; k<kmer; k++){
					Ir[k] = Integer.MIN_VALUE;
					
					for(i1=1; i1<=kmer; i1++){
						Ir[k] = max(Ir[k], FTR(i1,k));
					}
					System.out.println("IR at FTR"+Ir[k]);
					for(i1 = 0; i1<=k; i1++){
						Ir[k] = max(Ir[k], FLR(i1,k));
					}
					System.out.println("IR at FLR"+Ir[k]);
					Ir[k] = max(Ir[k], FCR(k));
					System.out.println("IR at FCR"+Ir[k]);
				}
				
				
				
				//TESTONLY!(Print kmers)
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
				}
							
				//5. Update SWArray with Ib, Ir
				for(p=j; p<j+kmer; p++){
					SWArray[i+kmer-1][p] = Ib[p-j];
				}
				
				for(p=i; p<i+kmer; p++){
					SWArray[p][j+kmer-1] = Ir[p-i];
				}
				
				SWArray[i+kmer-1][j+kmer-1] = max(Ib[kmer-1],Ir[kmer-1]);
			}
		}
		
		pr.printLCS(SWArray);
	
	}

}
