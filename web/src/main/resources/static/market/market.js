var updownChart = function(){
	var option = {
		color : ['#4db14d','#d93c37'],
	    tooltip : {
	        trigger: 'item',
	        formatter: "{b} : {d}%"
	    },
	    legend: {
	        orient: 'vertical',
	        bottom: '3%',
	        data: ['涨','跌']
	    },
	    series : [
	        {
	            type: 'pie',
	            hoverAnimation:false,
	            legendHoverLink:false,
	            labelLine:{show:false},
	            label:{show:false},
	            radius : '65%',
	            center: ['40%', '50%'],
	            data:[
	                {value:50, name:'跌', color:'#4db14d'},
	                {value:50, name:'涨', color:'#d93c37'}
	            ],
	            itemStyle: {
	                emphasis: {
	                   shadowBlur: 10,
	                   shadowOffsetX: 0,
	                   shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	           }
	        }
	    ]
	};
    var myChart = echarts.init(document.getElementById('24hUpdownChart'));
    $.ajax({
		url: basePath + '/market/updownWeight',
		success:function(re){
			if(re.code == 200){
				option.series[0].data[0].value = re.data[0];
			    option.series[0].data[1].value = re.data[1];
			    myChart.setOption(option);
			}else{
				alert(re.msg);
			}
		}
    });
}
var findCoinList = function (page, size){
	$('#hidPage').val(page);
	$('#hidSize').val(size);
	$.ajax({
		url: basePath + '/market/' + page + '/' + size,
		success:function(re){
			//console.log(re);
			if(re.code == 200){
				var html = '';
				var kLine = [];
				$.each(re.data, function(n, x){
					var color = '#7ED321';
					if(x.change > 0){
						color = '#D0021B;';
					}
					var change = new Number(x.change).toFixed(2);
					var volume = '<span>'+x.volumefrom+'</span>/<span>'+x.volumeto+'</span>';
					if(x.volumefrom == 0) volume = '<span>-</span>';
					html += '<tr data-name="'+x.name+'">' +
					'<td><img alt="logo" style="width: 28px;height: 28px;" src="'+ x.imageUrl +'"></td>' +
					'<td><a href="#">'+x.fullName+'</a></td>' +
					'<td><span>$ </span><span>'+x.open+'</span>/<span>'+x.close+'</span></td>' +
					'<td><span>$ </span><span>'+x.low+'</span>/<span>'+x.high+'</span></td>' +
					'<td>'+ volume +'</td>' +
					'<td style="color: '+ color +';"><span>'+ change +'</span>%</td>' +
					'<td><span class="quo-bd-canvas"><canvas width="110" height="20"></canvas></span></td>' +
					'</tr>';
					var kLineArr = [];
	                var k = Object.keys(x.trend);
	                for (var j = 0; j < k.length; j++) {
	                    kLineArr.push(x.trend[k[j]]);
	                }
	                kLine.push(kLineArr);
				});
				$('#coinList').empty().append(html);
				hourLine(kLine);
			}else{
				alert(re.msg);
			}
		}
	});
}

var hourLine = function (data) {
    for (var i = 0; i < data.length; i++) {
        var arr = data[i];
        var can = $(".quo-bd-canvas").eq(i).children("canvas")[0];
        var ctx = can.getContext("2d");
        var w = 110, h = 20;
        can.width = w;
        can.height = h;
        var cW = w / arr.length, cH = 0;
        var minH = arr[0], maxH = arr[0];
        for (var n = 0; n < arr.length; n++) {
            if (arr[n] - maxH > 0) {
                maxH = arr[n];
            } else if (arr[n] - minH < 0) {
                minH = arr[n];
            }
        }
        var lineW = w / arr.length;
        var lineH = maxH - minH == 0 ? h / 2 : h / (maxH - minH);
        ctx.beginPath();
        ctx.strokeStyle = "#5670BF";
        ctx.lineWidth = 1.2;
        ctx.lineJoin = "round";
        ctx.lineCap = "round";
        for (var m = 0; m < arr.length; m++) {
            var y = maxH - minH == 0 ? lineH : (arr[m] - minH) * lineH;
            ctx.lineTo(m * lineW, h - y);
        }
        ctx.stroke();
    }
}
var changeRank = function(sort){
	$.ajax({
		url: basePath + '/market/rank/' + sort + '/10',
		success:function(re){
			if(re.code == 200){
				var html = '';
				$.each(re.data, function(n, x){
					var rankColor = '#999';
					if(n < 3) rankColor = '#F29700';
					var color = '#7ED321';
					if(x.change > 0) color = '#D0021B;';
					var change = new Number(x.change).toFixed(2);
					html += '<tr>' + 
					'<td style="color:'+rankColor+';font-weight: bold;font-style: italic;">'+ (n+1) +'</td>' + 
					'<td><img alt="logo" style="width: 28px;height: 28px;" src="'+x.imageUrl+'"></td>' + 
					'<td><a href="#">'+x.fullName+'</a></td>' + 
					'<td><span>$ </span><span>'+x.close+'</span></td>' + 
					'<td style="color: '+color+';">'+change+'%</td>' + 
					'</tr>';
				});
				$('#changeRank').empty().append(html);
			}else{
				alert(re.msg);
			}
		}
	});
}
$('.prev').click(function(){
	var page = Number($('#hidPage').val());
	var size = Number($('#hidSize').val());
	if(page > 0) page = page - 1;
	findCoinList(page, size);
});
$('.next').click(function(){
	var page = Number($('#hidPage').val());
	var size = Number($('#hidSize').val());
	page = page + 1;
	findCoinList(page, size);
});
$('.btn-changeRank-up').click(function(){
	$('.btn-changeRank-up').removeClass('am-btn-default').addClass('am-btn-danger');
	$('.btn-changeRank-down').removeClass('am-btn-success').addClass('am-btn-default');
	changeRank('asc');
});
$('.btn-changeRank-down').click(function(){
	$('.btn-changeRank-up').removeClass('am-btn-danger').addClass('am-btn-default');
	$('.btn-changeRank-down').removeClass('am-btn-default').addClass('am-btn-success');
	changeRank('desc');
});
(function init(){
	updownChart();
	findCoinList(0, 30);
	changeRank('asc');
})();