package com.example.uno.model.message;

public class TypedMessage {

    private String type;
    private Object content;

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
