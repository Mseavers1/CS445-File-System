package structures;

import java.util.Dictionary;
import java.util.Hashtable;

public class SystemOpenFileTable {

    private Dictionary<String, FCB> files;

    public SystemOpenFileTable() {
        files = new Hashtable<>();
    }

}
