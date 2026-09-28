package view;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Board;
import model.Cell;
import model.utilities.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BoardView extends StackPane {

    private static final double MAX_SIZE = 440;
    private static final double PADDING = 20;
    private static final double GAP = 10;

    private final GridPane grid = new GridPane();
    private final Map<Position, CellView> cellViews = new HashMap<>();

    public BoardView(Board board, Consumer<Cell> leftClick, Consumer<Cell> rightClick) {

        setMaxSize(MAX_SIZE, MAX_SIZE);
        setPrefSize(MAX_SIZE, MAX_SIZE);

        setPadding(new Insets(PADDING));

        grid.setHgap(GAP);
        grid.setVgap(GAP);

        setStyle("-fx-background-color: #86CECB;");

        Rectangle border = new Rectangle(MAX_SIZE, MAX_SIZE);
        border.setFill(null);
        border.setStroke(Color.web("#373B3E"));
        border.setStrokeWidth(5);
        border.setManaged(false);
        border.setMouseTransparent(true);

        getChildren().add(border);
        getChildren().add(grid);

        populate(board, leftClick, rightClick);
    }

    private double calculateCellSize(int boardSize) {
        double availableSize = MAX_SIZE - (2 * PADDING);
        double totalGap = (boardSize - 1) * GAP;
        return (availableSize - totalGap) / boardSize;
    }

    private void populate(Board board, Consumer<Cell> leftClick, Consumer<Cell> rightClick) {
        int boardSize = board.getBoardSize();
        double cellSize = calculateCellSize(boardSize);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Cell cell = board.getCell(new Position(row, col));

                CellView cellView = new CellView(cell, cellSize, leftClick, rightClick);
                cellViews.put(cell.getPosition(), cellView);

                grid.add(cellView, col, row);
            }
        }
    }

    public void refresh(Cell cell) {
        CellView cellView = cellViews.get(cell.getPosition());

        if (cellView != null) {
            cellView.refresh();
        }
    }
}