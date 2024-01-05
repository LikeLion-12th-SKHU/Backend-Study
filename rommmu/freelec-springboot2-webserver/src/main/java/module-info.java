module com.jojoldo.book.freelecspringboot2webserver {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jojoldo.book.freelecspringboot2webserver to javafx.fxml;
    exports com.jojoldo.book.freelecspringboot2webserver;
}