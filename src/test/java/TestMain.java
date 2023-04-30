import me.greencat.lwebus.LWEBus;
import me.greencat.lwebus.core.EventBus;
import me.greencat.lwebus.core.annotation.EventModule;
import me.greencat.lwebus.core.type.Event;

public class TestMain {
    public static EventBus EVENT_BUS = new EventBus();
    public static int counter = 0;
    public static boolean status = true;
    public static void main(String[] args) {
        LWEBus.Bootstrap();
        Test test = new Test();
        TestListener2 test2 = new TestListener2();
        while (true) {
            EVENT_BUS.post(new TestEvent());
            EVENT_BUS.post(new TestEvent2());
             if(counter < 10){
                 counter++;
             } else {
                 counter = 0;
                 status = !status;
                 EVENT_BUS.unregister(test);
                 EVENT_BUS.unregister(test2);
             }
        }
    }
}