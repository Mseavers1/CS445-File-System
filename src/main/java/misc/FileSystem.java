package misc;

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

        // Does filename already exist? If so, return
        if (directory.get(fileName) != null) {
            System.out.println(fileName + " already exist.");
            return;
        }

        // Allocate the blocks required for file
        int startBlock = vcb.allocateBlocks(size);

        // Do not create file if there is no room
        if(startBlock == -1) {
            System.out.println("Not enough space to create " + fileName);
            return;
        }

        // Creates the file
        FCB file = new FCB(fileName, size, startBlock);
        directory.put(fileName, file);
        System.out.println("Created File ["+fileName+ "] Starting Block Number: " +startBlock + " Size of Block: "+size);
    }

    /**
     * Opens a file
     * @param fileName - name of the file
     * @return - Returns the handler id
     */
    public int Open(String fileName) {

        // Check to see if a process has the file opened, if it does, increment open count only
        if (systemTable.containsFCB(fileName)) directory.get(fileName).incrementOpenCount();

        // If a process doesn't, add a new entry of the file into the system open file table
        else systemTable.addFile(fileName, directory.get(fileName));

        // Add a new process
        return processTable.addProcess(fileName);
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
     * Writes to an existing file (starting at the beginning)
     * @param handler - Unique handler id given when opened the file
     * @param data - data in the form of bytes
     * @throws IOException - caused when size of data is larger than the file size
     */
    public void Write(int handler, byte[] data) throws IOException {

        // Does the handler exist? If not, return
        if (!processTable.containsHandler(handler)) return;

        // Get filename
        String fileName = processTable.getFileName(handler);

        // Update state - Writing
        processTable.UpdateProcess(handler, ProcessMetaData.WRITE);

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

            // Get length of either block or size
            int remainingBytes = Math.min(blockSize, data.length - (i * blockSize));

            // Get the subset of the data that can fit into the current block
            byte[] dividedData = new byte[blockSize];
            System.arraycopy(data, i * blockSize, dividedData, 0, remainingBytes);

            // Store data
            disk[blockID].storeData(dividedData);
        }

        // Update state - Completed
        processTable.UpdateProcess(handler, ProcessMetaData.IDLE);
    }

    /**
     * Reads to an existing file (entire file)
     * @param handler - the unique id of the process
     * @return - the entire data from all blocks associate with file
     * @throws IOException - the handler does not exist
     */
    public byte[] Read(int handler) throws IOException {

        // Does the handler exist? If not, throw error
        if (!processTable.containsHandler(handler)) throw new IOException("The handler (" + handler + ") does not exist!");

        // Get filename
        String fileName = processTable.getFileName(handler);

        // Update state - Reading
        processTable.UpdateProcess(handler, ProcessMetaData.READ);

        // Get FCB
        FCB file = systemTable.getFCB(fileName);

        int blockSize = vcb.getSizeOfBlocks();
        int startBlock = file.getStartBlock();
        int totalBlocks = (int) Math.ceil( (float) file.getFileSize() / (float) blockSize);
        int lastBlock = startBlock + totalBlocks - 1;
        byte[] completedData = new byte[file.getFileSize()];
        int sLocation = 0;

        // Go to each block and pool bytes
        for (int i = startBlock; i <= lastBlock; i++) {
            byte[] blockData = disk[i].getDataBytes();
            System.arraycopy(blockData, 0, completedData, sLocation * blockSize, Math.min(blockSize, file.getFileSize() - (sLocation * blockSize)));
            sLocation++;
        }

        // Update state - Idle
        processTable.UpdateProcess(handler, ProcessMetaData.IDLE);

        return completedData;
    }

    /**
     * Lists all files in the directory
     */
    public void Dir() {
        System.out.println("All files in current directory:");

        Enumeration<String> keys = directory.keys();

        while (keys.hasMoreElements()) {
            String fileName = keys.nextElement();
            FCB file = directory.get(fileName);
            System.out.println("\t - " + file);
        }
    }

    // Deletes a file
    public void Delete(String fileName) {

        // What if the file is open? --> probably close it then deleted it?

        // What if the file is currently being written to?

        FCB file = directory.get(fileName);
        if(file != null){
            vcb.freeBlocks(file.getStartBlock(), file.getFileSize());
            directory.remove(fileName);
            System.out.println("Deleted File (" + fileName + ")");

            return;
        }

        System.out.println("File Not Found ("+fileName+")");
    }


}
