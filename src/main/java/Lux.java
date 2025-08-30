import lux.parser.Command;
import lux.parser.CommandParser;
import lux.repo.TaskList;
import lux.storage.SaveFileManager;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

import java.io.IOException;

public class Lux {
    private TaskList taskList = new TaskList();
    private CommandParser cp;
    private Ui ui;

    public Lux() {
        this.cp = new CommandParser();
        this.ui = new Ui();
    }

    public void run() throws IOException {
        ui.greet();
        SaveFileManager.loadTask(this.taskList);
        handleConvo(ui, cp);
    }

    public static void main(String[] args) {
        try {
            new Lux().run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleConvo(Ui ui, CommandParser cp) {

        while (true) {
            try {
                String userInputInfo = ui.readline();
                Command cmd = cp.parse(userInputInfo);
                cmd.execute(taskList, ui);
                if (cmd.isExit()) {
                    break;
                }

            } catch (NoDescriptionException | NoCommandException e) {
                ui.speak(e.getMessage());
            }
        }
    }
}
