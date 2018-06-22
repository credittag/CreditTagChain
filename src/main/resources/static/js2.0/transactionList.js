$(function() {

	var allLinesHeigh = 0;
	var address = $("#address").html();

	$("#addressLabelId").text(address);
	
	var balanceDisPlayId = $("#dispalyBalanceId").html();
	console.log(balanceDisPlayId);
	if(balanceDisPlayId == 1){
		$("#balanceLabelId").text($("#balanceId").html());
		$("#bdispalyid").removeClass("hid");
	}else {
		$("#bdispalyid").addClass("hid");
	}

	var rows = 20;   // 默认一页20条
	var totalData = 0;
	
	
	function getDataByPage(paramData){
        $.ajax({
			type : "post",
			url : 'queryTransactionList',
			data: paramData,
			async:false, 
			success : function(msg) {
				$("#transactinListId").empty();
				totalData = msg.total;
				var res = '';
				allLinesHeigh = ((msg.rows.length * 44) + 50) + "px";
				$.each(msg.rows, function( key, val ) {
					if(val.direction == '0'){
						res = res 
					    +'<div>'
			       	 	+ '<div class="trxCls mouseOverOrOut"><span class="width40 height40"><a href="detail.do?trxId='+val.trxId+'">'+val.trxId+ '</a></span> <span class="width15 height40">'+val.trxTypeStr+'</span> <span class="width30 height40">'+val.trxTimeStr+'</span> <span class="width23 height40"><img class="sximg" src="/img2.0/x.png">'+val.voAmountBig+'</span></div>'
			        	+ '<div class="trxDetailCls"><label class="width40"><a href="javascript:void(0);" onclick="hrefAddress(\''+val.fromAccount+'\')">'+val.fromAccount+'</a></label><label class="width80"><span><img src="/img2.0/you.png" /></span></label><label><a href="javascript:void(0);" onclick="hrefAddress(\''+val.toAccount+'\')">'+val.toAccount+'</a></label></div>'
			   		 	+ '</div>';
					}else if(val.direction == '1'){
						
						res = res 
					    +'<div>'
			       	 	+ '<div class="trxCls mouseOverOrOut"><span class="width40 height40"><a href="detail.do?trxId='+val.trxId+'">'+val.trxId+ '</a></span> <span class="width15 height40">'+val.trxTypeStr+'</span> <span class="width30 height40">'+val.trxTimeStr+'</span> <span class="width23 height40"><img class="sximg" src="/img2.0/s.png">'+val.voAmountBig+'</span></div>'
			        	+ '<div class="trxDetailCls"><label class="width40"><a href="javascript:void(0);" onclick="hrefAddress(\''+val.fromAccount+'\')">'+val.fromAccount+'</a></label><label class="width80"><span><img src="/img2.0/you.png" /></span></label><label><a href="javascript:void(0);" onclick="hrefAddress(\''+val.toAccount+'\')">'+val.toAccount+'</a></label></div>'
			   		 	+ '</div>';

					}else{
						res = res 
					    +'<div>'
			       	 	+ '<div class="trxCls mouseOverOrOut"><span class="width40 height40"><a href="detail.do?trxId='+val.trxId+'">'+val.trxId+ '</a></span> <span class="width15 height40">'+val.trxTypeStr+'</span> <span class="width30 height40">'+val.trxTimeStr+'</span> <span class="width23 height40">'+val.voAmountBig+'</span></div>'
			        	+ '<div class="trxDetailCls"><label class="width40"><a href="javascript:void(0);" onclick="hrefAddress(\''+val.fromAccount+'\')">'+val.fromAccount+'</a></label><label class="width80"><span><img src="/img2.0/you.png" /></span></label><label><a href="javascript:void(0);" onclick="hrefAddress(\''+val.toAccount+'\')">'+val.toAccount+'</a></label></div>'
			   		 	+ '</div>';
					}

				} );
				
				$("#transactinListId").append(res);
				
			    $(".mouseOverOrOut").mouseover(function(){

			    	$(".trxDetailCls").css("display","none");
			        $(this).next().css("display","block");

			     });

			     
			     $("#transactinListId").css("height",allLinesHeigh);
			},
			error : function(msg) {
				alert("未知错误：请联系管理员");
			}
		});
	}
	var paramDataIndex = {
    		rows: rows,                         //页面大小，一页多少行
            page: 1,
            address:address
    };
	getDataByPage(paramDataIndex);
	
	$("#transactinListId").mouseleave(function(){
		$(".trxDetailCls").css("display","none");
	});

	if(totalData > 0){
		$('#transactinListPageId').pagination({
		    totalData: totalData,
		    showData: rows,
		    homePage: '<<',
		    endPage: '>>',
		    coping: true,
		    jump: true,
		    callback: function (api) {
		        var paramData = {
		        		rows: rows,                         //页面大小，一页多少行
	                    page: api.getCurrent(),
	                    address:address
		        };
		        getDataByPage(paramData);
		    }
		});	
	}


  
  
	

});
	 


