package view;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Board;
import model.Cell;
import model.utilities.BoardSize;
import model.utilities.Difficulty;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class MainView extends Pane {
    private final Consumer<Cell> leftClick, rightClick;
    private final Runnable playOnClick;

    private static final double WIDTH = 480;
    private static final double HEIGHT = 580;

    private static final double PLAY_WIDTH = 120;
    private static final double PLAY_HEIGHT = 40;

    private static final String PLAY_SHAPE = "M20,0 L100,0 L120,20 L100,40 L20,40 L0,20 Z";

    private static final String PLAY_BASE =
            "-fx-shape: \"" + PLAY_SHAPE + "\";" +
                    "-fx-scale-shape: true;" +
                    "-fx-padding: 0;" +
                    "-fx-font-family: 'Monospaced';" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #eeeeee;";

    private static final String PLAY_NORMAL = PLAY_BASE + "-fx-background-color: #373B3E;";
    private static final String PLAY_HOVER = PLAY_BASE + "-fx-background-color: #86CECB;";

    private BoardView boardView;
    private final ConfigView configView;

    private ImageView decoration;
    private ImageView configButton;
    private Button playButton;

    public MainView(Board board,
                    Consumer<Cell> leftClick, Consumer<Cell> rightClick,
                    Consumer<Difficulty> difficultyOnClick, Consumer<BoardSize> boardSizeOnClick,
                    Runnable playOnClick) {

        this.leftClick = leftClick;
        this.rightClick = rightClick;
        this.playOnClick = playOnClick;
        this.boardView = new BoardView(board, leftClick, rightClick);
        this.configView = new ConfigView(difficultyOnClick, boardSizeOnClick);

        setupView();
    }

    private void setupView() {
        setMaxSize(WIDTH, HEIGHT);
        setPrefSize(WIDTH, HEIGHT);
        setMinSize(WIDTH, HEIGHT);

        setStyle("-fx-background-color: #137A7F;");

        loadDecoration();
        loadConfigButton();
        loadPlayButton();
        positionElements();

        configView.setVisible(false);

        getChildren().addAll(configButton, playButton, decoration, boardView, configView);
    }

    private void loadDecoration() {
        decoration = new ImageView();
        try {
            Image decorationImage = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream("/assets/imgs/decoration.png")
                    )
            );
            decoration.setImage(decorationImage);
            decoration.setFitWidth(120);
            decoration.setFitHeight(74);
            decoration.setPreserveRatio(true);
            decoration.setSmooth(true);
        } catch (Exception e) {
            System.out.println("Decoração não encontrada, usando fundo padrão.");
        }
    }

    private void loadConfigButton() {
        configButton = new ImageView();
        try {
            Image configButtonImage = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream("/assets/imgs/config.png")
                    )
            );
            configButton.setImage(configButtonImage);
            configButton.setFitWidth(40);
            configButton.setFitHeight(40);
            configButton.setPreserveRatio(true);
            configButton.setSmooth(true);
            configButton.setCursor(Cursor.HAND);
            configButton.setOnMouseClicked(event -> toggleConfig());
        } catch (Exception e) {
            System.out.println("Config Button não encontrada, usando fundo padrão.");
        }
    }

    private void loadPlayButton() {
        playButton = new Button("PLAY");
        playButton.setPrefSize(PLAY_WIDTH, PLAY_HEIGHT);
        playButton.setMinSize(PLAY_WIDTH, PLAY_HEIGHT);
        playButton.setMaxSize(PLAY_WIDTH, PLAY_HEIGHT);
        playButton.setStyle(PLAY_NORMAL);
        playButton.setCursor(Cursor.HAND);
        playButton.setFocusTraversable(false);

        playButton.setOnMouseEntered(e -> playButton.setStyle(PLAY_HOVER));
        playButton.setOnMouseExited(e -> playButton.setStyle(PLAY_NORMAL));
        playButton.setOnAction(e -> playOnClick.run()); // o main.java.controller decide o que fazer
    }

    private void toggleConfig() {
        boolean show = !configView.isVisible();
        boolean hide = !boardView.isBoardVisible();

        configView.setVisible(show);
        boardView.setDisable(show);
        boardView.hideBoard(hide);
    }

    private void positionElements() {
        decoration.setLayoutX(WIDTH - decoration.getFitWidth() - 20);
        decoration.setLayoutY(35.5);

        configButton.setLayoutX(20);
        configButton.setLayoutY(41);

        playButton.setLayoutX((WIDTH - PLAY_WIDTH) / 2);
        playButton.setLayoutY(41);

        boardView.setLayoutX(20);
        boardView.setLayoutY(110);

        configView.setLayoutX(40);
        configView.setLayoutY(130);
    }

    public void refreshCells(Set<Cell> cells) {
        for (Cell c : cells) {
            boardView.refresh(c);
        }
    }

    public void selectDifficulty(Difficulty difficulty) {
        configView.selectDifficulty(difficulty);
    }

    public void selectBoardSize(BoardSize boardSize) {
        configView.selectBoardSize(boardSize);
    }

    public void startNewBoard(Board board) {
        if (configView.isVisible())
            toggleConfig();
        int index = getChildren().indexOf(boardView);
        boardView = new BoardView(board, leftClick, rightClick);
        boardView.setLayoutX(20);
        boardView.setLayoutY(110);
        getChildren().set(index, boardView);
    }

}