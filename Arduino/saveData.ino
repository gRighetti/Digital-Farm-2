
//*************************************************GUARDA LOS DATOS*************************************************************************************

void saveData(){
lcd.clear();                                            //Se borra la pantalla 
lcd.print("Save Data");                                 //Se indica en pantalla lo que se va a realizar

EEPROM.write(direccion, (byte) humedad1);               //casteo cada variable para que solo ocupen un byte
direccion++;                                            //avanzo un lugar en la posicion de memoria
EEPROM.write(direccion,(byte) temperatura1);
direccion++;
EEPROM.write(direccion,(byte) humedad2);
//direccion++;
//EEPROM.write(direccion,(byte)temperatura2);                                 
direccion++;                                            //avanzo un lugar en la posicion de memoria para la proxima vuelta;
delay(500);
lcd.clear();
lcd.print("Digital farm 2");
delay(500);
}


