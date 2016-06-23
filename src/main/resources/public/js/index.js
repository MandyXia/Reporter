$(function() {
	initTable();
});

function initTable(reload) {
	if (reload) {
		$('#recordtable').bootstrapTable('destroy');
	}
	var data = [];
	console.log("running");
	$.ajax({
		url : '/rest/finance',
		method: "GET", 
		success : function(result) {
			$.each(result, function(idx, records) {
				var row = {
					'sourceDate' : records.sourceYear + "-" + records.sourceMonth,
					'sapFile' : records.sapFilePath,
					'salesFile' : records.salesFilePath,
					'targetReports' : records.targetReports,
					'actions' : "<span>actions</span>"
				};
				data.push(row);
			});
			
			$('#recordtable').bootstrapTable({
				data : data
			});
		},
		error : function(result){
			
		}
	});
}