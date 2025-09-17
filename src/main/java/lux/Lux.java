package lux;

import java.io.IOException;

import lux.parser.Command;
import lux.parser.CommandParser;
import lux.repo.TaskList;
import lux.storage.SaveFileManager;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

/**
 * Starting point for Lux chatbot.
 */
public class Lux {
    private TaskList taskList = new TaskList();
    private CommandParser cp;
    private Ui ui;

    /**
     * Constructs the Lux chatbot and initalises the Ui and CommandParser for use.
     */
    public Lux() {
        this.cp = new CommandParser();
        this.ui = new Ui();
        try {
            SaveFileManager.loadTask(this.taskList);
        } catch (IOException e) {
            ui.speak(e.getMessage());
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command cmd = cp.parse(input);
            assert cmd != null : "cmd cannot be null";
            String reply = cmd.execute(taskList, ui);
            if (cmd.isExit()) {
                return ui.endConvo();
            } else {
                return reply;
            }
        } catch (NoDescriptionException | NoCommandException | IllegalArgumentException e) {
            ui.speak(e.getMessage());
            return e.getMessage();
        }
    }
}
