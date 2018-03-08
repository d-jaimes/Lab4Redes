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

	public final static int MESSAGE_SIZE = 256;  //Tamaño de los paquetes enviados.


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
			escritor.println("Please write the name of the file you want to download:");
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

			int bytesEnviados = 0;

			while(bytesEnviados < myByteArray.length) {
				if((bytesEnviados + MESSAGE_SIZE) < myByteArray.length) {
					os.write(myByteArray, bytesEnviados, MESSAGE_SIZE);
					bytesEnviados += MESSAGE_SIZE;
				}
				else {
					int faltaPorEnviar = myByteArray.length - bytesEnviados;
					os.write(myByteArray, bytesEnviados, faltaPorEnviar);
					bytesEnviados += faltaPorEnviar;
				}


			}

			//os.write(myByteArray, 0, myByteArray.length);
			os.flush();
			System.out.println("Done.");

		}
		finally {
			if (bis != null) bis.close();
			if (os != null) os.close();
		}


	}


}
