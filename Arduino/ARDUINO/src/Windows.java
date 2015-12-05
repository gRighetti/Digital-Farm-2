

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Windows extends javax.swing.JFrame implements SerialPortEventListener {

    int ancho = 0, alto = 0; //Ancho y alto de la pantalla
    int GraphMax = 100, GraphMin = 0; //Máximo y mínimo de la ráficag
    int Xstep, Ystep, LastX = 0, LastY = 0, tiempo, time;
    int cero, zero;
    int frecuencia = 1;
    Graphics g; //Se declara la variable que nos permitirá dibujar en el panel
    //Variables para el uso de Arduino
    private OutputStream Output = null;
    private InputStream Input = null;
    SerialPort serialPort;
    private final String PORT_NAME = "COM3";
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    String Mensaje = "";
    int DatoEntrada;
    DefaultTableModel modelo; //variable para agregar o eliminar filas en la jTableDatos
    Object[] fila; //Filas

    public Windows() {
        initComponents();
        ScreenDimension(); //Se establece que la ventana de nuestra aplicación cubrirá la ventana
        setControls();
        //Se declara la variable que hará las gráficas
        g = jPanelGrafica.getGraphics();
        ArduinoConnection();
        //se establece el modelo de la tabla que nos permitirá agregar o quitar filas
        modelo = (DefaultTableModel) jTableDatos.getModel();

    }

    public synchronized void serialEvent(SerialPortEvent oEvent) {
       
        while (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

            try {
                int datos; //Se declaran las variables
                datos = RecibirDatos(); //Se lee los datos en el puerto serie

                if (datos > 0) { //Si el valor leido es mayor a 0...
                    Mensaje = Mensaje + (char) datos; //Se acumula el mensaje

                    if (Mensaje.charAt(Mensaje.length() - 1) == ',') { //Cuando se recibe la coma
                        //el mensaje ha llegado a su final, por lo que se procede a imprimir
                        //La parte ENTERA de la humedad. Se busca el punto, donde quiera que esté
                        //y se transforma de String a entero
                        for (int i = 0; i <= Mensaje.length() - 1; i++) {
                            if (Mensaje.charAt(i) == '.') {
                                DatoEntrada = Integer.parseInt(Mensaje.substring(0, i));
                                Graph(DatoEntrada);
                                modelo.addRow(fila); //Se agrega una fila a la tabla
                                modelo.setValueAt((time - 1) * frecuencia, time - 1, 0); //Se agrega el tiempo en la columna 1
                                modelo.setValueAt(DatoEntrada, time - 1, 1);//Se agrega el dato leido en la columna 2
                                Mensaje = ""; //Se limpia la variable y se prepara para nueva lectura
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
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

            System.exit(ERROR);
            return;
        }

        try {

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            Output = serialPort.getOutputStream();
            Input = serialPort.getInputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {

            System.exit(ERROR);
        }

    }

    private int RecibirDatos() throws IOException {
        int Output = 0;
        Output = Input.read();
        return Output;
    }

    private void EnviarDatos(String data) {

        try {
            Output.write(data.getBytes());

        } catch (IOException e) {

            System.exit(ERROR);
        }
    }

    private void ScreenDimension() {
        //Se busca el alto y el ancho de la pantalla que se esté usando
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        ancho = dim.width;
        alto = dim.height;
        this.setBounds(0, 0, ancho, alto - 35);
        cero = alto - 118;
    }

    private void setControls() {

        //Se setea la ubicación de los paneles
        this.setLayout(null);
        jPanelGrafica.setBounds(200, 0, ancho - 200, alto);
        jPanelControles.setBounds(0, 0, 200, alto);
    }

    private void setGrid() {
        int number; //Número del eje Y
        int posX = 0; //Posición del número
        String tamaño; //Cantidad de caracteres del número
        g.setColor(Color.black); //Se establece un color negro
        g.fillRect(38, 0, 2, alto); //Se crea el eje Y
        g.fillRect(38, cero - (((0 - GraphMin) * 25) / ((GraphMax - GraphMin) / 25)), ancho, 2);

        for (int i = 1; i <= (alto / 25) - 3; i++) {
            g.drawLine(36, i * 25, 45, i * 25); //Se dibujan las rayitas de 25 en 25
            //Se generan los números. Siempre se empezará por el número máximo de la 
            //gráfica y la numeración descenderá hasta el mínimo. 
            number = (GraphMax) - ((i - 1) * ((GraphMax - GraphMin) / 25));
//Se asigna el número a la variable tamaño
            tamaño = "" + number;
            switch (tamaño.length()) { //Se cuenta la cantidad de caracteres 
                case 1:
                    posX = 20;
                    break;
                case 2:
                    posX = 15;
                    break;
                case 3:
                    posX = 10;
                    break;
                case 4:
                    posX = 5;
                    break;
                case 5:
                    posX = 0;
                    break;
            }
            //Dependiendo de los caracteres se establecerá una posición para el numero
            g.drawString(number + "", posX, (i * 25) + 4); //Se imprime el número
        }
        g.drawString(jTextFieldEjeX.getText(), ancho - 300, zero - 5);
        g.drawString(jTextFieldEjeY.getText(), 44, 20);

    }

    public void Graph(int input) {
        g.setColor(Color.red); //Se establece el color rojo
        if (Xstep == 0) { //En esta estructura se decide el LastX y el LastY
            //Como queremos que los puntos de la gráfica vallan unidos con una 
            //línea, necesitamos el punto anterior y el punto Actual.
            //Primero se establecen las condiciones para el inicio y luego para
            //cuando el programa ya este corriendo y halla procesado al menos 1 dato
            LastX = 38;
            //Se calcula la posición en Y a partir del uso de una regla de 3
            Ystep = cero - (((input - GraphMin) * 25) / ((GraphMax - GraphMin) / 25));
            LastY = Ystep;
        } else {
            //Si se ha obtenido al menos 1 dato, el LastY y el LastX serán los datos
            //del punto anterior que se graficó.
            LastX = Xstep;
            LastY = Ystep;
            Ystep = cero - (((input - GraphMin) * 25) / ((GraphMax - GraphMin) / 25));
        }
        Xstep = (tiempo * 25) + 38; //El Xstep es la posición en X que aumentará
        //a meidida que el tiempo aumenta.

        g.fillOval(Xstep - 2, Ystep - 2, 5, 5); //Se crea un puntito en la gráfica
        g.drawLine(LastX, LastY, Xstep, Ystep); //Se dibuja una línea del punto
        //anterior al punto actual

        //Se calcula el 0 de la gráfica. ESTA LINEA LA MOVEREMOS DE AQUÍ
        zero = cero - (((0 - GraphMin) * 25) / ((GraphMax - GraphMin) / 25));

        //Se cambia a color negro
        g.setColor(Color.black);

        //Se dibujan las rayitas sobre el eje X
        g.drawLine(Xstep, zero + 3, Xstep, zero - 5);

        if (tiempo > 0) {
            //Si y solo si el tiempo es mayor a 0...
            String tamaño; //Cantidad de caracteres del numero
            int number; //Numero en el eje X
            int Xpos = 0; //Posicion horizontal del numero
            number = time * frecuencia; //Se calcula el numero.
            //Necesitamos declarar la variable frecuencia como int.

            tamaño = "" + number; //Se hace lo mismo que con el eje Y


            switch (tamaño.length()) {
                case 1:
                    Xpos = 3;
                    break;
                case 2:
                    Xpos = 6;
                    break;
                case 3:
                    Xpos = 9;
                    break;
                case 4:
                    Xpos = 12;
                    break;
            }
//Se dibuja el número que se colocará en las rayitas en el eje X
            g.drawString(number + "", Xstep - Xpos, zero + 15);
        }

        g.setColor(Color.gray); //Se cambia el color a gris

        //Se hacen líneas intermitentes desde el eje X hasta el punto
        for (int i = 0; i <= zero - Ystep; i++) {

            if (i % 5 == 0) {
                g.fillOval(Xstep, zero - i, 1, 1);
            }
        }
//Se hacen lineas intermitentes del eje Y hasta el punto
        for (int i = 0; i <= Xstep - 38; i++) {

            if (i % 5 == 0) {
                g.fillOval(38 + i, Ystep, 1, 1);
            }
        }

        tiempo++;
        time++;
        //Se aumenta el tiempo

        //Si el tiempo sobrepasa la cantidad de datos que caben en el ancho de
        //la graficadora, entonces se resetea la gráfica. Se dibuja un cuadrado blanco
        //se redibuja la malla y se empieza de nuevo.
        if (tiempo == (ancho / 25) - 8) {
            g.setColor(Color.white);
            g.fillRect(0, 0, ancho, alto);
            setGrid();
            tiempo = 0;
            Xstep = 0;
            LastX = 38;
            Ystep = 0;
            LastY = 0;
        }
    }

    public void MaxMin() {
        int Max = 0;
        int Min = 0;
        int factor;
        //Se leen los valores máximos y mínimos
        Max = Integer.parseInt(jTextFieldMax.getText());
        Min = Integer.parseInt(jTextFieldMin.getText());
        //Debido a que estamos usando cuadros de 25 en 25, es mejor trabajar con números que sumen 25
        //Por ejemplo 13-(-12), o bien números múltiplos de 25
        //Aquí uno logra que cualquier dato que meta el usuario se estandarice a este patrón
        //Si la suma del máximo y el mínimo no es 25, ella arreglará las cosas para que el máximo
        // y el mínimo sean múltiplos de 25 y evitar tener que usar decimales en los números
        // de las rayitas de los ejes.
        if ((Max - Min) != 25) {
            if (Max < 25) {
                if (Max != 0) {
                    Max = -25;
                }
            } else {
                if ((Max % 25) != 0) {
                    factor = Max / 25;
                    factor++;
                    Max = factor * 25;
                }
            }

            if (Min > -25) {
                if (Min != 0) {
                    Min = -25;
                }

            } else {
                if ((Min % 25) != 0) {
                    factor = Math.abs(Min / 25);
                    factor++;
                    Min = factor * 25;
                    Min = Min * -1;
                }
            }



        }
        GraphMax = Max;
        GraphMin = Min;
        //Se estandariza y se colocan los resultados en los jTextField
        jTextFieldMax.setText(GraphMax + "");
        jTextFieldMin.setText(GraphMin + "");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelGrafica = new javax.swing.JPanel();
        jPanelControles = new javax.swing.JPanel();
        jButtonGraficar = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jButtonPausa = new javax.swing.JButton();
        jButtonReanudar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxFrecuencia = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldEjeX = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldEjeY = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDatos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldMax = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldMin = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelGrafica.setBackground(new java.awt.Color(255, 255, 255));
        jPanelGrafica.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelGraficaLayout = new javax.swing.GroupLayout(jPanelGrafica);
        jPanelGrafica.setLayout(jPanelGraficaLayout);
        jPanelGraficaLayout.setHorizontalGroup(
            jPanelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1442, Short.MAX_VALUE)
        );
        jPanelGraficaLayout.setVerticalGroup(
            jPanelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 589, Short.MAX_VALUE)
        );

        jPanelControles.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonGraficar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonGraficar.setText("Graficar");
        jButtonGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGraficarActionPerformed(evt);
            }
        });

        jButtonReset.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonReset.setText("Reset");
        jButtonReset.setEnabled(false);
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jButtonPausa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonPausa.setText("Pausa");
        jButtonPausa.setEnabled(false);
        jButtonPausa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPausaActionPerformed(evt);
            }
        });

        jButtonReanudar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonReanudar.setText("Reanudar");
        jButtonReanudar.setEnabled(false);
        jButtonReanudar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReanudarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Tiempo: ");

        jComboBoxFrecuencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxFrecuencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.5 segundos", "1 segundo", "2 segundos", "3 segundos", "4 segundos", "5 segundos", "6 segundos", "7 segundos", "8 segundos", "9 segundos", "10 segundos" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Título eje X");

        jTextFieldEjeX.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldEjeX.setText("Tiempo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Título eje Y");

        jTextFieldEjeY.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Eje X", "Eje Y"
            }
        ));
        jScrollPane1.setViewportView(jTableDatos);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Máximo de la gráfica: ");

        jTextFieldMax.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldMax.setText("100");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Mínimo de la gráfica: ");

        jTextFieldMin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldMin.setText("0");

        javax.swing.GroupLayout jPanelControlesLayout = new javax.swing.GroupLayout(jPanelControles);
        jPanelControles.setLayout(jPanelControlesLayout);
        jPanelControlesLayout.setHorizontalGroup(
            jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelControlesLayout.createSequentialGroup()
                        .addComponent(jButtonGraficar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelControlesLayout.createSequentialGroup()
                        .addComponent(jButtonPausa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonReanudar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .addGroup(jPanelControlesLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFrecuencia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTextFieldEjeX)
                    .addComponent(jTextFieldEjeY)
                    .addGroup(jPanelControlesLayout.createSequentialGroup()
                        .addGroup(jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldMax)
                            .addComponent(jTextFieldMin))))
                .addContainerGap())
        );
        jPanelControlesLayout.setVerticalGroup(
            jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEjeX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEjeY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxFrecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGraficar)
                    .addComponent(jButtonReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPausa)
                    .addComponent(jButtonReanudar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jPanelGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(147, Short.MAX_VALUE)
                        .addComponent(jPanelGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jPanelControles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGraficarActionPerformed

        try { //Meteremos todo dentro de un try para que se detenga la ejecución si surge
            //algun error


            //Se gestionan los posibles errores...
            //Si algún campo está vacío o si los valores máximos y mínimos no están bien o si son iguales
            if (jTextFieldEjeX.getText().equals("")) {
                jTextFieldEjeX.grabFocus();
                throw new Exception("Introduzca el nombre del eje X");
            }
            if (jTextFieldEjeY.getText().equals("")) {
                jTextFieldEjeY.grabFocus();
                throw new Exception("Introduzca el nombre del eje Y");
            }
            if (jTextFieldMax.equals("")) {
                jTextFieldMax.grabFocus();
                throw new Exception("Introduzca el máximo de la gráfica");
            }
            if (jTextFieldMin.equals("")) {
                jTextFieldMin.grabFocus();
                throw new Exception("Introduzca el mínimo de la gráfica");
            }
            if (Integer.parseInt(jTextFieldMin.getText()) > 0) {
                jTextFieldMax.grabFocus();
                throw new Exception("El mínimo de la gráfica no puede ser mayor a 0");
            }
            if (Integer.parseInt(jTextFieldMax.getText()) < 0) {
                jTextFieldMin.grabFocus();
                throw new Exception("El máximo de la gráfica no puede ser menor a 0");
            }
            if (Integer.parseInt(jTextFieldMax.getText()) == Integer.parseInt(jTextFieldMin.getText())) {
                jTextFieldMin.grabFocus();
                throw new Exception("El máximo y el mínimo no pueden ser iguales");
            }


            //Se deshabilitan todos los controles para evitar problemas 
            jTextFieldMax.setEnabled(false);
            jTextFieldMin.setEnabled(false);
            jComboBoxFrecuencia.setEnabled(false);
            jTextFieldEjeX.setEnabled(false);
            jTextFieldEjeY.setEnabled(false);

            jButtonGraficar.setEnabled(false);
            //Se lee la frecuencia desde la caja de combo
            frecuencia = jComboBoxFrecuencia.getSelectedIndex();
            //Se leen y verifican los máximos y mínimos
            MaxMin();
            //Se habilita a Arduino para el envío de datos
            EnviarDatos("b");

            //Se le indica a Arduino la frecuencia con la que estará enviando datos
            if (frecuencia != 10) {
                EnviarDatos(frecuencia + "");
            } else {
                EnviarDatos("a");
            }
            int distancia=Integer.parseInt(jTextFieldMax.getText())/25;
     
            if (distancia<10){
                EnviarDatos("0"+distancia);
            }
            else
            {
                EnviarDatos(""+distancia);
            }
            //Se establece los ejes y el cuadriculado
            setGrid();
            //Se habilitan los botones para pausar y para resetear.
            jButtonPausa.setEnabled(true);
            jButtonReset.setEnabled(true);

        } catch (Exception e) {
            //Se abre una ventana de mensaje si es que se produce algún tipo de error
            JOptionPane.showMessageDialog(null, e);

        }

    }//GEN-LAST:event_jButtonGraficarActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed

        //Se dibuja un cudrado blanco que elimine todo lo que halla en el panel
        g.setColor(Color.white);
        g.fillRect(0, 0, ancho, alto);
//Se resetean todos los contadores 
        tiempo = 0;
        time = 0;
        Xstep = 0;
        LastX = 38;
        Ystep = 0;
        LastY = 0;
        //Se deshabilita a Arduino para que ya no envíe datos
        EnviarDatos("c");


        //Se eliminan todas las filas de la jTableDatos
        int f = jTableDatos.getRowCount() - 1;
        for (int i = 0; i <= f; i++) {
            modelo.removeRow(0);
        }

        //Se habilitan y deshabilitan los botones necesarios.
        jButtonGraficar.setEnabled(true);
        jButtonPausa.setEnabled(false);
        jButtonReset.setEnabled(false);
        jTextFieldMax.setEnabled(true);
        jTextFieldMin.setEnabled(true);
        jComboBoxFrecuencia.setEnabled(true);
        jTextFieldEjeX.setEnabled(true);
        jTextFieldEjeY.setEnabled(true);
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jButtonPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPausaActionPerformed
        //Se deshabilita a Arduino para que no envíe datos
        EnviarDatos("c");
        //Se habilitan y deshabilitan los controles
        jButtonReanudar.setEnabled(true);
        jButtonPausa.setEnabled(false);
    }//GEN-LAST:event_jButtonPausaActionPerformed

    private void jButtonReanudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReanudarActionPerformed

        //Se rehabilita a Arduino para el envío de datos

        EnviarDatos("b");

        //Se rehabilitan los controles
        jButtonReanudar.setEnabled(false);
        jButtonPausa.setEnabled(true);


    }//GEN-LAST:event_jButtonReanudarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Windows().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonGraficar;
    private javax.swing.JButton jButtonPausa;
    private javax.swing.JButton jButtonReanudar;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JComboBox jComboBoxFrecuencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanelControles;
    private javax.swing.JPanel jPanelGrafica;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDatos;
    private javax.swing.JTextField jTextFieldEjeX;
    private javax.swing.JTextField jTextFieldEjeY;
    private javax.swing.JTextField jTextFieldMax;
    private javax.swing.JTextField jTextFieldMin;
    // End of variables declaration//GEN-END:variables
}
