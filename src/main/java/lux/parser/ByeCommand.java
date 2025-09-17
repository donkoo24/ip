package lux.parser;

import java.io.IOException;

import lux.repo.TaskList;
import lux.storage.SaveFileManager;
import lux.ui.Ui;

/**
 * Executable command to save data and exit out of programme.
 */
public class ByeCommand implements Command {

    /**
     * Writes out all tasks to save file, and ends the conversation in the UI.
     *
     * @param tasks The task list containing all tasks to be saved.
     * @param ui    The UI instance used to communicate with the user.
     * @return the farewell message if save succeeds, or an error message if saving fails.
     */

    @Override
    public String execute(TaskList tasks, Ui ui) {
        StringBuilder saveData = new StringBuilder();
        String reply;

        for (int i = 0; i < tasks.getSize(); i++) {
            saveData.append(tasks.getTask(i));
            saveData.append(System.lineSeparator());
        }

        try {
            SaveFileManager.updateSaveFile(saveData.toString());
        } catch (IOException e) {
            reply = "Did not manage to save task data";
            ui.speak(reply + e.getMessage() + "\n");
            return reply + e.getMessage();
        }

        return ui.endConvo();
    }

    /**
     * Indicates if this command will terminate application.
     *
     * @return true as this command will cause program to exit.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
