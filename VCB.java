package OperatingSYSTEM;
class VolumeControlBlock{
    int totalBlock = 512;
    int blockSize = 2048;
    int freeBlockCount = 511;
    boolean[] blockBitMap = new boolean[512];

    public VolumeControlBlock(){
        blockBitMap[0] = true; //RESERVED TAKEN FOR METADATA
    }
    public synchronized int allocateBlocks(int size){
        for (int i = 1; i<= totalBlock - size; i++){
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


