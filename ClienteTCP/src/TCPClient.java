import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class TCPClient {

	public final static int SOCKET_PORT = 8080;      // Puerto del socket
	public final static String SERVER = "127.0.0.1";  // localhost
	public final static String FILE_TO_RECEIVED = "C:\\Users\\Asus-PC\\Desktop\\University\\6Semestre\\Redes\\docs\\descarga";  // Nombre y lugar descarga archivo.
	public static int numero=0;
	public final static int FILE_SIZE = 50000000; // file size temporary hard coded
	// should bigger than the file to be downloaded
	
	public final static int MESSAGE_SIZE = 256;  //Tamaño paquetes
	

	public static void main (String [] args ) throws IOException {

		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket socket = null;

		PrintWriter escritor = null;
		BufferedReader lector = null;

		String inputLine;
		String outputLine;

		try {
			socket = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Connected.");

			escritor = new PrintWriter(socket.getOutputStream(), true);
			lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			boolean conexionEstablecida = true;


			String mensaje = lector.readLine();
			System.out.println(mensaje);

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String nombreArchivo = stdIn.readLine();

			escritor.println(nombreArchivo);
			String ext = nombreArchivo.substring(nombreArchivo.lastIndexOf('.'));
			
			//Iniciar medición tiempo descarga de un archivo.
			long startTime = System.currentTimeMillis();


			//Recibir archivo

			byte [] mybytearray  = new byte [FILE_SIZE];
			InputStream is = socket.getInputStream();
			fos = new FileOutputStream(FILE_TO_RECEIVED + (numero++)+ext);

			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;


			//String prueba = lector.readLine();
			//System.out.println(prueba);
			int messagesReceived = 0;
			int bytesReceived = 0;
			do {
				bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
				//System.out.println(bytesRead);
				bytesReceived += bytesRead;
				if(bytesReceived >= MESSAGE_SIZE) {

					messagesReceived += (bytesReceived/MESSAGE_SIZE);
					System.out.println("Messages Received: " + messagesReceived);
					bytesReceived -= MESSAGE_SIZE * (bytesReceived/MESSAGE_SIZE);
					//messagesReceived++;
				}
				if(bytesRead >= 0) current += bytesRead;
				//System.out.println(bytesRead);
			} while(bytesRead > -1);

			bos.write(mybytearray, 0 , current);
			bos.flush();
			System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("The download took " + (endTime - startTime) + " milliseconds");

		}
		finally {
			if (fos != null) fos.close();
			if (bos != null) bos.close();
			if (socket != null) socket.close();
		}
	}

}
