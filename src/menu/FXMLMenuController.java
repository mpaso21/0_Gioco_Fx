package menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


public class FXMLMenuController {
@FXML private Text actiontarget;

    
	
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("hai schiacciato play");
    }
}
