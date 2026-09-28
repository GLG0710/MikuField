package model;

import model.utilities.BoardSize;
import model.utilities.Difficulty;

public class Config {
    private int boardSize = BoardSize.MEDIUM.size;
    private double difficulty = Difficulty.MEDIUM.proportion;

    public Config() {
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