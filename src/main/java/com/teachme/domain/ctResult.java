package com.teachme.domain;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ctResult {

    private String id;
    private Long source;
    private Long target;

    public String getid() {
        return id;
    }

    public Long getSource() {
        return source;
    }

    public Long getTarget() {
        return target;
    }

}