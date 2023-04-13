
import java.time.*;
import java.time.temporal.*;
import java.util.*;

/*
La classe InterpreteTempiVolo per ogni funzione e condizione di volo,
data una lista di record volo restituisce un subtotale.
Fornisce anche il totale delle ore di volo.

I risultati sono forniti in minuti, in maniera da ridurre la perdita di informazione.
*/

public class InterpreteTempiVolo {
    List<RecordVolo> listaRecord;

    public InterpreteTempiVolo(List<RecordVolo> listaRecord) {
        this.listaRecord = listaRecord;
    }
    
    /*
    Per ottenere il tempo si sfrutta la classe LocalTime non come orario, ma come durata, trasformandola in minuti
    tramite il metodo until richiamato sull'istanza LocalTime.MIN (00:00).
    */
    public long getTempoTotaleIFR(){
        long tempoTotale = 0;
        for(RecordVolo record: listaRecord){
            tempoTotale+= (record.tempoIFR!=null)? LocalTime.MIN.until(record.tempoIFR, ChronoUnit.MINUTES): 0;
        }
        return tempoTotale;
    }
    
    public long getTempoTotaleNotte(){
        long tempoTotale = 0;
        for(RecordVolo record: listaRecord){
            tempoTotale+= (record.tempoNotte!=null)? LocalTime.MIN.until(record.tempoNotte, ChronoUnit.MINUTES): 0;
        }
        return tempoTotale;
    }
    
    public long getTempoTotalePIC(){
        long tempoTotale = 0;
        for(RecordVolo record: listaRecord){
            tempoTotale+= (record.tempoPIC!=null)? LocalTime.MIN.until(record.tempoPIC, ChronoUnit.MINUTES): 0;
        }
        return tempoTotale;
    }
    
    public long getTempoTotaleCopilota(){
        long tempoTotale = 0;
        for(RecordVolo record: listaRecord){
            tempoTotale+= (record.tempoCopilot!=null)? LocalTime.MIN.until(record.tempoCopilot, ChronoUnit.MINUTES): 0;
        }
        return tempoTotale;
    }
    
    public long getTempoTotaleDuale(){
        long tempoTotale = 0;
        for(RecordVolo record: listaRecord){
            tempoTotale+= (record.tempoDuale!=null)? LocalTime.MIN.until(record.tempoDuale, ChronoUnit.MINUTES): 0;
        }
        return tempoTotale;
    }
    
    public long getTempoTotaleIstruttore(){
        long tempoTotale = 0;
        for(RecordVolo record: listaRecord){
            tempoTotale+= (record.tempoIstruttore!=null)? LocalTime.MIN.until(record.tempoIstruttore, ChronoUnit.MINUTES): 0;
        }
        return tempoTotale;
    }
    
    public long getTempoTotale(){
        long tempoTotale = 0;
        for(RecordVolo record: listaRecord){
            tempoTotale+= (record.tempoVoloTotale!=null)? LocalTime.MIN.until(record.tempoVoloTotale, ChronoUnit.MINUTES): 0;
        }
        return tempoTotale;
    }
}
