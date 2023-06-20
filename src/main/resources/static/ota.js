function modalOpen() {
			//------------------------------------------------------------
			//  モーダルウインドウ オープン
			//------------------------------------------------------------
			document.getElementById("modalArea").className = "modalBg modalBgOpen";
		}

		function modalClose() {
			//------------------------------------------------------------
			//  モーダルウインドウ クローズ
			//------------------------------------------------------------
			document.getElementById("modalArea").className = "modalBg modalBgClose";
		}
		

 // ------------------------------------------------------------
 //     タブがクリックされた
 // ------------------------------------------------------------
function tabClick(argTabNo){
 
  document.getElementById("tabNo").innerHTML = 'タブ'+argTabNo;
 
}