package me.greencat.lwebus;


import me.greencat.lwebus.core.EventBus;
import me.greencat.lwebus.core.reflectionless.ReflectionlessEventHandler;
import me.greencat.lwebus.core.type.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class LWEBus {
    public static Logger LOGGER = LoggerFactory.getLogger(LWEBus.class);
    public final EventBus EVENT_BUS = new EventBus();

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
