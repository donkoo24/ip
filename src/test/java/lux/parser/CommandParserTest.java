package lux.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lux.domain.Task;
import lux.repo.TaskList;
import lux.ui.Ui;
import lux.util.NoCommandException;
import lux.util.NoDescriptionException;

public class CommandParserTest {
    private final CommandParser parser = new CommandParser();

    static class StubUi extends Ui {
        final List<String> results = new ArrayList<>();
        boolean ended = false;

        public StubUi() {
            super();
        }

        @Override public void speak(String s) {
            results.add(s);
        }
        @Override public void endConvo() {
            ended = true;
        }
    }

    static class StubTaskList extends TaskList {
        int listCalls = 0;
        int addCalls = 0;
        int markCalls = 0;
        int unmarkCalls = 0;
        int deleteCalls = 0;
        int lastIndex = -1;
        Task lastAdded = null;

        public StubTaskList() {
            super();
        }

        @Override public void showList(Ui ui) {
            listCalls++;
        }
        @Override public void addListItem(Task itemToAdd, Ui ui) {
            addCalls++; lastAdded = itemToAdd;
        }
        @Override public void markTask(int taskNumber, Ui ui) {
            markCalls++; lastIndex = taskNumber;
        }
        @Override public void unmarkTask(int taskNumber, Ui ui) {
            unmarkCalls++; lastIndex = taskNumber;
        }
        @Override public void deleteTask(int taskNumber, Ui ui) {
            deleteCalls++; lastIndex = taskNumber;
        }
    }

    @Test
    public void parse_listCommand_success() throws NoDescriptionException, NoCommandException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("list");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.listCalls);
    }

    @Test
    public void parse_toDoCommand_success() throws NoDescriptionException, NoCommandException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("todo sendhelp");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.addCalls);
        assertNotNull(tasks.lastAdded, "Parser should pass a Task to addListItem");
    }

    @Test
    public void parse_toDoCommand_throwsException() {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("todo   ");
        assertThrows(NoDescriptionException.class, () -> cmd.execute(tasks, ui));
        assertEquals(0, tasks.addCalls);
    }

    @Test
    public void parse_deadlineCommand_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("deadline submit report /by 2019-10-15");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.addCalls);
        assertNotNull(tasks.lastAdded);
    }

    @Test
    public void parse_deadlineCommand_throwsException() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("deadline something /by   ");
        assertThrows(NoDescriptionException.class, () -> cmd.execute(tasks, ui));
        assertEquals(0, tasks.addCalls);
    }

    @Test
    public void parse_eventCommand_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("event project meeting /from 2/12/2019 /to Dec 3 2019");
        cmd.execute(tasks, ui);


        assertEquals(1, tasks.addCalls);
        assertNotNull(tasks.lastAdded);
    }

    @Test
    public void parse_eventCommand_throwsException() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command missingTo = parser.parse("event meet boss /from 2/12/2019 /to  ");
        assertThrows(NoDescriptionException.class, () -> missingTo.execute(tasks, ui));

        Command missingFrom = parser.parse("event meet boss /from   /to Dec 3 2019");
        assertThrows(NoDescriptionException.class, () -> missingFrom.execute(tasks, ui));

        assertEquals(0, tasks.addCalls);
    }

    @Test
    public void parse_markCommand_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("mark 2");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.markCalls);
        assertEquals(2, tasks.lastIndex);
    }

    @Test
    public void parse_unmarkCommand_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("unmark 1");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.unmarkCalls);
        assertEquals(1, tasks.lastIndex);
    }

    @Test
    public void parse_deleteCommand_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("delete 3");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.deleteCalls);
        assertEquals(3, tasks.lastIndex);
    }

    @Test
    public void parse_byeCommand_success() {
        Command cmd = parser.parse("bye");
        assertTrue(cmd.isExit(), "bye should return an exit command");
        // Don't cmd.execute here as it will change the savefile
    }

    @Test
    public void parse_unknownCommand_throwsException() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("huh what");
        assertThrows(NoCommandException.class, () -> cmd.execute(tasks, ui));
    }
}
