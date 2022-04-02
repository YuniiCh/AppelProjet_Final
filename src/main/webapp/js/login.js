
$(document).ready(function(){
    var strName = localStorage.getItem('keyName');
    var strPass = localStorage.getItem('keyPass');
    if(strName){
    $('#email').val(strName);
}if(strPass){
    $('#psw').val(strPass);
}

});

function loginBtn_click(){
    var strName = $('#email').val();
    var strPass = $('#psw').val();
    localStorage.setItem('keyName',strName);
    if($('#remember').is(':checked')){
    localStorage.setItem('keyPass',strPass);
}else{
    localStorage.removeItem('keyPass');
}
}

// document.addEventListener("DOMContentLoaded",()=>{
//     document.getElementsByTagName("form").item(0).addEventListener("click", loginBtn_click);
// })