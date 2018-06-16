package com.teachme.domain;

import java.util.*;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Node {

	@Id @GeneratedValue private Long Id;

    private Long nodeId;
    private String question;
    private String answer;
    private String information;
    private Long rootId;
    

	private Node() { };

	public Node(Long nodeId, String question, String answer, String information, Long rootId) {
        this.nodeId = nodeId;
        this.question = question;
        this.answer = answer;
        this.information = information;
        this.rootId = rootId;
    }
    
	@Relationship(type = "hasChild")
    private List<Node> children = new ArrayList<>();

    public void addChild(Node child) {
        
        children.add(child);
    }

    public List<Node> getChildren(){
        return children;
    }
    
    public Long getnodeId() {
        return nodeId;
    }

    public void setnodeId(Long nodeId) {
        this.nodeId = nodeId; 
    }

    public void setrootId(Long rootId) {
        this.rootId = rootId;
    }

    public Long getrootId() {
        return this.rootId;
    }
    
	public String getQuestion() {
		return question;
    }
    
    public String getAnswer() {
		return answer;
    }
    
    public String getInformation() {
		return information;
	}

	public void setQuestion(String question) {
		this.question = question;
    }
    
    public void setAnswer(String answer) {
		this.answer = answer;
    }
    
    public void setInformation(String information) {
		this.information = information;
    }
    
    public Long getId() {
        return Id;
    }
}