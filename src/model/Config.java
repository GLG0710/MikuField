package model;

import model.utilities.BoardSize;
import model.utilities.Difficulty;

import java.util.Optional;

public class Config {
    private int boardSize;
    private double difficulty;

    public Config(BoardSize boardSize, Difficulty difficulty) {
        this.boardSize = (boardSize != null) ? boardSize.size : BoardSize.MEDIUM.size;
        this.difficulty = (difficulty != null) ? difficulty.proportion : Difficulty.MEDIUM.proportion;
    }

    public Config() {
        this(BoardSize.MEDIUM, Difficulty.MEDIUM);
    }

    public int getBoardSize() {
        return boardSize;
    }
    public double getDifficulty() {
        return difficulty;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }
}