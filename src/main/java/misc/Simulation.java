package misc;

import structures.Process;

public class Simulation {

    private static FileSystem fs = new FileSystem();

    /**
     *  Since tests use threading, please run each test individually as unwanted threads might run in the background.
     */
    public static void main(String[] args) {

        // Test taken from the assignment
        regularTest();

        // Custom test for testing deletion
        //deleteTest();

        // Custom test for testing deadlocks
        //deadlockTest();

        // Custom test for testing large files
        //largeFilesTest();

        // Other tests
        //miscTests();
    }

    /**
     * Misc Test Cases:
     *      1. Write to a file that doesn't exist or/and not opened
     *      2. Read a file that doesn't exist or/and not opened
     *      3. Create an already existing file
     *      4. Delete a file that doesn't exist
     *      5. Delete the first file and see where new files are added based on starting block
     */
    private static void miscTests() {
        Process p1 = new Process(fs, "p1", new Process.ProcessBuilder()
                .read("test")
                .write("test", "hi")
                .open("test")
                .close("test")
                .delete("test")
                .create("test", 10)
                .create("test", 1000)
                .write("test", "hi there seven.")
                .readString("test")
                .open("test")
                .write("test", "hi there seven.")
                .readString("test")
                .close("test")
                .create("test2", 10000)
                .create("test3", 10000)
                .create("test4", 10000)
                .delete("test")
                .create("test", 100)
                .delete("test2")
                .create("test2", 10000)
                .create("test10", 1000)
        );

        p1.start();
    }

    /**
     * This test includes various tests:
     *      1. Creating a file when there is no more room
     *      2. Writing to a file that doesn't have enough bytes
     */
    private static void largeFilesTest() {

        Process p1 = new Process(fs, "p1", new Process.ProcessBuilder()
                .create("file1.txt", 4096)
                .open("file1.txt")
                .write("file1.txt", "This is a simple example of writing into a file... Each letter has approx. 1 byte so it would take 4.1k words write but I don't feel like writing that much, but I will in a later test... but at a smaller file size. Okay, bye!")
                .readString("file1.txt")
                .close("file1.txt")
                .create("file2.txt", 4096)
                .create("fileBIG.txt", 200000)
                .create("fileBIG2.txt", 200000)
                .create("fileBIG3.txt", 200000)
                .create("fileBIG4.txt", 200000)
                .create("fileBIG5.txt", 200000)
                .create("fileBIG6.txt", 200000)
                .create("fileTiny.txt", 4)
                .open("fileTiny.txt")
                .write("fileTiny.txt", "There are probably more than 13 characters in this sentence.")
                .readString("fileTiny.txt")
                .write("fileTiny.txt", "hi!!")
                .readString("fileTiny.txt")
                .close("fileTiny.txt")
        );

        p1.start();
    }

    /**
     * This test checks to see when multiple processes open and writes to a file
     * We were expecting a deadlock, but it didn't happen so we think it is fine?
     */
    private static void deadlockTest() {

        Process p1 = new Process(fs, "p1", new Process.ProcessBuilder()
                .create("file1.txt", 4096)
                .open("file1.txt")
                .write("file1.txt", "This is a simple example of writing into a file... Each letter has approx. 1 byte so it would take 4.1k words write but I don't feel like writing that much, but I will in a later test... but at a smaller file size. Okay, bye!")
                .readString("file1.txt")
                .close("file1.txt")
        );

        Process p2 = new Process(fs, "p2", new Process.ProcessBuilder()
                .create("file2.txt", 4096)
                .dir()
                .open("file1.txt")
                .write("file1.txt", "Oops, I rewrote your work! My bad...")
                .readString("file1.txt")
                .close("file1.txt")
        );

        p1.start();
        p2.start();
    }

    /**
     * This test is for the deletion and dir operator
     * When run, it will either do a safe deletion -- If process open file, wait till it closes to delete
     * or a direct deletion - no process have file open so delete, but a file later tries to open it.
     */
    private static void deleteTest() {

        Process p1 = new Process(fs, "p1", new Process.ProcessBuilder()
                .create("file1.txt", 4096)
                .open("file1.txt")
                .write("file1.txt", "This is a simple example of writing into a file... Each letter has approx. 1 byte so it would take 4.1k words write but I don't feel like writing that much, but I will in a later test... but at a smaller file size. Okay, bye!")
                .read("file1.txt")
                .close("file1.txt")
                .dir()
        );

        Process p2 = new Process(fs, "p2", new Process.ProcessBuilder()
                .create("file2.txt", 50)
                .delete("file1.txt")
                .dir()
        );

        p1.start();
        p2.start();
    }

    /**
     * Test taken from assignment with addition of new operators
     */
    private static void regularTest() {

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
