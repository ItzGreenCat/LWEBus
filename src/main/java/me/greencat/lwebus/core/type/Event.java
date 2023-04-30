package me.greencat.lwebus.core.type;

public class Event {
    protected boolean isCanceled = false;
    public final void setCanceled(boolean isCanceled) {
        if(isCancelable()){
            this.isCanceled = isCanceled;
        } else {
            throw new RuntimeException("You cannot cancel a non-cancelable event");
        }
    }
    public final boolean isCanceled() {
        return isCanceled;
    }
    public boolean isCancelable() {
        return false;
    }
}
