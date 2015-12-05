void serialEvent() {                                            //funcion para recibir informacion del puerto Serie
  while (Serial.available()) {
   boton = (int)Serial.read();                                   //valor obtenido del puerto Serie
    Serial.print("boton: ");
    Serial.println(boton);
  }
}

