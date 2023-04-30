package me.greencat.lwebus;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LWEBus {
    public static Logger LOGGER = LoggerFactory.getLogger(LWEBus.class);

    private static final String VERSION = "1.0.0";
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
