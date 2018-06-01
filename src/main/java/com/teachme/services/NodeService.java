package com.teachme.services;
import java.util.*;

import com.teachme.domain.Node;
import com.teachme.domain.Counter;
import com.teachme.repositories.NodeRepository;
import com.teachme.repositories.CounterRepository;
import org.springframework.stereotype.Service;

@Service
public class NodeService {

    private final NodeRepository nodeRepository;
    private final CounterRepository counterRepository;
    
    public NodeService(NodeRepository nodeRepository, CounterRepository counterRepository) {
        this.nodeRepository = nodeRepository;
        this.counterRepository = counterRepository;
    }

    public void createTree() {
        Node root = new Node(getCurrentCounter(), "", "", "");
        nodeRepository.save(root);
        //write this id to user's tree list as root
    }

    public void createCounter() {
        Counter counter = new Counter();
        counterRepository.save(counter);
        //check if counter exists
    }

    public Long getCurrentCounter() {
        
        Counter counter = counterRepository.findBylabel("counter");
        Long currentCounter = counter.getCounter();
        counter.increaseCounter();
        counterRepository.save(counter);

        return currentCounter;
    }

    public void addChildToNode(Long Id, Node node) {
        
        Optional<Node> found_node = nodeRepository.findBynodeId(Id);
        Long nodeId = getCurrentCounter();
        node.setnodeId(nodeId);
        //Node child = new Node(getCurrentCounter(), question, answer, information);

        if(found_node.isPresent()) {
            found_node.get().addChild(node);
            nodeRepository.save(found_node.get());
        }
    }

    public Node getNode(Long Id) {
        Optional<Node> thenode = nodeRepository.findBynodeId(Id);
        
        if(!thenode.isPresent()){
            //error
        }
        return thenode.get();
    }

    public void updateNode(Long Id, String question, String answer, String information) {
        Optional<Node> node = nodeRepository.findBynodeId(Id);
        if(node.isPresent()) {
            node.get().setAnswer(answer);
            node.get().setQuestion(question);
            node.get().setInformation(information);
        }
    }

    public void deleteNode(Long Id) {
        
        if(nodeRepository.existsById(Id)) {
            nodeRepository.deleteById(Id);
        }
    }

    public void deleteAll() {
        nodeRepository.deleteAll();
    }


    public String treeFromId(Long Id) {
        return nodeRepository.tree(Id);
    }


    public String NodeTest() {
        
        String resultString = "";
        
        return resultString;
    }



}
