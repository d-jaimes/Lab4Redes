
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;


public class InterfazCliente extends JFrame {
	
	private TCPClient cliente;
	
	private PanelPrincipal panelPrincipal;
	
	private PanelDescargas panelDescargas;
	
	private JLabel estadoConexion;
	
	
	public InterfazCliente() {
		
		setTitle("TCP Client");
		setSize( 400, 250 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        cliente = new TCPClient();
        
     
        BorderLayout layout = new BorderLayout( );
        setBackground( Color.BLACK );
        setLayout( layout );
        
        panelPrincipal = new PanelPrincipal(this);
        add(panelPrincipal, BorderLayout.CENTER);
        
        panelDescargas = new PanelDescargas(this);
        add(panelDescargas, BorderLayout.SOUTH);
        
        estadoConexion = new JLabel("Estado Conexión: Desconectado");
        estadoConexion.setPreferredSize( new Dimension( 250, 30 ) );
        estadoConexion.setForeground( Color.BLUE );
        estadoConexion.setHorizontalAlignment( JLabel.CENTER );
        estadoConexion.setFont( new Font( "Tahoma", Font.BOLD, 18 ) );
        add(estadoConexion, BorderLayout.NORTH);
        
        
        setLocationRelativeTo( null );
        setResizable( true );
        
	}
	
	
    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
	
	public void descargar(String pNombreArchivo) {
		cliente.descargar(pNombreArchivo);
		
	}
	
	public void conectar() {
		 boolean conectado = cliente.conectar();
		 mostrarConexion(conectado);
	}
	
	public void desconectar() {
		boolean conectado = !cliente.desconectar();
		mostrarConexion(conectado);
	}
	
	public void mostrarConexion(boolean conectado) {
		
		if(conectado) {
			estadoConexion.setText("Estado Conexión: Conectado");
		}
		else {
			estadoConexion.setText("Estado Conexión: Desconectado");
		}
	}
	
	public void verDescargas() {

		   JFileChooser fileChooser = new JFileChooser();
		   File prueba = new File("C:\\carpetaDescargas");
		   fileChooser.setCurrentDirectory(prueba);
		   fileChooser.showOpenDialog(this);
		   File abre = fileChooser.getSelectedFile();
		   
		   ProcessBuilder p = new ProcessBuilder();
		   p.command("cmd.exe", "/c", abre.getAbsolutePath());
		   try {
			p.start();
		} catch (IOException e) {

			e.printStackTrace();
		}
		   
		   //System.out.println(abre.getAbsolutePath());
	}
	
	
    public static void main( String[] pArgs )
    {
        try
        {
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );

            InterfazCliente interfaz = new InterfazCliente( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }


}
