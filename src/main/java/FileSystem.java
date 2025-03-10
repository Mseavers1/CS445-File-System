import structures.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileSystem {

    private List<FCB> directory;
    private VCB vcb;
    private SystemOpenFileTable systemTable;
    private ProcessOpenFileTable processTable;

    public FileSystem() {
        directory = new ArrayList<>();
        vcb = new VCB();
        systemTable = new SystemOpenFileTable();
        processTable = new ProcessOpenFileTable();
    }

    // Creates a new file
    public void Create(String fileName, int size) {

        // Allocate the blocks required for file
        int startBlock = vcb.allocateBlocks(size);

        // Do not create file if there is no room
        if(startBlock == -1) {
            System.out.println("Not enough space to create " + fileName);
            return;
        }

        // Filename with the same name?

        // Creates the file
        FCB file = new FCB(fileName, size, startBlock);
        directory.add(file);
        System.out.println("Created File"+fileName+ "StartBlock" +startBlock +"sizeinblocks"+size);
    }

    // Opens a non-opened file
    public void Open() {

    }

    // Closes an opened file
    public void Close() {

    }

    // Writes to an open file
    public void Write() {

    }

    // Lists all files in the file system
    public void Dir() {
        System.out.println("Directory Listing");
        for (FCB file: directory){
            file.printFCB();
            System.out.println("-------------");
        }
    }

    // Deletes a file
    public void Delete(String fileName) {

        Iterator<FCB> iterator = directory.iterator();

        // Loops through the directory to find the file, then deletes it
        while (iterator.hasNext()){

            // What if the file is open? --> probably close it then deleted it?

            // What if the file is currently being written to?

            FCB file = iterator.next();
            if(file.getFileName().equals(fileName)){
                vcb.freeBlocks(file.getStartBlock(), file.getFileSize());
                iterator.remove();
                System.out.println("Deleted File" + fileName);

                return;
            }
        }

        System.out.println("File Not Found"+fileName);
    }


}
