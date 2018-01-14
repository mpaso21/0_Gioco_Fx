package rmi.server;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import utility.Constants;

public class FXML_Server_controller {
    @FXML Text text;
    
    @FXML protected void handleMortal(ActionEvent event) {
       Constants.MORTAL_MULTI = !Constants.MORTAL_MULTI;
        text.setText("Mortal: " + Constants.MORTAL_MULTI);
        text.setStyle(Constants.MORTAL_MULTI ? "-fx-fill: green" : "-fx-fill: red");
    }
    
    public void init() {
        text.setText("Mortal: " + Constants.MORTAL_MULTI);
        text.setStyle(Constants.MORTAL_MULTI ? "-fx-fill: green" : "-fx-fill: red");
    }
}
