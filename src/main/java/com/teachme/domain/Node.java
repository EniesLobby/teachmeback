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
    private Long rootId;
    private String question;
    private String questionHtml;
    private String questionLabel;
    private String answer;
    private String answerHtml;
    private String information;
    
	private Node() { };

	public Node(Long nodeId, String question, String question_html, String question_label, String answer, String answer_html, String information, Long rootId) {
        this.nodeId = nodeId;
        this.questionLabel = question_label;
        this.question = question;
        this.answer = answer;
        this.information = information;
        this.rootId = rootId;
        this.questionHtml = question_html;
        this.answerHtml = answer_html;
    }
    
	@Relationship(type = "hasChild")
    private List<hasChild> children = new ArrayList<>();

    @Relationship(type = "hasInformation")
    private hasInformation multiInformation;

    public void addInformation(hasInformation multiInformation) {
        this.multiInformation = multiInformation;
    }

    public hasInformation getMultiInformation() {
        return this.multiInformation;
    }

    public void addChild(Node source, Node target) {
        hasChild rel = new hasChild(source, target);
        
        children.add(rel);
    }

    public List<hasChild> getChildren(){
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
		return this.question;
    }
    
    public String getAnswer() {
		return this.answer;
    }
    
    public String getInformation() {
		return information;
    }
    
    public String getAnswerHtml() {
        return this.answerHtml;
    }

    public String getQuestionHtml() {
        return this.questionHtml;
    }

    public String getQuestionLabel() {
        return this.questionLabel;
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

    public void setAnswerHtml(String answer_html) {
        this.answerHtml = answer_html;
    }

    public void setQuestionHtml(String question_html) {
        this.questionHtml = question_html;
    }

    public void setQuestionLabel(String question_label) {
        this.questionLabel = question_label;
    }
    
    public Long getId() {
        return this.Id;
    }
}