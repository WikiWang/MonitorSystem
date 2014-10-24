var dataFile;
var dataToday = [];
var upperLimit;
var dataArray;
var timeBegin;
var series;
var tableobj;
var i=0,n=0,nextday=0,flag=0;
var chart;
var timeNext;
$(start);
var int;
function start() {	
	dataFile;
	for (i = 0; i < dataSelect.length; i++) {             
        if (dataSelect.options[i].selected == true){
        	dataFile = dataSelect.options[i].text;
        }            
    }
	$.ajax({
		type: 'POST',
        url: 'getData',
        data: {fileName:dataFile},
        dataType: 'json',
        success:function(data){       	         	 
        	 dataArray = data.data;
        	 timeBegin = data.time;
        	 timeNext = timeBegin;
        	 upperLimit = data.upperLimit;
        	 dataToday = [];
        	 document.getElementById("upperLimit").value = upperLimit;
//        	 alert(dataArray[0]);
        	 i=0;nextday=1440;
        	 chart = new Highcharts.Chart({
 	            chart: {
 	            	renderTo: 'container',  
 	                type: 'spline',
 	                animation: Highcharts.svg, // don't animate in old IE
 	                marginRight: 10,
 	                events: {
 	                    load: function () {
 	                        // set up the updating of the chart each second
 	                    	n=0;
 	                        series = this.series[0],series1 = this.series[1];
 	                        tableobj=document.getElementById("table_alarm");
 	                        int = setInterval(function () {
 	                        	flag = 0;
 	                            var x = (timeBegin + i*60)*1000, // current time
 	                                y = dataArray[i++]; 	                            
 	                            dataToday.push(y);
 	                            document.getElementById("time").innerText = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x);
 	                            if(y>upperLimit){
 	                            	var rowobj=tableobj.insertRow(tableobj.rows.length);
 	                            	var cell1=rowobj.insertCell(rowobj.cells.length);
 	                            	var cell2=rowobj.insertCell(rowobj.cells.length);
 	                            	var cell3=rowobj.insertCell(rowobj.cells.length);
 	                            	cell1.innerHTML=Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x) + dataFile;
 	                            	cell2.innerHTML=y;
 	                            	cell3.innerHTML="<input type='checkbox' value='" + ((n++)-1) + "' name='checkbox'/>是否确定异常";
// 	                            	document.getElementById("alarm").value += Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x) + " alarm: " + y + "\n";
 	                            	series.addPoint({x:x, y:y, color:'#FF0000'}, true, true);
 	                            }else{
 	                            	series.addPoint({x:x, y:y, color:'#33FF00'}, true, true);
 	                            } 	                        	
	                        	series1.addPoint({x:x, y:upperLimit}, true, true);
	                        	if(i%1440 == 0 && flag == 0){
	                        		var checkboxes=document.getElementsByName("checkbox");
	                        		nextday += 1440;
	                        		timeNext = timeNext + 1440*60;
//	                        		alert(checkboxes.length);
	                        		for(var k=0; k<checkboxes.length; k++){
//	                        			alert(checkboxes[k].checked);
	                        			if(checkboxes[k].checked){
//	                        				alert(parseInt(checkboxes[k].value));
	                        				dataToday[parseInt(checkboxes[k].value)] = -1;
	                        			}
	                        		}
	                        		n=0;
	                        		$.ajax({
	                        			type: 'POST',
	                        	        url: 'ChangeHistoryData',
	                        	        data: {fileName:dataFile,newData:dataToday.toLocaleString()},
	                        	        dataType: 'json',
	                        	        success:function(data){
	                        	        	upperLimit = data.upperLimit;
	                        	        	dataToday = [];
	                        	        	document.getElementById("upperLimit").value = upperLimit;
	                        	        	var list = document.getElementById("table_alarm");
	                        	            var tableLength = list.rows.length;
	                        	            for (var i = tableLength - 1; i > 0; i--) {
	                        	                list.deleteRow(i);
	                        	            }
	                        	        }
	                        		});
	                        	}
	                        	
 	                        }, 500);
 	                    }
 	                }
 	            },
 	            title: {
 	                text: 'real-time data'
 	            },
 	            xAxis: {
 	                type: 'datetime',
 	                tickPixelInterval: 100
 	            },
 	            yAxis: {
 	                title: {
 	                    text: 'Value'
 	                },
 	                plotLines: [{
 	                    value: 0,
 	                    width: 0.1,
 	                    color: '#808080'
 	                }]
 	            },
 	            tooltip: {
 	                formatter: function () {
 	                    return '<b>' + this.series.name + '</b><br/>' +
 	                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
 	                        Highcharts.numberFormat(this.y, 2);
 	                }
 	            },
 	            legend: {
 	                enabled: false
 	            },
 	            exporting: {
 	                enabled: false
 	            },
 	            series: [{
 	            	name: 'Real data',
 	            	data: (function () {
 	            		// generate an array of random data
 	            		var data = [],
 	            		time = timeBegin*1000,
 	            		j;

 	            		for (j = -200; j<0; j += 1) {
 	            			data.push({
 	            				x: time + j * 1000*60,
 	            				y: 0
 	            			});
 	            		}
 	            		return data;
 	            	}()),
 	            	color:'#33FF00'
 	            },
 	            {
 	            	name: 'upperLimit',
 	                data: (function () {
 	                    // generate an array of random data
 	                    var data = [],
 	                        time = timeBegin*1000,
 	                        j;

 	                    for (j = -200; j<0; j += 1) {
 	                        data.push({
 	                            x: time + j * 1000*60,
 	                            y: upperLimit
 	                        });
 	                    }
 	                    return data;
 	                }())
 	            }]
 	        });
        }
	});
}

function change() {
	clearInterval(int);
	var list = document.getElementById("table_alarm");
    var tableLength = list.rows.length;
    for (var i = tableLength - 1; i > 0; i--) {
        list.deleteRow(i);
    }
	start();
}

function changeUpperLimit() {
	upperLimit = parseFloat(document.getElementById("upperLimit").value);
}

function nextDay() {
	flag = 1;
	i=nextday;
	nextday += 1440;
	clearInterval(int);
	timeNext = timeNext + 1440*60;
	var checkboxes=document.getElementsByName("checkbox");	
//	alert(checkboxes.length);
	for(var k=0; k<checkboxes.length; k++){
//		alert(checkboxes[k].checked);
		if(checkboxes[k].checked){
//			alert(parseInt(checkboxes[k].value));
			dataToday[parseInt(checkboxes[k].value)] = -1;
		}
	}
	n=0;
	chart = new Highcharts.Chart({
		chart: {
			renderTo: 'container',  
			type: 'spline',
			animation: Highcharts.svg, // don't animate in old IE
			marginRight: 10,
			events: {
				load: function () {
					// set up the updating of the chart each second
					n=0;
					series = this.series[0],series1 = this.series[1];
					tableobj=document.getElementById("table_alarm");
					int = setInterval(function () {
						flag = 0;
						var x = (timeBegin + i*60)*1000, // current time
						y = dataArray[i++]; 	                            
						dataToday.push(y);
						document.getElementById("time").innerText = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x);
						if(y>upperLimit){
							var rowobj=tableobj.insertRow(tableobj.rows.length);
							var cell1=rowobj.insertCell(rowobj.cells.length);
							var cell2=rowobj.insertCell(rowobj.cells.length);
							var cell3=rowobj.insertCell(rowobj.cells.length);
							cell1.innerHTML=Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x) + dataFile;
							cell2.innerHTML=y;
							cell3.innerHTML="<input type='checkbox' value='" + ((n++)-1) + "' name='checkbox'/>是否确定异常";
//							document.getElementById("alarm").value += Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x) + " alarm: " + y + "\n";
							series.addPoint({x:x, y:y, color:'#FF0000'}, true, true);
						}else{
							series.addPoint({x:x, y:y, color:'#33FF00'}, true, true);
						} 	                        	
						series1.addPoint({x:x, y:upperLimit}, true, true);
						if(i%1440 == 0 && flag == 0){
							var checkboxes=document.getElementsByName("checkbox");
							nextday += 1440;
//							alert(checkboxes.length);
							for(var k=0; k<checkboxes.length; k++){
//								alert(checkboxes[k].checked);
								if(checkboxes[k].checked){
//									alert(parseInt(checkboxes[k].value));
									dataToday[parseInt(checkboxes[k].value)] = -1;
								}
							}
							n=0;
							$.ajax({
								type: 'POST',
								url: 'ChangeHistoryData',
								data: {fileName:dataFile,newData:dataToday.toLocaleString()},
								dataType: 'json',
								success:function(data){
									upperLimit = data.upperLimit;
									dataToday = [];
									document.getElementById("upperLimit").value = upperLimit;
									var list = document.getElementById("table_alarm");
									var tableLength = list.rows.length;
									for (var i = tableLength - 1; i > 0; i--) {
										list.deleteRow(i);
									}
								}
							});
						}

					}, 500);
				}
			}
		},
		title: {
			text: 'real-time data'
		},
		xAxis: {
			type: 'datetime',
			tickPixelInterval: 100
		},
		yAxis: {
			title: {
				text: 'Value'
			},
			plotLines: [{
				value: 0,
				width: 0.1,
				color: '#808080'
			}]
		},
		tooltip: {
			formatter: function () {
				return '<b>' + this.series.name + '</b><br/>' +
				Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
				Highcharts.numberFormat(this.y, 2);
			}
		},
		legend: {
			enabled: false
		},
		exporting: {
			enabled: false
		},
		series: [{
			name: 'Real data',
			data: (function () {
				// generate an array of random data
				var data = [],
				time = timeNext*1000,
				j;

				for (j = -200; j<0; j += 1) {
					data.push({
						x: time + j * 1000*60,
						y: 0
					});
				}
				return data;
			}()),
			color:'#33FF00'
		},
		{
			name: 'upperLimit',
			data: (function () {
				// generate an array of random data
				var data = [],
				time = timeNext*1000,
				j;

				for (j = -200; j<0; j += 1) {
					data.push({
						x: time + j * 1000*60,
						y: upperLimit
					});
				}
				return data;
			}())
		}]
	});
}


