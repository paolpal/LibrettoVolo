import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
La classe RecordVoloBean è usata dalla classe TabellaRecordVolo per visualizzare i dati relativi al singolo record.
*/
public class RecordVoloBean {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty data;
    private final SimpleStringProperty partenza;
    private final SimpleStringProperty arrivo;
    private final SimpleStringProperty velivolo;
    private final SimpleStringProperty equipaggio;
    private final SimpleStringProperty tempoVoloTotale;
    private final SimpleStringProperty nomePic;
    //private SimpleIntegerProperty decolli;
    //private SimpleIntegerProperty atterraggi;
    private final SimpleStringProperty dec_att;
    private final SimpleStringProperty tempiCondizioniVolo;
    private final SimpleStringProperty tempiFunzioniVolo;
    private final SimpleStringProperty osservazioni;
    
    RecordVoloBean(RecordVolo record){
        id = new SimpleIntegerProperty(record.id);
        data = new SimpleStringProperty(record.data.toString());
        partenza = new SimpleStringProperty(record.luogoPartenza +" "+ record.orarioPartenza);
        arrivo = new SimpleStringProperty(record.luogoArrivo +" "+ record.orarioArrivo);
        velivolo = new SimpleStringProperty(record.velivolo.toString());
        equipaggio = new SimpleStringProperty(record.velivolo.getInfo());
        tempoVoloTotale = new SimpleStringProperty(record.tempoVoloTotale.toString());
        nomePic = new SimpleStringProperty(record.nomePIC);
        //atterraggi = new SimpleIntegerProperty(record.atterraggi);
        //decolli = new SimpleIntegerProperty(record.decolli);
        dec_att = new SimpleStringProperty("Dec: "+record.decolli+"\nAtt: "+record.atterraggi);
        tempiCondizioniVolo = new SimpleStringProperty("Notte: "+record.tempoNotte + "\nIFR: "+record.tempoIFR);
        tempiFunzioniVolo = new SimpleStringProperty("PIC: "+record.tempoPIC+" Co: "+record.tempoCopilot+"\nDuale: "+record.tempoDuale+" Istruttore: "+record.tempoIstruttore);
        osservazioni = new SimpleStringProperty(record.osservazioni);
    }
    
    public int getId(){
        return id.get();
    }
    
    public String getData(){
        return data.get();
    }
    
    public String getPartenza(){
        return partenza.get();
    }
    
    public String getArrivo(){
        return arrivo.get();
    }
    
    public String getVelivolo(){
        return velivolo.get();
    }
    
    public String getEquipaggio(){
        return equipaggio.get();
    }
    
    public String getTempoVoloTotale(){
        return tempoVoloTotale.get();
    }
    
    public String getNomePic(){
        return nomePic.get();
    }
    /*
    public int getAtterraggi(){
        return atterraggi.get();
    }
    
    public int getDecolli(){
        return decolli.get();
    }
    */
    public String getDec_att(){
        return dec_att.get();
    }
    
    public String getTempiCondizioniVolo(){
        return tempiCondizioniVolo.get();
    }
    
    public String getTempiFunzioniVolo(){
        return tempiFunzioniVolo.get();
    }
    
    public String getOsservazioni(){
        return osservazioni.get();
    }
}
