package structures;

public class ProcessMetaData {

    public static final int IDLE = 0, READ = 1, WRITE = 2;

    private String fileName;
    private int state;

    public ProcessMetaData(String fileName) {
        this.fileName = fileName;
        state = 0;
    }

    public String getFileName() {
        return fileName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
