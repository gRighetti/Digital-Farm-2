#include "DHT.h"            //cargamos la librería DHT
#include <LiquidCrystal.h>  //libreria pantalla
#include <Servo.h>          // libreria servo
#include <EEPROM.h>         //libreria memoria interna atmel

//PANTALLA LCD
// initialize the library with the numbers of the interface pins
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

//SERVO
Servo myservo;              // create servo object to control a servo 
                            // twelve servo objects can be created on most boards
 int pos = 0;               // variable to store the servo position 

 

//SENSOR DE TEMPERATURA
#define DHTPIN 13           //Seleccionamos el pin en el que se //conectará el sensor
#define DHTTYPE DHT11       //Se selecciona el DHT11 (hay //otros DHT)
DHT dht(DHTPIN, DHTTYPE);   //Se inicia una variable que será usada por Arduino para comunicarse con el sensor

//MEMORIA EEPROM
int direccion = 0;          //Se crea una variable con el valor de la posición de memoria en la que se va a almacenar el byte.

//PULSADORES
const int BotonComienzoPin = 22; //Pin al que esta conectado el boton de comienzo de ciclo
const int BotonTransmisionPin = 23; //Pin al que esta conectado el boton de comienzo de transmicion

int EstadoBotonComienzo = 0;    //varialbes usadas para la lectura del pulsador
int EstadoBotonTransmision = 0;


//*************************************** CONFIGURACION ***************************************************************************************
void setup() {

   //CONFIGURACION PUERTO SERIE 
   Serial.begin(9600);      //Se inicia la comunicación serial 
   
   //CONFIGURACION SENSOR HUMEDAD 
   dht.begin();             //Se inicia el sensor

  //CONFIGURACION LCD
  // set up the LCD's number of columns and rows:
  lcd.begin(16, 2);
  // Print a message to the LCD.
  lcd.print("Digital farm 2");

  //CONFIGURACION SERVO
  myservo.attach(9);  // attaches the servo on pin 9 to the servo object

  //CONFIGURACION DE PULSADORES
  pinMode(BotonComienzoPin, INPUT);   //configuramos los pulsadores como entrada, 5v-->pulsador-->pinEntrada-->Resis(10k)-->0v (Pull-Down)
  pinMode(BotonTransmisionPin,INPUT);

  
}
//***************************************//VARIABLES//**************************************************************************************************************************
float humedad1;           //Se lee la humedad
float temperatura1 ;        //Se lee la temperatura
int sensorValue;
float temperatura2=0;  
float humedad2;
int contadorCycle=0;
byte valorEEPROM;
int boton;
//***************************************//BUCLE PRINCIPAL//**********************************************************************************************************

void loop() {
 
 EstadoBotonComienzo = digitalRead(BotonComienzoPin);        //Guardo el estado del pulsador 
 EstadoBotonTransmision = digitalRead(BotonTransmisionPin);  //
 

if(EstadoBotonComienzo==1){
  
  serialEvent();
 
 
 if(boton== 49){
  Serial.println("Start Cycle");
 

  for(int i=0;i<5;i++){
    servoDown();
    delay(1000);
    measuringCycle();
    saveData();
    servoUp();
    lcdPrint();
    
 
  Serial.print("cycle:");
  Serial.println(contadorCycle);
  Serial.print(humedad1);
    Serial.print("; ");
    Serial.print(temperatura1);
    Serial.print("; ");
    Serial.print(humedad2);
    Serial.print("; ");
    Serial.println(temperatura2);
  
  }
  contadorCycle=0;
  boton=0;
  Serial.println("Finish cycle");
 }
 
 if(boton == 57){
   Serial.println("Start Serie");
  txSerie();
  direccion=0;
  
  boton=0;
  Serial.println("Finish Serie");
 }
  

}
}
void serialEvent() {
  while (Serial.available()) {
  
   boton = (int)Serial.read();
   
    
    Serial.print("boton: ");
    Serial.println(boton);
  }
}





