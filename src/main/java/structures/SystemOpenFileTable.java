package structures;

import java.security.InvalidParameterException;
import java.util.Dictionary;
import java.util.Hashtable;

public class SystemOpenFileTable {

    private Dictionary<String, FCB> files;

    public SystemOpenFileTable() {
        files = new Hashtable<>();
    }

    // Returns the FCB of a filename
    public FCB getFCB(String filename) {

        // If no filename exist, return an error
        if (files.get(filename) == null) throw new InvalidParameterException("No matches found for the filename [" + filename + "]");

        return files.get(filename);
    }

}
