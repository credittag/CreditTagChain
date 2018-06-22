$(function() {
	var rows = 20;   // 默认一页20条
	var totalData = 0;
	
	
	function getDataByPage(paramData){
        $.ajax({
			type : "post",
			url : 'queryBlockList',
			data: paramData,
			async:false, 
			success : function(msg) {
				$("#alltBlockListId").empty();
				totalData = msg.total;					
				var res = '';
				$.each(msg.rows, function( key, val ) {
					res = res 
					    + '<div>'
			       	 	+ '<div class="trxCls"><span class="width15"><a href="block.do?blockId='+val.blockId+'">'+val.blockNum+'</a></span> <span class="width23">'+val.blockTimeStr+'</span> <span class="width15">'+val.signee+'</span><span class="width23">'+val.trxNum+'</span><span class="width23">'+val.amountBig+'</span></div>'
			   		 	+ '</div>';
				} );
				
				$("#alltBlockListId").append(res);
			},
			error : function(msg) {
				alert("未知错误：请联系管理员");
			}
		});
	}
	
	getDataByPage();
	
	$('#blockListId').pagination({
	    totalData: totalData,
	    showData: rows,
	    homePage: '<<',
	    endPage: '>>',
	    coping: true,
	    jump: true,
	    callback: function (api) {
	        var paramData = {
	        		rows: rows,                         //页面大小，一页多少行
                    page: api.getCurrent()
	        };
	        getDataByPage(paramData);
	    }
	});	

	
 
});

