package structures;

import java.security.InvalidParameterException;
import java.util.Dictionary;
import java.util.Hashtable;

public class SystemOpenFileTable {

    private Dictionary<String, FCB> files;

    public SystemOpenFileTable() {
        files = new Hashtable<>();
    }

    /**
     * Returns the FCB given the filename in the table
     * @param filename - name of the file
     * @return - FCB pointer matching the filename
     */
    public FCB getFCB(String filename) {

        // If no filename exist, return an error
        if (files.get(filename) == null) throw new InvalidParameterException("No matches found for the filename [" + filename + "]");

        return files.get(filename);
    }

    /**
     * Adds a file into the table
     * @param filename - name of the file
     * @param fcb - FCB pointer
     */
    public void addFile(String filename, FCB fcb) {

        // Add file only if the file is not already added
        if (files.get(filename) == null) {
            fcb.setOpenCount(fcb.getOpenCount() + 1);
            files.put(filename, fcb);
            return;
        }

        // If already added, find and increment open count
        files.get(filename).setOpenCount(fcb.getOpenCount() + 1);
    }

}
