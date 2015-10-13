
#include "DHT.h" //cargamos la librería DHT
#include <LiquidCrystal.h>

// initialize the library with the numbers of the interface pins
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

#define DHTPIN 13 //Seleccionamos el pin en el que se //conectará el sensor
#define DHTTYPE DHT11 //Se selecciona el DHT11 (hay //otros DHT)
DHT dht(DHTPIN, DHTTYPE); //Se inicia una variable que será usada por Arduino para comunicarse con el sensor
void setup() {
Serial.begin(9600); //Se inicia la comunicación serial 
dht.begin(); //Se inicia el sensor


// set up the LCD's number of columns and rows:
  lcd.begin(16, 2);
  // Print a message to the LCD.
  lcd.print("Digital farm");
}
void loop() {
float h = dht.readHumidity(); //Se lee la humedad
float t = dht.readTemperature(); //Se lee la temperatura

// read the input on analog pin 0:
  int sensorValue = analogRead(A0);
  // Convert the analog reading (which goes from 0 - 1023) to a voltage (0-100%):
  float humedad2 = sensorValue * (100 / 1023.0);
  // print out the value you read:



//Se imprimen las variables
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
lcd.print(h);
//Serial.println(t);
delay(2000); //Se espera 2 segundos para seguir leyendo //datos
lcd.clear();

lcd.setCursor(0, 0);
lcd.print("humedad2=");
lcd.setCursor(0,1);
lcd.print(humedad2);
delay(2000);
lcd.clear();


}



