$( document ).ready(function() {
  
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
		var nodeId = 27;

		$.ajax({
			type : "GET",
			url : window.location + "nodes/getTree?id=" + nodeId,
			success: function(result){
                console.log(result);
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

	function ajaxPost(){
    	
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
 
    }
    
    function resetData(){
    	$("#nodeId").val("");
		$("#question").val("");
		$("#answer").val("");
		$("information").val("")
    }
})