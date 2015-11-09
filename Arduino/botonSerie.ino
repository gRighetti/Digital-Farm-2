
boolean botonSerie(){
estadoBotonSerie = digitalRead(BotonTransmisionPin);

if(estadoBotonSerie==HIGH){
    if(flagSerie==true){
        flagSerie=false;
      }else{
        flagSerie=true;
          }
    }
return flagSerie;
}

