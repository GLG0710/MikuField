    import controller.GameController;
    import javafx.application.Application;
    import javafx.scene.Scene;
    import javafx.stage.Stage;
    import model.Config;
    import model.Game;
    import model.utilities.BoardSize;
    import model.utilities.Difficulty;

    public class Main extends Application {

        @Override
        public void start(Stage stage) {
            Config config = new Config();
            config.setBoardSize(BoardSize.BIG.size);
            Game game = new Game(config);
            GameController controller = new GameController(game);

            Scene scene = new Scene(controller.getView());
            stage.setTitle("Miku MineField");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }