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
	public final static String FILE_TO_SEND = "c:/datosRedes/ArchivoEnviado.txt";  //Nombre y ubicación del archivo enviado.

	public static void main (String [] args ) throws IOException {

		//In  
		FileInputStream fis = null;
		BufferedInputStream bis = null;

		//Out
		OutputStream os = null;

		//Lectura y escritura mensajes
		PrintWriter escritor = null;
		BufferedReader lector = null;

		//ServerSocket
		ServerSocket welcomeSocket = null;

		Socket socket = null;
		try {

			//Crear ServerSocket
			welcomeSocket = new ServerSocket(SOCKET_PORT);


			while (true) {
				System.out.println("Waiting...");
				try {
					//Aceptar conexión cliente
					socket = welcomeSocket.accept();
					System.out.println("Accepted connection : " + socket);

					escritor = new PrintWriter(socket.getOutputStream(), true);
					lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					escritor.println("Ingrese el nombre del archivo que desea descargar");
					String nombreArchivo = lector.readLine();



					//Enviar archivo
					//File myFile = new File (FILE_TO_SEND);
					
					File myFile = new File("c:/datosRedes/" + nombreArchivo);
					byte [] myByteArray  = new byte [(int)myFile.length()];
					fis = new FileInputStream(myFile);
					bis = new BufferedInputStream(fis);
					bis.read(myByteArray, 0, myByteArray.length);

					os = socket.getOutputStream();
					System.out.println("Sending " + FILE_TO_SEND + "(" + myByteArray.length + " bytes)");
					os.write(myByteArray, 0, myByteArray.length);
					os.flush();
					System.out.println("Done.");
				}
				finally {
					if (bis != null) bis.close();
					if (os != null) os.close();
					if (socket!=null) socket.close();
				}
			}
		}
		finally {
			if (welcomeSocket != null) welcomeSocket.close();
		}
	}
}
