void acelerarMotor(){
  Serial.println("acelero");
  int t=0;

      for( t=0; t <= 255; t=t+1){                          // se realiza un bucle para acelerar los motores hasta el valor correspondiente
          if(t <= velocidadMaximaMotorRight){              // compara con la varialble con la velocidad maxima del motor derecho para saber si tiene que aumentar el PWM o no 
          analogWrite(ENRight,t);                          //modifica el PWM del motor derecho
          //Serial.println("entro aca");
          //Serial.println(t);
          }
          if(t <= velocidadMaximaMotorLeft){               // compara con la varialble con la velocidad maxima del motor izquierdo para saber si tiene que aumentar el PWM o no 
          analogWrite(ENLeft,t);                           //modifica el PWM del motor izquierdo
          }
        ultraSonido();
        if (distancia <distanciaULtrasonido ){             // si estoy acelerando y detecto algo freno y activo la bandera para volver a acelerar cuando corresponda
        analogWrite(ENLeft,0);                             // frena los motores
        analogWrite(ENRight,0);                            // frena los motores
        falgAcelerar=true;                                 // avtivo la bandera
       // Serial.print("#aaaaa");
        break;                                             // salgo del FOR
        }        
        delay(tiempoAceleracion);                          // tiempo entre valores consecutivos del PWM para acelerar
  } 
          
  
}
