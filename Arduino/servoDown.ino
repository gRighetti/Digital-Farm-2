  
  void servoDown(){
  
  Serial.println("Bajando Servo");
  for(pos = 90; pos>=0; pos-=1)                     // goes from 90 degrees to 0 degrees 
  {                                
    myservo.write(pos);                              // tell servo to go to position in variable 'pos' 
    delay(15);    // waits 15ms for the servo to reach the position 
    
  } 
                 }
