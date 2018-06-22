$(function() {

	var rows = 20;   // 默认一页20条
	var totalData = 0;
	
	var allLinesHeigh = 900;
	
	
	function getDataByPage(paramData){
        $.ajax({
			type : "post",
			url : 'queryAllTransactionList',
			data: paramData,
			async:false, 
			success : function(msg) {
				$("#alltransactinListId").empty();
				totalData = msg.total;					
				var res = '';
				allLinesHeigh = ((msg.rows.length * 44) + 45) + "px";
				$.each(msg.rows, function( key, val ) {
					
						res = res 
					    +'<div>'
			       	 	+ '<div class="trxCls mouseOverOrOut"><span class="width40 height40"><a href="detail.do?trxId='+val.trxId+'">'+val.trxId+ '</a></span> <span class="width15 height40">'+val.trxTypeStr+'</span> <span class="width30 height40">'+val.trxTimeStr+'</span> <span class="width23 height40">'+val.voAmountBig+'</span></div>'
			        	+ '<div class="trxDetailCls"><label class="width40"><a href="javascript:void(0);" onclick="hrefAddress(\''+val.fromAccount+'\')">'+val.fromAccount+'</a></label><label class="width80"><span><img src="/img2.0/you.png" /></span></label><label><a href="javascript:void(0);" onclick="hrefAddress(\''+val.toAccount+'\')">'+val.toAccount+'</a></label></div>'
			   		 	+ '</div>';
				

				} );

				$("#alltransactinListId").append(res);
				
			    $(".mouseOverOrOut").mouseover(function(){
	
			    	$(".trxDetailCls").css("display","none");
			        $(this).next().css("display","block");

			     });
			     
			     $("#alltransactinListId").css("height",allLinesHeigh);
			},
			error : function(msg) {
				alert("未知错误：请联系管理员");
			}
		});
	}
	
	$("#alltransactinListId").mouseleave(function(){
		$(".trxDetailCls").css("display","none");
	});


	getDataByPage();
	

		var endpage=Math.ceil(totalData/rows)+'';

		$('#transactinListId').pagination({
		    totalData: totalData,
		    showData: rows,
		    homePage: '1',
		    endPage: endpage,
		    coping: true,
		    //jump: true,
		    callback: function (api) {
		        var paramData = {
		        		rows: rows,                         //页面大小，一页多少行
	                    page: api.getCurrent()
		        };
		        getDataByPage(paramData);
		    }
		});	

});
