package model;

import model.utilities.Position;

public class Cell {
    private int value = 0; // -1 == Bomb, 0 = no bomb around, 1-8 number of bombs around
    private boolean revealed = false;
    private boolean flagged = false;

    private final Position position;

    public Cell(Position position) {
        this.position = position;
    }

    // Getters and Setters
    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed() {
        this.revealed = true; // set only for true, never for false
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged() { this.flagged = !flagged; }

    public boolean isMine() { return value == -1; }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }
}