module com.example.messenger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens messengerApp to javafx.fxml;
    exports messengerApp;
}