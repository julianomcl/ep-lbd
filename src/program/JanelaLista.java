package program;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;

import database.Reserva;
import database.ReservaDAO;
import database.Sala;
import database.SalaDAO;

public class JanelaLista extends Frame{
	
	private Choice listas = new Choice();
	private List lista = new List();
	private Button voltar = new Button("Voltar");
	private TextField mes = new TextField();
	private TextField ano = new TextField();
	private Choice sala = new Choice();
	private Label lblMes = new Label("Mês");
	private Label lblAno = new Label("Ano");
	private Label lblSala = new Label("Sala");
	
	private ArrayList<Object> arrResultados = new ArrayList<Object>();
	private ArrayList<Sala> arrSalas = new ArrayList<Sala>();
	
	private ReservaDAO reserva = new ReservaDAO();
	
	public JanelaLista(){
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(640, 480);
		this.setTitle("Academia LBD");
		this.setLocation((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 640)/2, (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 480)/2);
		this.setVisible(true);
		
		listas.setBounds(170,40,300,20);
		listas.add("Reservas de sala por dia");
		listas.add("Usuários com maior número de reservas por mês");
		listas.add("Usuários com maior número de reservas por ano");
		listas.add("Usuários por número de reservas não utilizadas");
		listas.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				UpdateLista();
			}
		});
		
		lblMes.setBounds(240,80,100,20);
		lblMes.setVisible(false);
		mes.setBounds(300,80,100,20);
		mes.setVisible(false);
		mes.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				char input = e.getKeyChar();
				if(input != '\b' && !Character.isDigit(input)){
					e.consume();
				}
				UpdateLista();
			}
		});
		lblAno.setBounds(240,80,100,20);
		lblAno.setVisible(false);
		ano.setBounds(300,80,100,20);
		ano.setVisible(false);
		ano.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				char input = e.getKeyChar();
				if(input != '\b' && !Character.isDigit(input)){
					e.consume();
				}
				UpdateLista();
			}
		});
		arrSalas = new SalaDAO().getAllSalasSquash();
		lblSala.setBounds(240,80,100,20);
		lblSala.setVisible(false);
		sala.setBounds(300,80,100,20);
		sala.setVisible(false);
		for(int i = 0; i < arrSalas.size();i++){
			sala.add(arrSalas.get(i).getNroId().toString());
		}
		sala.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				UpdateLista();
			}
		});
		
		
		lista.setBounds(170, 120, 300, 270);
		UpdateLista();
		voltar.setBounds(270,420,100,20);
		voltar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal jP = new JanelaPrincipal();
				dispose();
			}
		});
		
		this.add(listas);
		this.add(lista);
		this.add(voltar);
		this.add(mes);
		this.add(lblMes);
		this.add(ano);
		this.add(lblAno);
		this.add(sala);
		this.add(lblSala);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JanelaPrincipal jP = new JanelaPrincipal();
				dispose();
			}
		});
	}
	
	private void UpdateLista(){
		lista.removeAll();
		switch(listas.getSelectedIndex()){
			case 0:{
				EscondeTudo();
				sala.setVisible(true);
				lblSala.setVisible(true);
				arrResultados = reserva.getReservasSalaPorDia(arrSalas.get(sala.getSelectedIndex()));
				for(int i=0;i<arrResultados.size();i++){
					lista.add("Sala: " + ((Reserva)(arrResultados.get(i))).getNroIdSala() + " - Id sócio: " + ((Reserva)(arrResultados.get(i))).getNroSocio() + " - Data: " + ((Reserva)(arrResultados.get(i))).getData() + " " + ((Reserva)(arrResultados.get(i))).getHora() + ":00"); 
				}
				break;
			}
			case 1:{
				if(!mes.isVisible()){
					EscondeTudo();
					mes.setVisible(true);
					lblMes.setVisible(true);
				}
				try{
					int m = Integer.parseInt(mes.getText());
					if(m > 0 && m < 13){
						Calendar c = Calendar.getInstance();
						c.set(Calendar.MONTH, m);
						lista.add(reserva.getReservaByMes(c));
					}
				}catch(Exception ex){
				}
				break;
			}
			case 2:{
				if(!ano.isVisible()){
					EscondeTudo();
					ano.setVisible(true);
					lblAno.setVisible(true);
				}
				try{
					int a = Integer.parseInt(ano.getText());
					Calendar c = Calendar.getInstance();
					c.set(Calendar.YEAR, a);
					lista.add(reserva.getReservaByAno(c));
				}catch(Exception ex){
				}
				break;
			}
			case 3:{
				EscondeTudo();
				arrResultados = reserva.getReservasNaoUtilizadas();
				for(int i=0;i<arrResultados.size();i++){
					lista.add(arrResultados.get(i).toString());
				}
				break;
			}
		}
	}
	
	private void EscondeTudo(){
		ano.setVisible(false);
		lblAno.setVisible(false);
		mes.setVisible(false);
		lblMes.setVisible(false);
		sala.setVisible(false);
		lblSala.setVisible(false);
	}
	
}
