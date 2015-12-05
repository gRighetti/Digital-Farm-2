import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Graficos2 extends JFrame implements SerialPortEventListener {
	private OutputStream Output = null;
	private InputStream Input = null;
	SerialPort serialPort;
	private final String PORT_NAME = "COM3";
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;
	String Mensaje = "";
	int DatoEntrada;
	public String[][] bd;
	public int Contador = 0;
	public TableModel Table;
	public int datos;
	public JTable Tb;

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
	public void Grafica() {
		
		//Tb = TB;
		// bd = new String[6][5];

		ArduinoConnection();
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
				EnviarDatos(1);
		
				
				
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
		//Serie1 Ard =new Serie1(Tb);
		
		
	//	if(contador==1){
		
		//Ard.EnviarDatos(1);
		//contador=0;
	//}
		
	}
	


		
	
	@Override
	public void serialEvent(SerialPortEvent oEvent) {

		while (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

			try {
				int dato; // Se declaran las variables
				dato = RecibirDatos();

				if (dato > 0) {
					Mensaje = Mensaje + (char) dato; // Se acumula el mensaje

					if (Mensaje.charAt(Mensaje.length() - 1) == ',') {

						for (int i = 0; i <= Mensaje.length() - 1; i++) {
							if (Mensaje.charAt(i) == '.') {
								datos = Integer.parseInt(Mensaje.substring(0, i));
								Mensaje = "";
								Contador++;
								System.out.println(datos);
								switch (Contador) {
								case 1:
									Table.setValueAt(datos, 1, 1);
			//						Tb.repaint();
									break;
								case 2:
									Table.setValueAt(datos, 1, 2);
				//					Tb.repaint();
									break;
								case 3:
									Table.setValueAt(datos, 1, 3);
						//			Tb.repaint();
									break;
								case 4:
									Table.setValueAt(datos, 1, 4);
					//				Tb.repaint();
									break;
								case 5:
									Table.setValueAt(datos, 2, 1);
						//			Tb.repaint();
									break;
								case 6:
									Table.setValueAt(datos, 2, 2);
							//		Tb.repaint();
									break;
								case 7:
									Table.setValueAt(datos, 2, 3);
								//	Tb.repaint();
									break;
								case 8:
									Table.setValueAt(datos, 2, 4);
									//Tb.repaint();
									break;
								case 9:
									Table.setValueAt(datos, 3, 1);
									//Tb.repaint();
									break;
								case 10:
									Table.setValueAt(datos, 3, 2);
									//Tb.repaint();
									break;
								case 11:
									Table.setValueAt(datos, 3, 3);
									//Tb.repaint();
									break;
								case 12:
									Table.setValueAt(datos, 3, 4);
									//Tb.repaint();
									break;
								case 13:
									Table.setValueAt(datos, 4, 1);
									//Tb.repaint();
									break;
								case 14:
									Table.setValueAt(datos, 4, 2);
									//Tb.repaint();
									break;
								case 15:
									Table.setValueAt(datos, 4, 3);
									//Tb.repaint();
									break;
								case 16:
									Table.setValueAt(datos, 4, 4);
									//Tb.repaint();
									break;
								case 17:
									Table.setValueAt(datos, 5, 1);
									//Tb.repaint();
									break;
								case 18:
									Table.setValueAt(datos, 5, 2);
								//	Tb.repaint();
									break;
								case 19:
									Table.setValueAt(datos, 5, 3);
									//Tb.repaint();
									break;
								case 20:
									Table.setValueAt(datos, 5, 4);
									//Tb.repaint();
									break;

								}
							}

						}

					}
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

	public String[][] getBd() {
		return bd;
	}

	public void setBd(String[][] bd) {
		this.bd = bd;
	}

	public void ArduinoConnection() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			if (PORT_NAME.equals(currPortId.getName())) {
				portId = currPortId;
				break;
			}
		}
		if (portId == null) {
			// System.exit(Error);
			return;
		}

		try {

			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			Output = serialPort.getOutputStream();
			Input = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
		} catch (Exception e) {
			// System.exit(ERROR);
		}

	}

	public int RecibirDatos() throws IOException {
		int Output = 0;
		Output = (byte) Input.read();
		Input.close();

		return Output;
	}

	public void EnviarDatos(int data) {

		try {
			Output.write(data);

		} catch (IOException e) {

			// System.exit(ERROR);
		}
	}
	

	// public static void main(String args[]) {
	// Serie1 Arduino = new Serie1();
	// while(true){

	// }

	// }
}


