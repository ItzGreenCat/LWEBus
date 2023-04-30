import me.greencat.lwebus.core.annotation.EventModule;
import me.greencat.lwebus.core.type.Event;

public class Test{
    public Test(){
        TestMain.EVENT_BUS.register(this);
    }
    @EventModule
    public void onEvent(TestEvent e){
        System.out.println("test");
    }
}
