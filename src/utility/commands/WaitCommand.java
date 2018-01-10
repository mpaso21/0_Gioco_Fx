package utility.commands;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utility.Command;

public class WaitCommand implements Command {

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 1600, 800);

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.LIGHTSLATEGRAY);
        gc.setLineWidth(2);
        Font theFont = Font.font("Bungee", FontWeight.BOLD, 90);
        gc.setFont(theFont);
        gc.fillText("Waiting\nPlayer 2", 180, 260);//colorato
        gc.strokeText("Waiting\nPlayer 2", 180, 260);//contonro 
    }

}
