import sys
import numpy

'''
*Created by: Tanaya V.
*File: newDTWv3.py
*Description: dtw3()- fills up score matrix  using Dymanic Time Warping algorithm(naive DTW).
              dtw3_tile()- uses 6 optimized DTW equations to calculate same. Based on '1' block dependency equations 
              Edge case considered in FLB, FLR, FTB, FTR
              2 block dependency also works but redundant
              DTW using DTW
              Block 4 dependency
              
*Status: Working!
*To Do: 1. Append string A,B by character once for all,since the appending a character each time 
            for chopped sring is expensive. 
        3. if--else edge condition remove. Add alternative
        4. Clean Comments
*Parameters: None
*Date Created: 4/19/17
'''

def match(chara, charb):
    if chara == charb:
        return 0
    return 1

#Naive DTW score matrix fill-up
def dtw1(stra, strb):
    a = len(stra)
    b = len(strb)
    lena = a+1
    lenb = b+1
    matrix = numpy.empty((lena, lenb,))
    matrix[:] = a * b * 1
    matrix[0,0] = 0
    for i in range(1,lena):
        for j in range(1,lenb):
            matrix[i,j] = min(matrix[i-1,j  ],
                              matrix[i,  j-1],
                              matrix[i-1,j-1]) + match(stra[i-1],strb[j-1])
    return matrix    

#DTW2 based on new DTW equations
def dtw2(stra, strb, x, y):
    lena = len(stra) + 1
    lenb = len(strb) + 1
    #lena = a+1
    #lenb = b+1
    matrix = numpy.empty((lena, lenb,))
    #matrix[:] = a * b * 1    #can initialize +Infinity a well
    #matrix[x,y] = 0
    
    for i in range(0,lena):
        for j in range(0,lenb):
            matrix[i,j] = float("infinity") #can initialize a*b*1 as well (Max value less than infinity)

    matrix[x,y] = 0

    for i in range(1, lena):
        for j in range(1, lenb):
            matrix[i,j] = min(matrix[i-1,j  ],
                              matrix[i,  j-1],
                              matrix[i-1,j-1]) + match(stra[i-1],strb[j-1])
    return matrix

#Tiled-DTW based on new DTW equations
def dtw3(stra, strb):
    a = len(stra)
    b = len(strb)
    lena = a+1
    lenb = b+1
    matrix = numpy.empty((lena, lenb,))
    matrix[:] = a * b * 1
    matrix[0,0] = 0
    for ti in range(1, lena, tile):
        for tj in range(1, lenb, tile):
            dtw3_tile(matrix, stra, strb,
                      ti, min(ti+tile, lena),  
                      tj, min(tj+tile, lenb))
    return matrix

#influence of left vector on bottom vector
def flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
 return matrix[i, tjlo-1] + \
        dtw_score(dtw2, stra[i-1 : tihi-1], strb[tjlo-1 : j], 1, 0)   #Block4

#influence of top vector on right vector
def ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
   return matrix[tilo-1, j] + \
        dtw_score(dtw2, stra[tilo-1 : i], strb[j-1 : tjhi-1], 0, 1)   #Block4

#influence of left vector on right vector
def flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i): 
    return matrix[il, tjlo-1] + \
        dtw_score(dtw2, stra[il-1 : i], strb[tjlo-1 : tjhi-1], 1, 0) #Block4

#influence of top vector on bottom vector
def ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j):
    return matrix[tilo-1, jt] + \
        dtw_score(dtw2, stra[tilo-1 : tihi-1], strb[jt-1 : j], 0, 1)  #Block4

#influence of corner element on bottom vector
def fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j):
    return matrix[tilo-1, tjlo-1] + dtw_score(dtw2, stra[tilo-1 : tihi-1], strb[tjlo-1 : j], 0, 0)    #Block4
 
#influence of corner element on right vector
def fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i):
    return matrix[tilo-1, tjlo-1] + dtw_score(dtw2, stra[tilo-1 : i], strb[tjlo-1 : tjhi-1], 0, 0)    #Block4


def dtw3_tile(matrix, stra, strb,
              tilo, tihi, tjlo, tjhi):
    #assert tihi <= matrix.shape[0]
    #assert tjhi <= matrix.shape[1]

    for i in range(tilo, tihi):
        for j in range(tjlo, tjhi):
            matrix[tihi-1, j] = min(matrix[tihi-1, j],
                                    flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j))

    for i in range(tilo, tihi):
        for j in range(tjlo, tjhi):
            matrix[i, tjhi-1] = min(matrix[i, tjhi-1],
                                    ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j))
          
    for i in range(tilo, tihi):
        for il in range(tilo, i+1):
            matrix[i, tjhi-1] = min(matrix[i, tjhi-1],
                                    flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i))    
    
    for j in range(tjlo, tjhi):
        for jt in range(tjlo, j+1):
            matrix[tihi-1, j] = min(matrix[tihi-1, j],
                                    ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j))    
    
    for j in range(tjlo, tjhi):
        matrix[tihi-1, j] = min(matrix[tihi-1, j],
                                fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j))    
      
    for i in range(tilo, tihi):
        matrix[i, tjhi-1] = min(matrix[i, tjhi-1],
                                fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i))

#To get the end score of score-matrix(used in dtw3 to get final score of kmers)
def dtw_score(dtw_fn, *arg):
    return dtw_fn(*arg)[-1,-1]  

def main():
    stra = "AGCAAAATT" #GCG"
    strb = "GCCAAAAGT" #GCG"
    global tile
    tile = 3

    '''
    assert lcs_score(lcs1, stra, strb) == \
        lcs_score(lcs2, stra, strb, tile) == \
        lcs_score(lcs3, stra, strb, tile)
    '''
    print "Naive DTW Result:"
    print dtw1(stra, strb)
    print
    print "New DTW Result:"
    print dtw3(stra,strb)
    
if __name__ == "__main__":
    main()

'''
Test cases:

1. 
stra = "AGCAAAATTGCG"
strb = "GCCAAAAGTGCG"
tile = 3/4

2.
stra = "AGCAAAATCGCG"
strb = "GCCAGAAGTGCG"
tile = 3/4

3. 
stra = "AGCAAAATT"
strb = "GCCAAAAGT"
tile = 3

4.
stra = "AAAAAAAAAA"
strb = "AAAAAAAAAA"
tile = 5

5.
stra = "AAAAAAAAAA"
strb = "CCCCCCCCCC"
tile = 5

6.
stra = "AAAAAAAAAA"
strb = "CCCCCCCCCCCCC"
tile = 5 


'''