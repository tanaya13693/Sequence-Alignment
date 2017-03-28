import sys
import numpy

def match(chara, charb):
    if chara == charb:
        return 0
    return 1

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

def dtw_score(dtw_fn, *arg):
    return dtw_fn(*arg)[-1,-1]     

def dtw3(stra, strb, tile):
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
                      ti, min(ti+tile-1, lena),  
                      tj, min(tj+tile-1, lenb))
    return matrix


def flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j): 
    if i==tihi:
        return matrix[tihi, tjlo-1] + dtw_score(dtw1, stra[tihi-1: tihi], strb[tjlo-1 : j])

    return matrix[i, tjlo-1] + \
        min(dtw_score(dtw1, stra[i-1 : tihi], strb[tjlo-1 : j]),
            dtw_score(dtw1, stra[i : tihi], strb[tjlo-1 : j]),
            dtw_score(dtw1, stra[i : tihi], '0'+strb[tjlo-1 : j]))


def ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j):
    if j==tjhi:
        return matrix[tilo-1, tjhi] + dtw_score(dtw1, stra[tilo-1 : i], strb[tjhi-1 : tjhi]) #checked

    return matrix[tilo-1, j] + \
        min(dtw_score(dtw1, '0' + stra[tilo-1 : i], strb[j : tjhi]),
            dtw_score(dtw1, stra[tilo-1 : i], strb[j-1 : tjhi]),
            dtw_score(dtw1, stra[tilo-1 : i], strb[j : tjhi])) 


def flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i):
    if il==i:
        return matrix[il, tjlo-1] + dtw_score(dtw1, stra[il-1: il], strb[tjlo-1 : tjhi])

    return matrix[il, tjlo-1] + \
        min(dtw_score(dtw1, stra[il-1 : i], strb[tjlo-1 : tjhi]),
            dtw_score(dtw1, stra[il : i], '0' + strb[tjlo-1 : tjhi]), 
            dtw_score(dtw1, stra[il : i], strb[tjlo-1 : tjhi]))  

def ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j):
    if jt==j:
        return matrix[tilo-1, jt] + dtw_score(dtw1, stra[tilo-1 : tihi], strb[jt-1 : jt])

    return matrix[tilo-1, jt] + \
        min(dtw_score(dtw1, stra[tilo-1 : tihi], strb[jt-1 : j]),
            dtw_score(dtw1, stra[tilo-1 : tihi], strb[jt : j]),
            dtw_score(dtw1, '0' + stra[tilo-1 : tihi], strb[jt : j])) 

def fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j):
    return matrix[tilo-1, tjlo-1] + \
        min(dtw_score(dtw1, stra[tilo-1 : tihi], '0' + strb[tjlo-1 : j]),
            dtw_score(dtw1, stra[tilo-1 : tihi], strb[tjlo-1 : j]),
            dtw_score(dtw1, '0' + stra[tilo-1 : tihi], strb[tjlo-1 : j])) 

def fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i):
    return matrix[tilo-1, tjlo-1] + \
        min(dtw_score(dtw1, stra[tilo-1 : i], '0' + strb[tjlo-1 : tjhi]),
            dtw_score(dtw1, stra[tilo-1 : i], strb[tjlo-1 : tjhi]),
            dtw_score(dtw1, '0' + stra[tilo-1 : i], strb[tjlo-1 : tjhi]))

def dtw3_tile(matrix, stra, strb,
              tilo, tihi, tjlo, tjhi):
    #assert tihi <= matrix.shape[0]
    #assert tjhi <= matrix.shape[1]

    for i in range(tilo, tihi+1):
        for j in range(tjlo, tjhi+1):
            matrix[tihi, j] = min(matrix[tihi, j],
                                    flb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j))

    for i in range(tilo, tihi+1):
        for j in range(tjlo, tjhi+1):
            matrix[i, tjhi] = min(matrix[i, tjhi],
                                    ftr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i, j))

          
    for i in range(tilo, tihi+1):
        for il in range(tilo, i+1):
            matrix[i, tjhi] = min(matrix[i, tjhi],
                                    flr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, il, i))
    
    
    for j in range(tjlo, tjhi+1):
        for jt in range(tjlo, j+1):
            matrix[tihi, j] = min(matrix[tihi, j],
                                    ftb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, jt, j))
    
    
    for j in range(tjlo, tjhi+1):
        matrix[tihi, j] = min(matrix[tihi, j],
                                fcb(matrix, stra, strb, tilo, tihi, tjlo, tjhi, j))
    
      
    for i in range(tilo, tihi+1):
        matrix[i, tjhi] = min(matrix[i, tjhi],
                                fcr(matrix, stra, strb, tilo, tihi, tjlo, tjhi, i))


def main():
    stra = "AGCAAAATTGCG"
    strb = "GCCAAAAGTGCG"
    tile = 4
    '''
    assert lcs_score(lcs1, stra, strb) == \
        lcs_score(lcs2, stra, strb, tile) == \
        lcs_score(lcs3, stra, strb, tile)
    print "Naive LCS Result:"
    print lcs1(stra, strb)
    print
    print "New LCS Result:"
    print lcs3(stra,strb, tile)
    '''
    print "Naive DTW Result:"
    print dtw1(stra, strb)
    print
    print "New DTW Result:"
    print dtw3(stra,strb,tile)
    
if __name__ == "__main__":

    main()
