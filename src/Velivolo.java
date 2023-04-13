
import java.io.Serializable;

public class Velivolo implements Serializable{
    public String modello;
    public String registrazione;
    public Equipaggio equipaggio;
    public Motore motore;

    public Velivolo(String mod, String reg, Equipaggio eq, Motore mot) {
        modello = mod;
        registrazione = reg;
        equipaggio = eq;
        motore = mot;
    }
    
    public String toString(){
        return modello+" "+registrazione;
    }

    /*
    Il metodo equals è stato ridefinito per poter trovare un elemento in una lista.
    Un elemento deserializzato da binario non risultava uguale ad una sua versione deserializzata da xml.
    */
    public boolean equals(Object obj) {
        if(obj instanceof Velivolo){
            Velivolo v = (Velivolo)obj;
            return (v.modello.equals(modello)&&v.registrazione.equals(registrazione));
        }
        return false;
    }
    
    public String getInfo(){
        return (equipaggio == Equipaggio.PLURIMO)?"Plurimo":
                (motore == Motore.MONO)?"Singolo Mono-motore":
                "Singolo Multi-motore";
    }
    
}
