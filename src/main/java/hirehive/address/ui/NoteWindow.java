package hirehive.address.ui;

import java.util.logging.Logger;

import hirehive.address.commons.core.LogsCenter;
import hirehive.address.logic.Logic;
import hirehive.address.model.person.Note;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the note page.
 */
public class NoteWindow extends UiPart<Stage> {

    public static final String EMPTY_NOTE = "No note currently.";

    private static final Logger logger = LogsCenter.getLogger(NoteWindow.class);
    private static final String FXML = "NoteWindow.fxml";

    @FXML
    private Label note;

    /**
     * Creates a new NoteWindow.
     * @param root Stage to use as the root of the NoteWindow.
     */
    public NoteWindow(Stage root) {
        super(FXML, root);
        note.setText(EMPTY_NOTE);
    }

    /**
     * Creates a new NoteWindow.
     */
    public NoteWindow() {
        this(new Stage());
    }

    /**
     * Sets the note in the NoteWindow.
     * @param logic from the MainWindow.
     */
    public void setNote(Logic logic) {
        // How to update this window continuously
        // singletonObservableList? use model to update
        Note newNote = logic.getPersonNote();
        note.setText(newNote.value);
    }

    /**
     * Shows the NoteWindow.
     */
    public void show() {
        logger.fine("Showing note page.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the note window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the note window.
     */
    public void hide() {
        note.setText(EMPTY_NOTE);
        getRoot().hide();
    }

    /**
     * Focuses on the note window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
