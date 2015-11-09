


void txSerie(){
  lcd.clear();
lcd.print("TxSerie");
  direccion =0;
  for(int j=1;j<6;j++){
                  Serial.print("Ciclo:");
                  Serial.println(j);
                  //Valor de humedad1 guardado en la eeprom
                  valorEEPROM =EEPROM.read(direccion);   //casteo cada variable para que solo ocupen un byte
                  direccion++;  //avanzo un lugar en la posicion de memoria
                  Serial.print("humidity 1: ");
                  Serial.print(valorEEPROM, DEC);
                  Serial.print("; ");
                  delay (1000);
                  //Valor de temperatura1 guardado en la eeprom
                  valorEEPROM =EEPROM.read(direccion);   //casteo cada variable para que solo ocupen un byte
                  direccion++;  //avanzo un lugar en la posicion de memoria
                  Serial.print("temperature 1: ");
                  Serial.print(valorEEPROM, DEC);
                  Serial.print("; ");
                  delay (1000);
                  //Valor de humedad2 guardado en la eeprom
                  valorEEPROM =EEPROM.read(direccion);   //casteo cada variable para que solo ocupen un byte
                  direccion++;  //avanzo un lugar en la posicion de memoria
                  Serial.print("humidity 2: ");
                  Serial.print(valorEEPROM, DEC);
                  Serial.print("; ");
                  delay (1000);
                  //Valor de temperatura2 guardado en la eeprom
                  valorEEPROM =EEPROM.read(direccion);   //casteo cada variable para que solo ocupen un byte
                  direccion++;  //avanzo un lugar en la posicion de memoria
                  Serial.print("temperature 2: ");
                  Serial.println(valorEEPROM, DEC);
                  delay (1000);
                                   
                  
                                                       
                      
                      
  }
  lcd.clear();
lcd.print("Digital farm 2");
  
  
  
}



 
