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
        for (int i = 1; i<= numberOfBlocks - size; i++){
            boolean available = true;
            for (int j = i; j<= i + size; j++){
                if (blockBitMap[j]){
                    available = false;
                    break;
                }
            }
            if (available){
                for (int j = i; j < i+size ; j++){
                    blockBitMap[j] = true; // mark them as used

                }
                freeBlockCount -=size;
                return i;
            }
        }
        return -1; //No Contigious block
    }
    // Free allocated blocks
    public synchronized void freeBlocks(int startBlock, int size) {
        for (int i = startBlock; i < startBlock + size; i++) {
            blockBitMap[i] = false;
        }
        freeBlockCount += size;
    }

}
