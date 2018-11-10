package com.teachme.domain;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

public class multiInformation {

	@Id @GeneratedValue private Long Id;

    private String idOfNodes;
    private String information;
    private String notes;

    multiInformation() { }

    multiInformation(String key, String value, String notes) {
        this.idOfNodes = key;
        this.information = value;
        this.notes = notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setIdOfNodes(String id) {
        this.idOfNodes = id;
    }

    public String getIdOfNodes() {
        return this.idOfNodes;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return this.information;
    }
 
}