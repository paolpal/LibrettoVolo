import com.thoughtworks.xstream.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/*
La classe trasferitore record si occupa di serializzare e deserializzare una Lista di record,
per facilitare il trasferimento da un applicativo ad un altro, nel caso di cambio di dispositivo.
*/
public class TrasferitoreRecord {
    
    public static boolean serializzaListaRecord(List<RecordVolo> listaRecord, File f){
        XStream xs = new XStream();
        xs.useAttributeFor(Velivolo.class, "equipaggio");        
        xs.useAttributeFor(Velivolo.class, "motore");
        xs.registerConverter(new LocalTimeConverter());
        xs.registerConverter(new LocalDateConverter());
        xs.omitField(RecordVolo.class, "id");
        xs.setMode(XStream.NO_REFERENCES);
        try{
            Files.write(f.toPath(), xs.toXML(listaRecord).getBytes());
        } catch (IOException | NullPointerException ex) {
            if(ex instanceof NullPointerException) return false;
            ex.printStackTrace();
        }
        return true;
    }
    
    public static List<RecordVolo> deserializzaListaRecord(File f){
        List<RecordVolo> listaRecord = null;
        XStream xs = new XStream();
        xs.useAttributeFor(Velivolo.class, "equipaggio");        
        xs.useAttributeFor(Velivolo.class, "motore");
        xs.registerConverter(new LocalTimeConverter());
        xs.registerConverter(new LocalDateConverter());
        xs.omitField(RecordVolo.class, "id");
        xs.setMode(XStream.NO_REFERENCES);
        try{
            String xml = new String(Files.readAllBytes(f.toPath()));
            listaRecord = (List<RecordVolo>) xs.fromXML(xml);
        } catch (IOException | NullPointerException ex) {
            listaRecord = new ArrayList<RecordVolo>();
            if(!(ex instanceof NullPointerException)) ex.printStackTrace();
        }
        return listaRecord;
    }
}
