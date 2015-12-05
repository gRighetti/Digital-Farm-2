

void frenarMotor(){
  
      analogWrite(ENLeft,velocidadMaximaMotorLeft-20);
      analogWrite(ENRight,velocidadMaximaMotorRight-20);
      for(int i=180; i >= 0 ; i--){                                
          analogWrite(ENLeft,i);
          analogWrite(ENRight,i);         
          delay(tiempoFrenado);                     
  } 
 Serial.print("freno"); 
}
