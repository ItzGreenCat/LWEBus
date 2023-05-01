# LWEBus
A light weight event bus that also provides a way to not use reflection
# Install
You only need to go to the Releases page to download the Jar file and add it to your project dependencies
# Usage
To initialize LWEBus, insert the following code at the program entry point
```java
LWEBus.Bootstrap();
```
To create a new event, please inherit the Event class and override isCancelable() depending on whether the event needs to be canceled
That's a example
```java
public class TestEvent extends Event{
    public String testString = "";
    @Override
    public boolean isCancelable(){
    //Once the event is canceled, it does not continue to be passed downward, and calling isCanceled() returns true
      return true;
    }
}
```
When we want to use events, we need to annotate a method with @EventModule annotations and register it with the event bus
Here is an example of Event Method
```java
public class TestListener{
    //This method will be run when TestEvent is Posted
    @EventModule
    public void onEvent(TestEvent event){
      event.testString = "Hello World";
    }
}
```
This is an example of registering an event listener class
```java
TestListener listener = new TestListener();
LWEBus.getInstance().EVENT_BUS.register(listener);
```
Just now using the default event bus provided by LWEBus, we can also add our own event bus, just need this
```java
EventBus Your_Bus_Name = new EventBus();
```
After the registration is completed, we need the Post event, and when the event is Post, the corresponding method in the listener will be run
Example:
```java
TestEvent  event = new TestEvent();
//I registered the EventBus provided by LWEBus above, so to post the event to the EventBus provided by LWEBus, if you register your own EventBus (such as the Your_Bus_Name above), you need to Your_Bus_Name.post()
LWEBus.getInstance().EVENT_BUS.post(event);
System.out.println(event.testString);//If I write it in the code above, Hello World will be output
```
# Reflectionless Event
Reflectionless Event is not much different from the above, only a few changes are required  
**Implement ReflectionlessEventHandler**  
No longer need to use @EventModule, using Reflectionless Event requires implementing the ReflectionlessEventHandler method, which is an example
```java
public class TestListener implements ReflectionlessEventHandler{
    public void onEvent(TestEvent event){
      event.testString = "Hello World";
    }
    //All event will be posted here
    public void invoke(Event event){
      if(event instanceof TestEvent){
        onEvent((TestEvent)event);
      }
    }
}
```
Only this one change, other things such as registering events, etc. are exactly the same as written above

