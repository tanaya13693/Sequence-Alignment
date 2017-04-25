import sys
import numpy

'''
*Created by: Tanaya V.
*File: newLCSv3.py (Testing code)  
*Description: lcs1- Naive LCS algorithm score matrix computation
              lcs3_tile()- using 6 optimized equations computes score matrix (1 block dependency)
              lcs using lcs
              1 block dependency also works but redundants
              Block4 depedency
              
*Status: working
*To Do: 
*Parameters: None
*Assumptions: match/mismatch = 1/0
*Updates:
*created: 04/17/17
'''

#LCS Scoring
def match(chara, charb):
    if chara == charb:
        return 1
    return 0

#Naive LCS-Score matrix
def lcs1(stra, strb):
    lena = len(stra)+1
    lenb = len(strb)+1
    matrix = numpy.zeros((lena, lenb), numpy.int)
    for i in range(1,lena):
        for j in range(1,lenb):
            matrix[i,j] = max(matrix[i-1,j  ],
                              matrix[i,  j-1],
                              matrix[i-1,j-1]+match(stra[i-1],strb[j-1])
            )
    return matrix

#Newly initialized LCS-Score matrix
def lcs2(stra, strb, x, y):
    lena = len(stra)+1
    lenb = len(strb)+1
    matrix = numpy.empty((lena, lenb,))
    for i in range(0,lena):
        for j in range(0,lenb):
            matrix[i,j] = float("-infinity")

    matrix[x,y] = 0
    for i in range(1,lena):
        for j in range(1,lenb):
            matrix[i,j] = max(matrix[i-1,j  ],
                              matrix[i,  j-1],
                              matrix[i-1,j-1]+match(stra[i-1],strb[j-1]))

    #print stra
    #print strb
    #print matrix        
    return matrix

#---------------------------------------LCS Equations----------------------------------------------#
#flb(i,0)->(n-1,j) = IL(i,0) + LCS(A(i+1:n) + B(0:j+1))
#ftb(0,i)->(n-1,j) = IT(0,i) + LCS(A(:) + B(i+1:j+1))
#flr(j,0)->(i,n-1) = IL(j,0) + LCS(A(j+1:i+1) + B(:))
#ftr(0,j)->(i,n-1) = I(0,j) + LCS(A(0:i+1) + B(j+1:n))
#fcb(n-1,j) = Ic + LCS(A(0:n) + B(0:j+1))
#fcr(i,n-1) = Ic + LCS(A(0:i+1) + B(:))
#--------------------------------------------------------------------------------------------------# 

#influence of left vector on bottom vector
def flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
 return matrix[i, tjlo-1] + \
        lcs_score(lcs2, stra[i-1 : tihi-1], strb[tjlo-1 : j], 1, 0)   #Block4

#influence of top vector on right vector
def ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
   return matrix[tilo-1, j] + \
        lcs_score(lcs2, stra[tilo-1 : i], strb[j-1 : tjhi-1], 0, 1)   #Block4

#influence of left vector on right vector
def flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i): 
    return matrix[il, tjlo-1] + \
        lcs_score(lcs2, stra[il-1 : i], strb[tjlo-1 : tjhi-1], 1, 0) #Block4

#influence of top vector on bottom vector
def ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j):
    return matrix[tilo-1, jt] + \
        lcs_score(lcs2, stra[tilo-1 : tihi-1], strb[jt-1 : j], 0, 1)  #Block4

#influence of corner element on bottom vector
def fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j):
    return matrix[tilo-1, tjlo-1] + lcs_score(lcs2, stra[tilo-1 : tihi-1], strb[tjlo-1 : j], 0, 0)    #Block4
 
#influence of corner element on right vector
def fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i):
    return matrix[tilo-1, tjlo-1] + lcs_score(lcs2, stra[tilo-1 : i], strb[tjlo-1 : tjhi-1], 0, 0)    #Block4
 
def lcs3_tile(matrix, stra, strb,
              tilo, tihi, tjlo, tjhi):
    assert tihi <= matrix.shape[0]
    assert tjhi <= matrix.shape[1]

    for i in range(tilo, tihi):
        for j in range(tjlo, tjhi):
            matrix[tihi-1, j] = max(matrix[tihi-1, j],
                                    flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j))

    for i in range(tilo, tihi):
        for j in range(tjlo, tjhi):
            matrix[i, tjhi-1] = max(matrix[i, tjhi-1],
                                    ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j))
          
    for i in range(tilo, tihi):
        for il in range(tilo, i+1):
            matrix[i, tjhi-1] = max(matrix[i, tjhi-1],
                                    flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i))    
    
    for j in range(tjlo, tjhi):
        for jt in range(tjlo, j+1):
            matrix[tihi-1, j] = max(matrix[tihi-1, j],
                                    ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j))    
    
    for j in range(tjlo, tjhi):
        matrix[tihi-1, j] = max(matrix[tihi-1, j],
                                fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j))    
      
    for i in range(tilo, tihi):
        matrix[i, tjhi-1] = max(matrix[i, tjhi-1],
                                fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i))

def lcs3(stra, strb, tile):
    lena = len(stra)+1
    lenb = len(strb)+1
    matrix = numpy.zeros((lena, lenb), numpy.int)
    
    '''
    matrix = numpy.empty((lena, lenb,))
    for i in range(0,lena):
        for j in range(0,lenb):
            matrix[i,j] = float("-infinity")
    '''
    matrix[0,0] = 0

    for ti in range(1, lena, tile):
        for tj in range(1, lenb, tile):
            lcs3_tile(matrix, stra, strb,
                      ti, min(ti+tile, lena),  
                      tj, min(tj+tile, lenb))
    return matrix

def lcs_score(lcs_fn, *arg):
    return lcs_fn(*arg)[-1,-1]  

def main():
    
    stra = "AGCAAAATTAC"
    strb = "GCCAAAAGT"
    global tile
    tile = 3
 
    '''
    assert lcs_score(lcs1, stra, strb) == \
        lcs_score(lcs2, stra, strb, tile) == \
        lcs_score(lcs3, stra, strb, tile)
    '''
        
    print "Naive LCS Result:"
    print lcs1(stra, strb)
    print
    print "New LCS Result:"
    print lcs3(stra,strb, tile)

if __name__ == "__main__":

    main()

'''
Test Cases:
1.
stra = "AGCAAAATT"
strb = "GCCAAAAGT"
tile = 3

2.
stra = "AGCAAAATTAC"
strb = "GCCAAAAGT"
tile = 3

stra = "AGCAAAATTAAGTGTGTGTGT"
strb = "GCCAAAAGT"
tile = 3
'''