package structures;

import java.security.InvalidParameterException;
import java.util.Dictionary;
import java.util.Hashtable;

public class ProcessOpenFileTable {

    private Dictionary<String, Integer> openFileTable;
    private static final int READ_ONLY = 0, WRITE_ONLY = 1, READ_WRITE = 2;

    public ProcessOpenFileTable() {
        openFileTable = new Hashtable<>();
    }

    /**
     * Retrieve identifier information of a process
     * @param filename - name of the file
     * @return - 0: Read Only | 1: Write Only | 2: Read and Write
     */
    public Integer getFileName(String filename) {

        // If no matches found, throw an error
        if (openFileTable.get(filename) == null) throw new InvalidParameterException("No matches found for the filename [" + filename + "]");

        return openFileTable.get(filename);
    }

}
