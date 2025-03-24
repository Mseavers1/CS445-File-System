package structures;

// File Control Block
public class FCB {

    private String fileName;
    private int fileSize;
    private int startBlock;
    private int openCount;

    public FCB(String fileName, int size, int start) {
        this.fileName = fileName;
        this.fileSize = size;
        this.startBlock = start;
        this.openCount = 0;
    }

    public int getOpenCount(){return openCount;}

    public int getFileSize(){return fileSize;}

    public int getStartBlock(){return startBlock;}

    public String getFileName(){return fileName;}

    public void setOpenCount(int openCount){this.openCount = openCount;}

    public void printFCB(){
        System.out.println("FileSize" + fileSize); //NEED TO ADD KB
        System.out.println("StartBlock"+startBlock);
    }

}
