package view;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import model.Cell;

import java.util.Objects;
import java.util.function.Consumer;

public class CellView extends Button {
    private final Cell cell;

    // Background Colors
    private static final String COLOR_DEFAULT = "#BEC8D1E6";
    private static final String COLOR_BORDER = "#373B3EE6";
    private static final String COLOR_MINE = "#E12885E6";
    private static final String COLOR_FLAG = "#137A7FE6";
    private static final String COLOR_ZERO = "#373B3EE6";
    private static final String COLOR_NUMBER_BG = "#137A7FE6";

    // Text Colors
    private static final String[] TEXT_COLORS = {
            "#FFFFFF", // 1
            "#FFD700", // 2
            "#FF8C00", // 3
            "#00CED1", // 4
            "#FF1493", // 5
            "#7B68EE", // 6
            "#32CD32", // 7
            "#FF6347"  // 8
    };

    public CellView(Cell cell, double cellSize, Consumer<Cell> leftClick, Consumer<Cell> rightClick) {
        this.cell = cell;

        setPrefSize(cellSize, cellSize);
        setMinSize(cellSize, cellSize);
        setMaxSize(cellSize, cellSize);

        setStyle(createHeartStyle());
        applyDefaultStyle();

        setCursor(Cursor.HAND);

        setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.PRIMARY) {
                leftClick.accept(cell);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rightClick.accept(cell);
            }

        });

        refresh();
    }

    // STYLE
    private String createHeartStyle() {
        String heartPath =
                "M23.6,0c-3.4,0-6.3,2.7-7.6,5.6" +
                        "C14.7,2.7,11.8,0,8.4,0" +
                        "C3.8,0,0,3.8,0,8.4" +
                        "c0,9.4,9.5,11.9,16,21.2" +
                        "c6.1-9.3,16-12.1,16-21.2" +
                        "C32,3.8,28.2,0,23.6,0z";

        return "-fx-shape: \"" + heartPath + "\";" +
                "-fx-background-radius: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-width: 2;" +
                "-fx-border-color: " + COLOR_BORDER + ";" +
                "-fx-background-insets: 0;";
    }

    private void applyDefaultStyle() {
        setStyle(
                createHeartStyle() +
                        "-fx-background-color: " + COLOR_DEFAULT + ";" +
                        "-fx-text-fill: " + COLOR_BORDER + ";"
        );

        setText("");
        setGraphic(null);
    }

    private void applyMineStyle() {
        setStyle(
                createHeartStyle() +
                        "-fx-background-color: " + COLOR_MINE + ";" +
                        "-fx-text-fill: white;"
        );


        Image image = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream("/assets/imgs/bomb.png"),
                        "Imagem /assets/imgs/bomb.png não encontrada!"
                ),
                256,
                0,
                true,
                true
        );

        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        imageView.fitWidthProperty()
                .bind(widthProperty().multiply(0.65));

        setGraphic(imageView);
        setFont(Font.font(30));

    }

    private void applyFlagStyle() {
        setStyle(
                createHeartStyle() +
                        "-fx-background-color: " + COLOR_FLAG + ";" +
                        "-fx-text-fill: white;"
        );


        Image image = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream(
                                "/assets/imgs/flag.png"
                        ),
                        "Imagem /assets/imgs/flag.png não encontrada!"
                ),
                256,
                0,
                true,
                true
        );

        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        imageView.fitWidthProperty()
                .bind(widthProperty().multiply(0.65));

        setGraphic(imageView);
        setFont(Font.font(30));

    }

    private void applyZeroStyle() {
        setStyle(
                createHeartStyle() +
                        "-fx-background-color: " + COLOR_ZERO + ";" +
                        "-fx-text-fill: white;"
        );

        setText("");
        setGraphic(null);
    }

    private void applyNumberStyle(int value) {
        setStyle(
                createHeartStyle() +
                        "-fx-background-color: " + COLOR_NUMBER_BG + ";" +
                        "-fx-text-fill: " + TEXT_COLORS[value - 1] + ";"
        );

        setText(String.valueOf(value));
        setFont(Font.font(28));

        setGraphic(null);
    }

    // EVENTS
    public void refresh() {
        if (cell.isFlagged()) {
            applyFlagStyle();
            return;
        }

        if (!cell.isRevealed()) {
            applyDefaultStyle();
            return;
        }

        if (cell.getValue() == -1) {
            applyMineStyle();
            return;
        }

        if (cell.getValue() == 0) {
            applyZeroStyle();
            return;
        }

        applyNumberStyle(cell.getValue());
    }
}