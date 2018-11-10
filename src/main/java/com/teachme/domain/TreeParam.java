package com.teachme.domain;

import java.util.*;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class TreeParam
{
    @Id @GeneratedValue private Long Id;

    private String tree;
}