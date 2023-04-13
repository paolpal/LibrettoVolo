import com.thoughtworks.xstream.converters.basic.*;
import java.time.*;


public class LocalDateConverter extends AbstractSingleValueConverter{
    public boolean canConvert(Class clazz){
        return clazz.equals(LocalDate.class);
    }
    
    public Object fromString(String str){
        LocalDate date = LocalDate.parse(str);
        return date;
    }
}
