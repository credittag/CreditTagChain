function hrefBlock(blockId) {
	window.location.href = "block.do?blockId=" + blockId;
}

function hrefTransaction(trxId) {
	window.location.href = "detail.do?trxId=" + trxId;
}

function hrefBlockNum(blockNum) {
	window.location.href = "blockNum.do?blockNum=" + blockNum;
}
function hrefAddress(address) {
	window.location.href = "transactionList?address=" + address;
}

function hrefContractDetail(contractId) {
	window.location.href = "contractDetail?contractId=" + contractId;
}

// 块的全部
function blockList(){
	window.location.href = "blockList";
}

function allTransactionList(){
	window.location.href = "allTransactionList";
}

$(function() {
	$("ul li").on("click", function() {
		var index = $(this).index();
		$("ul li").each(function(){
		    $(this).removeClass("active");
		  });
		$(this).addClass("active");
		if (index == 0) {
			window.location.href = "https://www.credittag.io#homepage";
		} else if (index == 1) {
			window.location.href = "https://www.credittag.io#creditpage";
		} else if (index == 2) {
			window.location.href = "https://www.credittag.io#productpage";
		} else if (index == 3) {
			window.location.href = "https://browser.credittag.io";
		} else if (index == 4) {
			window.location.href = "https://www.credittag.io#partnerpage";
		} else if (index == 5) {
			window.location.href = "https://www.credittag.io#contactpage";
		}
	});
	$('#txtSearchInput').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            // 回车执行查询
            $('#searchInput').click();
        }
    });
	$("#searchInput").on(
			"click",
			function() {
				var keyword = $("#txtSearchInput").val();
				// 主地址  或者 子地址
/*				if (keyword != null && keyword != ""
						&& (RegExp(/^[a-zA-Z0-9]{30,39}$/).test(keyword) || RegExp(/^[a-zA-Z0-9]{60,78}$/).test(keyword))) {
					hrefAddress(keyword);
					return;
				}*/
				
				if(keyword != null && keyword != ""){
					keyword = keyword.trim();
					if((RegExp(/^[a-zA-Z0-9]{30,39}$/).test(keyword) || RegExp(/^[a-zA-Z0-9]{60,78}$/).test(keyword))){
						hrefAddress(keyword);
						return;
					}
				}
				
				
				if (keyword != "") {
					$.ajax({
						type : "POST",
						url :"searchBlock",
						data : {
							"keyword" : keyword
						},
						dataType : "json",
						success : function(data, textStatus) {
							var retCode = data.key;
							if ('fail' == retCode) {
								if($('#alert').length<=0){
								 $("body").before('<div id="alert" class="alert alert-warning"  role="alert"><a href="#" class="close" data-dismiss="alert">&times;</a>Did not check the results</div>'); 
								 setTimeout(function(){
									 $('div#alert').remove();
								 },3000);
								}
							}
							if ("block" == retCode) {
								hrefBlock(data.values);
							}
							if ("blockNum" == retCode) {
								hrefBlockNum(data.values);
							}
							if ("trx" == retCode) {
								hrefTransaction(data.values);
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							if($('#alert').length<=0){
							$("body").before('<div id="alert" class="alert alert-warning" role="alert"><a href="#" class="close" data-dismiss="alert" >&times;</a>Did not check the results</div>'); 
							 setTimeout(function(){
								 $('div#alert').remove();
							 },3000);
							}
						}
					});
				}
			});
});
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?94bf3c9104995527f406721fd8d914c6";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();