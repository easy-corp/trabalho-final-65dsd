package br.udesc.core.server.messages;

public class TypedMessage {

    private String type;
    private Object content;

    public TypedMessage(String type, Object content) {
        this.type = type;
        this.content = content;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Object getContent() {
        return content;
    }
    public void setContent(Object content) {
        this.content = content;
    }

    
    
}
