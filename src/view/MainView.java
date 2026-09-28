package view;

import model.Board;
import javafx.scene.layout.Pane;
import model.Cell;
import java.util.function.Consumer;

public class MainView extends Pane {

    private static final double WIDTH = 480;
    private static final double HEIGHT = 580;

    private final BoardView boardView;

    public MainView(Board board, Consumer<Cell> leftClick, Consumer<Cell> rightClick) {

        setMaxSize(WIDTH, HEIGHT);
        setPrefSize(WIDTH, HEIGHT);
        setMinSize(WIDTH, HEIGHT);

        boardView = new BoardView(board, leftClick, rightClick);

        getChildren().add(boardView);

        createGameView();
    }

    private void createGameView() {

        setStyle(
                "-fx-background-color: #137A7F;"
        );

        boardView.setLayoutX(20);
        boardView.setLayoutY(110);
    }

    public BoardView getBoardView() {
        return boardView;
    }
}