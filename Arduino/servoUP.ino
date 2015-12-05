
void servoUp(){


//****************************SERVO**********************************************************

  
for(pos = 0; pos <= 90; pos += 1)                           // goes from 0 degrees to 180 degrees 
  {                                                          // in steps of 1 degree 
    myservo.write(pos);                                      // tell servo to go to position in variable 'pos' 
    delay(15);                                               // waits 15ms for the servo to reach the position 
  }
               

                 
}
                
