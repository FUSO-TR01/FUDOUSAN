// script.js

function validateAndOpenModal() {
  var name = document.getElementsByName("name")[0].value;
  var space = document.getElementsByName("space")[0].value;
  var money = document.getElementsByName("money")[0].value;
  var address = document.getElementsByName("address")[0].value;

  // 必須項目の入力チェック
  if (!name || !space || !money || !address) {
    alert("必須項目が入力されていません");
  } else {
    modalOpen();
  }
}

function modalOpen() {
  //------------------------------------------------------------
  //  モーダルウインドウ オープン
  //------------------------------------------------------------
  document.getElementById("modalArea").className = "modalBg modalBgOpen";

  var name = document.getElementsByName("name")[0].value;
  var space = document.getElementsByName("space")[0].value;
  var money = document.getElementsByName("money")[0].value;
  var address = document.getElementsByName("address")[0].value;

  var comments = document.getElementsByName("comment");

  var selectedComments = [];

  for (var i = 0; i < comments.length; i++) {
    if (comments[i].checked) {
      selectedComments.push(comments[i].nextSibling.nodeValue.trim());
    }
  }

  document.getElementById("popupName").textContent = "名前: " + name;
  document.getElementById("popupSpace").textContent = "間取り: " + space;
  document.getElementById("popupMoney").textContent = "家賃: " + money;
  document.getElementById("popupAddress").textContent = "住所: " + address;
  document.getElementById("popupComment").textContent = "詳細: " + selectedComments.join(", ");

  document.getElementById("modalArea").style.display = "block";
  document.getElementById("modalWindow").style.display = "block";
}

function modalClose() {
  //------------------------------------------------------------
  //  モーダルウインドウ クローズ
  //------------------------------------------------------------
  document.getElementById("modalArea").style.display = "none";
  document.getElementById("modalWindow").style.display = "none";
}
