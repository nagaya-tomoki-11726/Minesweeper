package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MinesweeperBoard {

  private GridPane grid;
  private MinesweeperGame game;
  private boolean isFirstClick = true;
  private VBox root;
  private Label mineLabel;
  private Button[][] buttons;

  public MinesweeperBoard(int rows,
      int cols,
      int mines) {
    game = new MinesweeperGame(rows,
        cols,
        mines);
    buttons = new Button[rows][cols];
    grid = new GridPane();
    grid.setHgap(0);
    grid.setVgap(0);
    grid.setAlignment(Pos.CENTER);

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Button btn = new Button();
        btn.setPrefSize(40, 40);
        btn.setStyle("-fx-background-color: #c0c0c0;" +
            "-fx-border-color: #ffffff #808080 #808080 #ffffff;" +
            "-fx-border-width: 3;");
        buttons[r][c] = btn;
        grid.add(btn, c, r);

        final int row = r;
        final int col = c;

        btn.setOnMouseClicked(e -> {
          if (game.gameOver || game.isWon())
            return;
          if (e.getButton() == MouseButton.SECONDARY) {
            game.flagged[row][col] = !game.flagged[row][col];
          } else {
            if (isFirstClick) {
              game.placeMines(row, col);
              game.calcNumbers();
              isFirstClick = false;
              game.reveal(row, col);
            } else if (game.revealed[row][col]) {
              game.chord(row, col);
            } else {
              game.reveal(row, col);
            }
          }
          updateBoard();
        });
      }
    }
    mineLabel = new Label("残り地雷数: " + mines);
    Button resetBtn = new Button("リセット");
    resetBtn.setOnAction(e -> reset(rows,
        cols,
        mines));
    HBox topBar = new HBox(20, mineLabel, resetBtn);
    topBar.setAlignment(Pos.CENTER);
    topBar.setPadding(new Insets(10));

    root = new VBox(10, topBar, grid);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(10));
    root.setStyle("-fx-background-color: #c0c0c0;");
  }

  private void updateBoard() {
    int flagCount = 0;
    for (int r = 0; r < game.rows; r++) {
      for (int c = 0; c < game.cols; c++) {
        if (game.flagged[r][c])
          flagCount++;
        Button btn = buttons[r][c];
        if (game.flagged[r][c]) {
          btn.setText("🚩");
        } else if (game.revealed[r][c]) {
          if (game.board[r][c] == -1) {
            btn.setText("💥");
          } else if (game.board[r][c] == 0) {
            btn.setText("");
            btn.setDisable(false);
            btn.setMouseTransparent(false);
            btn.setStyle("-fx-background-color: #c0c0c0;" +
                "-fx-border-color: #808080 #ffffff #ffffff #808080;" +
                "-fx-border-width: 1;");
          } else {
            btn.setText(String.valueOf(game.board[r][c]));
            btn.setDisable(false);
            btn.setMouseTransparent(false);
            String color = switch (game.board[r][c]) {
              case 1 -> "-fx-text-fill: blue;";
              case 2 -> "-fx-text-fill: green;";
              case 3 -> "-fx-text-fill: red;";
              case 4 -> "-fx-text-fill: darkblue;";
              case 5 -> "-fx-text-fill: darkred;";
              case 6 -> "-fx-text-fill: teal;";
              case 7 -> "-fx-text-fill: black;";
              case 8 -> "-fx-text-fill: gray;";
              default -> "";
            };
            btn.setStyle("-fx-background-color: #c0c0c0;" +
                "-fx-border-color: #808080 #ffffff #ffffff #808080;" +
                "-fx-border-width: 1;" + color);
          }
        } else {
          btn.setText("");
          btn.setMouseTransparent(false);
          btn.setStyle("-fx-background-color: #c0c0c0;" +
              "-fx-border-color: #ffffff #808080 #808080 #ffffff;" +
              "-fx-border-width: 3;");
        }
      }
    }
    mineLabel.setText("残り地雷数: " + (game.mines - flagCount));
    if (game.gameOver) {
      for (int r = 0; r < game.rows; r++) {
        for (int c = 0; c < game.cols; c++) {
          if (game.board[r][c] == -1) {
            buttons[r][c].setText("💥");
          }
        }
      }
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("ゲームオーバー");
      alert.setHeaderText(null);
      alert.setContentText("💥地雷を踏みました!");
      alert.showAndWait();
    } else if (game.isWon()) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("クリア!");
      alert.setHeaderText(null);
      alert.setContentText("🎉おめでとうございます!");
      alert.showAndWait();
    }
  }

  public VBox getView() {
    return root;
  }

  public void reset(int rows,
      int cols,
      int mines) {
    game = new MinesweeperGame(rows,
        cols,
        mines);
    isFirstClick = true;
    mineLabel.setText("残り地雷数: " + game.mines);
    for (int r = 0; r < game.rows; r++) {
      for (int c = 0; c < game.cols; c++) {
        buttons[r][c].setText("");
        buttons[r][c].setDisable(false);
        buttons[r][c].setMouseTransparent(false);
        buttons[r][c].setStyle("-fx-background-color: #c0c0c0;" +
            "-fx-border-color: #ffffff #808080 #808080 #ffffff;" +
            "-fx-border-width: 3;");
      }
    }
  }
}
