import java.io.*;
import java.time.*;
import java.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class LibrettoVolo extends Application {
    
    private Stage stage;
    
    private TabellaRecordVolo tabellaRecord;
    private GraficoTempiVolo graficoTempi;
    private ModuloNuovoRecord moduloIngresso;

    private ParametriConfigurazioneLibretto configurazioni;
    private ArchivioRecordVolo archivio;
    
    private DatePicker da;
    private DatePicker a;
    private Button aggiorna;
    private Button elimina;
    private Button aggiungi;
    private Button importa;
    private Button esporta;
    
    private TextField tempoTotale;
    private Button modifica;
    
    @Override
    public void start(Stage primaryStage) {
        
        stage = primaryStage;
        
        configurazioni = ParametriConfigurazioneLibretto.deserializzaXML();
        
        final Label titolo = new Label("Libretto di Volo");
        titolo.setStyle("-fx-font-size: 20px; -fx-font-weight:bold; -fx-padding: 0 0 50 0;");
        
        tabellaRecord = new TabellaRecordVolo();
        graficoTempi = new GraficoTempiVolo();
        moduloIngresso = new ModuloNuovoRecord(configurazioni.velivoli);
        
        archivio = new ArchivioRecordVolo(configurazioni.ipDBMS, configurazioni.portaDBMS, configurazioni.usernameDBMS, configurazioni.passwordDBMS);
        
         
        HBox moduloIntervalloTempo = creaModuloIntervallo();
        VBox moduloPrincipale = new VBox(titolo, tabellaRecord, moduloIngresso, graficoTempi);
        moduloPrincipale.setMaxHeight(550);
        moduloPrincipale.setAlignment(Pos.CENTER);
        VBox moduloBottoni = creaModuloBottoni();
        VBox moduloEsportazione = creaModuloEsportazione();
        VBox moduloTempoTotale = creaModuloTempoTotale();
        VBox utente = creaEtichettaUtente();
        
        assegnaFunzioni();
        
        moduloPrincipale.setLayoutY(15);
        
        Group root = new Group(moduloPrincipale,moduloIntervalloTempo,moduloBottoni,moduloTempoTotale,utente,moduloEsportazione);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Libretto Volo");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        
        EventoNavigazioneLibretto e = new EventoNavigazioneLibretto("LibrettoVolo", configurazioni.ipClient, Instant.now(), "Avvio");
        aggiorna();
        RecordVolo tmp;
        if((tmp = CacheUltimoRecordVolo.deserializzaBIN()) != null)
            moduloIngresso.ripristinaRecord(tmp);
        primaryStage.show();
        e.inviaLog(configurazioni.ipServerLog, configurazioni.portaServerLog);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private HBox creaModuloIntervallo() {
        HBox box = new HBox();
        Label label_da = new Label("Da: ");
        da = new DatePicker(LocalDate.now().minusWeeks(4));
        da.setMaxWidth(120);
        Label label_a = new Label("a: ");
        a = new DatePicker(LocalDate.now());
        a.setMaxWidth(120);
        
        aggiorna = new Button("Aggiorna");
        
        box.setPadding(new Insets(5,2,5,2));
        box.setSpacing(4);
        
        box.setAlignment(Pos.CENTER);
        
        box.getChildren().addAll(label_da,da,label_a,a,aggiorna);
        box.setLayoutX(920);
        box.setLayoutY(50);
        return box;
        
    }

    private VBox creaModuloBottoni() {
        VBox box = new VBox();
        modifica = new Button("Modifica");
        modifica.setMaxWidth(Double.MAX_VALUE);
        elimina = new Button("Elimina");
        elimina.setMaxWidth(Double.MAX_VALUE);
        aggiungi = new Button("Aggiungi");
        aggiungi.setMaxWidth(Double.MAX_VALUE);
        box.getChildren().addAll(modifica,elimina,aggiungi);
        box.setLayoutX(1200);
        box.setLayoutY(420);
        box.setSpacing(5);
        return box;
    }
    
    private VBox creaModuloEsportazione() {
        VBox box = new VBox();
        importa = new Button("Importa");
        importa.setMaxWidth(Double.MAX_VALUE);
        esporta = new Button("Esporta");
        esporta.setMaxWidth(Double.MAX_VALUE);
        box.getChildren().addAll(importa,esporta);
        box.setLayoutX(20);
        box.setLayoutY(420);
        box.setSpacing(5);
        return box;
    }

    private VBox creaModuloTempoTotale() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        Label l = new Label("Tempo di Volo Totale");
        l.setStyle("-fx-font-weight:bold;");
        tempoTotale = new TextField();
        tempoTotale.setMouseTransparent(true);
        tempoTotale.setFocusTraversable(false);
        tempoTotale.setMaxWidth(100);
        tempoTotale.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(l,tempoTotale);
        box.setLayoutX(1000);
        box.setLayoutY(410);
        return box;
    }

    private VBox creaEtichettaUtente() {
        VBox box = new VBox();
        Label utente = new Label(configurazioni.nomeUtente);
        utente.setStyle("-fx-font-size: 15px;");
        Label licenza = new Label(configurazioni.numLicenza);
        licenza.setStyle("-fx-font-size: 12px;");
        box.getChildren().addAll(utente,licenza);
        box.setLayoutX(20);
        box.setLayoutY(40);
        return box;
    }
    
    private void modifica(){
        int id = tabellaRecord.getIdRecordSelezionato();
        if(id!=-1){
            RecordVolo record = archivio.getRecordVolo(id);
            archivio.eliminaRecord(id);
            moduloIngresso.ripristinaRecord(record);
            aggiorna();
        }
    }
    
    private void aggiorna(){
        List<RecordVolo> listRecord = archivio.getRecordNelPeriodo(da.getValue(), a.getValue());
        tabellaRecord.aggiornaListaRecordVolo(listRecord);
        InterpreteTempiVolo i = new InterpreteTempiVolo(listRecord);
        tempoTotale.setText(String.format("%d:%02d",i.getTempoTotale()/60,i.getTempoTotale()%60));
        graficoTempi.aggiornaTempi(i);
    }
    
    private void aggiungi(){
        RecordVolo record = moduloIngresso.getNuovoRecord();
        moduloIngresso.svuotaModulo();
        archivio.salvaRecord(record);
        aggiorna();
    }
    
    private void elimina(){
        int id = tabellaRecord.getIdRecordSelezionato();
        if(id!=-1){
            archivio.eliminaRecord(id);
            aggiorna();
        }
    }
    
    private void esporta(){
        List<RecordVolo> listaRecord = archivio.getRecordNelPeriodo(da.getValue(), a.getValue());
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Esporta Record Volo");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File XML", "*.xml", "*.XML"));
        File f = chooser.showSaveDialog(stage);
        boolean esitoPositivo = TrasferitoreRecord.serializzaListaRecord(listaRecord, f);
        if(esitoPositivo)
            for (RecordVolo record : listaRecord) {
                archivio.eliminaRecord(record.id);
            }
        aggiorna();
    }
    
    private void importa(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Importa Record Volo");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File XML", "*.xml", "*.XML"));
        File f = chooser.showOpenDialog(stage);
        List<RecordVolo> listaRecord = TrasferitoreRecord.deserializzaListaRecord(f);
        for (RecordVolo record : listaRecord) {
            archivio.salvaRecord(record);
        }
        aggiorna();
    }

    /*
    il metodo assegna funzioni assegna ad ogni bottone la sequenza di istruzioni che deve compiere.
    Solitamente una funzione e l'invio dell'evento al log remoto.
    */
    private void assegnaFunzioni() {
        aggiorna.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                EventoNavigazioneLibretto e = new EventoNavigazioneLibretto("LibrettoVolo", configurazioni.ipClient, Instant.now(), "Aggiorna");
                aggiorna();
                e.inviaLog(configurazioni.ipServerLog, configurazioni.portaServerLog);
            }
        });
        
        aggiungi.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                EventoNavigazioneLibretto e = new EventoNavigazioneLibretto("LibrettoVolo", configurazioni.ipClient, Instant.now(), "Aggiungi");
                aggiungi();
                e.inviaLog(configurazioni.ipServerLog, configurazioni.portaServerLog);
            }
        });
        
        elimina.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                EventoNavigazioneLibretto e = new EventoNavigazioneLibretto("LibrettoVolo", configurazioni.ipClient, Instant.now(), "Elimina");
                elimina();
                e.inviaLog(configurazioni.ipServerLog, configurazioni.portaServerLog);
            }
        });
        
        modifica.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                EventoNavigazioneLibretto e = new EventoNavigazioneLibretto("LibrettoVolo", configurazioni.ipClient, Instant.now(), "Modifica");
                modifica();
                e.inviaLog(configurazioni.ipServerLog, configurazioni.portaServerLog);
            }
        });
        
        esporta.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                EventoNavigazioneLibretto e = new EventoNavigazioneLibretto("LibrettoVolo", configurazioni.ipClient, Instant.now(), "Esporta");
                esporta();
                e.inviaLog(configurazioni.ipServerLog, configurazioni.portaServerLog);
            }
        });
        
        importa.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                EventoNavigazioneLibretto e = new EventoNavigazioneLibretto("LibrettoVolo", configurazioni.ipClient, Instant.now(), "Importa");
                importa();
                e.inviaLog(configurazioni.ipServerLog, configurazioni.portaServerLog);
            }
        });
        
        /*
        Alla chiusura dello stage l'input corrente viene serializzato, quindi si procede a inviare l'evento di chiusura.
        */
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                EventoNavigazioneLibretto e = new EventoNavigazioneLibretto("LibrettoVolo", configurazioni.ipClient, Instant.now(), "Termine");
                RecordVolo record = moduloIngresso.getNuovoRecord();
                CacheUltimoRecordVolo.serializzaBin(record);
                e.inviaLog(configurazioni.ipServerLog, configurazioni.portaServerLog);
            }
        });
    }
    
}

