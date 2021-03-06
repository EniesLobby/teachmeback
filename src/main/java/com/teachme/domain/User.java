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
    private Boolean firstEnter;
    private List<String> treeRootIds = new ArrayList<>();

    public User(String name, String email, String password, Boolean firstEnter) {
        this.name = name;
        this.email = email;
        this.firstEnter = firstEnter;
        this.password = password;
    };

    public User() { }

    public List<String> getTreeRootIds() {
        return this.treeRootIds;
    }

    public void setTreeRootIds(String rootId) {
        this.treeRootIds.add(rootId);
    }

    public void deleteRootId(String rootId) { 
        
        for(int i = 0; i < this.treeRootIds.size(); i ++ ) {
            if(this.treeRootIds.get(i).equals(rootId)) {
                this.treeRootIds.remove(i);
            }
        }
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

    public void setFirstEnter(Boolean firstEnter) {
        // Changes after click in tutorial
        this.firstEnter = firstEnter;
    }

    public Boolean getFirstEnter() {
        return this.firstEnter;
    }
}