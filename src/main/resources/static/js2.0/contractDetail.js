
function hrefTransaction(trxId) {
    window.location.href="detail?trxId="+trxId;
}
$(function() {
	initTable();
});
var colums_deal = [ {
	field : 'trxId',
	title : 'Transaction ID'
}, {
	field : 'trxTypeStr',
	title : 'Type of Transaction'
}, {
	field : 'amountBig',
	title : 'Amount of Transactions'
}, {
	field : 'trxTimeStr',
	title : 'Transaction Time'
} ];
function initTable() {
	$('#tb_contract_detail').bootstrapTable('destroy');
	var address = $("#contractId").html();
	$("#tb_contract_detail").bootstrapTable({
		method : "get", 
		url : "queryTransactionList", 
		striped : true, 
		pagination : true, 
		pageSize : 20, 
		pageNumber : 1, 
		pageList : [ 20 ],
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
				address : address
			};
			return param;
		},
		columns : colums_deal,
		onLoadSuccess : function() {  
		},
		onLoadError : function() {  
		},
		onClickRow : function(row, $element, field) {
			hrefTransaction(row.trxId);
		}
	});

};