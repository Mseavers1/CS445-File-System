package OperatingSYSTEM;

import java.io.File;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


class FileSystem{
    private VolumeControlBlock vcb = new VolumeControlBlock();
    private List<FileControlBlock> directory = new ArrayList<>();

    public synchronized void createFile(String fileName,int sizeInBlocks){
        int startBlock =vcb.allocateBlocks(sizeInBlocks);
        if( startBlock == -1){
            System.out.println("Not enough space to create"+fileName);
            return;
        }
        FileControlBlock file = new FileControlBlock(fileName,sizeInBlocks,startBlock);
        directory.add(file);
        System.out.println("Created File"+fileName+ "StartBlock" +startBlock +"sizeinblocks"+sizeInBlocks);
    }
    public synchronized void deleteFile(String fileName){
        Iterator<FileControlBlock> iterator = directory.iterator();
        while (iterator.hasNext()){
            FileControlBlock file = iterator.next();
            if(file.getFileName().equals(fileName)){
                vcb.freeBlocks(file.getStartBlock(),file.getFileSize());
                iterator.remove();
                System.out.println("Deleted File" + fileName);
                return;

            }
        }
        System.out.println("File Not Found"+fileName);
    }

    public synchronized void listFile(){
        System.out.println("Directory Listing");
        for (FileControlBlock file: directory){
            file.printFCB();
            System.out.println("-------------");
        }
    }

}

