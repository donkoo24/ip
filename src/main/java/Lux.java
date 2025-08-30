import UI.Ui;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Lux {
    private static TaskList taskList = new TaskList();
    private final static Pattern MARK_PATTERN = Pattern.compile("(^mark)\\s(\\d+)", Pattern.CASE_INSENSITIVE);
    private final static Pattern UNMARK_PATTERN = Pattern.compile("^(unmark)\\s(\\d+)", Pattern.CASE_INSENSITIVE);
    private final static Pattern TODO_PATTERN = Pattern.compile("(todo)\\s(.*)", Pattern.CASE_INSENSITIVE);
    private final static Pattern DEADLINE_PATTERN = Pattern.compile("(deadline)\\s(.*)\\s/by\\s(.*)", Pattern.CASE_INSENSITIVE);
    private final static Pattern EVENT_PATTERN = Pattern.compile("(event)\\s(.*)\\s/from\\s(.*)\\s/to\\s(.*)", Pattern.CASE_INSENSITIVE);
    private final static Pattern DELETE_PATTERN = Pattern.compile("(delete)\\s(.*)", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) throws IOException {
        Ui ui = new Ui();
        CommandParser cp = new CommandParser();

        ui.greet();
        loadTask();
        handleConvo(ui, cp);
    }

    private static void handleConvo(Ui ui, CommandParser cp) {

        while (true) {
            try {
                String userInputInfo = ui.readline();
                Command cmd = cp.parse(userInputInfo);
                cmd.execute(taskList, ui);
                if (cmd.isExit()) {
                    break;
                }

            } catch (NoDescriptionException | NoCommandException e){
                ui.speak(e.getMessage());
            }
        }

        /*while (!userInputInfo.equalsIgnoreCase("bye")) {
            if (userInputInfo.equalsIgnoreCase("list")) {
                taskList.showList(ui);
            } else {
                Matcher markMatcher = MARK_PATTERN.matcher(userInputInfo);
                Matcher unmarkMatcher = UNMARK_PATTERN.matcher(userInputInfo);
                Matcher deleteMatcher = DELETE_PATTERN.matcher(userInputInfo);
                if (markMatcher.find()) {
                    taskList.markTask(Integer.parseInt(markMatcher.group(2)), ui);
                } else if (unmarkMatcher.find()) {
                    taskList.unmarkTask(Integer.parseInt(unmarkMatcher.group(2)), ui);
                } else if (deleteMatcher.find()) {
                    taskList.deleteTask(Integer.parseInt(deleteMatcher.group(2)), ui);
                }

                else {
                    try {
                        buildTaskFrom(userInputInfo, ui);
                    } catch (NoDescriptionException | NoCommandException e ) {
                        ui.speak(e.getMessage());
                    }
                }
            }
            userInputInfo = ui.readline();
        }
        */
    }

    /*
    private static void endConvo(Ui ui) {
        StringBuilder saveData = new StringBuilder();

        for (int i = 0; i < taskList.getSize(); i++) {
            saveData.append(taskList.getTask(i)).append(System.lineSeparator());
        }

        try {
            SaveFileManager.updateSaveFile(saveData.toString());
        } catch (IOException e) {
            ui.speak("Did not manage to save task data"  + e.getMessage());
        }

        ui.endConvo();
    }

    private static void buildTaskFrom(String item, Ui ui) throws NoDescriptionException, NoCommandException {
        Matcher toDoMatcher = TODO_PATTERN.matcher(item);
        Matcher deadlineMatcher = DEADLINE_PATTERN.matcher(item);
        Matcher eventMatcher = EVENT_PATTERN.matcher(item);

        if (toDoMatcher.find()) {
            if (toDoMatcher.group(2).isBlank()) {
                throw new NoDescriptionException("bruh, task name cannot be empty la");
            }
            Task itemToAdd = new ToDo(toDoMatcher.group(2));
            tasklist.addListItem(itemToAdd, ui);
        } else if (deadlineMatcher.find()) {
            if (deadlineMatcher.group(2).isBlank()) {
                throw new NoDescriptionException("bruh, task name cannot be empty la");
            } else if (deadlineMatcher.group(3).isBlank()) {
                throw new NoDescriptionException("bruh, deadline cannot be empty la, if not go use todo");
            }

            Task itemToAdd = new Deadline(deadlineMatcher.group(2), deadlineMatcher.group(3));
            TaskList.addListItem(itemToAdd, ui);
        } else if (eventMatcher.find()) {
            if (eventMatcher.group(2).isBlank()) {
                throw new NoDescriptionException("bruh, task name cannot be empty la");
            } else if (eventMatcher.group(3).isBlank()) {
                throw new NoDescriptionException("bruh, start field cannot be empty la, if not go use todo");
            } else if (eventMatcher.group(4).isBlank()) {
                throw new NoDescriptionException("bruh, end field cannot be empty la, if not go use deadline or todo");
            }
            Task itemToAdd = new Event(eventMatcher.group(2), eventMatcher.group(3), eventMatcher.group(4));
            TaskList.addListItem(itemToAdd, ui);
        } else {
            throw new NoCommandException("bruh, idk what this mean. If you are using a valid command, make sure to have the necessary descriptions.");
        }

    }*/

    private static void loadTask() throws IOException {
        SaveFileManager.getOrCreateSaveFile();
        SaveFileManager.loadData(taskList.getList());
    }
}
