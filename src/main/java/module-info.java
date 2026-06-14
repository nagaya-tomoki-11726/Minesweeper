module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.example to javafx.fxml, javafx.graphics;

    exports com.example;
}
