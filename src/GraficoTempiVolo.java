import javafx.scene.chart.*;

/*
La classe permette di visualizzare graficamente un riassunto dei tempi visualizzati nella tabella.
*/
public class GraficoTempiVolo extends BarChart<String, Number>{
    final static String ifr = "IFR";
    final static String notte = "Notte";
    final static String copilota = "Co-pilota";
    final static String pic = "PIC";
    final static String duale = "Duale";
    final static String istruttore = "Istruttore";

    public GraficoTempiVolo(){
        super(new CategoryAxis(), new NumberAxis());
        setTitle("Tempi di Volo");
        getYAxis().setLabel("Ore");
        getXAxis().setLabel("");
        setMaxWidth(500);
        setMaxHeight(450);
    }
    
    /*
    Il metodo crea una nuova serie di tempi,
    quindi cancella quelli presenti nel grafico e 
    li sostituisce con i nuovi.
    */
    public void aggiornaTempi(InterpreteTempiVolo interprete){
        XYChart.Series tempi = new XYChart.Series<>();
        tempi.getData().add(new XYChart.Data(ifr, (double)interprete.getTempoTotaleIFR()/60));
        tempi.getData().add(new XYChart.Data(notte, (double)interprete.getTempoTotaleNotte()/60));
        tempi.getData().add(new XYChart.Data(pic, (double)interprete.getTempoTotalePIC()/60));
        tempi.getData().add(new XYChart.Data(copilota, (double)interprete.getTempoTotaleCopilota()/60));
        tempi.getData().add(new XYChart.Data(duale, (double)interprete.getTempoTotaleDuale()/60));
        tempi.getData().add(new XYChart.Data(istruttore, (double)interprete.getTempoTotaleIstruttore()/60));

        getData().clear();
        getData().add(tempi);
    }
    
    
}
