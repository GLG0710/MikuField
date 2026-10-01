package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.utilities.BoardSize;
import model.utilities.Difficulty;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigView extends VBox {
    private static final int VIEW_SIZE = 400;
    private static final int PADDING = 20;
    private static final int GAP = 30;
    private static final int OPTION_GAP = 20;
    private static final int TITLE_WIDTH = 180;

    private static final String VIEW_STYLE =
            "-fx-background-color: #1f8287;" +
                    "-fx-border-color: #3f4a4c;" +
                    "-fx-border-width: 5px;";

    private static final String TEXT_STYLE =
            "-fx-font-family: 'Monospaced';" +
                    "-fx-font-size: 22px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #eeeeee;";

    private static final String SELECTED_STYLE =
            "-fx-font-family: 'Monospaced';" +
                    "-fx-font-size: 22px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #373B3E;";

    private final Map<Difficulty, Label> difficultyLabels = new EnumMap<>(Difficulty.class);
    private final Map<BoardSize, Label> boardSizeLabels = new EnumMap<>(BoardSize.class);

    public ConfigView(Consumer<Difficulty> difficultyOnClick, Consumer<BoardSize> boardSizeOnClick) {
        setPrefSize(VIEW_SIZE, VIEW_SIZE);
        setMinSize(VIEW_SIZE, VIEW_SIZE);
        setMaxSize(VIEW_SIZE, VIEW_SIZE);

        setPadding(new Insets(PADDING));
        setSpacing(GAP);
        setStyle(VIEW_STYLE);

        getChildren().addAll(
                createRow("Difficulty:", createOptions(Difficulty.values(), difficultyLabels, difficultyOnClick)),
                createRow("Size:", createOptions(BoardSize.values(), boardSizeLabels, boardSizeOnClick))
        );
    }

    // BUILD
    private HBox createRow(String title, VBox options) {
        Label label = new Label(title);
        label.setStyle(TEXT_STYLE);
        label.setPrefWidth(TITLE_WIDTH);
        label.setMinWidth(TITLE_WIDTH);

        HBox row = new HBox(label, options);
        row.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(options, Priority.ALWAYS);
        VBox.setVgrow(row, Priority.ALWAYS);
        return row;
    }

    private <E extends Enum<E>> VBox createOptions(E[] values, Map<E, Label> labels, Consumer<E> onClick) {
        VBox box = new VBox(OPTION_GAP);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(Double.MAX_VALUE);

        for (E value : values) {
            Label label = new Label(value.name());
            label.setStyle(TEXT_STYLE);
            label.setCursor(Cursor.HAND);
            label.setOnMouseClicked(event -> onClick.accept(value));

            labels.put(value, label);
            box.getChildren().add(label);
        }
        return box;
    }

    // EVENTS
    public void selectDifficulty(Difficulty selected) { highlight(difficultyLabels, selected); }

    public void selectBoardSize(BoardSize selected) { highlight(boardSizeLabels, selected); }

    private <E extends Enum<E>> void highlight(Map<E, Label> labels, E selected) {
        labels.forEach((value, label) ->
                label.setStyle(value == selected ? SELECTED_STYLE : TEXT_STYLE));
    }
}