import sys
import numpy

#lcs1 : naive LCS
#lcs2 : Naive LCS tiled approach
#lcs2_tile : tile function for niave LCS tiled approach
#lcs3 : New LCS
#lcs3_tile : tile function(by new equations) for new lcs 

#Status: Working
#To Do: Assert
#1. Add Assert
#2. Add lcs2 tiled function

def match(chara, charb):
    if chara == charb:
        return 1
    return 0

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

def flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
    return matrix[i, tjlo-1] + \
        lcs_score(lcs1, stra[i : tihi], strb[tjlo-1 : j]) 

def ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
    return matrix[tilo-1, j] + \
        lcs_score(lcs1, stra[tilo-1 : i], strb[j : tjhi]) 

def flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i):
    return matrix[il, tjlo-1] + \
        lcs_score(lcs1, stra[il : i], strb[tjlo-1 : tjhi]) 

def ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j):
    return matrix[tilo-1, jt] + \
        lcs_score(lcs1, stra[tilo-1 : tihi], strb[jt : j]) 

def fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j):
    return matrix[tilo-1, tjlo-1] + \
        lcs_score(lcs1, stra[tilo-1 : tihi], strb[tjlo-1 : j]) 

def fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i):
    return matrix[tilo-1, tjlo-1] + \
        lcs_score(lcs1, stra[tilo-1 : i], strb[tjlo-1 : tjhi]) 

def lcs3_tile(matrix, stra, strb,
              tilo, tihi, tjlo, tjhi):
    #assert tihi <= matrix.shape[0]
    #assert tjhi <= matrix.shape[1]
    
    #tilo = max(tilo, 1)
    #tjlo = max(tjlo, 1)

    '''
    print tilo
    print tihi
    print tjlo
    print tjhi
    '''

    for i in range(tilo, tihi+1):
        for j in range(tjlo, tjhi+1):
            matrix[tihi, j] = max(matrix[tihi, j],
                                    flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j))

           
    for i in range(tilo, tihi+1):
        for j in range(tjlo, tjhi+1):
            matrix[i, tjhi] = max(matrix[i, tjhi],
                                    ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j))

           
    for i in range(tilo, tihi+1):
        for il in range(tilo, i+1):
            matrix[i, tjhi] = max(matrix[i, tjhi],
                                    flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i))
    
    
    for j in range(tjlo, tjhi+1):
        for jt in range(tjlo, j+1):
            matrix[tihi, j] = max(matrix[tihi, j],
                                    ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j))
    
    
    for j in range(tjlo, tjhi+1):
        matrix[tihi, j] = max(matrix[tihi, j],
                                fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j))
    
      
    for i in range(tilo, tihi+1):
        matrix[i, tjhi] = max(matrix[i, tjhi],
                                fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i))

    

def lcs3(stra, strb, tile):
    lena = len(stra)+1
    lenb = len(strb)+1
    matrix = numpy.zeros((lena, lenb), numpy.int)
    for ti in range(1, lena, tile):
        for tj in range(1, lenb, tile):
            lcs3_tile(matrix, stra, strb,
                      ti, min(ti+tile-1, lena),  
                      tj, min(tj+tile-1, lenb))
    return matrix

def lcs_score(lcs_fn, *arg):
    return lcs_fn(*arg)[-1,-1]  

def main():
    stra = "AGCAAAATT"#GCG"
    strb = "GCCAAAAGT"#GCG"
    tile = 3
    assert lcs_score(lcs1, stra, strb) == \
        lcs_score(lcs2, stra, strb, tile) == \
        lcs_score(lcs3, stra, strb, tile)
    print "Naive LCS Result:"
    print lcs1(stra, strb)
    print
    print "New LCS Result:"
    print lcs3(stra,strb, tile)

if __name__ == "__main__":

    main()
