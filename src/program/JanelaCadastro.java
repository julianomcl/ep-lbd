package program;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import database.Reserva;
import database.ReservaDAO;
import database.Sala;
import database.SalaDAO;
import database.Socio;
import database.SocioDAO;

public class JanelaCadastro extends Frame{
	
	private Choice salas = new Choice();
	private Choice socios = new Choice();
	private JDatePickerImpl data;
	private Choice hora = new Choice();
	private Label lblSala = new Label("Sala:");
	private Label lblSocio = new Label("Sócio:");
	private Label lblData = new Label("Data:");
	private Label lblHora = new Label("Hora:");
	private Button cadastrar = new Button("Fazer Reserva");
	private Button voltar = new Button("Cancelar");
	
	private ArrayList<Socio> arrSocios;
	private ArrayList<Sala> arrSalas;
	private ArrayList<Integer> arrHoras;
	
	private ReservaDAO reservas = new ReservaDAO();
	
	public JanelaCadastro(){
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(640, 480);
		this.setTitle("Academia LBD");
		this.setLocation((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 640)/2, (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 480)/2);
		this.setVisible(true);
		
		arrSalas = new SalaDAO().getAllSalasSquash();
		arrSocios = new SocioDAO().getAllSocios();
		
		lblSala.setBounds(170,150,80,20);
		salas.setBounds(260,150,210,20);
		for(int i = 0; i < arrSalas.size();i++){
			salas.add(arrSalas.get(i).getNroId().toString());
		}
		salas.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if(data.getModel().getValue() != null)
					UpdateHoras();
			}
		});
		lblSocio.setBounds(170,190,80,20);
		socios.setBounds(260,190,210,20);
		for(int i = 0; i < arrSocios.size();i++){
			socios.add(arrSocios.get(i).getNome());
		}
		lblHora.setBounds(170,230,80,20);
		hora.setBounds(260,230,210,20);
		
		lblData.setBounds(170,270,80,27);
		Properties p = new Properties();
		p.put("text.today", "Hoje");
		p.put("text.month", "Mês");
		p.put("text.year", "Ano");
		SqlDateModel model = new SqlDateModel();
		model.setSelected(true);
		data = new JDatePickerImpl(new JDatePanelImpl(model, p), new DateLabelFormatter());
		data.setBounds(260,270,210,27);
		data.getModel().addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent arg0) {
				if(salas.getSelectedIndex() > -1 && data.getModel().getValue() != null){
					UpdateHoras();
				}
			}
		});
		cadastrar.setBounds(185,310,120,20);
		cadastrar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(data.getModel().getValue() == null){
					JOptionPane.showMessageDialog(null, "Selecione uma data!!!");
					return;
				}
				
				if(hora.getSelectedIndex() == -1){
					JOptionPane.showMessageDialog(null, "Selecione uma hora!!!");
					return;
				}
				
				if(salas.getSelectedIndex() == -1){
					JOptionPane.showMessageDialog(null, "Selecione uma sala!!!");
					return;
				}
				
				if(socios.getSelectedIndex() == -1){
					JOptionPane.showMessageDialog(null, "Selecione um sócio!!!");
					return;
				}
				
				Reserva reserva = new Reserva();
				reserva.setData((Date)data.getModel().getValue());
				reserva.setHora(arrHoras.get(hora.getSelectedIndex()));
				reserva.setNroIdSala(arrSalas.get(salas.getSelectedIndex()).getNroId());
				reserva.setNroSocio(arrSocios.get(socios.getSelectedIndex()).getNroSocio());
				reservas.adiciona(reserva);
				
				UpdateHoras();
				JOptionPane.showMessageDialog(null, "Reserva feita com sucesso!!!");
			}
		});
		voltar.setBounds(320,310,120,20);
		voltar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal jP = new JanelaPrincipal();
				dispose();
			}
		});
		
		if(data.getModel().getValue() != null && salas.getSelectedIndex() > -1)
			UpdateHoras();
		
		this.add(lblSala);
		this.add(salas);
		this.add(lblSocio);
		this.add(socios);
		this.add(lblHora);
		this.add(hora);
		this.add(lblData);
		this.add(data);
		this.add(cadastrar);
		this.add(voltar);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JanelaPrincipal jP = new JanelaPrincipal();
				dispose();
			}
		});
	}
	
	public class DateLabelFormatter extends AbstractFormatter {

	    private String datePattern = "dd/MM/yyyy";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }

	}
	
	private void UpdateHoras(){
		arrHoras = reservas.getHorasDisponiveisBySalaAndData(arrSalas.get(salas.getSelectedIndex()), (Date)data.getModel().getValue());
		hora.removeAll();
		for(int i=0;i<arrHoras.size();i++){
			hora.add(arrHoras.get(i).toString());
		}
	}

}
