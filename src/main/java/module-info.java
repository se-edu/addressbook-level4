module AddressBookL4.main {
    requires java.xml.bind;
    requires java.logging;
    requires java.desktop;
    requires java.sql;
    requires javafx.web;
    requires javafx.fxml;
    requires guava;

    exports seedu.address;

    opens seedu.address.ui to javafx.fxml, guava;
    opens seedu.address.storage to java.xml.bind, guava;
    opens seedu.address.commons.core to jackson.databind;
    opens seedu.address.model to jackson.databind;
}
