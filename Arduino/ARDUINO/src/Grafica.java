import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class Grafica extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private byte recibir=1;
	private int contador;
	;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Grafica frame = new Grafica();
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Grafica() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JButton btnStart = new JButton("Start");
		panel.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
		
		
			
			@Override
			public void actionPerformed(ActionEvent e) {
				contador=1;
		
				
				
			}
		});
		JButton btnSave = new JButton("Save");
		panel.add(btnSave);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		panel.add(btnActualizar);
		

		
		Tabla Tb =new Tabla();
		
		table = new JTable(Tb);
		
		
		
		contentPane.add(table, BorderLayout.CENTER);
		Serie1 Ard =new Serie1(Tb);
		
		
		if(contador==1){
		
		Ard.EnviarDatos(1);
		contador=0;
		}
		
	}
	

}
