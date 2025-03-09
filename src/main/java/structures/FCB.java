package structures;

// File Control Block
public class FCB {

    private String fileName;
    private int fileSize;
    private int startBlock;

    public FCB(String fileName, int size, int start){
        this.fileName = fileName;
        this.fileSize = size;
        this.startBlock = start;
    }

    public int getFileSize(){return fileSize;}

    public int getStartBlock(){return startBlock;}

    public void printFCB(){
        System.out.println("FileSize" + fileSize); //NEED TO ADD KB
        System.out.println("StartBlock"+startBlock);
    }

}
