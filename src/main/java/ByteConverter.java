import java.nio.charset.StandardCharsets;

public class ByteConverter {

    public static byte[] convert(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static String convert(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8).replace("\0", "");
    }

}
