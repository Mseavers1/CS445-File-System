import structures.*;

public class FileSystem {

    private Directory directory;
    private VCB vcb;
    private SystemOpenFileTable systemTable;
    private ProcessOpenFileTable processTable;

    public FileSystem() {
        directory = new Directory();
        vcb = new VCB();
        systemTable = new SystemOpenFileTable();
        processTable = new ProcessOpenFileTable();
    }

    // Creates a new file
    public void Create(String fileName, int size) {

        // Allocate the blocks required for file
        int startBlock = vcb.allocateBlocks(size);

        if(startBlock == -1) {
            System.out.println("Not enough space to create"+fileName);
            return;
        }

        FCB file = new FCB(fileName, size, startBlock);
        directory.addFile(file);
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

    }

    // Deletes a file
    public void Delete() {

    }


}
