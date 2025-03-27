package misc;

import structures.Process;

public class Simulation {

    private static FileSystem fs = new FileSystem();

    public static void main(String[] args) {

        // Test taken from the assignment
        Test1();

    }

    /**
     * Test taken from assignment with addition of new operators
     */
    private static void Test1() {

        Process p1 = new Process(fs, "p1", new Process.ProcessBuilder()
                .create("file1.txt", 4096)
                .open("file1.txt")
                .write("file1.txt", "This is a simple example of writing into a file... Each letter has approx. 1 byte so it would take 4.1k words write but I don't feel like writing that much, but I will in a later test... but at a smaller file size. Okay, bye!")
                .close("file1.txt")
                .dir()
                .create("file2.txt", 1048)
                .open("file2.txt")
                .write("file2.txt", new byte[] {1,0,1,0,1,1,1,0,0,0,1,0,1})
                .close("file2.txt")
                .dir()
        );

        Process p2 = new Process(fs, "p2", new Process.ProcessBuilder()
                .open("file1.txt")
                .readString("file1.txt")
                .close("file1.txt")
        );

        Process p3 = new Process(fs, "p3", new Process.ProcessBuilder()
                .open("file2.txt")
                .read("file2.txt")
                .close("file2.txt")
                .delete("file2.txt")
                .dir()
                .create("file3.txt", 1048)
                .dir()
        );

        p1.start();
        try {
            p1.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        p2.start();
        p3.start();
    }

}
