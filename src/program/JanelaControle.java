package program;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import database.Reserva;
import database.ReservaDAO;
import database.Sala;
import database.SalaDAO;
import database.Socio;
import database.SocioDAO;

public class JanelaControle extends Frame{
	
	private Choice socios = new Choice();
	private Choice reservas = new Choice();
	private Checkbox utilizada = new Checkbox("Utilizada");
	private Label lblSocios = new Label("Sócio:");
	private Label lblReservas = new Label("Reserva:");
	private Button voltar = new Button("Voltar");
	
	private ArrayList<Socio> arrSocios;
	private ArrayList<Reserva> arrReservas;
	
	private ReservaDAO reserva = new ReservaDAO();
	
	public JanelaControle(){
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(640, 480);
		this.setLocation((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 640)/2, (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 480)/2);
		this.setTitle("Academia LBD");
		this.setVisible(true);
		
		arrSocios = new SocioDAO().getAllSocios();
		
		lblSocios.setBounds(170, 170, 80, 20);
		socios.setBounds(260, 170, 210, 20);
		for(int i = 0; i < arrSocios.size();i++){
			socios.add(arrSocios.get(i).getNome());
		}
		socios.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent arg0) {
				UpdateReservas();
			}
		});
		lblReservas.setBounds(170, 210, 80, 20);
		reservas.setBounds(260, 210, 210, 20);
		reservas.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				utilizada.setState(arrReservas.get(reservas.getSelectedIndex()).isUtilizada());				
			}
			
		});
		UpdateReservas();
		utilizada.setBounds(170, 250, 300, 20);
		utilizada.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if(utilizada.getState()){
					arrReservas.get(reservas.getSelectedIndex()).setUtilizada(1);
					reserva.UtilizaSala(arrReservas.get(reservas.getSelectedIndex()));
				}else{
					arrReservas.get(reservas.getSelectedIndex()).setUtilizada(1);
					reserva.UtilizaSala(arrReservas.get(reservas.getSelectedIndex()));
				}
			}
		});
		utilizada.setState(arrReservas.get(reservas.getSelectedIndex()).isUtilizada());
		voltar.setBounds(270, 290, 100, 20);
		voltar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal jP = new JanelaPrincipal();
				dispose();
			}
		});
		
		this.add(lblSocios);
		this.add(socios);
		this.add(lblReservas);
		this.add(reservas);
		this.add(utilizada);
		this.add(voltar);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JanelaPrincipal jP = new JanelaPrincipal();
				dispose();
			}
		});
	}
	
	private void UpdateReservas(){
		if(socios.getSelectedIndex() == -1)
			return;
		reservas.removeAll();
		arrReservas = reserva.getReservaBySocio(arrSocios.get(socios.getSelectedIndex()));
		for(int i = 0; i < arrReservas.size();i++){
			reservas.add("Sala: " + arrReservas.get(i).getNroIdSala() + " - " + arrReservas.get(i).getData() + " " + arrReservas.get(i).getHora() + ":00");
		}
	}
	
}
