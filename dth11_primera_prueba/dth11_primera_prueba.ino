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
int Direccion = 0;          //Se crea una variable con el valor de la posición de memoria en la que se va a almacenar el byte.

byte Val1;                  //Se crean una variables para leer los valores de la memoria EEPROM
byte Val2;


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
  lcd.print("Digital farm");

  //CONFIGURACION SERVO
  myservo.attach(9);  // attaches the servo on pin 9 to the servo object
}
//***************************************//BUCLE PRINCIPAL//**************************************************************************************************************************
void loop() {

//***************************************VARIABLES Y MATEMATICA************************************************************************************

float h = dht.readHumidity();           //Se lee la humedad
float t = dht.readTemperature();        //Se lee la temperatura
float t2=0;                             //  LA TEMPERATURA 2 YA SEA CALCULADA O MEDIDA DE ALGUN SENSOR               <--------- //¡CAMBIAR!//

// read the input on analog pin 0:
  int sensorValue = analogRead(A0);
  // Convert the analog reading (which goes from 0 - 1023) to a voltage (0-100%):
  float humedad2 = sensorValue * (100 / 1023.0);
  // print out the value you read:



//*************************************PANTALLA***Se imprimen las variables***************************************************************************
lcd.setCursor(0, 0);
lcd.print("humedad=");
lcd.setCursor(0,1);
lcd.print(h);

delay (2000);
lcd.clear();
//Serial.println(h);
lcd.setCursor(0, 0);
lcd.print("Temperatura: ");

lcd.setCursor(0,1);
lcd.print(t);
//Serial.println(t);
delay(2000);                  //Se espera 2 segundos para seguir leyendo //datos
lcd.clear();

lcd.setCursor(0, 0);
lcd.print("humedad2=");
lcd.setCursor(0,1);
lcd.print(humedad2);
delay(2000);
lcd.clear();


/*
//****************************SERVO*** hay que definir condiciones para bajar o subir el servo*******************************************************
while(condicion){
  delay(1000);
  
for(pos = 0; pos <= 180; pos += 1) // goes from 0 degrees to 180 degrees 
  {                                  // in steps of 1 degree 
    myservo.write(pos);              // tell servo to go to position in variable 'pos' 
    delay(15);// waits 15ms for the servo to reach the position 
  }
               } 
  
while(condicion2){  
  for(pos = 180; pos>=0; pos-=1)     // goes from 180 degrees to 0 degrees 
  {                                
    myservo.write(pos);              // tell servo to go to position in variable 'pos' 
    delay(15);                       // waits 15ms for the servo to reach the position 
  } 
                 }
                 */

//*************************************************GUARDA LOS DATOS*************************************************************************************


EEPROM.write(Direccion, (byte) h);   //casteo cada variable para que solo ocupen un byte
Direccion+1;                         //avanzo un lugar en la posicion de memoria
EEPROM.write(Direccion,(byte) t);
Direccion+1;
EEPROM.write(Direccion,(byte) sensorValue);
Direccion+1;
EEPROM.write(Direccion,(byte)t2);                                 
Direccion+1;  //avanzo un lugar en la posicion de memoria para la proxima vuelta;



// *****************************************************************************FIN BUCLE PRINCIPAL*********************************************************************************


}



