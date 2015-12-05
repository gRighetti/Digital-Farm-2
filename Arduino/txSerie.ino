


void txSerie(){
  lcd.clear();                                               //Se borra la pantalla para luego poder escribirla 
lcd.print("TxSerie");                                        //Se imprime en la pantala lo que va hacer
  direccion =0;                                              //Se selecciona la direccion en donde estan los datos guardados en la memoria eeprom
  for(int j=1;j<6;j++){
                 // Serial.print("Ciclo:");
                 // Serial.println(j);
                 
                  //Valor de humedad1 guardado en la eeprom
                  valorEEPROM =EEPROM.read(direccion);       //Se lee el valor guardado 
                  direccion++;  //avanzo un lugar en la posicion de memoria
                  //Serial.print("humidity 1: ");
                  Serial.print(valorEEPROM);                 // se imprime el valor 
                  Serial.print(",");                         // se usa para que el programa en java detecte cuanto termina un numero
                  delay (200);
                  
                  //Valor de temperatura1 guardado en la eeprom
                  valorEEPROM =EEPROM.read(direccion);       // Se lee el valor guardado 
                  direccion++;                               //avanzo un lugar en la posicion de memoria
                  //Serial.print("temperature 1: ");
                  Serial.print(valorEEPROM);                 // se imprime el valor 
                  Serial.print(",");                         // se usa para que el programa en java detecte cuanto termina un numero
                  delay (200);
                  
                  //Valor de humedad2 guardado en la eeprom
                  valorEEPROM =EEPROM.read(direccion);       //Se lee el valor guardado 
                  direccion++;                               //avanzo un lugar en la posicion de memoria
                  //Serial.print("humidity 2: ");
                  Serial.print(valorEEPROM);                 // se imprime el valor 
                  Serial.print(",");                         // se usa para que el programa en java detecte cuanto termina un numero
                  delay (200);
                  
                  //Valor de temperatura2 guardado en la eeprom
                //  valorEEPROM =EEPROM.read(direccion);      //Se lee el valor guardado 
                //  direccion++;                              //avanzo un lugar en la posicion de memoria
                 // Serial.print("temperature 2: ");
                  //Serial.print(valorEEPROM);                // se imprime el valor 
                  //Serial.print(",");                        // se usa para que el programa en java detecte cuanto termina un numero
                 // delay (200);
                                   
                  
                                                       
                      
                      
  }
  lcd.clear();                                              //Se borra la pantalla para luego poder escribirla
lcd.print("Digital farm 2");                                //Se escribe en la pantalla
  
  
  
}



 
