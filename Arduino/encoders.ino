//Hay que poner NO interruptor y interruptor para poder trabajar sino jode la interrpcion del encoders
// Falta calcular cuantos centimetros son cada valor que cambia el encoders

void encoders(){

if(flagEncoders==true){
  encRight.write(0);                                      // se setea la cuenta de los encoders en 0 para los casos necesarios como un reinicio de ciclo
  encLeft.write(0);
  flagEncoders=false;

}else{
encoderRight=encRight.read();                             // se lee la cuenta de los encoders para poder determina la distancia recorrida por cada uno
encoderLeft=encLeft.read();                              // ademas se utiliza dicha cuanta para la direccion   

}

// se calcula la distancia recorrida real 
// se mide en 10 cm cuantas cuentas del los encoders corresponde 
// con ese numero se hace la siguiente relacion 
distanciaEncoders=encoderRight*9.5/50; //Falta completar medir en 10 cm cuantas cuentas hace el encoders 
Serial.print("disstancia Encoders: ");
Serial.println(distanciaEncoders);



}
