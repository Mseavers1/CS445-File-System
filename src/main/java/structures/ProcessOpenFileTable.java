package structures;

import java.util.Dictionary;
import java.util.Hashtable;

public class ProcessOpenFileTable {

    private Dictionary<Integer, String> fileIdentifiers;

    public ProcessOpenFileTable() {
        fileIdentifiers = new Hashtable<>();
    }

}
