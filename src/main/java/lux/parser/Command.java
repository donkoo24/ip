package lux.parser;

import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

public interface Command {
    void execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException;
    default boolean isExit() {
        return false;
    }
}
