
void servoUp(){


//****************************SERVO*** hay que definir condiciones para bajar o subir el servo*******************************************************

  delay(1000);
  
for(pos = 0; pos <= 180; pos += 1) // goes from 0 degrees to 180 degrees 
  {                                  // in steps of 1 degree 
    myservo.write(pos);              // tell servo to go to position in variable 'pos' 
    delay(15);// waits 15ms for the servo to reach the position 
  }
               

                 
}
                
