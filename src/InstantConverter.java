import com.thoughtworks.xstream.converters.basic.*;
import java.time.*;

public class InstantConverter extends AbstractSingleValueConverter{
    public boolean canConvert(Class clazz){
        return clazz.equals(Instant.class);
    }
    
    public Object fromString(String str){
        Instant instant = Instant.parse(str);
        return instant;
    }
}
