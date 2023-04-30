import me.greencat.lwebus.core.annotation.EventModule;

public class TestListener2 {
    public TestListener2(){
        TestMain.EVENT_BUS.register(this);
    }
    @EventModule
    public void onEvent(TestEvent2 event){
        System.out.println("test2");
    }
}
