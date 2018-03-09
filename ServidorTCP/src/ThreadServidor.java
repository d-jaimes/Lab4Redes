import java.io.*;
import java.net.Socket;

public class ThreadServidor extends Thread {

	// public final static String PATH = "C:\\Users\\Asus-PC\\Desktop\\University\\6Semestre\\Redes\\docs";  //Nombre y ubicación del archivo enviado.

	public final static int MESSAGE_SIZE = 256;  //Tamaño de los paquetes enviados.
	public  final static int TAM_BUFFER = 130_000_000 ;


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

			File myFile = new File(TCPServer.PATH + nombreArchivo);
			byte [] myByteArray  = new byte [(int)myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(myByteArray, 0, myByteArray.length);

			os =  new BufferedOutputStream(socket.getOutputStream(),TAM_BUFFER);
			System.out.println("Sending " + TCPServer.PATH + nombreArchivo + "(" + myByteArray.length + " bytes)");

			int bytesEnviados = 0;

			while(bytesEnviados < myByteArray.length) {
				if((bytesEnviados + MESSAGE_SIZE) < myByteArray.length) {
					os.write(myByteArray, bytesEnviados, MESSAGE_SIZE);
					bytesEnviados += MESSAGE_SIZE;
				}
				else {
					int faltaPorEnviar = myByteArray.length - bytesEnviados;
					os.write(myByteArray, bytesEnviados, faltaPorEnviar);
					bytesEnviados += faltaPorEnviar+1;
				}
			}
			//os.write(myByteArray, bytesEnviados, myByteArray.length);

			//System.out.println("vamos:"+ bytesEnviados+ " falta:"+ (myByteArray.length - bytesEnviados) );
			os.flush();
			System.out.println("Done.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			if (bis != null) bis.close();
			if (os != null) os.close();
		}


	}


}
