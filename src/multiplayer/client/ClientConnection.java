package multiplayer.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
	public Socket socket;
	public ObjectOutputStream out;
	public ObjectInputStream in;
	
	public boolean tryToConnect(){
		 try {
			socket = new Socket("127.0.0.1",2121);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		 } catch (IOException e) {
			return false;
		 }
		 return true;
	}

	public Object read() {
		Object o = null;
		try {
			o =  in.readObject();
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
		 //e.printStackTrace();
		}
		return o;
	}
}
