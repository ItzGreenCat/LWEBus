package me.greencat.lwebus.core;

import me.greencat.lwebus.LWEBus;
import me.greencat.lwebus.core.annotation.EventModule;
import me.greencat.lwebus.core.exception.EventModuleParameterEvent;
import me.greencat.lwebus.core.reflectionless.ReflectionlessEventHandler;
import me.greencat.lwebus.core.type.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private final HashSet<String> cachedElements = new HashSet<>();
    private final HashMap<String,HashSet<String>> typeCache = new HashMap<>();
    private final HashMap<String,HashMap<String,Method>> eventModuleCache = new HashMap<>();
    private final CopyOnWriteArrayList<Object> registeredListeners = new CopyOnWriteArrayList<>();
    private final HashSet<String> reflectionlessCacheRecord = new HashSet<>();
    private final HashSet<String> reflectionlessHandlerSet = new HashSet<>();

    public void register(Object o){
        if(!cachedElements.contains(o.getClass().getName())){
            LWEBus.LOGGER.info("Cached EventListener Class " + o.getClass().getName());
            findDynamicListeners(o);
            cachedElements.add(o.getClass().getName());
        }
        if(!reflectionlessCacheRecord.contains(o.getClass().getName())){
            LWEBus.LOGGER.info("Cached Reflectionless Listener Class " + o.getClass().getName());
            findReflectionlessEventHandler(o);
            reflectionlessCacheRecord.add(o.getClass().getName());
        }
        registeredListeners.add(o);
    }
    public void unregister(Object o) {
        registeredListeners.remove(o);
    }
    public void post(Event e){
        if(LWEBus.isLoaded()) {
            HashSet<String> classes = typeCache.get(e.getClass().getSimpleName());
            if(classes == null){
                return;
            }
            for(Object object : registeredListeners){
                if (e.isCanceled()) {
                    return;
                }
                if(reflectionlessHandlerSet.contains(object.getClass().getName())){
                    ((ReflectionlessEventHandler)object).invoke(e);
                }
                if(classes.contains(object.getClass().getName())){
                    Method method = eventModuleCache.get(object.getClass().getName()).get(e.getClass().getSimpleName());
                    try {
                        method.invoke(object, e);
                    } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException exception){
                        throw new RuntimeException(exception);
                    }
                }
            }
        }
    }
    private void findReflectionlessEventHandler(Object o){
        Class<?>[] clazzList = o.getClass().getInterfaces();
        for(Class<?> clazz : clazzList){
            if(clazz == ReflectionlessEventHandler.class){
                reflectionlessHandlerSet.add(o.getClass().getName());
                LWEBus.LOGGER.info("Added " + o.getClass().getName() + " into Reflectionless Event Cache");
                return;
            }
        }
        LWEBus.LOGGER.info("Cannot found Reflectionless Listener in Class " + o.getClass().getName());
    }
    private void findDynamicListeners(Object o){
        eventModuleCache.put(o.getClass().getName(), new HashMap<>());
        for(Method method : o.getClass().getMethods()){
            if(method.isAnnotationPresent(EventModule.class)){
                int parameterCount = method.getParameterCount();
                if(parameterCount != 1){
                    throw new EventModuleParameterEvent(method.getName() + " at " + o.getClass().getName() + " requires 1 parameter,But found " + parameterCount + " parameter");
                } else {
                    boolean hasParameter = false;
                    Class<?> clazz = method.getParameters()[0].getType();
                    if(clazz.getName().equals(Event.class.getName())){
                        hasParameter = true;
                    } else {
                        Class<?> superClass = clazz.getSuperclass();
                        while(superClass != null) {
                            if (superClass.getName().equals(Event.class.getName())) {
                                hasParameter = true;
                                break;
                            } else {
                                superClass = superClass.getSuperclass();
                            }
                        }
                    }
                    if(!hasParameter){
                        throw new EventModuleParameterEvent(method.getName() + " at " + o.getClass().getName() + "doesn't have event parameter");
                    } else {
                        Parameter parameter = method.getParameters()[0];
                        if(typeCache.get(parameter.getType().getSimpleName()) == null){
                            discoverNewEvent(parameter.getType().getSimpleName());
                        }
                        if(typeCache.get(parameter.getType().getSimpleName()) == null){
                            throw new RuntimeException("Cannot create a new HashSet for this event",new NullPointerException(parameter.getType().getSimpleName()));
                        }
                        eventModuleCache.get(o.getClass().getName()).put(parameter.getType().getSimpleName(),method);
                        typeCache.get(parameter.getType().getSimpleName()).add(o.getClass().getName());
                        LWEBus.LOGGER.info("EventBus " + this + "added " + o + " into type \"" + parameter.getType().getSimpleName() + "\" cache");
                    }
                }
            }
        }
    }
    private void discoverNewEvent(String event){
        LWEBus.LOGGER.info("EventBus " + this + "discovered a new event -- " + event + ",add to cache");
        typeCache.put(event, new HashSet<>());
    }
}
