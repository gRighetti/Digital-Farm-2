void avanzar(){
  //Preparamos la salida para que el motor 1 gire en un sentido derecho
  digitalWrite (IN1, HIGH);
  digitalWrite (IN2, LOW);
  
  //Preparamos la salida para que el motor 2 gire en un sentido izquierdo
  digitalWrite (IN3, LOW);
  digitalWrite (IN4, HIGH);
  ultraSonido();                                                // se llama a la funcion ultrasonido para saber si hay algo adelante, o no 
  Serial.print(distancia);
  encoders();                                                   // se inicializan los encoders 
   //Serial.print(distancia);
  while(distanciaEncoders < distanciaRecorrida){                // se hace un bucle hasta que se termina de completar la distancia total recorrida
     encoders();                                                // se mira la distancia de los encoders 
     ultraSonido();                                             // se mira la distancia de los objetos al frente
     
     // lo de abajo borrar 
     Serial.print("encoderRight:  ");
     Serial.println(encoderRight);
     Serial.print("encoderLeft:  ");
     Serial.println(encoderLeft);
     Serial.println(distancia);
     delay(500);
     
     
     //**************************************correccion de la direccion *********************************************************
     int difEncoders = abs(encoderRight-encoderLeft);
      if (((abs(encoderRight-encoderLeft)) > diferenciaEncoders) && (distancia > distanciaULtrasonido)){           // se mira que la diferencia entre los valores medidos por los encoders no sea mayor a la establecida
          if(encoderRight> encoderLeft){                                                                           // si la diferencia es mayor se determina cual avanzo mas para poder corregir      
            Serial.println("correcion Right");                                                                     // el encoder derecho avanzo mas que el izquierdo 
            analogWrite(ENRight,velocidadReduccionRight);                                                          // se reduce la velocidad del motor derecho un determinado tiempo
            delay(tiempoCorrecion * difEncoders);
            analogWrite(ENRight,velocidadMaximaMotorRight);
          }else{
            Serial.println("correcion Left");                                                                       // el encoder izquierdo avanzo mas que el derecho 
                analogWrite(ENLeft,velocidadReduccionLeft);                                                         // se reduce la velocidad del motor izquierdo un determinado tiempo
                delay(tiempoCorrecion * difEncoders);
                analogWrite(ENLeft,velocidadMaximaMotorLeft);  
                      
          }
        
      }
  //llama a la funcion acelerar cuando inicializa el bucle o si se detiene sin completar la distancia total establecida 
    if(falgAcelerar==true && distancia  > distanciaULtrasonido){
      falgAcelerar=false;
      acelerarMotor();
      
      
    }
    
    // comprueba se hay un objeto adelante y si es correcto frena 
    if (distancia < distanciaULtrasonido && falgAcelerar==false){
      frenarMotor();
      falgAcelerar=true;    
    }
      
    }
    
    
    // fin del Bucle while    
      frenarMotor();                           // frena los motores cuando completo la distancia recorrida
      flagEncoders=true;                      // Setea la bandera para borrar la cuenta de los encoders para que el proximo ciclo comienze correctamente
      encoders();                                  
      falgAcelerar=true;                       
  
      // se bajan las salidas de control de los motores por proteccion 
      digitalWrite (IN1, LOW);               
      digitalWrite (IN2, LOW);
      digitalWrite (IN3, LOW);
      digitalWrite (IN4, LOW);
    
  }
  
  

