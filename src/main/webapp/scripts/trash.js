function recover(){
 		 $("#recover_ul a").removeClass("checked");
		 $(this).parent().parent().find("a").addClass("checked");
 		 //获取请求参数
 		 var $li = $("#recover_ul a.checked").parent();
 		 var noteId = $li.data("noteId");
 		 console.log(noteId);
 		 //发送请求
 		 $.ajax({
 			url:base_path+"/trash/recover.do",
 			type:"post",
 			data:{"noteId":noteId},
 			dataType:"json",
 			success:function(result){
 				if(result.status==0){
 					$li.remove();
 					alert(result.msg);
 				}else if(result.status==1){
 					alert(result.msg);
 				}
 			},
 			error:function(){
 				alert("Recovery failed");
 			}
 		 });
 	 }

function trash(){
	$("#pc_part_4 ul").empty();
	 $("#pc_part_2").hide();
	 $("#pc_part_4").show();
	 $("#pc_part_6").hide();
	 $("#pc_part_7").hide();
	 $("#pc_part_8").hide();
	 var userId = getCookie("uid");
	
	$.ajax({
		url:base_path+"/note/trash.do",
		type:"post",
		data:{"userId":userId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var trashes = result.data;
				for(var i=0;i<trashes.length;i++){
					var noteTitle=trashes[i].cn_note_title;
					var noteId=trashes[i].cn_note_id;
					var sli = "";
					sli+='<li class="disable"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
					sli+= noteTitle;
					sli+='<button type="button" class="btn btn-default btn-xs btn_position btn_delete">';
					sli+='<i class="fa fa-times"></i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay">';
					sli+='<i class="fa fa-reply"></i></button></a></li>';
					var $li = $(sli);
					$li.data("noteId",noteId);
					 $("#pc_part_4 ul").append($li);
				}
			}
		},
		error:function(){
			alert("Loading list failed");
		}
		
	});
 }

function sureRemove(){
	 var $li = $("#recover_ul a.checked").parent();
	 var noteId = $li.data("noteId");
	 //发送请求
	 $.ajax({
		url:base_path+"/trash/sure_delete.do", 
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.msg);
				$li.remove();
			}
		},
		error:function(){
			alert("Discarding failed");
		}
	 });
}