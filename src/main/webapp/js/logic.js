
var ae = 0;

var data = [
 // nodes
 { "data": { "id": '1', question: "ae", answer: "ae", information: "ae" } },
 { "data": { "id": '2', question: "ae", answer: "ae", information: "ae" } },
 { "data": { "id": '3', question: "ae", answer: "ae", information: "ae" } },
 
 // edges
 { data: { id: '1-2', source: '1', target: '2' } },
 { data: { id: '1-3', source: '1', target: '3' } } ];

var appr =  [
    {
        selector: 'node',
        style: {
            'background-color': '#4db8ff',
            'border-width': 4,
            'border-color': '#006bb3',
            'width': 90,
            'height': 90,
            label: 'data(id)',
            'font-size': 50
        }
    }]

var appr_around =  [
    {
        selector: 'node',
        style: {
            'background-color': '#4db8ff',
            'border-width': 4,
            'border-color': '#006bb3',
            'width': 120,
            'height': 120,
            'font-size': 50
        }
}]

    
var options = {
    name: 'breadthfirst',
    
    fit: true, // whether to fit the viewport to the graph
    directed: false, // whether the tree is directed downwards (or edges can point in any direction if false)
    padding: 30, // padding on fit
    circle: false, // put depths in concentric circles if true, put depths top down if false
    spacingFactor: 1.75, // positive spacing factor, larger => more space between nodes (N.B. n/a if causes overlap)
    boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
    avoidOverlap: true, // prevents node overlap, may overflow boundingBox if not enough space
    nodeDimensionsIncludeLabels: false, // Excludes the label when calculating node bounding boxes for the layout algorithm
    roots: "#48", // the roots of the trees
    maximalAdjustments: 0, // how many times to try to position the nodes in a maximal way (i.e. no backtracking)
    animate: false, // whether to transition the node positions
    animationDuration: 500, // duration of animation in ms if enabled
    animationEasing: undefined, // easing of animation if enabled,
    animateFilter: function ( node, i ){ return true; }, // a function that determines whether the node should be animated.  All nodes animated by default on animate enabled.  Non-animated nodes are positioned immediately when the layout starts
    ready: undefined, // callback on layoutready
    stop: undefined, // callback on layoutstop
    transform: function (node, position ){ return position; } // transform a given node position. Useful for changing flow direction in discrete layouts
    };

var result_tree = [];

$(document).ready( function() {
    //////////////////////////
  // GET REQUEST

    $("#GetTreeById").click(function(event) {
        event.preventDefault();
        ajaxGet();
    });

    // DELETE ALL NODES FROM DB
    $("#deleteAll").click(function(event) {
        ajaxDeleteAll();
    });

    $("#createTree").click(function(event) {
        ajaxCreateTree();
    })

    $("#refreshView").click(function(event) {
        refresh();
    })

    // DO CREATE A TREE
    function ajaxCreateTree() {
        
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "nodes/createTree",
            success : function(result) {
                console.log(result);
            }
        });
    }

    // DO DELETE ALL NODES
    function ajaxDeleteAll() {
        
        $.ajax({
            url: 'nodes/deleteAll',
            type: 'DELETE',
            success: function(result) {
                alert("good");
            }
        });
    }

    // DO GET
    function ajaxGet() {
        var nodeId = 48;

        $.ajax({
            type : "GET",
            url : "nodes/getTreeCT?id=48",
            success: function(result) {
                
                result.replace(/ /g,'');
                console.log(result);
                result_tree = JSON.parse(result);
            },
            error : function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });	
    }

    // SUBMIT FORM
    $("#addNode").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });

    //Node update form handler
    $("#update_node").submit(function(event) {
        event.preventDefault();
        ajaxPut();
    })

    //Updates node
    function ajaxPut() {

        var formData = {
            question :  $("#question_update").val(),
            answer :  $("#answer_update").val(),
            information :  $("#information_update").val(),
            rootId: $("#rootId_update").val()
        }

        var nodeId = $("#nodeId_update").val();

        $.ajax({
            contentType : "application/json",
            url: '/nodes/node/' + nodeId,
            type: 'PUT',
            data: JSON.stringify(formData),
            success : function(result) {
                console.log(result);
                console.log(JSON.stringify(formData));
            }
          });
    }

    //Creates new node
    function ajaxPost() {
        
        // PREPARE FORM DATA
        var formData = {
            question :  $("#question").val(),
            answer :  $("#answer").val(),
            information :  $("#information").val(),
            rootId: $("#rootId").val()
        }

        var nodeId = $("#nodeId").val();
        ///node/add/{id}
        // DO POST
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "nodes/node/add/" + nodeId,
            data : JSON.stringify(formData),
            dataType : 'json',
            success : function(result) {
                console.log(result);
            }
        });

        $("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" + 
        "Post Successfully! <br>");
        // Reset FormData after Posting
        resetData();
        refresh();
    }

    function resetData() {
        $("#nodeId").val("");
        $("#question").val("");
        $("#answer").val("");
        $("#information").val("");
        $("#rootId").val("")
    }
    /////////////////////////

    function refresh() {
        $.ajax({
            type : "GET",
            url : "nodes/getTreeCT?id=48",
            success: function(result) {
                callback(result);
            },
            error : function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });	
    }


    function callback(result) {

        //Draw a tree only after the json-tree was got
        result_tree = JSON.parse(result);
        console.log(result_tree);

        var cy = cytoscape({
            container: $('#cy'),
            elements: result_tree,
            style: appr
          });
        
        cy.layout( options ).run();

        cy.on('tap', 'edge', function(evt) {
            var edge = evt.target;
            console.log( 'tapped ' + edge.id() );
          });
    
        cy.on('tap', 'node', function(evt) {
            var node = evt.target;
            cy.$("#" + node.id()).style({'background-color': '#b3e0ff'})
            // onclick view nodes params
            var pos = cy.$("#" + node.id()).position();

            //Show information about clicked node
            $("#node_information").empty();
            // actions on the second circle:
            // [o] Toggle/View children
            // [o] Add child
            // [o] Delete node
            // [o] Update node        
    
        });
    
        cy.on('tap', function(event) {
            // target holds a reference to the originator
            // of the event (core or element)
    
            var evtTarget = event.target;
          
            if( evtTarget === cy ) {
                cy.$("node").style({'background-color': '#4db8ff'})
            } else {
              console.log('tap on some element');
            }
          });

          //Q-TIP///POP UP MESSAGES///////
          cy.elements().qtip({
            content: function(){ return "id: " + 
            this.id() + " information: " + 
            this.data().information + " question: " + 
            this.data().question + " answer: " + 
            this.data().answer },

            position: {
                my: 'top center',
                at: 'bottom center'
            },
            style: {
                classes: 'qtip-bootstrap',
                tip: {
                    width: 16,
                    height: 8
                }
            }
        });
        /////////////////////////////////////

          cy.layout( options ).run();

          /////////////////////////Right-click menu///////////////////////////////
          var contextMenu = cy.contextMenus({
            menuItems: [
                {
                    id: 'add_node',
                    content: 'New Answer',
                    selector: 'node',
                    onClickFunction: function (event) {
                        
                        var node = event.target;
                        //Show add node form with nodeId clicked
                        console.log("add node");
                        $('#myModal').modal('toggle');
                        $('#current_question').append(node.data().question)
                        $("#nodeId").val(node.id());
                        $("#question").val("");
                        $("#answer").val("");
                        $("#information").val("");
                        $("#rootId").val(node.data().rootId)

                    },
                    hasTrailingDivider: false
                  },
                  {
                    id: 'edit_node',
                    content: 'Edit node',
                    selector: 'node',
                    onClickFunction: function (event) {
                        
                        var node = event.target;
                        
                        console.log("edit");

                        $("#nodeId_update").val(node.id());
                        $("#question_update").val(node.data().question);
                        $("#answer_update").val(node.data().answer);
                        $("#information_update").val(node.data().information);
                        $("#rootId_update").val(node.data().rootId)
                        
                        $('#EditModal').modal('toggle');
                    },
                    hasTrailingDivider: false
                  },
                  {
                    id: 'remove',
                    content: 'Remove',
                    selector: 'node',
                    onClickFunction: function (event) {
                        console.log("remove");
                    },
                    hasTrailingDivider: false
                  }]});
          ////////////////////////////////////////////////////////////////////////
    }

    refresh();

});