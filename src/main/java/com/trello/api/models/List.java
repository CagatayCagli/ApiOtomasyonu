package com.trello.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class List {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("idBoard")
    private String idBoard;
    
    @JsonProperty("closed")
    private boolean closed;
    
    @JsonProperty("pos")
    private double pos;
    

    public List() {}

    public List(String name, String idBoard) {
        this.name = name;
        this.idBoard = idBoard;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIdBoard() {
        return idBoard;
    }
    
    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }
    
    public boolean isClosed() {
        return closed;
    }
    
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    
    public double getPos() {
        return pos;
    }
    
    public void setPos(double pos) {
        this.pos = pos;
    }
    
    @Override
    public String toString() {
        return "List{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idBoard='" + idBoard + '\'' +
                ", closed=" + closed +
                '}';
    }
}
