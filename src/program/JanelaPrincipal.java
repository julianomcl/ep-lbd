package program;

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.javafx.tk.Toolkit;

public class JanelaPrincipal extends Frame{
	
	private Button cadastroReservas = new Button("Fazer Reserva");
	private Button listarReservas = new Button("Consultar Reservas");
	private Button controleUso = new Button("Controlar Uso das Salas");
	private Button sair = new Button("Sair");
	
	public JanelaPrincipal(){
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(640, 480);
		this.setTitle("Academia LBD");
		this.setLocation((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 640)/2, (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 480)/2);
		this.setVisible(true);
		
		cadastroReservas.setBounds(170, 170, 300, 20);
		cadastroReservas.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				JanelaCadastro jC = new JanelaCadastro();
				dispose();
			}
		});
		
		listarReservas.setBounds(170, 210, 300, 20);
		listarReservas.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				JanelaLista jL = new JanelaLista();
				dispose();
			}
		});

		controleUso.setBounds(170, 250, 300, 20);
		controleUso.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				JanelaControle jC = new JanelaControle();
				dispose();
			}
		});

		sair.setBounds(170, 290, 300, 20);
		sair.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		this.add(cadastroReservas);
		this.add(listarReservas);
		this.add(controleUso);
		this.add(sair);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
}
