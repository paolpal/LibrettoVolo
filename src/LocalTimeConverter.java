import com.thoughtworks.xstream.converters.basic.*;
import java.time.*;


public class LocalTimeConverter extends AbstractSingleValueConverter{
    public boolean canConvert(Class clazz){
        return clazz.equals(LocalTime.class);
    }
    
    public Object fromString(String str){
        LocalTime time = LocalTime.parse(str);
        return time;
    }
}
