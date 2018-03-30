function findLives(page, size){
	$.ajax({
		url: basePath + '/lives/find/' + page + "/" + size,
		success:function(re){
			if(re.code == 200){
				var html = '';
				$.each(re.data, function(n, x){
					html += '<li><a href="lives/' + x.id + '"><span>' + x.date + '</span> -- <span>' + x.title + '</span></a></li>';
				});
				$('#lives-content').empty().append(html);
			}else{
				alert(re.msg);
			}
		}
	})
}
function findNews(page, size){
	$('#hidPage').val(page);
	$('#hidSize').val(size);
	$.ajax({
		url: basePath + '/news/find/' + page + "/" + size,
		success:function(re){
			if(re.code == 200){
				var html = '';
				$.each(re.data, function(n, x){
					var content = x.content.indexOf('<img') > -1 ? '' : x.content;
					html += '<article class="am-g blog-entry-article">'+
					'<div class="am-u-lg-6 am-u-md-6 am-u-sm-12 blog-entry-img">' +
					'<img style="height:218px" src="'+ x.img +'" alt="" class="am-u-sm-12"></div>' +
					'<div class="am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-text">'+
					'<h1><a href="news/' + x.id + '">'+ x.title +'</a></h1>'+
					'<span>' + x.date + '</span>' + content + 
					'<p><a href="" class="blog-continue">continue</a></p></div></article>';
				});
				$('#news-content').empty().append(html);
			}else{
				alert(re.msg);
			}
		}
	})
}
$('#prev').click(function(){
	var page = Number($('#hidPage').val());
	var size = Number($('#hidSize').val());
	if(page > 0) page = page - 1;
	findNews(page, size);
});
$('#next').click(function(){
	var page = Number($('#hidPage').val());
	var size = Number($('#hidSize').val());
	page = page + 1;
	findNews(page, size);
});
findLives(0, 25);
findNews(0, 10);

