
// ------------------------------------------------------------
//     タブがクリックされた
// ------------------------------------------------------------
function tabClick(argTabNo) {

	document.getElementById("tabNo").innerHTML = 'タブ' + argTabNo;

}

window.addEventListener("scroll", buttonDispHB, true);

function buttonDispHB() {
	// ==================================================================
	//  ヘッダーの高さ調整＆移動ボタン表示制御
	// ==================================================================
	var wBody = window.document.body;
	var wHtml = window.document.documentElement;
	var wHeader = document.getElementById('sampleHeader');
	var wHDarea = document.getElementById('hdBtnArea');
	var wEraseH = wHeader.clientHeight - sValue["leaveHeight"];
	var nowY = wBody.scrollTop || wHtml.scrollTop;

	if (nowY <= wEraseH) {
		wHeader.style.top = '-' + nowY + 'px';
		wHDarea.style.display = 'none';
	} else {
		wHeader.style.top = '-' + wEraseH + 'px';
		wHDarea.style.display = '';
	}

}

function goScroll(argScroll) {
	// ==================================================================
	//  スクロール位置計算
	// ==================================================================
	var wBody = window.document.body;
	var wHtml = window.document.documentElement;

	sValue["coef"] = 40;  // ←滑らか係数（大きいほど滑らか）
	sValue["cnt"] = 0;
	sValue["sumY"] = 0;

	// ---------------------------------------
	//  現在のスクロール位置取得
	// ---------------------------------------
	sValue["startX"] = wBody.scrollLeft || wHtml.scrollLeft;
	sValue["startY"] = wBody.scrollTop || wHtml.scrollTop;

	// ---------------------------------------
	//  終了位置を計算
	// ---------------------------------------
	if (argScroll == 'H') {
		sValue["endY"] = 0;
	} else {
		sValue["endY"] = wHtml.offsetHeight - wHtml.clientHeight;
	}

	// ---------------------------------------
	//  スクロールの単位計算
	// ---------------------------------------
	var moveSplitCnt = 0;
	for (var i = 1; i <= sValue["coef"]; i++) {
		moveSplitCnt += i * i;
	}
	sValue["unitH"] = Math.abs(sValue["startY"] - sValue["endY"]) / (moveSplitCnt * 2);

	// ---------------------------------------
	//  スクロール開始
	// ---------------------------------------
	goScrollLoop();
}

function goScrollLoop() {
	// ==================================================================
	//  スクロール実行
	// ==================================================================
	var wNextY = 0;

	sValue["cnt"]++;

	// ---------------------------------------
	//  次のスクロール幅（高さ）計算
	// ---------------------------------------
	var Coef = 0;
	if (sValue["cnt"] <= sValue["coef"]) {
		Coef = sValue["cnt"];
	} else {
		Coef = ((sValue["coef"] * 2) + 1) - sValue["cnt"];
	}

	sValue["sumY"] = sValue["sumY"] + Math.round(sValue["unitH"] * (Coef * Coef));

	// ---------------------------------------
	//  スクロール位置計算
	// ---------------------------------------
	if (sValue["startY"] > sValue["endY"]) {
		wNextY = sValue["startY"] - sValue["sumY"];
		if (wNextY < sValue["endY"]) {
			wNextY = sValue["endY"];
		}
	} else {
		wNextY = sValue["startY"] + sValue["sumY"];
		if (wNextY > sValue["endY"]) {
			wNextY = sValue["endY"];
		}
	}

	if (sValue["cnt"] >= (sValue["coef"] * 2)) {
		wNextY = sValue["endY"];
	}

	// ---------------------------------------
	//  スクロール実行
	// ---------------------------------------
	window.scrollTo(sValue["nextX"], wNextY);

	// ---------------------------------------
	//  次のスクロールを設定
	// ---------------------------------------
	if (wNextY == sValue["endY"]) {
		clearTimeout(sValue["timer"]);                   // 終了：タイマクリア
	} else {
		sValue["timer"] = setTimeout("goScrollLoop()", 10);  // 次のループ
	}
}
