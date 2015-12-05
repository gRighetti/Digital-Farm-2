
void measuringCycle() {

 if(contadorCycle < 5){ 
   contadorCycle ++;                          // variable para saber en que ciclo esta trabajando
   humedad1 = dht.readHumidity();             //Se lee la humedad dth 
   temperatura1 = dht.readTemperature();      //Se lee la temperatura dth
 //  temperatura2=0;                            //  LA TEMPERATURA 2 YA SEA CALCULADA O MEDIDA DE ALGUN SENSOR 

  // read the input on analog pin 0:
  sensorValue = analogRead(A0);
  // Convert the analog reading (which goes from 0 - 1023) to a voltage (0-100%):
  humedad2 = sensorValue * (100 / 1023.0);

 

 }



}
