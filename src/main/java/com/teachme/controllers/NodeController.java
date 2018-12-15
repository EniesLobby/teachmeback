package com.teachme.controllers;
import com.teachme.domain.*;
import com.teachme.services.*;
import java.util.*;

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
import org.springframework.web.bind.annotation.ResponseBody;;

@RestController
@RequestMapping("/nodes")
@CrossOrigin(origins = "http://localhost:4200")

public class NodeController {

    private final NodeService nodeService;
	
	public NodeController(NodeService nodeService) {
		this.nodeService = nodeService;
	}

    // creates a tree
    @RequestMapping(value = "/createTree", method = RequestMethod.POST)
    public Long createTree() {
        return nodeService.createTree();
    }

    // returns json tree in cytoscape format
    @GetMapping("/getTreeCT")
    public String getTreeCT(@RequestParam long id) {
        return nodeService.treeFromIdCT(id);
    }

    // Retrieve children of the given node
    @RequestMapping(value = "/node/{id}/children", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    public @ResponseBody List<Node> getChildren(@PathVariable("id") long id) {
        return nodeService.getChildren(id);
    }

    // Retrieve a single node
    @RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
    public Node getNode(@PathVariable("id") long Id) {
        return nodeService.getNode(Id);
    }

    // Add a node to ID
    @RequestMapping(value = "/node/add/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Long createNode(@PathVariable("id") long id, @RequestBody Node node) {

        return nodeService.addChildToNode(id, node);
    }

    @DeleteMapping("/deleteNode/{nodeId}")
    public void deleteNode(@PathVariable("nodeId") Long nodeId) {
        nodeService.deleteNode(nodeId);
    }

    @DeleteMapping("/deleteAnswer/node/{nodeId}/answer/{answer_id}")
    public void deleteAnswer(@PathVariable("nodeId") Long nodeId, @PathVariable("answer_id") String answer_id) {
        nodeService.deleteAnswer(nodeId, answer_id);
    }

    @DeleteMapping(path = "/deleteAll")
    public void deleteAllNodes() {
        nodeService.deleteAll();
    }

    @GetMapping("/createCounter")
    public void createCounter() {
        nodeService.createCounter();
    }

    // Update the given node
    @PutMapping("/node/{id}")
    public void updateNode(@PathVariable("id") long id, @RequestBody Node node) {
        nodeService.updateNode(id, node);
    }

    // Add or Update an Information to node ID and Answer id (String "nodeId-nodeId")
    @RequestMapping(value = "/node/information/add/{id_node}/answer", method = RequestMethod.POST)
    public void addInformation(@PathVariable("id_node") long nodeId, @RequestBody Map<String, String> body) {
        
        String answer_id = body.get("answer_id");
        String information = body.get("information");
        String notes = body.get("notes");

        nodeService.addInformation(nodeId, answer_id, information, notes);
    }

    // Get all Information related node ID
    @RequestMapping(value = "/node/information/{id_node}/", method = RequestMethod.GET)
    public  @ResponseBody List<multiInformation> getAllInformation(@PathVariable("id_node") long id_node) {
        return nodeService.getAllInformation(id_node);
    }

    // Get Information
    @RequestMapping(value = "/node/information_one/{id_node}/", method = RequestMethod.GET)
    public @ResponseBody Information getInformation(@PathVariable("id_node") long id_node) {
        return nodeService.getInformation(id_node);
    }

    // Get user by email
    @RequestMapping(value = "/user/{email}", method = RequestMethod.GET)
    public  @ResponseBody User getUser(@PathVariable("email") String email) {
        return nodeService.getUser(email);
    }

    // Set title
    @RequestMapping(value = "/title/{rootId}", method = RequestMethod.POST)
    public void setTitle(@PathVariable("rootId") Long rootId, @RequestBody Map<String, String> body) {
        String title = body.get("title");
        nodeService.setTitle(rootId, title);
    }
    

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public void addUser(@RequestBody Map<String, String> body) {
        
        String email = body.get("email");
        String name = body.get("name");
        String password = body.get("password");

        nodeService.createUser(email, name, password);
    }

    @RequestMapping(value = "/user/addroot/{email}", method = RequestMethod.POST)
    public void addRoot(@PathVariable("email") String email, @RequestBody Map<String, String> body) {
        String rootId = body.get("rootId");
        nodeService.addRootId(email, rootId);
    }

    // password check
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public @ResponseBody boolean checkUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        if(nodeService.checkUser(email, password)) {
            return true;
        } else {
            return false;
        }
    }
}