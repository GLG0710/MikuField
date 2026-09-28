package model;

public class Game {
    private Board board;
    private Config config;

    public Game(Config config) {
        this.config = config;
        this.board = new Board(config);
    }

    public void startNewGame(Config newConfig) {
        config = newConfig;
        board = new Board(config);
    }

    public Board getBoard() {
        return board;
    }
}