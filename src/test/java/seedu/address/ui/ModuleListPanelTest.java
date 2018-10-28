package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalModules.getTypicalModules;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysModule;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

//import java.nio.file.Path;
//import java.nio.file.Paths;

//import org.junit.Test;

import guitests.guihandles.ModuleCardHandle;
import guitests.guihandles.ModuleListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.module.Module;

public class ModuleListPanelTest extends GuiUnitTest {
    private static final ObservableList<Module> TYPICAL_MODULES =
            FXCollections.observableList(getTypicalModules());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_MODULE);

    //private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    //private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private ModuleListPanelHandle moduleListPanelHandle;

    /**
     * TODO: REMOVE
     */
    //@Test
    public void display() {
        initUi(TYPICAL_MODULES);

        for (int i = 0; i < TYPICAL_MODULES.size(); i++) {
            moduleListPanelHandle.navigateToCard(TYPICAL_MODULES.get(i));
            Module expectedModule = TYPICAL_MODULES.get(i);
            ModuleCardHandle actualCard = moduleListPanelHandle.getModuleCardHandle(i);

            assertCardDisplaysModule(expectedModule, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * TODO: REMOVE
     */
    //@Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_MODULES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ModuleCardHandle expectedModule = moduleListPanelHandle.getModuleCardHandle(INDEX_SECOND_MODULE.getZeroBased());
        ModuleCardHandle selectedModule = moduleListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedModule, selectedModule);
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Module> backingList) {
        ModuleListPanel moduleListPanel = new ModuleListPanel(backingList);
        uiPartRule.setUiPart(moduleListPanel);

        moduleListPanelHandle = new ModuleListPanelHandle(getChildNode(moduleListPanel.getRoot(),
                ModuleListPanelHandle.MODULE_LIST_VIEW_ID));
    }
}
