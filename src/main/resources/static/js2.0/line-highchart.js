$(document).ready(
				function() {
					var trxTime = [];
					var title = {
						text : '',
						style: { fontSize: '14px'}
					};
					
					var textNubersTitle = {
							text : '',
							style: { fontSize: '14px'}
						};

					var xAxis = {
						categories : []

					};
					var yAxis = [{
						title : {
							text : ''
						},
						lineWidth: 2,
						lineColor:"#87CEFF"
					}
					];
					var tooltip = {
						formatter : function() {
							var r = trxTime[this.point.x]
									+ '<br><span style="color:#00BFFF" >Transactions: </span>'
									+ this.point.y;
							return r;
						}
					};

					var legend = {
						layout : 'vertical',
						borderWidth : 0,
						enabled : false
					};

					var series = [ {
						name : '',
						data : []
					}];
					
					var seriesNumbers = [ {
						name : '',
						data : []
					}];

					$.ajax({
						type : "post",
						url : 'getLineTransactions?time='+ new Date().getTime(),
						//url : 'js2.0/line.json',
						async : false,
						success : function(msg) {

							title.text = msg.text;  // 交易金额标题
							textNubersTitle.text = msg.textNubers; // 交易次数标题
							
							xAxis.categories = msg.xAxis; // 横轴标题

							series[0].data = msg.series; // 交易金额数据
							seriesNumbers[0].data = msg.series2; // 交易数
							trxTime = msg.trxTime;
						},
						error : function(msg) {
							alert("未知错误：请联系管理员");
						}
					});

					var json = {};

					json.title = textNubersTitle;
					json.xAxis = xAxis;
					json.yAxis = yAxis;
					json.tooltip = tooltip;
					json.legend = legend;
					json.series = seriesNumbers;

					$('#containerNums').highcharts(json);
					
					var jsonAmount = {};
					jsonAmount.title = title;
					jsonAmount.xAxis = xAxis;
					jsonAmount.yAxis = yAxis;
					jsonAmount.tooltip = tooltip;
					jsonAmount.legend = legend;
					jsonAmount.series = series;
					
					$('#containerAmount').highcharts(jsonAmount);
				});