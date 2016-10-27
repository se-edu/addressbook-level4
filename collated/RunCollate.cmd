:: Name:     RunCollate.cmd
:: Purpose:  Runs Collate!
:: Author:   A0135812L Kenneth

rmdir main /S /q
rmdir test /S /q
rmdir docs /S /q

java -jar Collate-TUI.jar collate from ../src/main to main include java, fxml, css

java -jar Collate-TUI.jar collate from ../src/test to test include java

java -jar Collate-TUI.jar collate from ../docs to docs include md, html