package structures;

import misc.ByteConverter;
import misc.FileSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Class that creates process threads for the simulation
 * Uses builder to build processes the user wants
 */
public class Process extends Thread {

    private List<String> instructions;
    private String processName;
    private FileSystem fileSystem;

    private Map<String, Integer> handlers;

    public Process(FileSystem fileSystem, ProcessBuilder builder) {
        this(fileSystem, "Process", builder);
    }

    public Process(FileSystem fileSystem, String processName, ProcessBuilder builder) {
        this.instructions = builder.getInstructions();
        this.processName = processName;
        this.fileSystem = fileSystem;
        this.handlers = new LinkedHashMap<>();
    }

    @Override
    public void run() {
        for (String instruction : instructions) {
            System.out.println(processName + ": " + instruction);

            try {
                executeInstruction(instruction);
            }
            catch (Exception e) {
                System.out.println(processName + ": Instruction Failed (" + e.getMessage() + ")");
            }
        }
    }

    /**
     * Execute code of a certain instruction
     * @param instruction - name of the instruction
     */
    private void executeInstruction(String instruction) throws IOException, FileNotFoundException {

        String instruction_type = instruction.split(" ")[0];
        String filename = "";

        if (instruction.split(" ").length > 1)
            filename = instruction.split(" ")[1];


        if (Objects.equals(instruction_type, "--Create")) {
            int fileSize = Integer.parseInt(instruction.split(" ")[2]);

            //System.out.println();
            fileSystem.Create(filename, fileSize);
            //System.out.println();
        }

        else if (Objects.equals(instruction_type, "--Open")) {
            int handler = fileSystem.Open(filename);
            handlers.put(filename, handler);
        }

        else if (Objects.equals(instruction_type, "--Close")) {

            int handler = -1;

            if (handlers.get(filename) != null) handler = handlers.get(filename);

            fileSystem.Close(handler);
        }

        else if (Objects.equals(instruction_type, "--ReadString")) {

            int handler = -1;

            if (handlers.get(filename) != null) handler = handlers.get(filename);

            byte[] content = fileSystem.Read(handler);

            //System.out.println();
            System.out.println(filename + ": " + ByteConverter.convert(content));
            //System.out.println();
        }

        else if (Objects.equals(instruction_type, "--Read")) {

            int handler = -1;

            if (handlers.get(filename) != null) handler = handlers.get(filename);

            byte[] content = fileSystem.Read(handler);

            //System.out.println();
            System.out.println(filename + ": " + Arrays.toString(content));
            //System.out.println();
        }

        else if (Objects.equals(instruction_type, "--Write")) {

            int handler = -1;

            if (handlers.get(filename) != null) handler = handlers.get(filename);

            int index = instruction.indexOf(filename) + filename.length() + 1;
            String strContent = instruction.substring(index);

            if (strContent.charAt(0) == '[') {
                byte[] content = ByteConverter.convertStringToByteArray(strContent);

                fileSystem.Write(handler, content);
            }
            else {
                fileSystem.Write(handler, ByteConverter.convert(strContent));
            }

        }

        else if (Objects.equals(instruction_type, "--Dir")) {
            //System.out.println();
            fileSystem.Dir();
            //System.out.println();
        }

        else if (Objects.equals(instruction_type, "--Delete")) {
            //System.out.println();
            fileSystem.Delete(filename);
            //System.out.println();
        }

        else {
            throw new RuntimeException("Unknown instruction: " + instruction);
        }

    }

    /**
     * Builder architecture that works for the Process class
     */
    public static class ProcessBuilder {
        private List<String> instructions = new ArrayList<>();

        public List<String> getInstructions() {return instructions;}

        public ProcessBuilder write(String fileName, String content) {
            instructions.add("--Write " + fileName + " " + content);
            return this;
        }

        public ProcessBuilder write(String fileName, byte[] content) {
            instructions.add("--Write " + fileName + " " + Arrays.toString(content));
            return this;
        }

        public ProcessBuilder readString(String fileName) {
            instructions.add("--ReadString " + fileName);
            return this;
        }

        public ProcessBuilder read(String fileName) {
            instructions.add("--Read " + fileName);
            return this;
        }

        public ProcessBuilder create(String fileName, int fileSize) {
            instructions.add("--Create " + fileName + " " + fileSize);
            return this;
        }

        public ProcessBuilder open(String fileName) {
            instructions.add("--Open " + fileName);
            return this;
        }

        public ProcessBuilder close(String fileName) {
            instructions.add("--Close " + fileName);
            return this;
        }

        public ProcessBuilder delete(String fileName) {
            instructions.add("--Delete " + fileName);
            return this;
        }

        public ProcessBuilder dir() {
            instructions.add("--Dir ");
            return this;
        }
    }

}