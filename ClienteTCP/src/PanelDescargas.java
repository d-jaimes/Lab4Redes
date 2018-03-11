import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelDescargas extends JPanel implements ActionListener{

	public static final String VER_DESCARGAS = "Ver Descargas";

	private InterfazCliente principal;

	private JButton btnDescargas;

	public PanelDescargas(InterfazCliente pPrincipal) {

		principal = pPrincipal;

		TitledBorder borde = BorderFactory.createTitledBorder( " Ver archivos descargados. " );
		setBorder( borde );

		setLayout(new GridLayout(1, 3));

		btnDescargas = new JButton("Ver Descargas");
		btnDescargas.addActionListener(this);
		btnDescargas.setActionCommand(VER_DESCARGAS);
		
		add(btnDescargas);
		add(new JLabel(""));
		add(new JLabel(""));

	}



	@Override
	public void actionPerformed(ActionEvent pEvento) {
		String comando = pEvento.getActionCommand();

		if(comando.equals(VER_DESCARGAS)) {
			principal.verDescargas();
		}

	}



}
