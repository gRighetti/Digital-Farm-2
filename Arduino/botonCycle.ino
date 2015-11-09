
boolean botonCycle(){
estadoBotonCycle = digitalRead(BotonComienzoPin);

if(estadoBotonCycle==HIGH){
    if(flagCycle==true){
        flagCycle=false;
      }else{
        flagCycle=true;
          }
    }
return flagCycle;
}

