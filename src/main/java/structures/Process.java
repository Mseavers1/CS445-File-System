package structures;

import misc.ByteConverter;
import misc.FileSystem;

import java.io.IOException;
import java.util.*;

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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void executeInstruction(String instruction) throws IOException {

        String instruction_type = instruction.split(" ")[0];
        String filename = instruction.split(" ")[1];

        if (Objects.equals(instruction_type, "--Create")) {
            int fileSize = Integer.parseInt(instruction.split(" ")[2]);

            fileSystem.Create(filename, fileSize);
        }

        else if (Objects.equals(instruction_type, "--Open")) {
            int handler = fileSystem.Open(filename);
            handlers.put(filename, handler);
        }

        else if (Objects.equals(instruction_type, "--Close")) {
            fileSystem.Close(handlers.get(filename));
        }

        else if (Objects.equals(instruction_type, "--ReadString")) {
            byte[] content = fileSystem.Read(handlers.get(filename));

            System.out.println(filename + ": " + ByteConverter.convert(content));
        }

        else if (Objects.equals(instruction_type, "--Read")) {
            byte[] content = fileSystem.Read(handlers.get(filename));

            System.out.println(filename + ": " + Arrays.toString(content));
        }

        else if (Objects.equals(instruction_type, "--Write")) {

            int index = instruction.indexOf(filename) + filename.length() + 1;
            String strContent = instruction.substring(index);

            if (strContent.charAt(0) == '[') {
                byte[] content = ByteConverter.convertStringToByteArray(strContent);

                fileSystem.Write(handlers.get(filename), content);
            }
            else {
                fileSystem.Write(handlers.get(filename), ByteConverter.convert(strContent));
            }

        }

        else if (Objects.equals(instruction_type, "--Dir")) {
            fileSystem.Dir();
        }

        else if (Objects.equals(instruction_type, "--Delete")) {
            fileSystem.Delete(filename);
        }

        else {
            throw new RuntimeException("Unknown instruction: " + instruction);
        }

    }

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

        public ProcessBuilder dir(String fileName) {
            instructions.add("--Dir " + fileName);
            return this;
        }
    }

}