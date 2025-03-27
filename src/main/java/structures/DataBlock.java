package structures;

/**
 * DataBlock mimicking that of an OS
 */
public class DataBlock {

    private byte[] data;
    private int maxSize;

    public DataBlock(int size) {
        maxSize = size;
        data = new byte[maxSize];
    }

    public void storeData(byte[] data) {
        this.data = data;
    }

    public byte[] getDataBytes() {
        return data;
    }

}
