/**
 * Cette méthode "Ajax" affiche le XML.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */
const url = "appelCtrl";
let nbClick = 0;
let id_seance = document.getElementById("id_seance").innerText.split("°")[1];
function getNbClick(obj){
    if (nbClick>1){
        nbClick = 0;
    }else {
        nbClick++;
    }
    console.log(obj.currentTarget);
    changeEtatPresence(obj,nbClick);
}
function changeEtatPresence(obj,nb){
    let xhr = new XMLHttpRequest();
    let param = "etatpresence=" + encodeURIComponent(nb);
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function (){
        if (xhr.status===200){
            console.log("click");
            let etat_presence = xhr.responseXML.getElementsByTagName("etatpresence");
            // let etat = document.getElementById("etatPresent" + numbt_etatp);
            for (let i = 0; i<etat_presence.length; i++){
                console.log(etat_presence[i].firstChild.nodeValue);
                obj.firstElementChild.innerHTML = etat_presence[i].firstChild.nodeValue;
                // obj.CHILD(document.getElementById("etatPresent")).innerHTML = etat_presence[i].firstChild.nodeValue;
            }
        }
    }
    xhr.send(param);
}

let nbTousClick = 0;
function getNbClickALL(obj){
    if (nbTousClick===1){
        nbTousClick = 0;
    }else {
        nbTousClick++;
    }
    console.log(obj.currentTarget);
    tousPresence(obj,nbTousClick);
}

function tousPresence(obj, nb) {
    let btEtat = document.getElementsByClassName("btn_etatp_cl");
    if (nb === 1) {
        document.getElementById("toustext").innerHTML = "Tous Absent";
        for(let i = 0; i<btEtat.length;i++) {
            // btEtat[i].getElementsByClassName("etatpresent_cl")[0].innerHTML = "Pésent";
            btEtat[i].firstElementChild.innerHTML = "Absent";
        }
    }else {
        document.getElementById("toustext").innerHTML = "Tous Pésent";
        for(let i = 0; i<btEtat.length;i++) {
            // btEtat[i].getElementsByClassName("etatpresent_cl")[0].innerHTML = "Pésent";
            btEtat[i].firstElementChild.innerHTML = "Pésent";
        }
    }
}

function addStudent(){
    // 获取弹窗
    const pop = document.getElementById('add_student_pop');

// 获取 <span> 元素，用于关闭弹窗 that closes the modal
//     var span = document.getElementsByClassName("close")[0];

    // // 点击 <span> (x), 关闭弹窗
//     span.onclick = function() {
//         document.getElementById("search_student").innerHTML = "";
//         pop.style.display = "none";
//     }

    // 点击按钮打开弹窗
    pop.style.display = "block";


// 在用户点击其他地方时，关闭弹窗
    window.onclick = function(event) {
        if (event.target === pop) {
            pop.style.display = "none";
        }
    }
}
function closePOP(){
    document.getElementById("search_student").value = "";
    document.getElementById('add_student_pop').style.display = "none";
    document.getElementById("zone_show").innerHTML = "";
}

function showStudents() {
    let xhr = new XMLHttpRequest();
    let search_student = document.getElementById("search_student");
    console.log(search_student.value);
    let param = "search_student=" + encodeURIComponent(search_student.value)  + "&seance=" + encodeURIComponent(id_seance);
    xhr.open("POST", url)
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
            if (xhr.status === 200) {
                let students = xhr.responseXML.getElementsByTagName("student");
                let zone = document.getElementById("zone_show");
                zone.innerHTML = "";
                if (search_student.value === "") {
                    zone.innerHTML = ""
                    zone.style.display = "none";
                } else {
                    zone.style.display = "inline";
                    for (let i = 0; i < students.length; i++){
                        console.log(students[i].firstChild.nodeValue);
                        zone.insertAdjacentHTML("beforeend", "<div class = 'student' id='student" +students[i].firstChild.nodeValue.split(" ")[0] +"'><span>" + students[i].firstChild.nodeValue + "</span></div>");
                    }
                    let list_students = document.getElementsByClassName("student");
                    for (let i = 0; i < list_students.length; i++){
                        list_students[i].addEventListener("click", showStrudentsList);
                    }
                }
            }


    };
    xhr.send(param);
}

function showStrudentsList(){
    let student = this.firstChild.firstChild.nodeValue;
    let show_student = document.getElementById("zone_students_chosen");
    let xhr = new XMLHttpRequest();
    let param = "addstudent=" + encodeURIComponent(student.split(" ")[0]);
    console.log(param);
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.status === 200) {
            let add_status = xhr.responseXML.getElementsByTagName("student");
            for(let i=0; i< add_status.length; i++){
                show_student.insertAdjacentHTML("beforeend","<p>"+student+"</p>")
                let msg = document.getElementById("addmsg");
                if(add_status[i].firstChild.nodeValue === "add"){
                    msg.innerHTML = "Ajouter "+ student ;
                    showStudents();
                }else{
                    msg.innerHTML = add_status[i].firstChild.nodeValue;
                }
            }
        }
    };
    xhr.send(param);
}


function deleteSearchInfo(){
    console.log("delete search information");
    document.getElementById("search_student").value = "";
    console.log( document.getElementById("search_student").value);
}

let nbDeleteClick = 1;
function deleteStudent(){
    if (nbDeleteClick===1){
        document.querySelectorAll('.btn_delet_one').forEach(item => {
            item.style.display = "block";
            item.style.pointerEvents = "auto";
        });
        nbDeleteClick = 0;
    }else {
        document.querySelectorAll('.btn_delet_one').forEach(item => {
            item.style.display = "none";
            item.style.pointerEvents = "none";
        });
        nbDeleteClick = 1;
    }

}

function deleteOneStudent(id){
    let xhr = new XMLHttpRequest();
    let param = "deletestudent=" + encodeURIComponent(id) + "&seance=" + encodeURIComponent(id_seance);
    console.log(param);
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.status === 200) {
            let delete_status = xhr.responseXML.getElementsByTagName("student");
            for(let i=0; i< delete_status.length; i++){
                let msg = document.getElementById("addmsg");
                if(delete_status[i].firstChild.nodeValue === "delete"){
                    let info = document.getElementById("delet_" + id).parentNode.parentElement.innerText.trim().split(" ");
                    console.log(info);
                    let student_info = "";
                    for(let i = 0; i < info.length; i++){
                        student_info = student_info + " " +  info[i].split("\t")[0].replace(/(^[\s\n\t]+|[\s\n\t]+$)/g, "");
                    }
                    alert("Supprimer Etudiant: " + student_info);
                // /(^[\s\n\t]+|[\s\n\t]+$)/g
                }
            }
        }
    };
    xhr.send(param);
}
//     let xhr = new XMLHttpRequest();
//     let param = "touspresence=" + encodeURIComponent(nb);
//     let url = "appelCtrl";
//     xhr.open("GET", url+"?"+param);
//     // xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//     xhr.onload = function (){
//         if (xhr.status===200){
//             console.log("click");
//             let etat_presence = xhr.responseXML.getElementsByTagName("allpresence");
//             for (let i = 0; i<etat_presence.length; i++){
//                 console.log(etat_presence[i].firstChild.nodeValue);
//                 document.getElementById("toustext").innerHTML = etat_presence[i].firstChild.nodeValue;
//             }
//         }
//     }
//     xhr.send();
// }

// window.onload=function (){
//
// }
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btn_touspresence").addEventListener("click", getNbClickALL);
    document.getElementById("btn_add").addEventListener("click", addStudent);
    document.getElementById("search_student").addEventListener("keyup",showStudents);
    document.getElementById("delete_search").addEventListener("click", deleteSearchInfo);
    document.getElementById("btn_delete").addEventListener("click", deleteStudent);
    document.getElementsByClassName("close")[0].addEventListener("click", closePOP);
    document.querySelectorAll('.btn_delet_one').forEach(item => {
        item.addEventListener('click', event => {
            const id_delete = item.id.split("_")[1];
            deleteOneStudent(id_delete);
        })
    })
});