package misc;

import java.nio.charset.StandardCharsets;

/**
 * Helper class that converts from bytes[] to string and vise versa
 */
public class ByteConverter {

    /**
     * Convert strings to byte[]
     * @param str - the string you want to convert
     * @return - byte[] of the given string
     */
    public static byte[] convert(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Convert byte[] to string
     * @param bytes - the given array to turn into a string
     * @return - string of the given byte[]
     */
    public static String convert(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8).replace("\0", "");
    }

    // Produced by ChatGPT version GPT-40-mini
    public static byte[] convertStringToByteArray(String content) {
        // Remove the square brackets
        content = content.substring(1, content.length() - 1);

        // Split the string by commas
        String[] parts = content.split(", ");

        // Create a byte array of the same length as the parts
        byte[] byteArray = new byte[parts.length];

        // Convert each part to a byte and store it in the byte array
        for (int i = 0; i < parts.length; i++) {
            byteArray[i] = Byte.parseByte(parts[i]);
        }

        return byteArray;
    }
}
