package structures;

import java.util.ArrayList;
import java.util.List;

// Can only have 1 directory (flat directory structure)
public class Directory {
    private List<FCB> files;

    public Directory() {
        files = new ArrayList<>();
    }

    public void addFile(FCB file) {
        files.add(file);
    }


}
