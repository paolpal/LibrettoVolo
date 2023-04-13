import java.io.*;

/*
La classe fornisce due metodi statici per serializzare e deserializzare un'istanza di RecordVolo.
L'istanza viene prelevata alla chiusura dell'applicazione e ripristinata all'apertura.
*/
public class CacheUltimoRecordVolo {
    public static void serializzaBin(RecordVolo record){
        try(FileOutputStream fout = new FileOutputStream("./cache/record.bin");
            ObjectOutputStream oout = new ObjectOutputStream(fout);
        ){
            oout.writeObject(record);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static RecordVolo deserializzaBIN(){
        RecordVolo record = null;
        try(FileInputStream fin = new FileInputStream("./cache/record.bin");
            ObjectInputStream oin = new ObjectInputStream(fin);
        ){
            record = (RecordVolo) oin.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            record = null;
        }
        return record;
    }
}
