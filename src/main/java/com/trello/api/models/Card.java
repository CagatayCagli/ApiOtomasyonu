package com.trello.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("desc")
    private String desc;
    
    @JsonProperty("idList")
    private String idList;
    
    @JsonProperty("idBoard")
    private String idBoard;
    
    @JsonProperty("closed")
    private boolean closed;
    
    @JsonProperty("pos")
    private double pos;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("shortUrl")
    private String shortUrl;

    public Card() {}

    public Card(String name, String idList) {
        this.name = name;
        this.idList = idList;
    }

    public Card(String name, String desc, String idList) {
        this.name = name;
        this.desc = desc;
        this.idList = idList;
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
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getIdList() {
        return idList;
    }
    
    public void setIdList(String idList) {
        this.idList = idList;
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
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getShortUrl() {
        return shortUrl;
    }
    
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
    
    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", idList='" + idList + '\'' +
                ", idBoard='" + idBoard + '\'' +
                ", closed=" + closed +
                '}';
    }
}
