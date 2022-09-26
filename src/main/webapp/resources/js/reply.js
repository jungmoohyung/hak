/**
 * 2022.09.26 댓글 모듈
 */
 
 console.log("Reply Module start");
 
 var replyService = (function(){
 	
 	function add(reply, callback){
 		console.log("reply add.....");
 		
 		$.ajax({
 			type: 'post',
 			url : '/reply/add',
 			data : JSON.stringify(reply),
 			contentType : 'application/json; charset=utf-8',
 			success : function(result, status, xhr){
 				if (callback){
 					callback(result);
 					/*location.href="/board/detail?no="+reply.boardNo;*/
 				}
 			},
 			error : function (xhr, status, er){
 				if (error) {
 					error(er);
 				}
 			}
 		
 		});
 	}
 	
 	function getList(param, callback, error){
 		var bno = param.bno;
 		var page = param.page || 1;
 		
 		$.getJSON("/reply/list/"+bno+"/"+page+".json",function(data){
 			if(callback){
 				callback(data);
 			}
 		}).fail(function(xhr,status,err){ 
 			if(error){
 				error();
 			}
 		});
 	}
 	
 	
	 	
 	return {
 		add:add,
 		getList:getList
	};
 	
 })();
 