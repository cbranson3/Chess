package boardview;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import gamecontrol.AIChessController;
import gamecontrol.ChessController;
import gamecontrol.GameController;
import gamecontrol.NetworkedChessController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Main class for the chess application
 * Sets up the top level of the GUI
 * @author Gustavo
 * @version
 */
public class ChessFX extends Application {

    private GameController controller;
    private BoardView board;
    private Text state;
    private Text sideStatus;
    private VBox root;
    private HBox userInt;

    @Override
    public void start(Stage primaryStage) throws UnknownHostException {
        root = new VBox();
        VBox bottom = new VBox();
        HBox boxBot = new HBox();
        userInt = new HBox();
        controller = new ChessController();
        state = new Text("Ongoing - ");
        sideStatus = new Text();
        board = new BoardView(controller, state, sideStatus);
        Button resetButton = new Button();
        resetButton.setText("Reset");
        resetButton.setOnAction(e -> {
                board.reset(new ChessController());
            });
        Button computerPlay = new Button();
        computerPlay.setText("Play a computer");
        computerPlay.setOnAction(e -> {
                board.reset(new AIChessController());
            });

        Button ipPlay = new Button();
        ipPlay.setText("Host a game");
        ipPlay.setOnMouseClicked(makeHostListener());

        Text ipAddress = new Text(InetAddress.getLocalHost().toString());

        TextField inputField = new TextField();
        inputField.setText("Enter an IP address");

        Button joinPlay = new Button();
        joinPlay.setText("Join a game");
        joinPlay.setOnMouseClicked(makeJoinListener(inputField));

        boxBot.getChildren().addAll(ipPlay, ipAddress, inputField, joinPlay);

        userInt.getChildren()
            .addAll(resetButton, computerPlay, state, sideStatus);

        bottom.getChildren().addAll(userInt, boxBot);

        root.getChildren().addAll(board.getView(), bottom);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess");
        primaryStage.show();

        this.board.reset(this.controller);
    }

    private EventHandler<? super MouseEvent> makeHostListener() {
        return event -> {
            board.reset(new NetworkedChessController());
        };
    }

    private EventHandler<? super MouseEvent> makeJoinListener(TextField input) {
        return event -> {
            try {
                InetAddress addr = InetAddress.getByName(input.getText());
                GameController newController
                    = new NetworkedChessController(addr);
                board.reset(newController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }


    public static void main(String[] args) {
        launch(args);
    }
}
