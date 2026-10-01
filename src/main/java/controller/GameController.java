package controller;

import javafx.scene.media.AudioClip;
import model.Cell;
import model.Game;
import model.utilities.BoardSize;
import model.utilities.Difficulty;
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
                this::cellLeftClick,
                this::cellRightClick,
                this::selectDifficulty,
                this::selectBoardSize,
                this::startNewGame
        );
    }


    private void selectBoardSize(BoardSize boardSize) {
        mainView.selectBoardSize(boardSize);
        game.setBoardSize(boardSize);
    }

    private void selectDifficulty(Difficulty difficulty) {
        mainView.selectDifficulty(difficulty);
        game.setDifficulty(difficulty);
    }

    public MainView getView() {
        return mainView;
    }

    // Events
    private void cellLeftClick(Cell cell) {
        if (game.isGameOver() || game.isVictory()) {
            return;
        }

        Set<Cell> revealed = game.revealCell(cell.getPosition());
        refreshCells(revealed);

        if (game.isGameOver()) {
            revealAllMines();
            playSound("/assets/sounds/mikudawo.wav");
        }
    }

    private void cellRightClick(Cell cell) {
        if (game.isGameOver() || game.isVictory()) {
            return;
        }

        game.toggleFlag(cell);
        refreshCells(Set.of(cell));

        if (game.isVictory()) {
            IO.println("VICTORY!");
        }
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
    private void refreshCells(Set<Cell> cells) { mainView.refreshCells(cells); }

    private void revealAllMines() {
        Set<Cell> mines = game.revealAllMines();
        mainView.refreshCells(mines);
    }

    // NewGame
    private void startNewGame() {
        game.startNewGame(game.getBoardSize(), game.getDifficulty());

        mainView.startNewBoard(game.getBoard());
    }
}