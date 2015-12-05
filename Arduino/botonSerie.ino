
boolean botonSerie(){                                                            // Se mira el estado del pin y si se activo se lenatan las banderas para indicar dicha accion 
estadoBotonSerie = digitalRead(BotonSeriePin);

if(estadoBotonSerie==HIGH){
    if(flagSerie==true){
        flagSerie=false;
      }else{
        flagSerie=true;
          }
    }
return flagSerie;
}

