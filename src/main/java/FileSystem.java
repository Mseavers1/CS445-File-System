import structures.Directory;
import structures.ProcessOpenFileTable;
import structures.SystemOpenFileTable;
import structures.VCB;

public class FileSystem {

    private Directory directory;
    private VCB vcb;
    private SystemOpenFileTable systemTable;
    private ProcessOpenFileTable processTable;

    public FileSystem() {
        directory = new Directory();
        vcb = new VCB();
        systemTable = new SystemOpenFileTable();
        processTable = new ProcessOpenFileTable();
    }

    // Creates a new file
    public void Create() {

    }

    // Opens a non-opened file
    public void Open() {

    }

    // Closes an opened file
    public void Close() {

    }

    // Writes to an open file
    public void Write() {

    }

    // Lists all files in the file system
    public void Dir() {

    }

    // Deletes a file
    public void Delete() {

    }


}
