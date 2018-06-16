package com.teachme.controllers;
import com.teachme.domain.*;
import com.teachme.services.*;

import org.neo4j.kernel.api.exceptions.Status.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*; 

@RestController
@RequestMapping("/nodes")
@CrossOrigin(origins = "http://localhost:4200")

public class NodeController {

    private final NodeService nodeService;
	
	public NodeController(NodeService nodeService) {
		this.nodeService = nodeService;
	}

    @GetMapping("/test")
    public String nodes() {
        return nodeService.NodeTest();
    }

    @RequestMapping(value = "/createTree", method = RequestMethod.POST)
    public void createTree() {
        nodeService.createTree();
    }
    
    @GetMapping("/getTree") //returns json string with tree starting from nodeId
    public String getTree(@RequestParam long id) {
        return nodeService.treeFromId(id);
    }

    //Retrieve a single node
    @RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
    public Node getNode(@PathVariable("id") long Id) {
        return nodeService.getNode(Id);
    }

    //Add a node to ID
    @RequestMapping(value = "/node/add/{id}", method = RequestMethod.POST)
    public void createNode(@PathVariable("id") long id, @RequestBody Node node) {

        nodeService.addChildToNode(id, node);
    }

    @GetMapping("/deleteNode")
    public void deleteNode(@RequestParam Long Id) {
        nodeService.deleteNode(Id);
    }

    @DeleteMapping(path = "/deleteAll")
    public void deleteAllNodes() {
        nodeService.deleteAll();
    }

    @GetMapping("/createCounter")
    public void createCounter() {
        nodeService.createCounter();
    }
}