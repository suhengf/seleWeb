package beijing;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dict {

    public  Map<String,Integer> keyDict(){
        Map<String, Integer> objectObjectMap = new ConcurrentHashMap<>();
        objectObjectMap.put("0",KeyEvent.VK_0);
        objectObjectMap.put("1",KeyEvent.VK_1);
        objectObjectMap.put("2",KeyEvent.VK_2);
        objectObjectMap.put("3",KeyEvent.VK_3);
        objectObjectMap.put("4",KeyEvent.VK_4);
        objectObjectMap.put("5",KeyEvent.VK_5);
        objectObjectMap.put("6",KeyEvent.VK_6);
        objectObjectMap.put("7",KeyEvent.VK_7);
        objectObjectMap.put("8",KeyEvent.VK_8);
        objectObjectMap.put("9",KeyEvent.VK_9);
        objectObjectMap.put("a",KeyEvent.VK_A);
        return objectObjectMap;
    }

}
