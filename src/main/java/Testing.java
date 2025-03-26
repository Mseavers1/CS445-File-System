import java.io.IOException;
import java.util.Arrays;

// Testing only
public class Testing {

    static FileSystem fs = new FileSystem();

    public static void main(String[] args) throws IOException {

        // Process 1 (would be a thread)
        fs.Create("Test.txt", 2048);
        int handler = fs.Open("Test.txt");

        fs.Write(handler, ByteConverter.convert("Hello There MikeOBits!"));

        byte[] output = fs.Read(handler);

        System.out.println(ByteConverter.convert(output));

        fs.Close(handler);
    }



}
