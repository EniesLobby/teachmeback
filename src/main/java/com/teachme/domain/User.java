package com.teachme.domain;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.*;

@NodeEntity
public class User {

    @Id @GeneratedValue private Long Id;
    
    private String name;
    private String email;
    private String password;
    private List<String> treeRootIds = new ArrayList<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    };

    public User() { }

    public List<String> getTreeRootIds() {
        return this.treeRootIds;
    }

    public void setTreeRootIds(String rootId) {
        this.treeRootIds.add(rootId);
    }
    
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}