package controller;

import javafx.scene.media.AudioClip;
import model.Board;
import model.Cell;
import model.Config;
import model.Game;
import model.utilities.Position;
import view.BoardView;
import view.MainView;

import java.net.URL;
import java.util.Set;

public class GameController {
    private final Game game;
    private final MainView mainView;

    public GameController(Game game) {
        this.game = game;

        this.mainView = new MainView(
                game.getBoard(),
                this::handleLeftClick,
                this::handleRightClick
        );
    }

    public MainView getView() {
        return mainView;
    }

    // Events
    private void handleLeftClick(Cell cell) {
        Board board = game.getBoard();

        if (board.isGameOver()) {
            return;
        }

        Set<Cell> revealed = board.revealCell(cell.getPosition());

        refreshCells(revealed);

        if (board.isGameOver()) {
            revealAllMines();
            playSound("/assets/sounds/boom.mp3");
        }
    }

    private void handleRightClick(Cell cell) {
        if (game.getBoard().isGameOver()) {
            return;
        }

        cell.toggleFlag();
        mainView.getBoardView().refresh(cell);
    }

    public void playSound(String resourcePath) {
        try {
            URL resource = getClass().getResource(resourcePath);

            if (resource == null) {
                System.err.println("Sound file not found: " + resourcePath);
                return;
            }

            AudioClip clip = new AudioClip(resource.toString());
            clip.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update View
    private void refreshCells(Set<Cell> cells) {
        BoardView boardView = mainView.getBoardView();
        for (Cell c : cells) {
            boardView.refresh(c);   // BoardView acha o CellView pelo Position
        }
    }

    private void revealAllMines() {
        Board board = game.getBoard();
        BoardView boardView = mainView.getBoardView();
        int size = board.getBoardSize();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Cell cell = board.getCell(new Position(r, c));
                if (cell.getValue() == -1 && !cell.isRevealed()) {
                    cell.setRevealed();
                    boardView.refresh(cell);
                }
            }
        }
    }

    // New game, create!!
    public void startNewGame(Config newConfig) {
        game.startNewGame(newConfig);

        // Como o Board é recriado, as CellViews antigas apontam para Cells mortas.
        // É mais simples recriar a MainView inteira:
        MainView fresh = new MainView(
                game.getBoard(),
                this::handleLeftClick,
                this::handleRightClick
        );

        mainView.getChildren().setAll(fresh.getChildren());
        // Ou melhor ainda: recrie a cena no App.
    }
}