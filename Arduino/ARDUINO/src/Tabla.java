import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Tabla implements TableModel {

public String[][] getBd() {
		return bd;
	}




	public void setBd(String[][] bd) {
		this.bd = bd;
	}

public String[][] bd;

	
	public Tabla (){
		
		bd= new String[6][5];
		bd[0][1]="Humedad 1";
		bd[0][2]="Temperatura 1";
		bd[0][3]="Humedad 2";
		bd[0][4]="Temperatura 2";
		bd[1][0]="Cycle1";
		bd[2][0]="Cycle2";
		bd[3][0]="Cycle3";
		bd[4][0]="Cycle4";
		bd[5][0]="Cycle5";
		
	
		
		
	}
	
	
	

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return bd[0].length;
	}

	@Override
	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return "c"+arg0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return bd.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return bd[arg0][arg1];
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		
		return true;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
		
		
		bd[arg1][arg2]=arg0.toString();
		
				
		
		
	}


				
		                    }
		      
