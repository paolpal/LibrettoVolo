
import java.time.*;
import java.time.format.*;
import java.util.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

/*
La classe ModuloNuovoRecord crea le componenti del modulo d'ingresso dell'applicazione.
Usa metodi privati per creare le varie sotto componenti.
*/
public class ModuloNuovoRecord extends HBox{
    
    private Velivolo[] velivoli;
    private DatePicker data;
    private VBox partenza;
    private TextField l_partenza;
    private TextField o_partenza;
    private VBox arrivo;
    private TextField l_arrivo;
    private TextField o_arrivo;
    private ChoiceBox velScelto;
    private TextField infoEquipaggio;
    private TextField tempoVoloTotale;
    private HBox pic;
    private CheckBox altroPic;
    private TextField nomePic;
    private HBox decAtt;
    private TextField numDec;
    private TextField numAtt;
    private VBox condVolo;
    private HBox funzVolo;
    private TextArea osservazioni;
    private TextField tempoNotte;
    private TextField tempoIfr;
    private TextField tempoPic;
    private TextField tempoDuale;
    private TextField tempoCoPilot;
    private TextField tempoIstruttore;
    
    private final double PICCOLI;
    private final double MEDI;


    public ModuloNuovoRecord(Velivolo[] velivoli) {
        PICCOLI = 50.;
        MEDI = 100.;
        this.velivoli = velivoli;
        data = new DatePicker(LocalDate.now());
        data.setMaxWidth(MEDI+20);
        partenza = new VBox();
        creaFormPartenza();
        arrivo = new VBox();
        creaFormArrivo();
        velScelto = new ChoiceBox(FXCollections.observableArrayList(velivoli));
        infoEquipaggio = new TextField();
        infoEquipaggio.setMouseTransparent(true); // 1
        infoEquipaggio.setFocusTraversable(false); // 2
        infoEquipaggio.setMaxWidth(MEDI);
        tempoVoloTotale = new TextField("00:00");
        tempoVoloTotale.setMaxWidth(PICCOLI);
        tempoVoloTotale.setMouseTransparent(true); // 1
        tempoVoloTotale.setFocusTraversable(false); // 2
        pic = new HBox();
        creaFormPic();
        decAtt = new HBox();
        creaFormDecAtt();
        condVolo = new VBox();
        creaFormCondVolo();
        funzVolo = new HBox();
        creaFormFunzVolo();
        osservazioni = new TextArea();
        osservazioni.setMaxWidth(MEDI);
        osservazioni.setMaxHeight(50);
        
        assegnaComportamenti();
        velScelto.setValue(velivoli[0]);
        
        setPadding(new Insets(15,12,15,12));
        setSpacing(10);
        setMaxWidth(1350);
        setAlignment(Pos.CENTER);
        getChildren().addAll(data, partenza, arrivo, velScelto, infoEquipaggio, tempoVoloTotale, pic, decAtt, condVolo, funzVolo,osservazioni);
    }
    
    private void creaFormPartenza(){
        l_partenza = new TextField();
        l_partenza.setMaxWidth(PICCOLI);
        final Label luogo = new Label("Luogo: ");
        HBox l = new HBox();
        l.setAlignment(Pos.CENTER_RIGHT);
        l.getChildren().addAll(luogo, l_partenza);
        
        o_partenza = new TextField("00:00");
        o_partenza.setMaxWidth(PICCOLI);
        final Label ora = new Label("Ora: ");
        HBox o = new HBox();
        o.setAlignment(Pos.CENTER_RIGHT);
        o.getChildren().addAll(ora, o_partenza);
        
        partenza.setSpacing(5);
        partenza.getChildren().addAll(l, o);
    }
    
    private void creaFormArrivo(){
        l_arrivo = new TextField();
        l_arrivo.setMaxWidth(PICCOLI);
        final Label luogo = new Label("Luogo: ");
        HBox l = new HBox();
        l.setAlignment(Pos.CENTER_RIGHT);
        l.getChildren().addAll(luogo, l_arrivo);
        
        o_arrivo = new TextField("00:00");
        o_arrivo.setMaxWidth(PICCOLI);
        final Label ora = new Label("Ora: ");
        HBox o = new HBox();
        o.setAlignment(Pos.CENTER_RIGHT);
        o.getChildren().addAll(ora, o_arrivo);
        
        arrivo.setSpacing(5);
        arrivo.getChildren().addAll(l, o);
    }
    
    private void creaFormPic(){
        altroPic = new CheckBox();
        nomePic = new TextField("SELF");
        nomePic.setMaxWidth(MEDI);
        pic.setAlignment(Pos.CENTER);
        pic.getChildren().addAll(altroPic, nomePic);
    }
    
    private void creaFormDecAtt(){
        numDec = new TextField("1");
        numDec.setMaxWidth(PICCOLI);
        final Label labelDec = new Label("Decolli");
        VBox dec = new VBox();
        dec.setAlignment(Pos.CENTER);
        dec.getChildren().addAll(labelDec, numDec);
        
        numAtt = new TextField("1");
        numAtt.setMaxWidth(PICCOLI);
        final Label labelAtt = new Label("Atterraggi");
        VBox att = new VBox();
        att.setAlignment(Pos.CENTER);
        att.getChildren().addAll(labelAtt, numAtt);
        
        decAtt.getChildren().addAll(dec, att);
    }

    private void creaFormCondVolo() {
        final Label labelNotte = new Label("Notte: ");
        tempoNotte = new TextField("00:00");
        tempoNotte.setMaxWidth(PICCOLI);
        HBox notte = new HBox();
        notte.setAlignment(Pos.CENTER_RIGHT);
        notte.getChildren().addAll(labelNotte, tempoNotte);
        
        final Label labelIfr = new Label("IFR: ");
        tempoIfr = new TextField("00:00");
        tempoIfr.setMaxWidth(PICCOLI);
        HBox ifr = new HBox();
        ifr.setAlignment(Pos.CENTER_RIGHT);
        ifr.getChildren().addAll(labelIfr, tempoIfr);
        
        condVolo.setSpacing(5);
        condVolo.getChildren().addAll(notte, ifr);
    }

    private void creaFormFunzVolo() {
        final Label labelPic = new Label("PIC: ");
        tempoPic = new TextField("00:00");
        tempoPic.setMaxWidth(PICCOLI);
        HBox f_pic = new HBox();
        f_pic.setAlignment(Pos.CENTER_RIGHT);
        f_pic.getChildren().addAll(labelPic, tempoPic);
        
        final Label labelDuale = new Label("Duale: ");
        tempoDuale = new TextField("00:00");
        tempoDuale.setMaxWidth(PICCOLI);
        HBox duale = new HBox();
        duale.setAlignment(Pos.CENTER_RIGHT);
        duale.getChildren().addAll(labelDuale, tempoDuale);
        
        VBox col1 = new VBox();
        col1.setSpacing(5);
        col1.getChildren().addAll(f_pic, duale);
        
        final Label labelCopilot = new Label("Co-pilota: ");
        tempoCoPilot = new TextField("00:00");
        tempoCoPilot.setMaxWidth(PICCOLI);
        HBox co = new HBox();
        co.setAlignment(Pos.CENTER_RIGHT);
        co.getChildren().addAll(labelCopilot, tempoCoPilot);
        
        final Label labelIstruttore = new Label("Istruttore: ");
        tempoIstruttore = new TextField("00:00");
        tempoIstruttore.setMaxWidth(PICCOLI);
        HBox istruttore = new HBox();
        istruttore.setAlignment(Pos.CENTER_RIGHT);
        istruttore.getChildren().addAll(labelIstruttore, tempoIstruttore);
        
        VBox col2 = new VBox();
        col2.setSpacing(5);
        col2.getChildren().addAll(co, istruttore);
        
        funzVolo.setSpacing(5);
        funzVolo.getChildren().addAll(col1,col2);
    }
    
    /*
    Il metodo getNuovoRecord preleva dall'modulo i dati e quindi crea un'istanza di RecordVolo che restituisce.
    */
    public RecordVolo getNuovoRecord(){
        String nome = (altroPic.isSelected())? nomePic.getText() : "SELF";
        RecordVolo record = new RecordVolo(data.getValue(), l_partenza.getText(), LocalTime.parse(o_partenza.getText()), l_arrivo.getText(), 
                LocalTime.parse(o_arrivo.getText()), (Velivolo)velScelto.getValue(), nome, Integer.parseInt(numDec.getText()), Integer.parseInt(numAtt.getText()), 
                LocalTime.parse(tempoIstruttore.getText()), LocalTime.parse(tempoDuale.getText()), LocalTime.parse(tempoCoPilot.getText()), 
                LocalTime.parse(tempoPic.getText()), LocalTime.parse(tempoIfr.getText()), LocalTime.parse(tempoNotte.getText()), osservazioni.getText());
        return record;
    }
    
    /*
    Il metodo Svuota modulo, inizializza le componenti del modulo d'ingresso con degli input standard.
    */
    public void svuotaModulo(){
        data.setValue(LocalDate.now());
        l_partenza.setText("");
        o_partenza.setText("00:00");
        l_arrivo.setText("");
        o_arrivo.setText("00:00");
        velScelto.setValue(velivoli[0]);
        altroPic.setSelected(false);
        nomePic.setText("SELF");
        numDec.setText("1");
        numAtt.setText("1");
        tempoIstruttore.setText("00:00");
        tempoPic.setText("00:00");
        tempoCoPilot.setText("00:00");
        tempoDuale.setText("00:00");
        tempoNotte.setText("00:00");
        tempoIfr.setText("00:00");
        osservazioni.setText("");
    }
    
    /*
    Il metodo riprestinaRecord prende come parametro un RecordVolo, 
    quindi ripristina all'interno del modulo i parametri che sono stati assegnati all'istanza.
    */
    public void ripristinaRecord(RecordVolo record){
        if(record.data!=null) data.setValue(record.data);
        if(record.luogoPartenza!=null) l_partenza.setText(record.luogoPartenza);
        if(record.orarioPartenza!=null) o_partenza.setText(record.orarioPartenza.toString());
        if(record.luogoArrivo!=null) l_arrivo.setText(record.luogoArrivo);
        if(record.orarioArrivo!=null) o_arrivo.setText(record.orarioArrivo.toString());
        
        if(record.velivolo!=null){
            int i = Arrays.asList(velivoli).indexOf(record.velivolo);
            if(i<0)i=0;
            velScelto.setValue(velivoli[i]);
        }
        else{
            velScelto.setValue(velivoli[0]);
        }
        if(!record.nomePIC.equals("SELF")){
            altroPic.setSelected(true);
            nomePic.setText(record.nomePIC);
        }
        
        numDec.setText(Integer.toString(record.decolli));
        numAtt.setText(Integer.toString(record.atterraggi));
        
        if(record.tempoIstruttore!=null) tempoIstruttore.setText(record.tempoIstruttore.toString());
        if(record.tempoDuale!=null) tempoDuale.setText(record.tempoDuale.toString());
        if(record.tempoCopilot!=null) tempoCoPilot.setText(record.tempoCopilot.toString());
        if(record.tempoPIC!=null) tempoPic.setText(record.tempoPIC.toString());
        if(record.tempoIFR!=null) tempoIfr.setText(record.tempoIFR.toString());
        if(record.tempoNotte!=null) tempoNotte.setText(record.tempoNotte.toString());
        
        if(record.osservazioni!=null) osservazioni.setText(record.osservazioni);
    }

    
    /*
    il metodo assegnaComportamenti assegna alle componenti del modulo dei comportamenti funzionali per 
    l'uso dell'applicazione.
    Quando il velivolo è cambiato nel choice box cambiano dinamicamente le informazioni sull'equipaggio visualizzate nell'interfaccia, 
    dato che dipendono solo dal velivolo.
    Quando modifico gli orari di partenza o arrivo, aggiorno il tempo di volo.
    Quando clicco con il mouse su un textfield di tempo di volo, dato che solitamente, le funzioni o condizioni hanno durata pari al tempo di volo
    copio direttamente il tempo, che poi può essere modificato.
    */
    private void assegnaComportamenti() {
        velScelto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Velivolo>(){
            public void changed(ObservableValue ov, Velivolo value, Velivolo newValue){
                infoEquipaggio.setText(newValue.getInfo());
            }
        });
        
        o_partenza.textProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue ov, String value, String newValue){
                String tempoVolo = null;
                try{
                    tempoVolo = RecordVolo.durata(LocalTime.parse(o_partenza.getText()), LocalTime.parse(o_arrivo.getText())).toString();
                } catch(DateTimeParseException e){
                    tempoVolo = "00:00";
                }
                tempoVoloTotale.setText(tempoVolo);
            }
        });
        
        o_arrivo.textProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue ov, String value, String newValue){
                String tempoVolo = null;
                try{
                    tempoVolo = RecordVolo.durata(LocalTime.parse(o_partenza.getText()), LocalTime.parse(o_arrivo.getText())).toString();
                } catch(DateTimeParseException e){
                    tempoVolo = "00:00";
                }
                tempoVoloTotale.setText(tempoVolo);
            }
        });
        
        copiaTempo(tempoPic);
        copiaTempo(tempoCoPilot);
        copiaTempo(tempoDuale);
        copiaTempo(tempoIstruttore);
        copiaTempo(tempoNotte);
        copiaTempo(tempoIfr);
        
    }

    private void copiaTempo(TextField t){
        t.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                t.setText(tempoVoloTotale.getText());
            }
        });
    }
    
}

/*
1 - rende l'oggetto grafico trasparente agli eventi del mouse: non può essere selezionato cliccando
2 - sull'oggetto non posso trasferire il focus tramite il tab

Combinando i metodi descritti ho simulato da disabilitazione dei field, senza ottenere il filtro grigio.
Mantenendo la funzioanle lettura dei dati che ci vengono disposti.
*/