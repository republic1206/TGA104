/**
 * Account Settings - Account
 */

'use strict';


/* 上傳照片預覽效果 */

window.addEventListener("load", function(){
  var the_file_element = document.getElementById("upload");
  the_file_element.addEventListener("change", function(e){          

    // 寫在這
    var picture_list = document.getElementsByClassName("picture_list")[0];
    picture_list.innerHTML = ""; // 清空
    
    // 跑每個使用者選的檔案，留意 i 的部份
    for (let i = 0; i < this.files.length; i++) {
      let reader = new FileReader(); // 用來讀取檔案
      reader.readAsDataURL(this.files[i]); // 讀取檔案
      reader.addEventListener("load", function () {
        console.log(reader.result);
        let li_html = `
                        <li><img
                          src="${reader.result}"
                          alt="user-avatar"
                          class="d-block rounded"
                          height="100"
                          width="100"
                          id="uploadedAvatar"
                        /></li>
                      `;
        picture_list.insertAdjacentHTML("beforeend", li_html); // 加進節點
      });
    }


  });
});
