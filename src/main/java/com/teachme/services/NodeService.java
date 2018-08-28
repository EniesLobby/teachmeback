package com.teachme.services;
import java.util.*;

import com.teachme.domain.*;
import com.teachme.repositories.NodeRepository;
import com.teachme.repositories.CounterRepository;
import com.teachme.repositories.hasChildRepository;
import org.springframework.stereotype.Service;


@Service
public class NodeService {

    private final NodeRepository nodeRepository;
    private final CounterRepository counterRepository;
    private final hasChildRepository haschildRepository;
    
    public NodeService(NodeRepository nodeRepository, CounterRepository counterRepository,
        hasChildRepository haschildRepository) {
        this.nodeRepository = nodeRepository;
        this.counterRepository = counterRepository;
        this.haschildRepository = haschildRepository;
    }

    public void createTree() {
        Long rootId = getCurrentCounter();
        Node root = new Node(rootId, "", "", "", rootId);
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

        //create a relation
        //Node child = new Node(getCurrentCounter(), question, answer, information);
        
        //Add relation information

        if(found_node.isPresent()) {
            Node source = found_node.get();
            Node target = node;

            found_node.get().addChild(source, target);
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

    public void updateNode(Long Id, Node node) {
        //History mechanism (??)
        Optional<Node> found_node = nodeRepository.findBynodeId(Id);
        
        if(found_node.isPresent()) {
            found_node.get().setAnswer(node.getAnswer());
            found_node.get().setQuestion(node.getQuestion());
            found_node.get().setInformation(node.getInformation());

            nodeRepository.save(found_node.get());
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

    
    public String treeFromIdCT(Long Id) {
        
        //Relations
        List<String> resultListRelations = treeFromIdMap(Id);
        //Nodes
        List<String> resultListNodes = new ArrayList<String>();

        //Convert List<Node> to List<String> in {data: {Node}} format
        
        Long   nodeId;
        String question;
        String answer;
        String information;
        Long   rootId;

        String resultNodeString = "";
        List<Node> ListNodes = nodeRepository.treeCT(Id);

        for( int i = 0; i < ListNodes.size(); i ++ ) {
            
            nodeId      = ListNodes.get(i).getnodeId();
            question    = ListNodes.get(i).getQuestion();
            answer      = ListNodes.get(i).getAnswer();
            information = ListNodes.get(i).getInformation();
            rootId      = ListNodes.get(i).getrootId();

            resultNodeString = "{ \"data\": { \"id\": \"" + nodeId + "\", \"question\": \"" + question + "\", " + 
                               "\"answer\": \"" + answer + "\", \"information\": \"" + information + "\", \"rootId\": \"" + rootId + "\" } }";
            resultListNodes.add(resultNodeString);

            resultNodeString = "";
        }

        //Result TreeJsonString
        String treeJsonString = "[ ";

        for(int i = 0; i < resultListNodes.size(); i ++ ) {
            treeJsonString = treeJsonString + resultListNodes.get(i) + ", ";
        }

        for(int i = 0; i <  + resultListRelations.size(); i ++ ) {
            treeJsonString = treeJsonString + resultListRelations.get(i) + ", ";
        }

        treeJsonString = treeJsonString.substring(0, treeJsonString.length() - 2);
        treeJsonString = treeJsonString + " ]";

        return treeJsonString;
    }

    //Returns List<String> with relations in {data: {ctResult}} format
    public  List<String> treeFromIdMap(Long Id) {

        /**
         * private String id;
           private Long source;
           private Long target; */
        
        List<ctResult> listOfRel = haschildRepository.tree(Id);
        
        String id;
        Long source = 2L;
        Long target = 2L;

        String result;
        List<String> resultListRelations = new ArrayList<String>();

        for( int i = 0; i < listOfRel.size(); i ++ ) {
            
            id = listOfRel.get(i).getid();
            source = listOfRel.get(i).getSource();
            target = listOfRel.get(i).getTarget();
    
            result = "{" + " \"data\": { \"id\": \"" + id + "\", \"source\": \"" + source + "\", \"target\": \"" + target + "\" } }";

            resultListRelations.add(result);
            result = "";
        }

        return resultListRelations;
    }
}
