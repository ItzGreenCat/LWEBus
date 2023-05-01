package me.greencat.lwebus;


import me.greencat.lwebus.core.EventBus;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LWEBus {
    public static Logger LOGGER = Logger.getLogger("Light Weight Event Bus");
    public final EventBus EVENT_BUS = new EventBus();

    private static final String VERSION = "1.2.0";
    private static LWEBus instance;
    private static boolean isLoaded = false;
    public static void Bootstrap(){
        if(!isLoaded){
            isLoaded = true;
            LOGGER.info("LWEBus Loading...");
            LOGGER.info("LWEBus version " + VERSION);
            instance = new LWEBus();
        }
    }
    public static LWEBus getInstance() {
        return instance;
    }
    public static boolean isLoaded(){
        return isLoaded;
    }
}
