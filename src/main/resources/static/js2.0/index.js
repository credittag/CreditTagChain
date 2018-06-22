$(function() {

	// 1.初始化Table
	var oTable = new TableInit();
	oTable.Init();

	// 2.初始化Button的点击事件
	var oButtonInit = new ButtonInit();
	oButtonInit.Init();

});

function loadStatic() {
	$.ajax({
		type : "POST",
		url : "getStatis",
		dataType : "json",
		success : function(data, textStatus) {
			$("#transCount").empty().append(data.trxCount);
			$("#transAmount").empty().append(data.allTrxAmount);
			$("#transFee").empty().append(data.allTrxFee);
			$("#transAveHour").empty().append(data.trxLatestHour);
			$("#maxHourCount").empty().append(data.trxMaxHour);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}
// block info
function loadBlock() {
	$.ajax({
		type : "get",
		url : "blocksInfo",
		dataType : "json",
		success : function(data) {
			$('#tb_block').bootstrapTable('load', data);
		}
	});
}
// trans info
function loadTransaction() {
	$.ajax({
		type : "POST",
		url : "getTrxance",
		dataType : "json",
		success : function(data) {
			$('#tb_deal').bootstrapTable('load', data);
		},

	});
}
// contract info
function loadContract() {
	$.ajax({
		type : "POST",
		url : "/queryContractList",
		dataType : "json",
		data : {
			"keyword" : "",
			"page" : 1
		},
		success : function(data) {
			$('#tb_contract').bootstrapTable('load', data.rows);
		}
	});
}
$(function(){
$("#searchBth").on(
		"click",
		function() {
			var keyword = $("#searchInput").val();
			if (keyword != null && keyword != ""
					&& RegExp(/^[a-zA-Z0-9]{30,39}$/).test(keyword)) {
				hrefAddress(keyword);
				return;
			}
			if (keyword != "") {
				$.ajax({
					type : "POST",
					url : "searchBlock",
					data : {
						"keyword" : keyword
					},
					dataType : "json",
					success : function(data, textStatus) {
						var retCode = data.key;
						if ('fail' == retCode) {
							$("#alertMsg").text("Did not check the results");
							$('#alertModel').modal('show')
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
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					}
				});
			}
		});
});
var TableInit = function() {
	var colums_block = [ {
		field : 'blockNum',
		title : 'Block Height',
		formatter: function indexFormatter(value, row, index) {
            return '<a href="javascript:void(0);">'+value+'</a>';
        }
	}, {
		field : 'blockTimeStr',
		title : 'Time'
	}, {
		field : 'trxNum',
		title : 'Number of Transactions'
	}, {
		field : 'amount',
		title : 'Amount of Transactions',
		formatter: function indexFormatter(value, row, index) {
            return value/100000000;
        }
	}, {
		field : 'signee',
		title : 'Block Issuance Agent'
	}, {
		field : 'blockSize',
		title : 'Block Size (Byte)'
	} ];
	// 交易记录
	var colums_deal = [ {
		field : 'trxId',
		title : 'Recent Transaction',
		formatter: function indexFormatter(value, row, index) {
            return '<a href="javascript:void(0);">'+value+'</a>';
        }
	}, {
		field : 'disTime',
		title : '&nbsp;'
	}, {
		field : 'amountBig',
		title : '&nbsp;'
	} ];
	var colums_contract = [ {
		field : 'contractId',
		title : 'Recent Contract',
			formatter: function indexFormatter(value, row, index) {
	            return '<a href="javascript:void(0);">'+value+'</a>';
	        }
	}, {
		field : 'name',
		title : '&nbsp;'
	}, {
		field : 'balanceBig',
		title : '&nbsp;'
	}, {
		field : 'regTimeStr',
		title : '&nbsp;'
	} ];

	var url = "";
	var oTableInit = new Object();
	// 初始化Table
	var tableOption={
			striped : true, 
			cache : false, 
			pagination : false, 
			sortable : false, 
			sortOrder : "asc", 
			queryParams : oTableInit.queryParams,
			sidePagination : "server", 
			pageNumber : 1, 
			pageSize : 10, 
			pageList : [ 20 ],
			search : false, 
			strictSearch : false,
			showColumns : false, 
			showRefresh : false, 
			minimumCountColumns : 2,
			clickToSelect : true, 
			showToggle : false,  
			cardView : false,  
			detailView : false,  
			locale : 'en_US' 
				
	}
	oTableInit.Init = function() {
		$('#tb_block').bootstrapTable({
			tableOption,
			columns : colums_block,
			onClickRow : function(row, $element, field) {
				if(field=='blockNum'){
					hrefBlock(row.blockId);
				}
			}
		});
		$('#tb_deal').bootstrapTable({tableOption,columns : colums_deal,
			onClickRow : function(row, $element, field) {
				if(field=='trxId'){
				hrefTransaction(row.trxId);
				}
			}
		});
		$('#tb_contract').bootstrapTable({tableOption,columns : colums_contract,onClickRow : function(row, $element, field) {
			if(field=='contractId'){
			hrefContractDetail(row.contractId);
			}
		}});
	};

	oTableInit.queryParams = function(params) {
		var temp = {  
			limit : params.limit, 
			offset : params.offset
		};
		return temp;
	};
	return oTableInit;
};

var ButtonInit = function() {
	var oInit = new Object();
	var postdata = {};

	oInit.Init = function() {
		loadStatic();
		loadBlock();
		loadTransaction();
		loadContract();
		$("#searchBth").on("click", function() {
			var val = $("#searchInput").val();
			if (val != '') {
			} else {
				$('#erroPro').popover('show');
				$("#searchInput").focus();
				setTimeout(function() {
					$('#erroPro').popover('destroy');
				}, 4000);
			}
		});

	};

	return oInit;
};