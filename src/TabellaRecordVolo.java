import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabellaRecordVolo extends TableView<RecordVoloBean>{
    ObservableList<RecordVoloBean> listaRecordOss;

    public TabellaRecordVolo() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setMaxWidth(1275);
        TableColumn colData = new TableColumn("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        
        TableColumn colPartenza = new TableColumn("Partenza");
        colPartenza.setCellValueFactory(new PropertyValueFactory<>("partenza"));
        
        TableColumn colArrivo = new TableColumn("Arrivo");
        colArrivo.setCellValueFactory(new PropertyValueFactory<>("arrivo"));
        
        TableColumn colVelivolo = new TableColumn("Velivolo");
        colVelivolo.setCellValueFactory(new PropertyValueFactory<>("velivolo"));
        
        TableColumn colEquipaggio = new TableColumn("Equipaggio");
        colEquipaggio.setCellValueFactory(new PropertyValueFactory<>("equipaggio"));
        
        TableColumn colTempoVoloTotale = new TableColumn("Tempo di Volo");
        colTempoVoloTotale.setCellValueFactory(new PropertyValueFactory<>("tempoVoloTotale"));
        
        TableColumn colPic = new TableColumn("Nome PIC");
        colPic.setCellValueFactory(new PropertyValueFactory<>("nomePic"));
        
        TableColumn colDecAtt = new TableColumn("Decolli / Atterraggi");
        colDecAtt.setCellValueFactory(new PropertyValueFactory<>("dec_att"));
        
        TableColumn colTempiCondizioni = new TableColumn("Tempi Condizioni di Volo");
        colTempiCondizioni.setCellValueFactory(new PropertyValueFactory<>("tempiCondizioniVolo"));
        
        TableColumn colTempiFunzioni = new TableColumn("Tempi Funzioni di Volo");
        colTempiFunzioni.setCellValueFactory(new PropertyValueFactory<>("tempiFunzioniVolo"));
        
        TableColumn colOsservazioni = new TableColumn("Osservazioni e Approvazioni");
        colOsservazioni.setCellValueFactory(new PropertyValueFactory<>("osservazioni"));
        
        getColumns().addAll(colData, colPartenza, colArrivo, colVelivolo, colEquipaggio, colTempoVoloTotale, colPic, colDecAtt, colTempiCondizioni, colTempiFunzioni, colOsservazioni);
        
        listaRecordOss = FXCollections.observableArrayList();
        setItems(listaRecordOss);
    }
    
    public void aggiornaListaRecordVolo(List<RecordVolo> listaRecord){
        listaRecordOss.clear();
        for (RecordVolo record : listaRecord) {
            listaRecordOss.add(new RecordVoloBean(record));
        }
    }
    
    public int getIdRecordSelezionato(){
        int id;
        try{
            id = getSelectionModel().getSelectedItem().getId();
        }catch(Exception e){
            id = -1;
        }
        return id;
    }
    
    
}
