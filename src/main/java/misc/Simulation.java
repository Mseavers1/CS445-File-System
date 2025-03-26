package misc;

import structures.Process;

public class Simulation {

    private static FileSystem fs = new FileSystem();

    public static void main(String[] args) {

        Process process1 = new Process(fs, "Process1", new Process.ProcessBuilder()
                .create("test.txt", 500)
                .open("test.txt")
                .write("test.txt", "This is a test.")
                .readString("test.txt")
                .close("test.txt")
        );

        Process process2 = new Process(fs, "Process2", new Process.ProcessBuilder()
                .create("test.txt", 500)
                .open("test.txt")
                .write("test.txt", "I don't know what I am doing, but cool beans.")
                .readString("test.txt")
                .close("test.txt")
        );

        process1.start();
        process2.start();


    }

}
