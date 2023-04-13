
import java.sql.*;
import java.time.*;
import java.util.*;

/*
La classe permette di cominicare con il database
*/
public class ArchivioRecordVolo {
    String ipDBMS;
    int portaDBMS;
    String usernameDBMS;
    String passwordDBMS;

    public ArchivioRecordVolo(String ip, int porta, String user, String pass){
        ipDBMS = ip;
        portaDBMS = porta;
        usernameDBMS = user;
        passwordDBMS = pass;
    }
    
    /*
    Il metodo restituisce la lista dei record nell'intervallo temporale che viene passato.
    Se fallisce ritorna una lista vuota, non null.
    */
    public List<RecordVolo> getRecordNelPeriodo(LocalDate da, LocalDate a){
        List<RecordVolo> listaRecord = new ArrayList<>();
        try(
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ipDBMS+":"+portaDBMS+"/librettovolo", usernameDBMS, passwordDBMS);
                PreparedStatement ps = co.prepareStatement("SELECT * FROM recordvolo WHERE data BETWEEN ? AND ? ORDER BY data");
            ) {
            ps.setDate(1, java.sql.Date.valueOf(da));
            ps.setDate(2, java.sql.Date.valueOf(a));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Velivolo vel = new Velivolo(rs.getString("v_modello"), rs.getString("v_registrazione"), Equipaggio.valueOf(rs.getString("v_equipaggio")), Motore.valueOf(rs.getString("v_motore")));
                listaRecord.add( new RecordVolo(
                        rs.getInt("id"), 
                        rs.getDate("data").toLocalDate(), 
                        rs.getString("luogopartenza"), 
                        LocalTime.parse(rs.getString("orariopartenza")), 
                        rs.getString("luogoarrivo"), 
                        LocalTime.parse(rs.getString("orarioarrivo")), 
                        vel, 
                        rs.getString("nomepic"), 
                        rs.getInt("decolli"), 
                        rs.getInt("atterraggi"), 
                        LocalTime.parse(rs.getString("tempoistruttore")), 
                        LocalTime.parse(rs.getString("tempoduale")), 
                        LocalTime.parse(rs.getString("tempocopilot")), 
                        LocalTime.parse(rs.getString("tempopic")), 
                        LocalTime.parse(rs.getString("tempoifr")), 
                        LocalTime.parse(rs.getString("temponotte")), 
                        rs.getString("osservazioni")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaRecord;
    }
    
    /*
    Il metodo restituisce il record identificato dall' id passato.
    */
    public RecordVolo getRecordVolo(int id){
        RecordVolo record = null;
        try(
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ipDBMS+":"+portaDBMS+"/librettovolo", usernameDBMS, passwordDBMS);
                PreparedStatement ps = co.prepareStatement("SELECT * FROM recordvolo WHERE id = ?");
            ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Velivolo vel = new Velivolo(rs.getString("v_modello"), rs.getString("v_registrazione"), Equipaggio.valueOf(rs.getString("v_equipaggio")), Motore.valueOf(rs.getString("v_motore")));
                record = new RecordVolo(
                        rs.getInt("id"), 
                        rs.getDate("data").toLocalDate(), 
                        rs.getString("luogopartenza"), 
                        LocalTime.parse(rs.getString("orariopartenza")), 
                        rs.getString("luogoarrivo"), 
                        LocalTime.parse(rs.getString("orarioarrivo")), 
                        vel, 
                        rs.getString("nomepic"), 
                        rs.getInt("decolli"), 
                        rs.getInt("atterraggi"), 
                        LocalTime.parse(rs.getString("tempoistruttore")), 
                        LocalTime.parse(rs.getString("tempoduale")), 
                        LocalTime.parse(rs.getString("tempocopilot")), 
                        LocalTime.parse(rs.getString("tempopic")), 
                        LocalTime.parse(rs.getString("tempoifr")), 
                        LocalTime.parse(rs.getString("temponotte")), 
                        rs.getString("osservazioni"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return record;
    }
    
    /*
    Il metodo inserisce nel database il record passato come parametro.
    */
    public void salvaRecord(RecordVolo record){
        try(
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ipDBMS+":"+portaDBMS+"/librettovolo", usernameDBMS, passwordDBMS);
                PreparedStatement ps = co.prepareStatement("INSERT INTO recordvolo (data, luogopartenza, orariopartenza, luogoarrivo, orarioarrivo, v_modello, v_registrazione,"+
                        " v_equipaggio, v_motore, nomepic, decolli, atterraggi, tempoistruttore, tempoduale, tempocopilot, tempopic, tempoifr, temponotte, osservazioni)"+
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ) {
            ps.setDate(1, java.sql.Date.valueOf(record.data));
            ps.setString(2, record.luogoPartenza);
            ps.setString(3, record.orarioPartenza.toString());
            ps.setString(4, record.luogoArrivo);
            ps.setString(5, record.orarioArrivo.toString());
            ps.setString(6, record.velivolo.modello);
            ps.setString(7, record.velivolo.registrazione);
            ps.setString(8, record.velivolo.equipaggio.toString());
            ps.setString(9, record.velivolo.motore.toString());
            ps.setString(10, record.nomePIC);
            ps.setInt(11, record.decolli);
            ps.setInt(12, record.atterraggi);
            ps.setString(13, record.tempoIstruttore.toString());
            ps.setString(14, record.tempoDuale.toString());
            ps.setString(15, record.tempoCopilot.toString());
            ps.setString(16, record.tempoPIC.toString());
            ps.setString(17, record.tempoIFR.toString());
            ps.setString(18, record.tempoNotte.toString());
            ps.setString(19, record.osservazioni);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /*
    Il metodo elimina dal database il record identificato dall'id passato*/
    public void eliminaRecord(int id){
        try(
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ipDBMS+":"+portaDBMS+"/librettovolo", usernameDBMS, passwordDBMS);
                PreparedStatement ps = co.prepareStatement("DELETE FROM recordvolo WHERE id=?");
            ){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
