package structures;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessOpenFileTable {

    private Dictionary<Integer, String> openFileTable;
    private Queue<Integer> completedHandlers;
    private int highestHandler;

    public ProcessOpenFileTable() {
        openFileTable = new Hashtable<>();
        completedHandlers = new LinkedList<>();
        highestHandler = 3; // Start at 3 b.c the first 3 are reserved
    }

    public boolean containsHandler(int handler) {
        return openFileTable.get(handler) != null;
    }

    /**
     * Removes the process from the table
     * @param handler - The unique handler associated to a process
     */
    public void removeProcess(Integer handler) {
        openFileTable.remove(handler);
        completedHandlers.add(handler);
    }

    public String getFileName(int handler) {
        return openFileTable.get(handler);
    }

    /**
     * Adds a new process into the table
     * @param fileName - name of the file the process is interacting with
     * @return - The unique handler associated to this process
     */
    public Integer addProcess(String fileName) {

        // Check to see if there are any used handlers, if so, use them
        if (!completedHandlers.isEmpty()) {
            int handler = completedHandlers.poll();
            openFileTable.put(handler, fileName);
            return handler;
        }

        // If not, create a new handler and increment highest handler
        int handler = highestHandler++;
        openFileTable.put(handler, fileName);
        return handler;

    }

}
