import com.xps.rest.ws.handler.ProtocolType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiongps on 2018/11/15.
 */
public class Test {

    public static void main(String []args) {
        byte a = 5;
        byte []protocol = new byte[]{1,2,4};
        ProtocolType []types = ProtocolType.values();
        for(ProtocolType type :types) {
            System.out.println(a&type.getPn());
        }
        Map map = new HashMap<>();
        map.put("a","bb");
        System.out.println(map.toString());
    }
}
