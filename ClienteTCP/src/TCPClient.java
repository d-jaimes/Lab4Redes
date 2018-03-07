import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {

  public final static int SOCKET_PORT = 8080;      // Puerto del socket
  public final static String SERVER = "127.0.0.1";  // localhost
  public final static String FILE_TO_RECEIVED = "c:/datosRedes/ArchivoRecibido.txt";  // Nombre y lugar descarga archivo.

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

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
      System.out.println("Conectado.");
      
      escritor = new PrintWriter(socket.getOutputStream(), true);
	  lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	  
	  String mensaje = lector.readLine();
	  System.out.println(mensaje);
	  
	  BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	  String nombreArchivo = stdIn.readLine();
	  
	  escritor.println(nombreArchivo);
	  

      //Recibir archivo
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = socket.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;

      do {
         bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (socket != null) socket.close();
    }
  }

}
