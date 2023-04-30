package me.greencat.lwebus.core.exception;

public class EventModuleParameterEvent extends RuntimeException{
    public EventModuleParameterEvent(String message){
        super(message);
    }
    public EventModuleParameterEvent(String message, Throwable cause) {
        super(message, cause);
    }
}
