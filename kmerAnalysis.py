#!/usr/bin/python

'''
Status: Working
'''

import sys
import math

def kmer_listgen(dna, k):
    length = len(dna)
    for x in range(0, length, k):
        yield dna[x:min(x+k,length)]

from Bio import SeqIO   #DNA-large

fasta_sequences = SeqIO.parse(open("DNA-large.fasta"),'fasta')
with open("histogramDistributionv1.txt") as out_file:
    for fasta in fasta_sequences:
        name, sequence = fasta.id, fasta.seq.tostring()

import collections
kmerSize = int(sys.argv[1])
global dnaLen
dnaLen = len(sequence)
totalKmers = math.ceil(dnaLen/float(kmerSize))	
histogram = {}
for kmer in kmer_listgen(sequence, kmerSize):
    if kmer in histogram:
        histogram[kmer]+=1
    else:
        histogram[kmer] = 1

totalUniqueKmers = len(histogram)
#nonUniqueKmers50 = sum(histogram.values(),0)
uniqueKmers50 = int(math.ceil((totalUniqueKmers * 50)/float(100)))  #u
uniqueKmers90 = int(math.ceil((totalUniqueKmers * 90)/float(100)))  #u

nonUniqueKmers50 = sum(sorted(histogram.values(), key=int, reverse=True)[0 : uniqueKmers50])
nonUniqueKmers90 = sum(sorted(histogram.values(), key=int, reverse=True)[0 : uniqueKmers90])
totalKmerComputation = totalKmers * totalKmers
#amt50 = nonUniqueKmers50 * uniqueKmers50 * uniqueKmers50 / (totalKmers * totalKmers)
#amt90 = nonUniqueKmers90 * uniqueKmers90 * uniqueKmers90 / (totalKmers * totalKmers)

computation50 = nonUniqueKmers50 * nonUniqueKmers50
computation90 = nonUniqueKmers90 * nonUniqueKmers90
#print sorted(histogram.values(), key=int, reverse=True)
print ("Hisogram distribution of: ", kmerSize, "-mer")
print ("Size of string = ", dnaLen)
print ("Total kmers", totalKmers )
print ("Total kmer computations", totalKmerComputation )
print ("Number of unique kmers", totalUniqueKmers)
print
print ("Number of unique kmers (50 percent)", uniqueKmers50)
print ("Number of non-unique kmers (50 percent)", nonUniqueKmers50 )
#print ("Amount of work saved for 50 percent unique kmers", amt50 )
print ("Total 50 percent kmer compuation", computation50)
print ("ratio of nonUniqueKmer50 computation to totalKmerComputation ", computation50/totalKmerComputation)
print
print ("Number of unique kmers (90 percent)", uniqueKmers90)
print ("Number of non-unique kmers (90 percent)", nonUniqueKmers90 )
#print ("Amount of work saved for 90 percent unique kmers", amt90 )
print ("Total 90 percent kmer compuation", computation90)
print ("ratio of nonUniqueKmer90 computation to totalKmerComputation ", computation90/totalKmerComputation)
print 
print
print
print

'''
#print(c.most_common(uniqueKmer))  #64 because total 3mer combinations in DNA sequence
#write_fasta("outputHumanMouse.txt")
'''
'''
#print my_list
print c
print list(c.values())
print list(collections.Counter(my_list).values())
print c.get(1)
'''