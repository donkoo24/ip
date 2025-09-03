package lux.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lux.domain.Deadline;
import lux.domain.Event;
import lux.domain.Task;
import lux.domain.ToDo;
import lux.repo.TaskList;

/**
 * Logic unit for handling the save file.
 */
public class SaveFileManager {
    private static final Path PATH_SAVEFILEDIRECTORY = Paths.get("./data/");
    private static final Path PATH_SAVEFILE = Paths.get("./data/Lux.txt");

    /**
     * Constructs a SaveFileManager.
     */
    public SaveFileManager() {}

    /**
     * Ensures save file and parent directory exists.
     * Creates then if missing.
     *
     * @throws IOException If directory or file creation fails.
     */
    public static void getOrCreateSaveFile() throws IOException {
        if (!Files.exists(PATH_SAVEFILE)) {
            Files.createDirectories(PATH_SAVEFILEDIRECTORY);
            Files.createFile(PATH_SAVEFILE);
        }
    }

    /**
     * Updates save file with the provided text from TaskList.
     * This method rewrites the whole file.
     *
     * @param textToAdd The full string of tasks from TaskList
     * @throws IOException If writing to the file fails.
     */
    public static void updateSaveFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(String.valueOf(PATH_SAVEFILE));
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Loads the tasks data into a list of tasks for use in TaskList.
     * Reconstructs tasks with all previously known data include task type, completion status, deadline (if any),
     * start (if any), end (if any).
     */
    public static void loadData(List<Task> taskList) {
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(PATH_SAVEFILE);

            for (String line : lines) {
                if (line.startsWith("[T]")) {
                    Pattern pattern = Pattern.compile("\\[(.)\\]\\[(.)\\]\\s(.*)");
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.matches()) {
                        String taskName = matcher.group(3);
                        String taskMark = matcher.group(2);

                        ToDo itemToAdd = new ToDo(taskName);

                        if (taskMark.equals("X")) {
                            itemToAdd.markCompleted();
                        }
                        taskList.add(itemToAdd);
                    }
                } else if (line.startsWith("[D]")) {
                    Pattern pattern = Pattern.compile("\\[(.)\\]\\[(.)\\]\\s(.*)\\s\\(by:\\s(.*)\\)");
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.matches()) {
                        String taskName = matcher.group(3);
                        String taskMark = matcher.group(2);
                        String taskDeadline = matcher.group(4);

                        Deadline itemToAdd = new Deadline(taskName, taskDeadline);

                        if (taskMark.equals("X")) {
                            itemToAdd.markCompleted();
                        }
                        taskList.add(itemToAdd);
                    }
                } else if (line.startsWith("[E]")) {
                    Pattern pattern = Pattern.compile(
                            "\\[(.)\\]\\[(.)\\]\\s(.*)\\s\\(from:\\s(.*)\\sto:\\s(.*)\\)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.matches()) {
                        String taskName = matcher.group(3);
                        String taskMark = matcher.group(2);
                        String taskFrom = matcher.group(4);
                        String taskTo = matcher.group(5);

                        Event itemToAdd = new Event(taskName, taskFrom, taskTo);

                        if (taskMark.equals("X")) {
                            itemToAdd.markCompleted();
                        }
                        taskList.add(itemToAdd);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads task from the save file into the provided TaskList.
     * Ensurs the save file exists before attempting to read.
     *
     * @param taskList The TaskList to populate.
     * @throws IOException If file reading fails.
     */
    public static void loadTask(TaskList taskList) throws IOException {
        SaveFileManager.getOrCreateSaveFile();
        SaveFileManager.loadData(taskList.getList());
    }
}
