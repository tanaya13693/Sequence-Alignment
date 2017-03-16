# Sequence-Alignment-Test-Codes

File: kmerAnalysis.py
Python version: 2.7.3
Bio-python version: 1.68
Script chops the input DNA sequence into kmers and finds their frequency in the complete sequence. 
The script supports any kind of bio-file format(like FASTA, abi etc) since Bio-python is used. 
Also, any k-mer distribution can be found out by doing respective change in the python script.



LTDPV1.1:
1. Working code of Amatrix generator needed for rank convergence.
2. Amatrix generator generates variable sequence length matrices
3. Multiply Amatrices in the order provided by the user and check rank convergence
Note: 'Rank Convergence' is a property of matrix where all rows are parallel to each other(May differ by some offset or some multiplication factor.). Refer rank convergence paper for more details.
Working cde for variables size matrices


Optimized LCS:
Refer New equations for-
1. FBL 2.FTL 3.FLR 4.FTR 5.FCB 6.FCR


LTDP.java:
Hardcoded test version of LTDP(Linear Tropical Dynamic Programming) based on 'Rank Convergence' concept. Refer LTDPV1.1 for better version and working code for variable sized matrices.
