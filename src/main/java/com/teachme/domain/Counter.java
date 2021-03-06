
package com.teachme.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Counter {
    @Id @GeneratedValue private Long Id;
    private String label = "counter";
    private Long counter;

    public Counter() {
        this.counter = 0L;
    }

    public Long getCounter() {
        return counter;
    }

    public void increaseCounter() {
        this.counter ++;
    }
}