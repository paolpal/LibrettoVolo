import com.thoughtworks.xstream.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/*
La classe ParametriConfigurazione contiene i dati necessari al funzionamento corretto dell'applicativo.
Simula la lettura di dati provenienti da altre parti di software (come nome utente e num. licenza)
E contiene la lista dei velivoli che il sistema dispone.
*/
public class ParametriConfigurazioneLibretto {
    public Velivolo[] velivoli;
    public String nomeUtente;
    public String numLicenza;
    public LocalDate dataPrimoUso;
    public String ipClient;
    public String ipServerLog;
    public int portaServerLog;
    public String ipDBMS;
    public int portaDBMS;
    public String usernameDBMS;
    public String passwordDBMS;
    
    /*
    Il metodo statico deserializzaXML crea un'istanza della classe a partire dal file di configurazione xml.
    Nel caso la convalida non avesse buon esito, viene ritornato null.
    */
    public static ParametriConfigurazioneLibretto deserializzaXML(){
        XStream xs = new XStream();
        xs.registerConverter(new LocalDateConverter());
        xs.useAttributeFor(Velivolo.class, "equipaggio");        
        xs.useAttributeFor(Velivolo.class, "motore");
        String xmlString = null;
        try{
            xmlString = new String(Files.readAllBytes(Paths.get("./configurazioni/configurazioni.xml")));
        } catch(Exception e){
            e.printStackTrace();
        }
        if(convalidaXML(xmlString)){
            return (ParametriConfigurazioneLibretto) xs.fromXML(xmlString);
        }
        else{
            return null;
        }
    }
    
    private static boolean convalidaXML(String xml){
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document d = db.parse(new ByteArrayInputStream(xml.getBytes()));
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema s = sf.newSchema(new StreamSource(new File("./configurazioni/configurazione.xsd")));
            s.newValidator().validate(new DOMSource(d));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof IOException) e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
