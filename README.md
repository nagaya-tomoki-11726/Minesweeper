# マインスイーパ

JavaFX製のマインスイーパゲームです。

## 機能

- 盤面サイズと地雷数を自由に設定
- 初手安全（最初のクリックは絶対に地雷なし）
- 右クリックで旗を立てる
- 数字クリックで周囲を自動開き
- 周囲の未開マス数が地雷数と一致する場合、自動で旗を立てる
- 残り地雷数の表示
- リセットボタン

## 遊び方

- 左クリック：マスを開く
- 右クリック：旗を立てる／外す
- 数字マスを左クリック：周囲を自動開き（旗が揃っている場合）

## ビルド方法

```bash
mvn clean javafx:run
```

## 実行ファイルの作成

```bash
mvn clean package -DskipTests
jpackage --input target --main-jar minesweeper-1.0-SNAPSHOT.jar --main-class com.example.App --name Minesweeper --type app-image --module-path "~/.m2/repository/org/openjfx/..." --add-modules javafx.controls,javafx.graphics,javafx.fxml
```

## 動作環境

- JDK 21以上
- JavaFX 23
