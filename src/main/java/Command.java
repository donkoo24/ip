import UI.Ui;

public interface Command {
    void execute(TaskList tasks, Ui ui) throws NoDescriptionException, NoCommandException;
    default boolean isExit() {
        return false;
    }
}
