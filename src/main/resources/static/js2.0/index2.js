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
var url="";
function loadStatic() {
	$.ajax({
		type : "POST",
		url : url+"getStatis",
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
	$("#mCSB_1_container").html('');
	$.ajax({
		type : "get",
		url : url+"blocksInfo",
		dataType : "json",
		success : function(data) {
			var dataHtml = "";
			for (var i = 0; i < data.length; i++) {
				var dat=data[i];
				/*
				 * dataHtml += '<div class="profile-event"><div
				 * class="date-formats" onclick="hrefBlock(\''+dat.blockId+'\')"
				 * style="width: 155px; height: 72px; margin-top: 2px"><span><a
				 * href="javascript:"><font size="2" color="white">Block
				 * '+dat.blockNum+'</font></a></span><p>&gt;'+dat.blockTimeStr+'</p></div><div
				 * class="overflow-h">Mined By <a href="javascript:void(0)"
				 * class="address-tag">'+dat.signee+'</a><p></p><p><a
				 * href="javascript:void(0)"><b>'+dat.trxNum+'</b></a> Number
				 * of Transactions</p><p>Amount of Transactions <b>.</b>'+parseFloat((dat.amount/100000000)*0.000000001)+'</p></div></div>';
				 */
				
				// dataHtml += '<div class="profile-event"><div class="date-formats" onclick="hrefBlock(\''+dat.blockId+'\')" style="width: 162px; height: 72px; margin-top: 2px"><span class="titleken"><a href="javascript:"><font size="2" color="white">Block '+dat.blockNum+'</font></a></span><p class="dateken">'+dat.blockTimeStr+'</p></div><div class="overflow-h">Mined By <span class="address-tag colorb">'+dat.signee+'</span><p></p><p> Number of Transactions <span class="colorb"><span>'+dat.trxNum+'</span></span></p><p>Amount of Transactions <span class="colorb">'+parseFloat((dat.amount/100000000)*0.000000001)+'</span></p></div></div>';
				dataHtml += '<div class="profile-event"><div class="date-formats" onclick="hrefBlock(\''+dat.blockId+'\')" style="width: 162px; height: 72px; margin-top: 2px"><span class="titleken"><a href="javascript:"><font size="2" color="white">Block '+dat.blockNum+'</font></a></span><p class="dateken">'+dat.blockTimeStr+'</p></div><div class="overflow-h">Mined By <span class="address-tag colorb">'+dat.signee+'</span><p></p><p> Number of Transactions <span class="colorb"><span>'+dat.trxNum+'</span></span></p><p>Amount of Transactions <span class="colorb">'+dat.amountBig+'</span></p></div></div>';
			}
			$("#mCSB_1_container").html(dataHtml);
		}
	});
}
// trans info
function loadTransaction() {
	$("#mCSB_2_container").html('');
	$
			.ajax({
				type : "POST",
				url : url+"getTrxance",
				dataType : "json",
				success : function(data) {
					var dataHtml = "";
					for (var i = 0; i < data.length; i++) {
						var dat=data[i];
						var colo="one";
						if(i==0){
							 colo="one";
						}else if(i==1){
							colo="two";
						}
						else if(i==2){
							colo="three";
						}
						else if(i==3){
							colo="four";
						}
						else if(i==4){
							colo="five";
						}
						else if(i==5){
							colo="six";
						}
						else if(i==6){
							colo="one";
						}
						else if(i==7){
							colo="two";
						}
						else if(i==8){
							colo="three";
						}
						else if(i==9){
							colo="four";
						}
						
						if(dat.fromAccount == null || typeof(dat.fromAccount) == 'undefined' || dat.fromAccount == ''){
							dataHtml += '<div class="profile-post color-'+colo+'" ><span class="profile-post-numb"><i class="fa fa-hdd-o"></i></span><div class="profile-post-in"><h3 class="heading-xs">TX# <a href="javascript:void(0);" onclick="hrefTransaction(\''+dat.trxId+'\')" class="hash-tag-frontpage"><font color="#3498db">'+dat.trxId+'</font></a><span class="pull-right" style="font-size: small">&gt; '+dat.disTime+' &nbsp;</span></h3><p> <span style="visibility:hidden">From <a href="javascript:void(0);" class="address-tag colorb">'+dat.fromAccount+'</a> </span>To <a href="javascript:void(0);" onclick="hrefAddress(\''+dat.toAccount+'\')" class="address-tag colorb">'+dat.toAccount+'</a></p><p>Amount '+dat.voIndexAmountBig+' CTC</p></div></div>';
						}else{
							dataHtml += '<div class="profile-post color-'+colo+'" ><span class="profile-post-numb"><i class="fa fa-hdd-o"></i></span><div class="profile-post-in"><h3 class="heading-xs">TX# <a href="javascript:void(0);" onclick="hrefTransaction(\''+dat.trxId+'\')" class="hash-tag-frontpage"><font color="#3498db">'+dat.trxId+'</font></a><span class="pull-right" style="font-size: small">&gt; '+dat.disTime+' &nbsp;</span></h3><p>From <a href="javascript:void(0);" onclick="hrefAddress(\''+dat.fromAccount+'\')" class="address-tag colorb">'+dat.fromAccount+'</a>To <a href="javascript:void(0);" onclick="hrefAddress(\''+dat.toAccount+'\')" class="address-tag colorb">'+dat.toAccount+'</a></p><p>Amount '+dat.voIndexAmountBig+' CTC</p></div></div>';							
						}
					}
					$("#mCSB_2_container").html(dataHtml);
				}

			});
}
$(function() {
	$("#scrollbar").mCustomScrollbar({
		setHeight:580,
		theme:"dark-3"
	});
	$("#scrollbar2").mCustomScrollbar({
		setHeight:580,
		theme:"dark-3"
	});
	 
	loadStatic();
	loadBlock();
	loadTransaction();
	$('#txtSearchInput').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            $('#searchInput').click();
        }
    });
	$("#searchInput").on(
			"click",
			function() {
				var keyword = $("#txtSearchInput").val();
				if (keyword != null && keyword != ""
						&& RegExp(/^[a-zA-Z0-9]{30,39}$/).test(keyword)) {
					hrefAddress(keyword);
					return;
				}
				if (keyword != "") {
					$.ajax({
						type : "POST",
						url : url+"searchBlock",
						data : {
							"keyword" : keyword
						},
						dataType : "json",
						success : function(data, textStatus) {
							var retCode = data.key;
							if ('fail' == retCode) {
								$("#alertMsg")
										.text("Did not check the results");
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
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
						}
					});
				}
			});

});
