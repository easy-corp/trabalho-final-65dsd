package br.udesc.core.model.messages;

public class LifecycleMessage {

    private MessageType type;
    private String name;
    private Object params;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public enum MessageType {
        INFO,
        END,
        WIN
    }

}
