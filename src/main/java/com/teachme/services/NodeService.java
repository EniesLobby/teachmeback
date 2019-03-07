package com.teachme.services;
import java.util.*;
import com.teachme.domain.*;
import com.teachme.repositories.*;
import org.springframework.stereotype.Service;


@Service
public class NodeService {

    private final NodeRepository nodeRepository;
    private final CounterRepository counterRepository;
    private final hasChildRepository haschildRepository;
    private final hasInformationRepository hasInformationRepository;
    private final informationRepository informationRepository;
    private final multiInformationRepository multiInformationRepository;
    private final UserRepository userRepository;
    
    public NodeService(
        NodeRepository nodeRepository, 
        CounterRepository counterRepository,
        hasChildRepository haschildRepository, 
        hasInformationRepository hasinformationRepository, 
        informationRepository informationRepository, 
        multiInformationRepository multiInformationRepository,
        UserRepository userRepository
        ) {
        this.nodeRepository = nodeRepository;
        this.counterRepository = counterRepository;
        this.haschildRepository = haschildRepository;
        this.hasInformationRepository = hasinformationRepository;
        this.informationRepository = informationRepository;
        this.multiInformationRepository = multiInformationRepository;
        this.userRepository = userRepository;
    }

    // Creates new tree and returns id of the root
    public Long createTree() {
        
        Long rootId = getCurrentCounter();
        Node root = new Node(rootId, "", "", "", "", "", "", rootId);
        nodeRepository.save(root);
        
        return rootId;
    }

    public void createCounter() {
        
        Counter counter = new Counter();
        counterRepository.save(counter);
    }

    public String getTree(Long rootId) {

        return this.nodeRepository.getTree(rootId);
    }

    public Long getCurrentCounter() {
        
        Counter counter = counterRepository.findBylabel("counter");
        Long currentCounter = counter.getCounter();
        counter.increaseCounter();
        counterRepository.save(counter);

        return currentCounter;
    }

    public void createUser(String email, String name, String password, Boolean firstEnter ) {

        User user = new User(name, email, password, firstEnter);
        userRepository.save(user);
    }

    public void addRootId(String email, String rootId) {
        
        User user = userRepository.findByEmail(email);
        user.setTreeRootIds(rootId);
        userRepository.save(user);
    }

    public Long addChildToNode(Long Id, Node node) {
        
        Optional<Node> found_node = nodeRepository.findBynodeId(Id);
        Long nodeId = getCurrentCounter();
        node.setnodeId(nodeId);

        if(found_node.isPresent()) {
            Node source = found_node.get();
            Node target = node;

            found_node.get().addChild(source, target);
            nodeRepository.save(found_node.get());
        }

        return nodeId;
    }

    public Node getNode(Long Id) {
        
        Node thenode = nodeRepository.getNode(Id);
        
        return thenode;  
    }

    public void updateNode(Long Id, Node node) {
    
        Optional<Node> found_node = nodeRepository.findBynodeId(Id);
        
        if(found_node.isPresent()) {
            found_node.get().setAnswer(node.getAnswer());
            found_node.get().setQuestion(node.getQuestion());
            found_node.get().setInformation(node.getInformation());
            found_node.get().setAnswerHtml(node.getAnswerHtml());
            found_node.get().setQuestionHtml(node.getQuestionHtml());
            found_node.get().setQuestionLabel(node.getQuestionLabel());

            nodeRepository.save(found_node.get());
        }
    }

    // Delete node with all related information
    public void deleteNode(Long nodeId) {

        if(this.nodeRepository.findBynodeId(nodeId).isPresent()) {
            this.nodeRepository.deleteByNodeIdhasChildren(nodeId);
            this.nodeRepository.delete(this.nodeRepository.findBynodeId(nodeId).get());
        }
    }

    // it is just delete the node :)
    public void deleteAnswer(Long nodeId, String answer_id) {
        
        this.multiInformationRepository.deleteAnswer(nodeId, answer_id);
    }

    public void deleteAll() {
        
        nodeRepository.deleteAll();
        multiInformationRepository.deleteAll();
        informationRepository.deleteAll();
    }

    public void addInformation(Long node_id, String answer_id, String information, String note) {

        Optional<Node> found_node = nodeRepository.findBynodeId(node_id);

        if(found_node.isPresent()) {
            Optional<Information> found_inf = informationRepository.findWithNodeId(node_id);
            if(found_inf.isPresent()) {
                Optional<multiInformation> found_multiInf = multiInformationRepository.getOneInformation(node_id, answer_id);

                if(found_multiInf.isPresent()) {
                    found_multiInf.get().setInformation(information);
                    multiInformationRepository.save(found_multiInf.get());
                } else {
                    found_inf.get().setNewMultiInformation(answer_id, information, "");
                    informationRepository.save(found_inf.get());
                }

            } else {
                Information newInf = new Information(note, found_node.get().getrootId(), found_node.get().getnodeId());
                newInf.setNewMultiInformation(answer_id, information, "");
                hasInformation relInf = new hasInformation(found_node.get(), newInf);
                found_node.get().addInformation(relInf);
                nodeRepository.save(found_node.get());
            }
        }
    }

    public void deleteInformation(Long node_id, Long answer_id) {
        
    }

    public boolean checkUser(String email, String password) {
        
        User user = userRepository.findByEmail(email);
        if(Objects.equals(user.getPassword(), password)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String email) {

        return userRepository.findByEmail(email);
    }

    public void setViewed(String email) {

        User user = userRepository.findByEmail(email);
        user.setFirstEnter(false);
        userRepository.save(user);
    }


    public void setTitle(Long rootId, String title) {

        Optional<Information> information = this.informationRepository.findBynodeId(rootId);
        Optional<Node> foundNode = this.nodeRepository.findBynodeId(rootId);
        
        if(information.isPresent()) {
            information.get().setNote(title);
            this.informationRepository.save(information.get());
        } else {
            if(foundNode.isPresent()) {
                this.addInformation(rootId, "", "", title);
            }
        }
    }

    public boolean treeIsExist(Long rootId) {

        Optional<Node> foundNode = this.nodeRepository.findBynodeId(rootId);
        
        return foundNode.isPresent();
    }

    public void deleteTree(Long rootId, String email) {

        //this.deleteNode(rootId);
        User foundUser = this.userRepository.findByEmail(email);
        foundUser.deleteRootId(String.valueOf(rootId));

        for(int i = 0; i < foundUser.getTreeRootIds().size(); i ++ ) {
            
            Long currentRootId = Long.parseLong(foundUser.getTreeRootIds().get(i));
            if(!this.treeIsExist(currentRootId) ) {
                foundUser.deleteRootId(String.valueOf(currentRootId));
            }
        }

        this.userRepository.save(foundUser);
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
        String question_html;
        String answer_html;
        String question_label;
        String information;
        Long   rootId;

        String resultNodeString = "";
        List<Node> ListNodes = nodeRepository.treeCT(Id);

        for( int i = 0; i < ListNodes.size(); i ++ ) {
            
            nodeId      = ListNodes.get(i).getnodeId();
            question    = ListNodes.get(i).getQuestion();
            question_html = ListNodes.get(i).getQuestionHtml();
            answer      = ListNodes.get(i).getAnswer();
            answer_html = ListNodes.get(i).getAnswerHtml();
            information = ListNodes.get(i).getInformation();
            rootId      = ListNodes.get(i).getrootId();
            question_label = ListNodes.get(i).getQuestionLabel();

            resultNodeString = "{ \"data\": { \"id\": \"" + nodeId + "\", \"question\": \"" + question + "\", " +
                               "\"answerHtml\": \"" + answer_html + "\", " + "\"questionHtml\": \"" + question_html + "\", " + "\"questionLabel\": \"" + question_label + "\", " +
                               "\"answer\": \"" + answer + "\", \"information\": \"" + information + "\", \"rootId\": \"" + rootId + "\" } }";
            
            resultListNodes.add(resultNodeString);

            resultNodeString = "";
        }

        //Result TreeJsonString
        String treeJsonString = "[  ";

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

    public List<Node> getChildren(Long id) {

        List<Node> children =  this.nodeRepository.getChildren(id);
        
        return children;
    }

    public List<multiInformation> getAllInformation(Long nodeId) {
        
        List<multiInformation> allInf = this.multiInformationRepository.getAllInformation(nodeId);
        
        return allInf;
    }

    public Information getInformation(Long rootId) {
        Optional<Information> foundNode = this.informationRepository.findWithNodeId(rootId);
        
        if(foundNode.isPresent()) {
            return foundNode.get();
        } else {
            return null;
        }
    }

    public void updateTitle(Long rootId, String title) {
        
        Optional<Information> foundNode = this.informationRepository.findBynodeId(rootId);

        if(foundNode.isPresent()) {
            foundNode.get().setNote(title);
            informationRepository.save(foundNode.get());
        }
    }

    public multiInformation getInformationSpec(String id_node) {

        if(id_node.indexOf("-") != -1) {
            List<Long> idList = new ArrayList<>();

            // Need to sort array id_node
            for (String retval : id_node.split("-")) {
                Long currentId = Long.parseLong(retval);
                idList.add(currentId);
            }
    
            Collections.sort(idList);
    
            String newId = "";
    
            for(Long item : idList) {
                String currentString = Long.toString(item);
                newId = newId + currentString + "-";
            }
    
            String str = newId.substring(0, newId.length() - 1);
    
            Optional<multiInformation> foundMultiInformation = multiInformationRepository.getSpecificInformationMulti(str);
            if(foundMultiInformation.isPresent()) {
                return foundMultiInformation.get();
            } else {
                Optional<multiInformation> foundMultiInformationMirror = multiInformationRepository.getSpecificInformationMulti(id_node);
                return foundMultiInformationMirror.get();
            }
        } else {
            Optional<multiInformation> foundMultiInformation = multiInformationRepository.getSpecificInformation(id_node);
            if(foundMultiInformation.isPresent()) {
                return foundMultiInformation.get();
            }
        }
        
        return null;
    }

}
