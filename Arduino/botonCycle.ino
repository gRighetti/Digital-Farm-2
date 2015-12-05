
boolean botonCycle(){
 
estadoBotonCycle = digitalRead(BotonComienzoPin);                      // se comprueba el estado del pin 

if(estadoBotonCycle==HIGH){                                            // si esta en alto esta activado 
   // se configuran las banderas 
    if(flagCycle==true){
        flagCycle=false;
      }else{
        flagCycle=true;
          }
    }
return flagCycle;
}

