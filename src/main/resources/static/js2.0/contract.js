$(function() {
	initTable();
	$("#search").on("click", function() {
		initTable();
	});
});
var colums_deal = [ {
	field : 'contractId',
	title : 'Contract ID'
}, {
	field : 'name',
	title : 'Name'
}, {
	field : 'balanceBig',
	title : 'Contract Balance'
}, {
	field : 'regTimeStr',
	title : 'Registration Time'
} ];

function initTable() {
	$('#tb_contract').bootstrapTable('destroy');
	var keyword = $("#formInput").val();
	$("#tb_contract").bootstrapTable({
		method : "get", 
		url : "queryContractList",
		striped : true, 
		pagination : true, 
		pageSize : 10, 
		pageNumber : 1, 
		pageList : [ 10 ], 
		search : false, 
		showColumns : false, 
		showRefresh : false, 
		locale : 'en-US',
		sidePagination : "server", 
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { 
			var param = {
				page : params.pageNumber,
				rows : params.pageSize,
				keyword : keyword
			};
			return param;
		},
		columns : colums_deal,
		onLoadSuccess : function() { 
		},
		onLoadError : function() { 
		},
		onClickRow : function(row, $element, field) {
			hrefContractDetail(row.contractId);
		}
	});

};