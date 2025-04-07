package hirehive.address.ui;

import java.util.logging.Logger;

import hirehive.address.commons.core.LogsCenter;
import hirehive.address.logic.Logic;
import hirehive.address.model.person.Note;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

/**
 * Controller for the note page.
 */
public class NoteWindow extends UiPart<Stage> {

    public static final String EMPTY_NOTE = "Currently empty...";

    private static final Logger logger = LogsCenter.getLogger(NoteWindow.class);
    private static final String FXML = "NoteWindow.fxml";

    @FXML
    private TextArea note;

    @FXML
    private Button save;

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
        Note newNote = logic.getPersonNote();
        if (newNote.isEmpty()) {
            note.setText(EMPTY_NOTE);
            note.setStyle("-fx-text-fill: yellow;");
        } else {
            note.setText(newNote.value);
            note.setStyle("-fx-text-fill: white;");
        }
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

    /**
     * Copies the contents of the note to the clipboard.
     */
    @FXML
    public void copyNote() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent noteText = new ClipboardContent();
        if (note.getStyle().equals("-fx-text-fill: white;")) {
            noteText.putString(note.getText());
        } else {
            noteText.putString("");
        }
        clipboard.setContent(noteText);
    }

}
