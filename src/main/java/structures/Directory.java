package structures;

import java.util.ArrayList;
import java.util.List;

// Can only have 1 directory (flat directory structure)
public class Directory {
    private List<FCB> directoryFiles;

    public Directory() {
        directoryFiles = new ArrayList<>();
    }


}
