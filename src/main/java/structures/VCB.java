package structures;

// Volume Control Block
public class VCB {

    private int numberOfBlocks; // Total number of blocks
    private int sizeOfBlocks;   // Size of a block in byes
    private int freeBlockCount; // Number of blocks not being used
    boolean[] blockBitMap;  // Array telling which blocks are in use

    public VCB() {
        this(512, 2048);
    }

    public VCB(int numberOfBlocks, int sizeOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
        this.sizeOfBlocks = sizeOfBlocks;
        freeBlockCount = numberOfBlocks - 1;
        blockBitMap = new boolean[numberOfBlocks];
        this.blockBitMap[0] = true; // First block is being used as the VCB
    }

    public synchronized int allocateBlocks(int size){

        // Convert size to number of blocks
        int totalNumberOfBlocks = (int) Math.ceil((float) size / (float) sizeOfBlocks);

        // Checks every position in bitmap to find an opening with the correct number of free spaces
        for (int i = 1; i <= numberOfBlocks - totalNumberOfBlocks; i++){
            boolean available = true;

            // Checks starting from i to the full length of blocks needed to see if there is space
            for (int j = i; j <= i + totalNumberOfBlocks; j++){
                if (blockBitMap[j]) {
                    available = false;
                    break;
                }
            }

            // Once the blocks are found, set them to be used & return starting block index
            if (available) {

                for (int j = i; j < i + totalNumberOfBlocks; j++){
                    blockBitMap[j] = true;
                }

                freeBlockCount -= totalNumberOfBlocks;
                return i;
            }
        }

        return -1; // No Contigious block
    }

    // Free allocated blocks
    public synchronized void freeBlocks(int startBlock, int size) {

        // Convert size to number of blocks
        int totalNumberOfBlocks = (int) Math.ceil((float) size / (float) sizeOfBlocks);

        for (int i = startBlock; i < startBlock + totalNumberOfBlocks; i++) {
            blockBitMap[i] = false;
        }

        freeBlockCount += size;
    }

    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public int getSizeOfBlocks() {
        return sizeOfBlocks;
    }
}
