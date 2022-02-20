# Weekly reports
Each weeks report will be found here with all the relevant information.

## week 1
Hours used: 6.

Found an subject that interested me( compressing) and read about Huffman and LZ77 coding. Made the specification document and just the basic structure for the project. 

Next week: Starting the fundamentals of Huffman coding.


## week 2
Hours used: 10.

Tried to implement the Huffman compressing algorithms, which worked fine, but writing it as binary to a file proved to be annoying. 
Managed to do the start of the job: first 64 bits are reserved for the size of the file --> 
+ treebuilding: 8 bits to count how many characters there are + 8 bits for the chars themselves + 8 bits for the length of the binary + 16 bits for the binary itself.
I have an vague idea how to read the huffman tree from these and decode, wont be ready on time though.
Also added checkstyle and Jacoco and some tests. 


## week 4
Hours used: 15.
Currently all tests for encoding and decoding is done. It also works fine for any ASCII containing text file. Next up is a fast build of a simple file chooser GUI, adding javadoc and other documentation and then moving onto ziv lempel algorithm. Im a week behind, but will be catching up this and next week hopefully. 


## week 5
Hours used: 9.
Writed javadoc for the decoder, encoder and HuffmanGenerator, with some commentary too. Will be writing now the GUI and reading on Lempel Ziv, also doing the peer review within today. Bit late(pun intended), but will be catching up next week.
