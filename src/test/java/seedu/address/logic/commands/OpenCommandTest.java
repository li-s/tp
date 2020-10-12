package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTags.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;
import seedu.address.testutil.TagBuilder;

class OpenCommandTest {

    private Model typicalModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    // Change to true to enable testing of opening files
    private final boolean isTestingOpenFile = false;

    @Test
    public void equals() {
        TagName tagName1 = new TagName("cs2103");
        TagName tagName2 = new TagName("cs2103T");

        OpenCommand openCommand1 = new OpenCommand(tagName1);
        OpenCommand openCommand2 = new OpenCommand(tagName2);

        // same object -> returns true
        assertTrue(openCommand1.equals(openCommand1));

        // same values -> returns true
        OpenCommand openCommand1Copy = new OpenCommand(tagName1);
        assertTrue(openCommand1.equals(openCommand1Copy));

        // different types -> returns false
        assertFalse(openCommand1.equals(1));

        // null -> returns false
        assertFalse(openCommand1.equals(null));

        // different tagName -> returns false
        assertFalse(openCommand1.equals(openCommand2));
    }

    @Test
    public void execute_tagNotInModel_throwCommandException() {
        TagName tagName = new TagName("haHaImWrong");
        OpenCommand openCommand = new OpenCommand(tagName);
        assertThrows(CommandException.class, String.format(OpenCommand.MESSAGE_TAG_NOT_FOUND,
                tagName.tagName), () -> openCommand.execute(typicalModel));
    }

    @Test
    public void execute_tagNameInModel_success() throws Exception {
        assumeTrue(isTestingOpenFile);
        Tag correctTag = new TagBuilder().build();
        OpenCommand openCommand = new OpenCommand(correctTag.getTagName());
        Model model = new ModelManager();
        model.addTag(correctTag);

        String expectedMessage = String.format(OpenCommand.MESSAGE_SUCCESS, correctTag);

        assertCommandSuccess(openCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_tagNameInModelFileNotFound_throwCommandException() {
        assumeTrue(isTestingOpenFile);
        Tag correctTag = new TagBuilder().withTagName("test")
                .withFileAddress(".\\src\\test\\java\\seedu\\address\\testutil\\testFileNotHere.bat").build();
        OpenCommand openCommand = new OpenCommand(correctTag.getTagName());
        Model model = new ModelManager();
        model.addTag(correctTag);

        assertThrows(CommandException.class, String.format(OpenCommand.MESSAGE_FILE_NOT_FOUND,
                correctTag.getFileAddress().value), () -> openCommand.execute(model));
    }

}
