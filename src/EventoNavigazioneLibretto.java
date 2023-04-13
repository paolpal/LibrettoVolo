
import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;
import java.time.*;


/*
La classe EventoNavigazioneLibretto serve a identificare i possibile eventi che possono verificarsi nell'applicazione.
Sono previsti:
    Avvio
    Aggiungi
    Elimina
    Modifica
    Aggiorna
    Termina
La classe provvede a serializzare come xml un'istanza e inviarla al server di log remoto.
*/

public class EventoNavigazioneLibretto {
    public final String nomeApplicazione;
    public final String ipClient;
    public final Instant istante;
    public final String evento;

    public EventoNavigazioneLibretto(String nomeApplicazione, String ipClient, Instant istante, String evento) {
        this.nomeApplicazione = nomeApplicazione;
        this.ipClient = ipClient;
        this.istante = istante;
        this.evento = evento;
    }
    
    private String serializzaXML(){
        XStream xs = new XStream();
        xs.registerConverter(new InstantConverter());
        return xs.toXML(this);
    }
    
    public void inviaLog(String ip, int porta){
        try(
                Socket sock = new Socket(ip,porta);
                DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
            ){
            String xml = serializzaXML();
            dout.writeUTF(xml);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
