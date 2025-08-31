package lux.storage;

import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

import lux.domain.Deadline;
import lux.domain.Event;
import lux.domain.Task;
import lux.domain.ToDo;
import lux.repo.TaskList;

public class SaveFileManager {
    private static final Path PATH_SAVEFILEDIRECTORY = Paths.get("./data/");
    private static final Path PATH_SAVEFILE = Paths.get("./data/Lux.txt");

    public SaveFileManager() {}

    public static void getOrCreateSaveFile() throws IOException {
        if (!Files.exists(PATH_SAVEFILE)) {
            Files.createDirectories(PATH_SAVEFILEDIRECTORY);
            Files.createFile(PATH_SAVEFILE);
        }
    }

    public static void updateSaveFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(String.valueOf(PATH_SAVEFILE));
        fw.write(textToAdd);
        fw.close();
    }

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

    public static void loadTask(TaskList taskList) throws IOException {
        SaveFileManager.getOrCreateSaveFile();
        SaveFileManager.loadData(taskList.getList());
    }
}
