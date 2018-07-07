package com.teachme.controllers;
import com.teachme.domain.*;
import com.teachme.services.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/nodes")
@CrossOrigin(origins = "http://localhost:4200")

public class NodeController {

    private final NodeService nodeService;
	
	public NodeController(NodeService nodeService) {
		this.nodeService = nodeService;
	}

    @RequestMapping(value = "/createTree", method = RequestMethod.POST)
    public void createTree() {
        nodeService.createTree();
    }

    @GetMapping("/getTreeCT") //returns json tree in cytoscape format
    public String getTreeCT(@RequestParam long id) {
        return nodeService.treeFromIdCT(id);
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

    @DeleteMapping("/deleteNode")
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

    @PutMapping("/node/{id}")
    public void updateNode(@PathVariable("id") long id, @RequestBody Node node) {
        nodeService.updateNode(id, node);
    }
}