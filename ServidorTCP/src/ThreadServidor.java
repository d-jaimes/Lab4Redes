import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadServidor extends Thread {

	public final static String PATH = "c:/datosRedes/";  //Nombre y ubicación del archivo enviado.


	private Socket socket = null;

	public ThreadServidor(Socket pSocket) {
		socket = pSocket;

	}

	public void run() {

		//Lectura y escritura mensajes
		PrintWriter escritor = null;
		BufferedReader lector = null;

		//In  
		FileInputStream fis = null;
		BufferedInputStream bis = null;

		//Out
		OutputStream os = null;



		try {
			escritor = new PrintWriter(socket.getOutputStream(), true);
			lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			procesar(lector, escritor);
			escritor.close();
			lector.close();
			socket.close();
		}
		catch(Exception e) {

		}

	}

	public void procesar(BufferedReader lector, PrintWriter escritor) throws Exception {

		//In  
		FileInputStream fis = null;
		BufferedInputStream bis = null;

		//Out
		OutputStream os = null;

		try {
			//Inicio Comunicación
			escritor.println("Ingrese el nombre del archivo que desea descargar");
			String nombreArchivo = lector.readLine();



			//Enviar archivo
			//File myFile = new File (FILE_TO_SEND);

			File myFile = new File(PATH + nombreArchivo);
			byte [] myByteArray  = new byte [(int)myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(myByteArray, 0, myByteArray.length);

			os = socket.getOutputStream();
			System.out.println("Sending " + PATH + nombreArchivo + "(" + myByteArray.length + " bytes)");
			os.write(myByteArray, 0, myByteArray.length);
			os.flush();
			System.out.println("Done.");

		}
		finally {
			if (bis != null) bis.close();
			if (os != null) os.close();
		}


	}


}
