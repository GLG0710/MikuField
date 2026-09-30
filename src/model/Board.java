package model;

import model.utilities.Position;

import java.util.*;

public class Board {
    private int boardSize;
    private int numberOfMines;

    private Cell[][] boardMatrix;
    private final Set<Position> minesLocation = new HashSet<>();

    // Determinate the victory conditional
    private int cellsRemaining;
    private int minesRemaining;

    private boolean gameOver = false;
    private boolean victory = false;

    public Board(int boardSize, double difficulty) {
        reset(boardSize, difficulty);
    }

    // Setup
    public void reset(int boardSize, double difficulty) {
        resetStateAndCounters( boardSize,  difficulty);
        create();
    }

    private void resetStateAndCounters(int boardSize, double difficulty) {
        this.boardSize = boardSize;
        int allCells = boardSize * boardSize;
        numberOfMines = (int) (allCells * difficulty);

        minesLocation.clear();
        gameOver = false;
        victory = false;

        cellsRemaining = allCells - numberOfMines;
        minesRemaining = numberOfMines;
    }

    // Board create
    private void create() {
        boardMatrix = new Cell[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardMatrix[i][j] = new Cell(new Position(i,j));
            }
        }

        setMinesOnBoard();
    }

    private void setMinesOnBoard() {
        Random random = new Random();

        for (int i = 0; i < numberOfMines; i++) {
            int mineX;
            int mineY;

            do {
                mineX = random.nextInt(boardSize);
                mineY = random.nextInt(boardSize);
            } while (minesLocation.contains(new Position(mineX,mineY)));

            Position local = new Position(mineX, mineY);

            boardMatrix[mineX][mineY].setValue(-1);

            minesLocation.add(local);
        }

        setElementsOnBoard();
    }

    private void setElementsOnBoard() {
        for(Position mine : minesLocation) {
            // For each mine, increases the values of around cell
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {

                    // Skip the element itself
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    int neighborRow = mine.row() + i;
                    int neighborCol = mine.column() + j;

                    // Ensure the neighbor index is inside the matrix
                    if (neighborRow < 0 || neighborRow >= boardSize
                            || neighborCol < 0 || neighborCol >= boardSize) {
                        continue;
                    }

                    Cell neighbor = boardMatrix[neighborRow][neighborCol];

                    // Mine
                    if (neighbor.getValue() == -1) {
                        continue;
                    }

                    // Other Elements
                    neighbor.setValue(neighbor.getValue() + 1);

                }
            }
        }
    }

    // Reveal Cell's
    public Set<Cell> revealCell(Position position) {
        Cell cell = boardMatrix[position.row()][position.column()];

        if (cell.isFlagged() || cell.isRevealed()) {
            return Set.of();
        }

        if (cell.getValue() == -1) {
            cell.setRevealed();
            gameOver = true;
            return Set.of(cell);
        }

        Set<Cell> cellsRevealed = new HashSet<>();

        if (cell.getValue() == 0) {
            cellsRevealed = cascadeReveal(cell);
        } else {
            cell.setRevealed();
            cellsRevealed.add(cell);
        }

        cellsRemaining -= cellsRevealed.size();
        return cellsRevealed;
    }

    private Set<Cell> cascadeReveal(Cell cell) {
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> cellsRevealed = new HashSet<>();
        queue.add(cell);

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();

            if (currentCell.isFlagged() || currentCell.isRevealed()) {
                continue;
            }

            currentCell.setRevealed();
            cellsRevealed.add(currentCell);

            // If the value is not empty (0), stop revealing neighbors
            if (currentCell.getValue() != 0) {
                continue;
            }


            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {

                    // Skip the element itself
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    int newRow = currentCell.getPosition().row() + i;
                    int newColumn = currentCell.getPosition().column() + j;

                    // Out of board
                    if (newRow < 0 || newRow >= boardSize ||
                            newColumn < 0 || newColumn >= boardSize) {
                        continue;
                    }

                    Cell neighbor = boardMatrix[newRow][newColumn];

                    if (!neighbor.isRevealed() && !neighbor.isFlagged()) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        return cellsRevealed;
    }

    public Set<Cell> revealAllMines() {
        Set<Cell> cellsRevealed = new HashSet<>();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Cell cell = getCell(new Position(i,j));

                if (cell.getValue() == -1 && !cell.isRevealed()) {
                    boardMatrix[i][j].setRevealed();
                    cellsRevealed.add(boardMatrix[i][j]);
                }
            }
        }

        return cellsRevealed;
    }

    // Flag system
    public void toggleFlag(Cell cell) {
        if (cell.isRevealed())
            return;

        if (cell.isFlagged() && cell.isMine()) {
            minesRemaining++;
        }
        if (!cell.isFlagged() && cell.isMine()) {
            if (--minesRemaining == 0 && cellsRemaining == 0) {
                victory = true;
            }
        }

        cell.setFlagged(); // If right click on flagged cell, it will be unflagged
    }

    // Getters
    public boolean isVictory() { return victory; }

    public boolean isGameOver() {
        return gameOver;
    }

    public Cell getCell(Position position) {
        return
            boardMatrix
                    [position.row()]
                    [position.column()];
    }

    public int getBoardSize() {
        return boardSize;
    }
}