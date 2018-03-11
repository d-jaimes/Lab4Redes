import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelPrincipal extends JPanel implements ActionListener{
	
    public static final String DESCARGAR = "Descargar";
    
    public static final String CONECTAR = "Conectar";
    
    public static final String DESCONECTAR = "Desconectar";
	
	private InterfazCliente principal;
	
	private JButton btnDescarga;
	
	private JButton btnConectar;
	
	private JButton btnDesconectar;
	
	private JComboBox<String> listaArchivos;
	
	
	public PanelPrincipal(InterfazCliente pPrincipal) {
		
		principal = pPrincipal;
		
		TitledBorder borde = BorderFactory.createTitledBorder( " Escoger archivo descarga " );
        setBorder( borde );
        
        setLayout(new GridLayout(3, 3));
        
        
        String[] archivos = new String[] {"Cheetah.jpg", "Dog.jpg", "Flower.jpg", "Water.jpg", "TCPIP.png"};
        
        listaArchivos = new JComboBox<String>(archivos);
        listaArchivos.setPreferredSize( new Dimension( 50, 30 ) );
        
        btnDescarga = new JButton("Descargar");
        btnDescarga.addActionListener(this);
        btnDescarga.setActionCommand(DESCARGAR);
        btnDescarga.setPreferredSize( new Dimension( 50, 30 ) );
        
        btnConectar = new JButton("Conectar");
        btnConectar.addActionListener(this);
        btnConectar.setActionCommand(CONECTAR);
        btnConectar.setPreferredSize( new Dimension( 50, 30 ) );

        
        btnDesconectar = new JButton("Desconectar");
        btnDesconectar.addActionListener(this);
        btnDesconectar.setActionCommand(DESCONECTAR);
        btnDesconectar.setSize( new Dimension( 50, 30 ) );
        
        
        add(btnConectar);
        add(new JLabel(""));
        add(btnDesconectar);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(listaArchivos);
        add(new JLabel(""));
        add(btnDescarga);
        
        
        
        
	}

	@Override
	public void actionPerformed(ActionEvent pEvento) {
		
		String comando = pEvento.getActionCommand();
		
		if(comando.equals(DESCARGAR)) {
			
			String nombreArchivo =(String) listaArchivos.getSelectedItem();
			principal.descargar(nombreArchivo);
		}
		else if(comando.equals(CONECTAR)) {
			principal.conectar();
		}
		else if(comando.equals(DESCONECTAR)) {
			principal.desconectar();
		}

		
	}

}
