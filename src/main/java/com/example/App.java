package com.example;

import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Dialog<int[]> dialog = createSettingsDialog();
        Optional<int[]> result = dialog.showAndWait();
        int[] settings = result.orElse(new int[] { 9, 9, 10 });

        MinesweeperBoard board = new MinesweeperBoard(settings[0],
                settings[1],
                settings[2]);
        Scene scene = new Scene(board.getView());
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setTitle("マインスイーパ");
        stage.setScene(scene);
        stage.show();
    }

    protected Dialog<int[]> createSettingsDialog() {
        Dialog<int[]> dialog = new Dialog<>();
        dialog.setTitle("ゲーム設定");
        dialog.setHeaderText("盤面サイズと地雷数を入力してください");

        ButtonType okButton = new ButtonType("開始", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        TextField rowsField = new TextField("9");
        TextField colsField = new TextField("9");
        TextField minesField = new TextField("10");

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.add(new Label("行数:"), 0, 0);
        grid.add(rowsField, 1, 0);
        grid.add(new Label("列数:"), 0, 1);
        grid.add(colsField, 1, 1);
        grid.add(new Label("地雷数"), 0, 2);
        grid.add(minesField, 1, 2);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == okButton) {
                try {
                    int rows = Integer.parseInt(rowsField.getText());
                    int cols = Integer.parseInt(colsField.getText());
                    int mines = Integer.parseInt(minesField.getText());
                    return new int[] { rows, cols, mines };
                } catch (NumberFormatException e) {
                    return new int[] { 9, 9, 10 };
                }
            }
            return new int[] { 9, 9, 10 };
        });

        return dialog;
    }

    public static void main(String[] args) {
        launch();
    }
}
