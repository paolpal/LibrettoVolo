import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

/*
La classe RecordVolo è un'astrazione del record volo effettivo.
E' necessaria per limitare il numero di parametri che altri metodi avrebbero dovuto necessitare.
Il suo scopo è rendere i dati che conserva facilmente accessibili, per questo i suoi parametri sono pubblici.
Possiede un campo id destinato al'id del record nel database, un nuovo record ha id -1.
*/
public class RecordVolo implements Serializable{
    public Integer id;
    public LocalDate data;
    public String luogoPartenza;
    public LocalTime orarioPartenza;
    public String luogoArrivo;
    public LocalTime orarioArrivo;
    public Velivolo velivolo;
    public LocalTime tempoVoloTotale;
    public String nomePIC;
    public int decolli;
    public int atterraggi;
    public LocalTime tempoIstruttore;
    public LocalTime tempoDuale; 
    public LocalTime tempoCopilot; 
    public LocalTime tempoPIC; 
    public LocalTime tempoIFR; 
    public LocalTime tempoNotte; 
    public String osservazioni;
    
    public RecordVolo(int id, LocalDate data, String luogoPartenza, LocalTime orarioPartenza, String luogoArrivo, LocalTime orarioArrivo, Velivolo velivolo, String nomePIC, int decolli, int atterraggi, LocalTime tempoIstruttore, LocalTime tempoDuale, LocalTime tempoCopilot, LocalTime tempoPIC, LocalTime tempoIFR, LocalTime tempoNotte, String osservazioni){
        this.id = id;
        this.data = data;
        this.luogoPartenza = luogoPartenza;
        this.orarioPartenza = orarioPartenza;
        this.luogoArrivo = luogoArrivo;
        this.orarioArrivo = orarioArrivo;
        this.velivolo = velivolo;
        tempoVoloTotale = durata(orarioPartenza, orarioArrivo);
        this.nomePIC = nomePIC;
        this.decolli = decolli;
        this.atterraggi = atterraggi;
        this.tempoIstruttore = tempoIstruttore;
        this.tempoDuale = tempoDuale;
        this.tempoCopilot = tempoCopilot;
        this.tempoPIC = tempoPIC;
        this.tempoIFR = tempoIFR;
        this.tempoNotte = tempoNotte;
        this.osservazioni = osservazioni;
    }
    
    public RecordVolo(LocalDate data, String luogoPartenza, LocalTime orarioPartenza, String luogoArrivo, LocalTime orarioArrivo, Velivolo velivolo, String nomePIC, int decolli, int atterraggi, LocalTime tempoIstruttore, LocalTime tempoDuale, LocalTime tempoCopilot, LocalTime tempoPIC, LocalTime tempoIFR, LocalTime tempoNotte, String osservazioni){
        this(-1, data, luogoPartenza, orarioPartenza, luogoArrivo, orarioArrivo, velivolo, nomePIC, decolli, atterraggi, tempoIstruttore, tempoDuale, tempoCopilot, tempoPIC, tempoIFR, tempoNotte, osservazioni);
    }
    
    /*
    Per ottenere una durata in formato hh:mm ho calcolato la durata in minuti
    sommandola poi all'istanza LocalTime.MIN (00:00).
    */
    public static LocalTime durata(LocalTime da, LocalTime a){
        long durata = da.until(a, ChronoUnit.MINUTES);
        return LocalTime.MIN.plus(durata,ChronoUnit.MINUTES);
    }

}
