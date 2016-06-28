$(function() {
	initTable();
	
	$("#monthPicker").datetimepicker({
		autoclose: true,
		format: "yyyy-mm",
		startView: 3,
		minView: 3,
		maxView: 3
	});
	
	$("#rmonthPicker").datetimepicker({
		autoclose: true,
		format: "yyyy-mm",
		startView: 3,
		minView: 3,
		maxView: 3
	});
	
	$("#sapbtn").on("click", function() {
		var btn = $(this);
		uploadfile(btn, "/rest/finance/sap", "sapfile");
	});
	
	$("#salesbtn").on("click", function() {
		var btn = $(this);
		uploadfile(btn, "/rest/finance/sales", "salesfile");
	});
	
	$("#supplierbtn").on("click", function() {
		var btn = $(this);
		uploadfile(btn, "/rest/finance/supplier", "supplierfile");
	});
	
	$("#districtbtn").on("click", function() {
		var btn = $(this);
		uploadfile(btn, "/rest/finance/district", "districtfile");
	});
	
	$("#inputClosebtn").on("click", function() {
		initTable(true);
	});
	
	$("#refreshbtn").on("click", function() {
		initTable(true);
	});
	
	$("#updtotalbtn").on("click", function() {
		var btn = $(this);
		
		btn.html("<span class='glyphicon-left glyphicon glyphicon-refresh spinning'></span>");
		btn.css("disabled", true);
		
		var mon = $("#monthPicker").val();
		if (mon != "") {
			var sp = mon.split("-");
			var url = "/rest/finance/salestotal/year/";
			url += sp[0];
			url += "/month/";
			url += sp[1];
			
			var vipval = $("#viptotal").val();
			var animval = $("#animtotal").val();
			var stockval = $("#stocktotal").val();
			var displayval = $("#displaytotal").val();
			var insval = $("#instotal").val();
			var taxval = $("#taxtotal").val();
			
			$.ajax({
				url: url,
				method: "POST",
				data: {
					vip: vipval,
					anim: animval,
					stock: stockval,
					display: displayval,
					ins: insval,
					tax: taxval
				},
				success: function(result) {
					showOK(result);
					btn.html("Update");
					btn.css("disabled", false);
				},
				error: function(err) {
					showErr(err);
					btn.html("Update");
					btn.css("disabled", false);
				}
			});
		}
	});
	
	$("#rmonthPicker").datetimepicker().on("changeDate", function(ev) {
		var targetmon = $("#rmonthPicker").val();
		if (targetmon != "") {
			var sp = targetmon.split("-");
			$.ajax({
				url: "/rest/finance/district/year/" + sp[0] + "/month/" + sp[1],
				method: "GET",
				success: function(result) {
					var optdata = {};
					for (var i in result) {
						if (result[i].area == "") {
							continue;
						}
						var codeary = optdata[result[i].area] || [];
						codeary.push(result[i].code);
						optdata[result[i].area] = codeary;
					}
					
					for (var j in optdata) {
						var grp = $('<optgroup>', { label: j });
						for (var k in optdata[j]) {
							grp.append($('<option>', { text: optdata[j][k], value: optdata[j][k]}));
						}
						$("#targetStore").append(grp);
					}
					
					$("#targetStore").multiselect({
						enableFiltering: true,
						enableClickableOptGroups: true
					});
				}
			});
		}
	});
	
	$("#generatebtn").on("click", function() {
		var btn = $(this);
		btn.html("<span class='glyphicon-left glyphicon glyphicon-refresh spinning'></span>");
		btn.css("disabled", true);
		
		var mon = $("#rmonthPicker").val();
		var storecode = $("#targetStore").val();
		
		if (mon != "" && storecode != null) {
			var sp = mon.split("-");
			
			if (storecode.length > 1) {
				var queryparam = "";
				for (var sci in storecode) {
					if (sci > 0) {
						queryparam += "&";
					}
					queryparam += "sc=" + storecode[sci];
				}
				$.ajax({
					url: "/rest/report/time/year/" + sp[0] + "/month/" + sp[1] + "/stores?" + queryparam,
					method: "GET",
					success: function(result) {
						$("#rmsgokmsg").html(result);
						$("#rmsgok").css("display", "block");
						btn.css("disabled", false);
						btn.html("Generate");
					},
					error: function(err) {
						$("#rmsgerrmsg").html(err);
						$("#rmsgerr").css("display", "block");
						btn.css("disabled", false);
						btn.html("Generate");
					}
				});
			} else {
				$.ajax({
					url: "/rest/report/time/year/" + sp[0] + "/month/" + sp[1] + "/store/" + storecode,
					method: "GET",
					success: function(result) {
						$("#rmsgokmsg").html(result);
						$("#rmsgok").css("display", "block");
						btn.css("disabled", false);
						btn.html("Generate");
					},
					error: function(err) {
						$("#rmsgerrmsg").html(err);
						$("#rmsgerr").css("display", "block");
						btn.css("disabled", false);
						btn.html("Generate");
					}
				});
			}
		} else {
			$("#rmsgerrmsg").html("Month and Store code must be selected");
			$("#rmsgerr").css("display", "block");
			btn.css("disabled", false);
			btn.html("Generate");
		}
	});
});

function initTable(reload) {
	if (reload) {
		$('#recordtable').bootstrapTable('destroy');
	}
	var data = [];
	$.ajax({
		url : '/rest/finance',
		method: "GET", 
		success : function(result) {
			$.each(result, function(idx, records) {
				var targetReports = "";
				for (var i in records.targetReports) {
					var fname = records.targetReports[i].targetFile;
					var name = fname.substring(fname.lastIndexOf("/") + 1);
					targetReports += "<button type='button' class='btn btn-default' id='down" + records.targetReports[i].id + "'>" + name + "</button><br />";
				}
				var row = {
					'sourceDate' : records.sourceYear + "-" + records.sourceMonth,
					'sapReady' : records.sapFilePath != null ? createOK() : createNO(),
					'salesReady' : records.salesFilePath != null ? createOK() : createNO(),
					'supplierReady' : records.supplierPath != null ? createOK() : createNO(),
					"districtReady" : records.districtPath != null ? createOK() : createNO(),
					"vip" : records.viptotal,
					"anim" : records.animtotal,
					"stock" : records.stocktotal,
					"display" : records.displaytotal,
					"ins" : records.instotal,
					"tax" : records.taxtotal,
					'targetReports' : targetReports
				};
				data.push(row);
			});
			
			$('#recordtable').bootstrapTable({
				data : data
			});
			
			$("#recordtable .btn").on("click", function() {
				var btn = $(this);
				var reportid = btn.attr("id").substring(4);
				$("body").append("<iframe src='/rest/report/download/" + reportid + "' style='display:none'></iframe>");
			});
		},
		error : function(result){
			console.log(result);
		}
	});
};

var createOK = function() {
	return "<span class='glyphicon glyphicon-ok' style='color: green'></span>";
};

var createNO = function() {
	return "<span class='glyphicon glyphicon-remove' style='color: red'></span>";
};

var showOK = function(msg) {
	$("#msgokmsg").html("");
	$("#msgokmsg").html(msg);
	$("#msgok").css("display", "block");
};

var showErr = function(msg) {
	$("#msgerrmsg").html("");
	$("#msgerrmsg").html(msg);
	$("#msgerr").css("display", "block");
};

var uploadfile = function(btn, url, fileid) {
	btn.html("<span class='glyphicon-left glyphicon glyphicon-refresh spinning'></span>");
	btn.css("disabled", true);
	
	var mon = $("#monthPicker").val();
	if (mon != "") {
		var sp = mon.split("-");
		var formData = new FormData();
		formData.append(fileid, $("#" + fileid)[0].files[0]);
		formData.append("year", sp[0]);
		formData.append("month", sp[1]);
		$.ajax({
		    url: url,
		    method: "POST",
		    data: formData,
		    processData: false,
		    contentType: false,
		    success: function(result) {
		    	showOK(fileid + " successfully uploaded.");
		    	btn.html("Upload");
		    	btn.css("disabled", false);
		    },
		    error: function(result) {
		    	showErr(result);
		    	btn.html("Upload");
		    	btn.css("disabled", false);
		    }
		});
	} else {
		showErr("Please select the month");
		btn.html("Upload");
		btn.css("disabled", false);
	}
};