package lux.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
        private boolean isEnded = false;

        public StubUi() {
            super();
        }

        @Override public void speak(String s) {
            results.add(s);
        }
        @Override public String endConvo() {
            isEnded = true;
            return "";
        }
    }

    static class StubTaskList extends TaskList {
        private int listCalls = 0;
        private int addCalls = 0;
        private int markCalls = 0;
        private int unmarkCalls = 0;
        private int deleteCalls = 0;
        private int findCalls = 0;
        private int lastIndex = -1;
        private int massOpsCalls = 0;
        private MassTaskAction lastAction = null;
        private int[] lastIndices = null;
        private String lastQuery = null;
        private Task lastAdded = null;

        public StubTaskList() {
            super();
        }

        @Override
        public String showList(Ui ui) {
            listCalls++;
            return "";
        }
        @Override
        public String addListItem(Task itemToAdd, Ui ui) {
            addCalls++;
            lastAdded = itemToAdd;
            return "";
        }
        @Override
        public String markTask(int taskNumber, Ui ui) {
            markCalls++;
            lastIndex = taskNumber;
            return "";
        }
        @Override
        public String unmarkTask(int taskNumber, Ui ui) {
            unmarkCalls++;
            lastIndex = taskNumber;
            return "";
        }
        @Override
        public String deleteTask(int taskNumber, Ui ui) {
            deleteCalls++;
            lastIndex = taskNumber;
            return "";
        }
        @Override
        public String findTask(String query, Ui ui) {
            findCalls++;
            lastQuery = query;
            return "Found tasks matching : " + query;
        }

        public String getLastQuery() {
            return lastQuery;
        }

        public int getFindCalls() {
            return findCalls;
        }

        @Override
        public String massOrSingleOps(int[] tasksToAct, MassTaskAction mta, Ui ui) {
            massOpsCalls++;
            lastAction = mta;
            lastIndices = tasksToAct;
            return "Mass operation executed";
        }

        public int getMassOpsCalls() {
            return massOpsCalls;
        }

        public MassTaskAction getLastAction() {
            return lastAction;
        }

        public int[] getLastIndices() {
            return lastIndices;
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
    public void parse_byeCommand_success() {
        Command cmd = parser.parse("bye");
        assertTrue(cmd.isExit(), "bye should return an exit command");
        // Don't cmd.execute here as it will change the savefile
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("huh what");
        assertThrows(NoCommandException.class, () -> cmd.execute(tasks, ui));
    }

    @Test
    public void parse_emptyInput_throwsUnknownCommand() {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("");
        assertThrows(NoCommandException.class, () -> cmd.execute(tasks, ui));
    }

    @Test
    public void parse_findCommand_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("find report");
        String result = cmd.execute(tasks, ui);

        assertEquals(1, tasks.getFindCalls(), "Should call findTask once");
        assertEquals("report", tasks.getLastQuery(), "Query should match input");
        assertTrue(result.contains("report"), "Response should contain the search term");
    }

    @Test
    public void parse_findCommand_throwsException() {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("find   ");
        assertThrows(NoDescriptionException.class, () -> cmd.execute(tasks, ui));
        assertEquals(0, tasks.getFindCalls(), "Should not call findTask on blank input");
    }

    @Test
    public void parse_findCommandCaseInsensitive_success() throws Exception {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("find RePoRt");
        String result = cmd.execute(tasks, ui);

        assertEquals("report", tasks.getLastQuery().toLowerCase());
    }

    @Test
    public void parse_markCommandMassOps_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("mark 1, 3, 5");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.getMassOpsCalls());
        assertEquals(TaskList.MassTaskAction.MARK, tasks.getLastAction());
        assertArrayEquals(new int[]{1, 3, 5}, tasks.getLastIndices());
    }

    @Test
    public void parse_unmarkCommandMassOps_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("unmark 2, 4");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.getMassOpsCalls());
        assertEquals(TaskList.MassTaskAction.UNMARK, tasks.getLastAction());
        assertArrayEquals(new int[]{2, 4}, tasks.getLastIndices());
    }

    @Test
    public void parse_deleteCommandMassOps_success() throws NoCommandException, NoDescriptionException {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("delete 1, 2, 3");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.getMassOpsCalls());
        assertEquals(TaskList.MassTaskAction.DELETE, tasks.getLastAction());
        assertArrayEquals(new int[]{1, 2, 3}, tasks.getLastIndices());
    }

    @Test
    public void parse_massOpsWithExtraSpaces_success() throws Exception {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("mark 1,   2,    3");
        cmd.execute(tasks, ui);

        assertArrayEquals(new int[]{1, 2, 3}, tasks.getLastIndices());
    }

    @Test
    public void parse_massOpsWithNoSpace_success() throws Exception {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("mark 1,2,3");
        cmd.execute(tasks, ui);

        assertArrayEquals(new int[]{1, 2, 3}, tasks.getLastIndices());
    }

    @Test
    public void parse_commandWithExtraSpacesAndCase_success() throws Exception {
        StubTaskList tasks = new StubTaskList();
        StubUi ui = new StubUi();

        Command cmd = parser.parse("   ToDo   Buy milk   ");
        cmd.execute(tasks, ui);

        assertEquals(1, tasks.addCalls);
        assertNotNull(tasks.lastAdded);
    }



}
