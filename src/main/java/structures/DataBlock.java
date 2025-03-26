package structures;

import java.nio.charset.StandardCharsets;

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

    public void storeData(String data) {

        if (data == null) throw new NullPointerException("Parameter data can not be null");

        this.data = data.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getDataBytes() {
        return data;
    }

    public String getDataString() {
        return new String(data, StandardCharsets.UTF_8);
    }

}
