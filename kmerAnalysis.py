#!/usr/bin/python

def kmer_list(dna, k):
    result = []
    for x in range(len(dna)+1-k):  #lood for distribution with sliding
        result.append(dna[x:x+k])
    return result


#while x<(len(dna) - len(dna)%k):  #loop for distribution w/o sliding
#        result.append(dna[x:x+k])       
#        x = x+k
#    return result

from Bio import SeqIO

fasta_sequences = SeqIO.parse(open("humanMouse.fasta"),'fasta')
with open("outputHumanMouse.txt") as out_file:
    for fasta in fasta_sequences:
        name, sequence = fasta.id, fasta.seq.tostring()

import collections		
my_list = kmer_list(sequence, 4)
c = collections.Counter(my_list)
print(c.most_common(256))  #64 because total 3mer combinations in DNA sequence
#write_fasta("outputHumanMouse.txt")
