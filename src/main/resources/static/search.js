$(function() {
	// 変数にクラスを入れる
	var btn = $('.StickyButton1');
	var btn2 = $('.StickyButton2');
	var topbtn = $('.top-button');
	var topbtn2 = $('.top-button2');

	//スクロールしてページトップから400に達したらボタンを表示
	$(window).on('load scroll', function() {
		if ($(this).scrollTop() > 400) {
			btn.addClass('active');
		} else {
			btn.removeClass('active');
		}
	});

	$(window).on('load scroll', function() {
		if ($(this).scrollTop() > 400) {
			btn2.addClass('active');
		} else {
			btn2.removeClass('active');
		}
	});

	$(window).on('load scroll', function() {
		if ($(this).scrollTop() > 400) {
			topbtn.addClass('active');
		} else {
			topbtn.removeClass('active');
		}
	});

	$(window).on('load scroll', function() {
		if ($(this).scrollTop() > 400) {
			topbtn2.addClass('active');
		} else {
			topbtn2.removeClass('active');
		}
	});


	//スクロールしてトップへ戻る
	topbtn.on('click', function() {
		$('body,html').animate({
			scrollTop: 0
		});
	});

	topbtn2.on('click', function() {
		$('body,html').animate({
			scrollTop: 0
		});
	});

});

