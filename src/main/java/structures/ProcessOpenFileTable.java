package structures;

import java.security.InvalidParameterException;
import java.util.Dictionary;
import java.util.Hashtable;

public class ProcessOpenFileTable {

    private Dictionary<Integer, String> fileIdentifiers;

    public ProcessOpenFileTable() {
        fileIdentifiers = new Hashtable<>();
    }

    // Return the filename of the identifier
    public String getFileName(int identifier) {

        // If no matches found, throw an error
        if (fileIdentifiers.get(identifier) == null) throw new InvalidParameterException("No matches found for the identifier [" + identifier + "]");

        return fileIdentifiers.get(identifier);
    }

}
