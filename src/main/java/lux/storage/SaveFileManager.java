package lux.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
    private static final Pattern DATA_PATTERN = Pattern.compile(
            "^\\[(T|D|E)]\\[(.)] (.*?)"
            + "(?: \\(by: (.*?)\\)| \\(from: (.*?) to: (.*?)\\))?$"
            );

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
        assert !textToAdd.isEmpty() : "Can't update empty line to save file";

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
        assert taskList != null : "taskList cannot be null";

        try (Stream<String> lines = Files.lines(PATH_SAVEFILE)) {
            lines.map(DATA_PATTERN::matcher)
                    .filter(Matcher::matches)
                    .map(m -> {
                        Task t;
                        switch (m.group(1)) {
                        case "T" -> t = new ToDo(m.group(3));
                        case "D" -> t = new Deadline(m.group(3), m.group(4));
                        case "E" -> t = new Event(m.group(3), m.group(5), m.group(6));
                        default -> throw new IllegalStateException();
                        }
                        if ("X".equals(m.group(2))) {
                            t.markCompleted();
                        }
                        return t;
                    })
                    .forEach(taskList::add);
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
        assert taskList != null : "taskList cannot be null";

        SaveFileManager.getOrCreateSaveFile();
        SaveFileManager.loadData(taskList.getList());
    }
}
