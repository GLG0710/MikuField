package model;

import model.utilities.BoardSize;
import model.utilities.Difficulty;
import model.utilities.Position;

import java.util.Set;

public class Game {
    private final Board board;
    private final Config config;

    public Game(Config config) {
        this.config = config;
        this.board = new Board(config.getBoardSize(),config.getDifficulty());
    }

    public void startNewGame(int boardSize, double difficulty) {
        config.setBoardSize(boardSize);
        config.setDifficulty(difficulty);

        board.reset(boardSize,difficulty);
    }

    // Board Control
    public Board getBoard() {
        return board;
    }

    public boolean isVictory() {
        return board.isVictory();
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public Set<Cell> revealCell(Position position) {
        return board.revealCell(position);
    }

    public Set<Cell> revealAllMines() {
        return board.revealAllMines();
    }

    public void toggleFlag(Cell cell) {
        board.toggleFlag(cell);
    }

    public void setDifficulty(Difficulty difficulty) {
        config.setDifficulty(difficulty.proportion);
    }

    public double getDifficulty() { return config.getDifficulty(); }

    public void setBoardSize(BoardSize size) {
        config.setBoardSize(size.size);
    }

    public int getBoardSize() { return config.getBoardSize(); }
}