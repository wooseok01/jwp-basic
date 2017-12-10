// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);

$("#remove-question-button").on("click", function(e){
	e.preventDefault();
	$.ajax({
		url : "/api/qna/deleteQuestion",
		type : "get",
		data : {questionId : $("#questionId").val()},
		dataType : "json",
		success : function(json) {
			var result = json.result;
			if(result.status) {
				alert("질문이 삭제되었습니다.");
				location.href = "/";
			} else if(result.status == "delete_fail") {
				alert("질문 삭제에 실패했습니다.");
			}
		}, error : onError
	});
});

$(".link-modify-article").on("click", function(){
	$.ajax({
		url : "/api/qna/update/confirm",
		type : "get",
		data : {questionId : $("#questionId").val()},
		dataType : "json",
		success : function(json) {
			var result = json.result;
			if(result.status) {
				location.href = "/qna/update/form?questionId="+$("#questionId").val();
			} else if(result.status == false && result.message == "not_login") {
				alert("로그인을 해야합니다.");
				location.href = "/users/loginForm";
				
			} else if(result.status == false && result.message == "data_access_fail") {
				alert("error!");
				
			} else if(result.status == false && result.message == "access_denied") {
				alert("수정 권한이 없습니다.");
			}
		},
		error : onError
	});
});

$(".link-delete-article").on("click", function(e){
	e.preventDefault();
	var thisObj = $(this);
	var answerId = thisObj.parent().find("input[type='hidden']").val();
	var url = thisObj.parent().attr("action");
	
	$.ajax({
		url : url,
		type : "get",
		data : {answerId : answerId},
		dataType : "json",
		success : function(json) {
			if(json.result.status) {
				var countOfAnswer = $(".qna-comment-count > strong").html()*1;
				$(".qna-comment-count > strong").html(countOfAnswer-1);
				
				thisObj.parent().parent().parent().parent().parent().remove();
				alert("삭제가 완료되었습니다.");
			}
		},
		error : onError
	});
});

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
  
  var countOfAnswer = $(".qna-comment-count > strong").html()*1;
  $(".qna-comment-count > strong").html(countOfAnswer+1);
  
  $("#writer").val("");
  $("#contents").val("");
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};