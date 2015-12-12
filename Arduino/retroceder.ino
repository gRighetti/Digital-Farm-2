void retroceder(){
  //Preparamos la salida para que el motor 1 gire en un sentido 
  digitalWrite (IN1, LOW);
  digitalWrite (IN2, HIGH);
  
  //Preparamos la salida para que el motor 2 gire en un sentido 
  digitalWrite (IN3, HIGH);
  digitalWrite (IN4, LOW);
  // aceleramos para que inicie el movimiento
  acelerarMotor();
  // lo mantenemos un determinado tiempo 
  delay(tiempoGiro);
  // frenamos para inicializar el ciclo de regreso y se acomodan las banderas 
  frenarMotor();
  flagEncoders=true;
  falgAcelerar=true; 
  encoders();
 // cambiamos el sentido de giro de los motores 
 //Preparamos la salida para que el motor 1 gire en un sentido
  digitalWrite (IN1, LOW);
  digitalWrite (IN2, HIGH);
  //Preparamos la salida para que el motor 2 gire en un sentido
  digitalWrite (IN3, HIGH);
  digitalWrite (IN4, LOW);

  while(abs(distanciaEncoders) <= (abs(distanciaRecorrida *5 ))){                 // se hace un bucle hasta que se termina de completar la distancia total recorida
     encoders();                                                                  // se mira la distancia de los encoders 
     ultraSonido();                                                               // se mira la distancia de los objetos al frente
Serial.println(distanciaEncoders);
     //**************************************correccion de la direccion *********************************************************
    // int restaEncoders = encoder1-encoder2;
      if (((abs(encoderRight-encoderLeft)) >= diferenciaEncoders) && (distancia >= distanciaULtrasonido)){           // se mira que la diferencia entre los valores medidos por los encoders no sea mayor a la establecida
          if(encoderRight > encoderLeft){                                                                           // si la diferencia es mayor se determina cual avanzo mas para poder corregir      
            Serial.println("correcion Right");                                                                      // el encoder derecho avanzo mas que el izquierdo 
            

           analogWrite(ENLeft,velocidadReduccionLeft);                                                          // se reduce la velocidad del motor izquierdo un determinado tiempo
                delay(tiempoCorrecion);
                analogWrite(ENLeft,velocidadMaximaMotorLeft);
          
          }else{
            Serial.println("correcion Left");                                                                       // el encoder izquierdo avanzo mas que el derecho 
              analogWrite(ENRight,velocidadReduccionRight);                                                           // se reduce la velocidad del motor derecho un determinado tiempo
            delay(tiempoCorrecion);
            analogWrite(ENRight,velocidadMaximaMotorRight);  
                      
          }
        
      }
  //llama a la funcion acelerarcuando inicializa el bucle o si se detiene sin completar la distancia total establecida 
    if(falgAcelerar==true && distancia  >= distanciaULtrasonido){
      falgAcelerar=false;
      acelerarMotor();
      
      
    }
    
    // comprueba se hay un objeto adelante y si es correcto frena 
    if (distancia <= distanciaULtrasonido && falgAcelerar==false){
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
  
  

