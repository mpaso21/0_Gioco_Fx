package multiplayer.client;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import multiplayer.MultiplayerWorld;
import utility.WrapperValue;

public class MultiplayerView {
	private MultiplayerController controller;
	private GraphicsContext gc;
	
	private MultiplayerWorld world;
	private String lastMessage;
	
	public MultiplayerView(MultiplayerController controller){
		this.controller = controller;
		Canvas canvas = new Canvas(1600.0, 800.0);
		this.controller.getChildren().add(canvas);//canvas lla metto come nodo figlio del padre group
		gc = canvas.getGraphicsContext2D();//associo gc a canvas
	}
	
	public void setWorld(MultiplayerWorld world){
		this.world = world;//Setto world
	}
	
	public void render(Object obj){
		gc.clearRect(0, 0, 1600, 800);
		
		if(obj instanceof WrapperValue<?>) {
			lastMessage = ((WrapperValue<String>) obj).value;
//			System.out.println(lastMessage);
		} else if(obj instanceof MultiplayerWorld) {
			setWorld((MultiplayerWorld) obj); 
		}
		
//                System.out.println(obj + " " + obj.getClass());
                
		if(lastMessage != null && world == null) {
			if(lastMessage.equals("wait!")){
				drawWait();
			} else if(lastMessage.equals("ready")) {
				
			}	
		}
		
		if(world!= null){
			world.renderAll(gc);
		}
                
//                if(obj instanceof MultiplayerWorld) {
//                    ((MultiplayerWorld) obj).renderAll(gc);
//                }
	}
	
	private void drawWait(){
		gc.setFill( Color.BLACK );
	    gc.fillRect(0, 0, 1600, 800);
 	
		gc.setFill( Color.WHITE );
		    gc.setStroke( Color.LIGHTSLATEGRAY );
		    gc.setLineWidth(2);
		    Font theFont = Font.font( "Bungee", FontWeight.BOLD, 90 );
		    gc.setFont( theFont );
		    gc.fillText( "Waiting\nPlayer 2", 180, 260 );//colorato
		    gc.strokeText( "Waiting\nPlayer 2",180, 260 );//contonro 
   	}
}
