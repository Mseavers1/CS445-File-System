import structures.*;

import java.io.IOException;
import java.util.*;

public class FileSystem {

    private Dictionary<String, FCB> directory;
    private VCB vcb;
    private SystemOpenFileTable systemTable;
    private ProcessOpenFileTable processTable;
    private DataBlock[] disk;

    public FileSystem() {
        directory = new Hashtable<>();
        vcb = new VCB();
        systemTable = new SystemOpenFileTable();
        processTable = new ProcessOpenFileTable();
        disk = new DataBlock[vcb.getNumberOfBlocks()];

        // Initialize the size of each block
        for (int i = 0; i < disk.length; i++) {
            disk[i] = new DataBlock(vcb.getSizeOfBlocks());
        }
    }

    /**
     * Creates a new file
     * @param fileName - name of the file
     * @param size - size of the file in bytes
     */
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
        directory.put(fileName, file);
        System.out.println("Created File"+fileName+ "StartBlock" +startBlock +"sizeinblocks"+size);
    }

    /**
     * Opens a file
     * @param fileName - name of the file
     */
    public void Open(String fileName) {

        // Check to see if a process has the file opened, if it does, increment open count only
        if (systemTable.containsFCB(fileName)) directory.get(fileName).incrementOpenCount();

        // If a process doesn't, add a new entry of the file into the system open file table
        else systemTable.addFile(fileName, directory.get(fileName));

        // Add a new process
        processTable.addProcess(fileName);
    }

    /**
     * Closes a file
     * @param handler - Unique handler of a process
     */
    public void Close(Integer handler) {

        // Check if handler exist in the per process (Can't close if not-opened)
        if (!processTable.containsHandler(handler)) return;

        // Get filename
        String fileName = processTable.getFileName(handler);

        // Check if the file is opened, if not, throw an error b.c there should be an entry
        if(!systemTable.containsFCB(fileName)) throw new NoSuchElementException("No FCB in the system open file table for file '" + fileName + "'!");

        // Get FCB
        FCB file = systemTable.getFCB(fileName);

        // Remove process in per process table
        processTable.removeProcess(handler);

        // Decrement open count for file
        file.decrementOpenCount();

        // If there are other processes using the file, return
        if (file.getOpenCount() > 0) return;

        // If not, remove file from system open file table
        systemTable.removeFile(fileName);
    }

    /**
     * Writes to a existing file
     * @param fileName - name of the file
     * @param data - size of the amount of data wanting to write in bytes
     */
    public void Write(String fileName, byte[] data) throws IOException {

        // Is the file opened, if not, return
        if (!systemTable.containsFCB(fileName)) return;

        // Add new to per process
        processTable.addProcess(fileName);

        // Get FCB
        FCB file = systemTable.getFCB(fileName);

        int dataSize = data.length;
        int blockSize = vcb.getSizeOfBlocks();
        int startBlock = file.getStartBlock();
        int blocksNeeded = (int) Math.ceil( (float) dataSize / (float) blockSize);

        // Is there enough space? If not, give an error
        if (dataSize > file.getFileSize()) throw new IOException("Data size (" + dataSize + " bytes) is larger than file size (" + file.getFileSize() + " bytes)");

        // Store data into each block
        for (int i = 0; i < blocksNeeded; i++) {

            // Get index of block from disk
            int blockID = startBlock + i;

            // Get the subset of the data that can fit into the current block
            byte[] dividedData = new byte[blockSize];
            System.arraycopy(data, i * blockSize, dividedData, 0, (i + 1) * blockSize - 1);

            // Store data
            disk[blockID].storeData(dividedData);
        }

        // Close process once completed


    }

    /**
     * Reads to a existing file
     * @param fileName - name of the file
     * @param dataSize - size of the amount of data wanting to read in bytes
     */
    public void Read(String fileName, int dataSize) {

        // Is the file opened, if not, return
        if (!systemTable.containsFCB(fileName)) return;

    }

    /**
     * Lists all files in the directory
     */
    public void Dir() {
        System.out.println("Directory Listing");

        Enumeration<String> keys = directory.keys();

        while (keys.hasMoreElements()) {
            String fileName = keys.nextElement();
            FCB file = directory.get(fileName);

            file.printFCB();
            System.out.println("-------------");
        }
    }

    // Deletes a file
    public void Delete(String fileName) {

        // What if the file is open? --> probably close it then deleted it?

        // What if the file is currently being written to?

        FCB file = directory.get(fileName);
        if(file.getFileName().equals(fileName)){
            vcb.freeBlocks(file.getStartBlock(), file.getFileSize());
            System.out.println("Deleted File" + fileName);

            return;
        }

        System.out.println("File Not Found"+fileName);
    }


}
