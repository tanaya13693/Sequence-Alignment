import sys
import numpy

'''
*Created by: Tanaya V.
*File: nw.py
*Description: nw1- Naive Needleman Wunch algorithm score matrix computation
              nw3_tile()- using 6 optimized equations computes score matrix 
              nw using nw
              Block4 dependency

*Status: working
*To Do: 
*Parameters: None
*Assumptions: match/mismatch/gv/gh = 1/-1/-1/-1
*Date Created: 4/19/17
*Updates:
'''

#NW Scoring
def matchFun(chara, charb):
    if chara == charb:
        return match
    return mismatch

def nw_score(nw1_fn, *arg): 
    return nw1_fn(*arg)[-1,-1]         

#Naive NW-Score matrix
def nw1(stra, strb):
    lena = len(stra)+1
    lenb = len(strb)+1
    matrix = numpy.empty((lena, lenb,))
    matrix[0,0] = 0

    for i in range(1,lena):
        matrix[i,0] = matrix[i-1,0] + gv
    
    for i in range(1,lenb):
        matrix[0,i] = matrix[0,i-1] + gv

    for i in range(1,lena):
        for j in range(1,lenb):
            matrix[i,j] = max(matrix[i-1,j  ] + gv,
                              matrix[i,  j-1] + gh,
                              matrix[i-1,j-1] + matchFun(stra[i-1],strb[j-1]))
    return matrix

#NW2-Score matrix
def nw2(stra, strb, x, y):
    lena = len(stra)+1
    lenb = len(strb)+1
    matrix = numpy.empty((lena, lenb,))

    for i in range(0,lena):
        for j in range(0,lenb):
            matrix[i,j] = float("-infinity")

    matrix[x,y] = 0

    for i in range(1,lena):
        for j in range(1,lenb):
            matrix[i,j] = max(matrix[i-1,j  ] + gv,
                              matrix[i,  j-1] + gh,
                              matrix[i-1,j-1] + matchFun(stra[i-1],strb[j-1]))
    return matrix
#---------------------------------------Naive NW Recurrence----------------------------------------#
#matrix[0,0] = 0
#matrix[i,j] = max(matrix[i-1,j-1] + match/mismatch, matrix[i,j-1] + gh, matrix[i-1,j] + gv)
#--------------------------------------------------------------------------------------------------#

#influence of left vector on bottom vector
def flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
 return matrix[i, tjlo-1] + \
        nw_score(nw2, stra[i-1 : tihi-1], strb[tjlo-1 : j], 1, 0)   #Block4

#influence of top vector on right vector
def ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
   return matrix[tilo-1, j] + \
        nw_score(nw2, stra[tilo-1 : i], strb[j-1 : tjhi-1], 0, 1)   #Block4

#influence of left vector on right vector
def flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i): 
    return matrix[il, tjlo-1] + \
        nw_score(nw2, stra[il-1 : i], strb[tjlo-1 : tjhi-1], 1, 0) #Block4

#influence of top vector on bottom vector
def ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j):
    return matrix[tilo-1, jt] + \
        nw_score(nw2, stra[tilo-1 : tihi-1], strb[jt-1 : j], 0, 1)  #Block4

#influence of corner element on bottom vector
def fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j):
    return matrix[tilo-1, tjlo-1] + nw_score(nw2, stra[tilo-1 : tihi-1], strb[tjlo-1 : j], 0, 0)    #Block4
 
#influence of corner element on right vector
def fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i):
    return matrix[tilo-1, tjlo-1] + nw_score(nw2, stra[tilo-1 : i], strb[tjlo-1 : tjhi-1], 0, 0)    #Block4

def nw3_tile(matrix, stra, strb,  
              tilo, tihi, tjlo, tjhi):
    #assert tihi <= matrix.shape[0]
    #assert tjhi <= matrix.shape[1]

    '''
    print tilo
    print tihi
    print tjlo
    print tjhi
    '''    

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

def nw3(stra, strb): 
    lena = len(stra)+1
    lenb = len(strb)+1
    matrix = numpy.empty((lena, lenb,))
    for i in range(0,lena):
        for j in range(0,lenb):
            matrix[i,j] = float("-infinity")
    
    #print matrix
    matrix[0,0] = 0

    for i in range(1,lena):
        matrix[i,0] = matrix[i-1,0] + gv
    
    for i in range(1,lenb):
        matrix[0,i] = matrix[0,i-1] + gh

    for ti in range(1, lena, tile):
        for tj in range(1, lenb, tile):
            nw3_tile(matrix, stra, strb,
                      ti, min(ti+tile, lena),  
                      tj, min(tj+tile, lenb))        
    return matrix 

def main():
    stra = "GATTACAAA"
    strb = "GCATGCGGGTT"

    global tile
    tile = 3

    print stra
    print strb

    global match
    match = 2
    global mismatch
    mismatch = -1
    global gh
    gh = -1
    global gv
    gv = -1

    '''
    assert lcs_score(lcs1, stra, strb) == \
        lcs_score(lcs2, stra, strb, tile) == \
        lcs_score(lcs3, stra, strb, tile)
    '''
    print "Naive Needleman Wunch Result:"
    print nw1(stra, strb)
    print
    print "New Needleman Wunch Result:"
    print nw3(stra,strb)


if __name__ == "__main__":
    main()


'''
Test Cases:

1. 
stra = "GATTACA"
strb = "GCATGCU"
tile = 3

2.
stra = "GATTACAAA"
strb = "GCATGCGGGTT"
tile = 3

'''