import java.io.IOException;
import java.util.Arrays;

// Testing only
public class Testing {

    static FileSystem fs = new FileSystem();

    public static void main(String[] args) throws IOException {

        // Process 1 (would be a thread)
        fs.Create("Test.txt", 2048);
        int handler = fs.Open("Test.txt");

        fs.Write(handler, new byte[]{1,1,0,0,1,0,1});

        byte[] output = fs.Read(handler);

        System.out.println(Arrays.toString(output));

        fs.Close(handler);
    }



}
