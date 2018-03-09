import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public final static int SOCKET_PORT = 8080;  //Puerto
	public final static String PATH = "C:\\Users\\Asus-PC\\Desktop\\University\\6Semestre\\Redes\\docs\\";  //Nombre y ubicación del archivo enviado.

	public static void main (String [] args ) throws IOException {

		//ServerSocket
		ServerSocket welcomeSocket = null;

		//In  
		FileInputStream fis = null;
		BufferedInputStream bis = null;

		//Out
		OutputStream os = null;

		//Lectura y escritura mensajes
		PrintWriter escritor = null;
		BufferedReader lector = null;



		Socket socket = null;
		try {

			//Crear ServerSocket
			welcomeSocket = new ServerSocket(SOCKET_PORT);
			while (true) {
				System.out.println("Waiting for connections...");
				try {
					//Aceptar conexión cliente
					socket = welcomeSocket.accept();
					System.out.println("Accepted connection : " + socket);

					new ThreadServidor(socket).start();

				}
				finally {}
			}
		}
		finally {
			if (welcomeSocket != null) welcomeSocket.close();
		}
	}

}
