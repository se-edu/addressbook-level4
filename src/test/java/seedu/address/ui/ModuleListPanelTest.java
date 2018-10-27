package seedu.address.ui;

import guitests.guihandles.ModuleCardHandle;
import guitests.guihandles.ModuleListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.module.Module;
import seedu.address.storage.XmlSerializableAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalModules.getTypicalModules;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

public class ModuleListPanelTest extends GuiUnitTest {
    private static final ObservableList<Module> TYPICAL_MODULES =
            FXCollections.observableList(getTypicalModules());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_MODULE);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private ModuleListPanelHandle moduleListPanelHandle;

    @Test
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

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_MODULES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ModuleCardHandle expectedModule = moduleListPanelHandle.getModuleCardHandle(INDEX_SECOND_MODULE.getZeroBased());
        ModuleCardHandle selectedModule = moduleListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedModule, selectedModule);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Module> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of module cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code moduleCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Module> createBackingList(int moduleCount) throws Exception {
        Path xmlFile = createXmlFileWithModules(moduleCount);
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getModuleList());
    }

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithModules(int moduleCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<transcript>\n");
        for (int i = 0; i < moduleCount; i++) {
            builder.append("<modules>\n");
            builder.append("<code>").append(i).append("a</code>\n");
            builder.append("<semester>000</semester>\n");
            builder.append("<year>a@aa</year>\n");
            builder.append("<grade>a</grade>\n");
            builder.append("</modules>\n");
        }
        builder.append("</transcript>\n");

        Path manyModulesFile = Paths.get(TEST_DATA_FOLDER + "manyModules.xml");
        FileUtil.createFile(manyModulesFile);
        FileUtil.writeToFile(manyModulesFile, builder.toString());
        manyModulesFile.toFile().deleteOnExit();
        return manyModulesFile;
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
