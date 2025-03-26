package misc;

import java.io.IOException;

// misc.Testing only
public class Testing {

    static FileSystem fs = new FileSystem();

    public static void main(String[] args) throws IOException {

        // Process 1 (would be a thread)
        fs.Create("Test.txt", 5096);
        int handler = fs.Open("Test.txt");

        fs.Write(handler, ByteConverter.convert("Hello There MikeOBits! I do not know how much this is but I am going to BUT I AM TRYING TO EXCEED THE DATA BUT IDK IF IT IS WORKING OR NOT!?!"));

        byte[] output = fs.Read(handler);

        System.out.println(ByteConverter.convert(output));

        fs.Close(handler);
    }



}
