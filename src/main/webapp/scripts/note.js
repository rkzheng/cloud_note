/**note.js 封装笔记相关操作**/
//分享笔记
function shareNote(){
	 //获取请求参数
	 var $li = $(this).parents("li");
	 var noteId = $li.data("noteId");
	 //发送Ajax请求
	 $.ajax({
		 url:base_path+"/note/share.do",
		 type:"post",
		 data:{"noteId":noteId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 //添加分享图标
				 var img = '<i class="fa fa-sitemap"></i>';
				 $li.find(".btn_slide_down").before(img);
				 //提示成功
				 alert(result.msg);
			 }else if(result.status==1){
				 //提示已分享过
				 alert(result.msg);
			 }
		 },
		 error:function(){
			 alert("Share note exception");
		 }
	 });
 };
 
 function searchSharePreview(){
		 $("#search_ul a").removeClass("checked");
		 $(this).find("a").addClass("checked");
		 //获取请求参数
		 var shareId = $(this).data("shareId");
		 
		 $.ajax({
			url:base_path+"/share/preview.do",
			type:"post",
			data:{"shareId":shareId},
			dataType:"json",
			success:function(result){
				
				if(result.status==0){
					 var title = //获取笔记标题
						 result.data.cn_share_title;
					 var body = //获取笔记内容
						 result.data.cn_share_body;
					 //设置到编辑区域
					 $("#input_note_title").val(title);
					um.setContent(body);
				}
			},
			error:function(){
				alert("Note preview exception");
			}
		 });
	}
//转移笔记
function moveNote(){
	 //获取请求参数
	 //获取要转移的笔记ID
	 var $li = 
		$("#note_ul a.checked").parent();
	 var noteId = $li.data("noteId");
	 //获取要转入的笔记本ID
	 var bookId = $("#moveSelect").val();
	 //发送Ajax请求
	 $.ajax({
		 url:base_path+"/note/move.do",
		 type:"post",
		 data:{"noteId":noteId,"bookId":bookId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 //移除笔记li
				 $li.remove();
				 //提示成功
				 alert(result.msg);
			 }
		 },
		 error:function(){
			 alert("转移笔记异常");
		 }
	 });
 };
//删除笔记
function deleteNote(){
	 //获取请求参数
	 var $li = 
		$("#note_ul a.checked").parent();
	 var noteId = $li.data("noteId");
	 //发送Ajax请求
	 $.ajax({
		 url:base_path+"/note/delete.do",
		 type:"post",
		 data:{"noteId":noteId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 //删除li
				 $li.remove();
				 //提示成功
				 alert(result.msg);
			 }
		 },
		 error:function(){
			 alert("删除笔记异常");
		 }
	 });
 };
//隐藏笔记菜单
function hideNoteMenu(){
	 //隐藏所有笔记菜单
	 $("#note_ul div").hide();
 };
//弹出笔记菜单
function popNoteMenu(){
	 //隐藏所有笔记菜单
	 $("#note_ul div").hide();
	 //显示点击的笔记菜单
	 var $menu = $(this).parent().next();
	 $menu.slideDown(1000);
	 //设置点击笔记选中效果
	 $("#note_ul a").removeClass("checked");
	 $(this).parent().addClass("checked");
	 //阻止事件向li,body冒泡
	 return false;
 };
//创建笔记操作
function addNote(){
	 //获取请求参数
	 var userId = getCookie("uid");
	 var noteTitle = //获取对话框中笔记名称
		$("#input_note").val().trim();
	 var $li = //获取选中的笔记本li元素
		$("#book_ul a.checked").parent();
	 var bookId = $li.data("bookId");
	 //检查格式
	 var ok = true;
	 if(userId==null){
		 ok = false;
		 window.location.href="log_in.html";
	 }
	 if(noteTitle==""){
		 ok = false;
		 $("#note_span").html("note name can not be empty");
	 }
	 //发送Ajax请求
	 if(ok){
		 $.ajax({
			 url:base_path+"/note/add.do",
			 type:"post",
			 data:{"userId":userId,
				 "noteTitle":noteTitle,
				 "bookId":bookId},
			 dataType:"json",
			 success:function(result){
				 if(result.status==0){
					 //关闭对话框
					 closeAlertWindow();
					 //生成笔记列表li
					 var noteId = result.data;//获取服务器返回的笔记ID
					 createNoteLi(noteId,noteTitle);
					 //弹出提示
					 alert(result.msg);
				 }else if(result.status==1){
					 alert(result.msg);
				 }
			 },
			 error:function(){
				 alert("Note creating exception");
			 }
		 });
	 }
 };
//"保存笔记"按钮的处理
function updateNote(){
	 //获取请求参数
	 var title = 
		$("#input_note_title").val().trim();
	 var body = um.getContent();
	 var $li = //获取选中的笔记li元素
		 $("#note_ul a.checked").parent();
	 var noteId = $li.data("noteId");
	 //清除上次提示信息
	 $("#note_title_span").html("");
	 //检查格式
	 if($li.length==0){
		 alert("Select a notebook");
	 }else if(title==""){
		 $("#note_title_span").html("<font color='red'>name cannot be empty</font>");
	 }else{
		//发送Ajax请求
		$.ajax({
			url:base_path+"/note/update.do",
			type:"post",
			data:{"noteId":noteId,
				"title":title,"body":body},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//更新列表li中标题
					var sli = "";
					sli+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
					sli+= title;
					sli+='<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
					//将选中li元素的a内容替换
					$li.find("a").html(sli);
					//提示成功
					alert(result.msg);
				}
			},
			error:function(){
				alert("Saving exception");
			}
		});
	 }
 };
//根据笔记ID加载笔记信息
function loadNote(){
	 //设置笔记选中状态
	 $("#note_ul a").removeClass("checked");
	 $(this).find("a").addClass("checked");
	 //获取请求参数
	 var noteId = $(this).data("noteId");
	 //发送Ajax请求
	 $.ajax({
		 url:base_path+"/note/load.do",
		 type:"post",
		 data:{"noteId":noteId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 var title = //获取笔记标题
					 result.data.cn_note_title;
				 var body = //获取笔记内容
					 result.data.cn_note_body;
				 //设置到编辑区域
				 $("#input_note_title").val(title);
				 um.setContent(body);
			 }
		 },
		 error:function(){
			 alert("Load note exception");
		 }
	 });
 };
//根据笔记本ID加载笔记列表
function loadBookNotes(){
	 //切换列表显示
	 $("#pc_part_2").show();
	 $("#pc_part_4").hide();
	 $("#pc_part_6").hide();
	 $("#pc_part_7").hide();
	 $("#pc_part_8").hide();
	 //设置笔记本li选中效果
	 $("#book_ul a").removeClass("checked");
	 $(this).find("a").addClass("checked");
	 //获取请求参数
	 var bookId = $(this).data("bookId");
	 //发送Ajax请求
	 $.ajax({
		 url:base_path+"/note/loadnotes.do",
		 type:"post",
		 data:{"bookId":bookId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 //清空原有笔记列表
				//$("#note_ul").empty();
				 $("#note_ul li").remove();
				 //获取服务器返回的笔记集合信息
				 var notes = result.data;
				 //循环生成笔记li元素
				 for(var i=0;i<notes.length;i++){
					 //获取笔记ID和笔记标题
					 var noteId = notes[i].cn_note_id;
					 var noteTitle = notes[i].cn_note_title;
					 //创建一个笔记li元素
					 createNoteLi(noteId,noteTitle);
					 //将新添加的元素判断是否该加分享图标
					 var typeId = 
						 notes[i].cn_note_type_id;
					 if(typeId=='2'){//加分享图标
						 var img = '<i class="fa fa-sitemap"></i>';
						 //获取新添加的li元素
						 var $li = $("#note_ul li:last");
						 $li.find(".btn_slide_down").before(img);
					 }
				 }
			 }
		 },
		 error:function(){
			 alert("加载笔记列表异常");
		 }
	 });
 };
 
 //创建笔记列表li元素
 function createNoteLi(noteId,noteTitle){
	var sli = "";
	sli+='<li class="online">';
	sli+='	<a>';
	sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
	sli+= noteTitle;
	//sli+='<i class="fa fa-sitemap"></i>';
	sli+='		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
	sli+='	</a>';
	sli+='	<div class="note_menu" tabindex="-1">';
	sli+='		<dl>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_move" title="move to..."><i class="fa fa-random"></i></button></dt>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_share" title="share"><i class="fa fa-sitemap"></i></button></dt>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="delete"><i class="fa fa-times"></i></button></dt>';
	sli+='		</dl>';
	sli+='	</div>';
	sli+='</li>';
	//将noteId绑定到li元素上
	var $li = $(sli);
	$li.data("noteId",noteId);
	//将li元素添加到笔记列表ul中
	$("#note_ul").append($li); 
 };
 //分页查询笔记
 function searchByPage(keyword,page){
	
 $.ajax({
		 url:base_path+"/note/search_share.do",
		 type:"post",
		 data:{"keyword":keyword,"page":page},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 //获取服务器返回的搜索结果
				 var shares = result.data;
				
				 //循环解析生成列表li元素
				 for(var i=0;i<shares.length;i++){
					 var shareId = //分享ID
						 shares[i].cn_share_id;
					 var shareTitle = //分享标题
						 shares[i].cn_share_title;
					 //生成一个li
					  
				var sli = "";
				sli+='<li class="online">';
				sli+='	<a>';
				sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
				sli+= shareTitle;
				sli+='		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down" id="fav_button"><i class="fa fa-star"></i></button>';
				sli+='	</a>';
				sli+='</li>';
				var $li = $(sli);
				$li.data("shareId",shareId);
				
					 //添加到搜索结果ul中
					 $("#pc_part_6 ul").append($li);
					 console.log(i);
					 var favStatus = 
						 shares[i].fav_status;
					 if(favStatus=='1'){//加分享图标
						 var img = '<i class="fa fa-star"></i>';
						 //获取新添加的li元素
						 var $li = $("#search_ul li:last");
						 
						 $li.find("#fav_button").before(img);
					 }
				 }
				
			 }
		 },
		 error:function(){
			 alert("搜索异常");
		 }
	 });
 }
 
 
 function searchShare(event){
		 var code = event.keyCode;
		 if(code==13){//回车键
			//清除原有列表结果
		 $("#pc_part_6 ul").empty();
			 //显示搜索结果列表,其他列表隐藏
		 $("#pc_part_2").hide();
		 $("#pc_part_4").hide();
		 $("#pc_part_6").show();
		 $("#pc_part_7").hide();
		 $("#pc_part_8").hide();
			 //获取请求参数
			 
			 keyword = 
				$("#search_note").val().trim();
			 page=1;
			 //发送Ajax请求
			 searchByPage(keyword,page);
		 }
	 }
 function addFavorite(){
		var $li = $(this).parents("li");
		var shareId = $li.data("shareId");
		console.log(shareId);
		var userId = getCookie("uid");
		
		$.ajax({
			url:base_path+"/favorite.do",
			type:"post",
			data:{"shareId":shareId,"userId":userId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					 var img = '<i class="fa fa-star"></i>';
					 $li.find("#fav_button").before(img);
					 alert(result.msg);
				}else if(result.status==1){
					console.log(1);
					alert(result.msg);
				}
			},
			error:function(){
				alert("favorite exception");
			}
		});
	}
 function showfav(){
		$("#pc_part_7 ul").empty();
		 $("#pc_part_2").hide();
		 $("#pc_part_4").hide();
		 $("#pc_part_6").hide();
		 $("#pc_part_7").show();
		 $("#pc_part_8").hide();
		 var userId = getCookie("uid");
		 
		 $.ajax({
			 url:base_path+"/showfav.do",
			 type:"post",
			 data:{"userId":userId},
			 dataType:"json",
			 success:function(result){
				 if(result.status==0){
					 var favs = result.data;
					for(var i=0;i<favs.length;i++){
						var favTitle=favs[i].fav_title;
						var favId=favs[i].fav_id;
						
						var sli = "";
						sli+='<li class="idle"><a >';
						sli+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
						sli+=favTitle;
						sli+='<button type="button" class="btn btn-default btn-xs btn_position btn_delete" id="cancel_fav">';
						sli+='<i class="fa fa-times"></i></button></a></li>';
						var $li = $(sli);
						$li.data("favId",favId);
						$("#pc_part_7 ul").append($li);
					}
				}
			 },
			 error:function(){
				 alert("Loading favorite exception");
			 }
			 
		 });
	}
 
 function favPreview(){
		$("#fav_ul a").removeClass("checked");
		 $(this).find("a").addClass("checked");
		 //get request data
		 var favId = $(this).data("favId");
		 console.log(favId);
		 $.ajax({
			url:base_path+"/favpreview.do",
			type:"post",
			data:{"favId":favId},
			dataType:"json",
			success:function(result){
				if(result.status==1){
					alert(result.msg);
				}else if(result.status==0){
					var fav = result.data;
					var favTitle = fav.fav_title;
					var favBody = fav.fav_body;
					$("#input_note_title").val(favTitle);
					um.setContent(favBody);
				}
			},
			error:function(){
				alert("Loading preview exception");
			}
		 });
	}
 