package structures;

// File Control Block
public class FCB {

    private String fileName;
    private int fileSize;
    private int startBlock;
    private int openCount;
    private boolean isPendingDeletion;

    public FCB(String fileName, int size, int start) {
        this.fileName = fileName;
        this.fileSize = size;
        this.startBlock = start;
        this.openCount = 0;
        isPendingDeletion = false;
    }

    public int getOpenCount(){return openCount;}

    public int getFileSize(){return fileSize;}

    public int getStartBlock(){return startBlock;}

    public String getFileName(){return fileName;}

    public void incrementOpenCount(){openCount++;}

    public void decrementOpenCount(){openCount--;}

    public boolean isPendingDeletion(){return isPendingDeletion;}

    public void setPendingDeletion(boolean pendingDeletion){isPendingDeletion = pendingDeletion;}

    @Override
    public String toString() {
        return fileName + " (" + fileSize + " bytes)";
    }
}
