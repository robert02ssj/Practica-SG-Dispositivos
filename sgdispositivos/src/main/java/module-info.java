module com.ssj {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ssj to javafx.fxml;
    exports com.ssj;
}
