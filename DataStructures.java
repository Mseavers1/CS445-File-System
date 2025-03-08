package OperatingSYSTEM;
class FileControlBlock{
    private String fileName;
    private int fileSize;
    private int startBlock;

    public FileControlBlock(String name,int size,int start){
        this.fileName = name;
        this.fileSize = size;
        this.startBlock = start;
    }
    public String getFileName(){return fileName;}
    public int getFileSize(){return fileSize;}
    public int getStartBlock(){return startBlock;}

    public void printFCB(){
        System.out.println("FileName"+fileName);
        System.out.println("FileSize" + fileSize); //NEED TO ADD KB
        System.out.println("StartBlock"+startBlock);
    }
}
