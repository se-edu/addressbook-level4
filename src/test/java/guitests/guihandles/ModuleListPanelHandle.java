package guitests.guihandles;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.module.Module;


/**
 * Provides a handle for {@code ModuleListPanel} containing the list of {@code ModuleCard}.
 */
public class ModuleListPanelHandle extends NodeHandle<ListView<Module>> {
    public static final String MODULE_LIST_VIEW_ID = "#moduleListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Module> lastRememberedSelectedModuleCard;

    public ModuleListPanelHandle(ListView<Module> moduleListPanelNode) {
        super(moduleListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ModuleCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ModuleCardHandle getHandleToSelectedCard() {
        List<Module> selectedModuleList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedModuleList.size() != 1) {
            throw new AssertionError("Module list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(ModuleCardHandle::new)
                .filter(handle -> handle.equals(selectedModuleList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Module> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code module}.
     */
    public void navigateToCard(Module module) {
        if (!getRootNode().getItems().contains(module)) {
            throw new IllegalArgumentException("Module does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(module);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code ModuleCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the module card handle of a module associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ModuleCardHandle getModuleCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ModuleCardHandle::new)
                .filter(handle -> handle.equals(getModule(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Module getModule(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code ModuleCard} in the list.
     */
    public void rememberSelectedModuleCard() {
        List<Module> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedModuleCard = Optional.empty();
        } else {
            lastRememberedSelectedModuleCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ModuleCard} is different from the value remembered by the most recent
     * {@code rememberSelectedModuleCard()} call.
     */
    public boolean isSelectedModuleCardChanged() {
        List<Module> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedModuleCard.isPresent();
        } else {
            return !lastRememberedSelectedModuleCard.isPresent()
                    || !lastRememberedSelectedModuleCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
