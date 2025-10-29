package com.trello.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("desc")
    private String desc;
    
    @JsonProperty("closed")
    private boolean closed;
    
    @JsonProperty("idOrganization")
    private String idOrganization;
    
    @JsonProperty("pinned")
    private boolean pinned;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("shortUrl")
    private String shortUrl;

    public Board() {}

    public Board(String name) {
        this.name = name;
    }

    public Board(String name, String desc) {
        this.name = name;
        this.desc = desc;
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
    
    public boolean isClosed() {
        return closed;
    }
    
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    
    public String getIdOrganization() {
        return idOrganization;
    }
    
    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }
    
    public boolean isPinned() {
        return pinned;
    }
    
    public void setPinned(boolean pinned) {
        this.pinned = pinned;
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
        return "Board{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", closed=" + closed +
                ", url='" + url + '\'' +
                '}';
    }
}
