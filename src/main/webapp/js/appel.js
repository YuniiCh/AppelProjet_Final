/**
 * Cette méthode "Ajax" affiche le XML.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */
const url = "appelCtrl";
let nbClick = 0;
let id_seance = document.getElementById("id_seance").innerText.split("°")[1];
function iniAppel(){
    let xhr = new XMLHttpRequest();
    let param = "etatpresence=" + encodeURIComponent(nb);
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.status === 200) {

        }
    }
}
function getNbClick(obj){
    if (obj.innerHTML.nodeValue == "Retard"){
        nbClick = 1;
    }else if(obj.innerHTML.nodeValue == "Absent"){
        nbClick = 2;
    }
    if (nbClick>1){
        nbClick = 0;
    }else {
        nbClick++;
    }
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
                console.log(obj);
                if (etat_presence[i].firstChild.nodeValue === "Absent"){
                    console.log(etat_presence[i].firstChild.nodeValue);
                    obj.style.backgroundColor = "#ffcc99";
                }else if (etat_presence[i].firstChild.nodeValue === "Retard"){
                    console.log( etat_presence[i].firstChild.nodeValue);
                    obj.style.backgroundColor = "#ffff66";
                }else {
                    console.log(etat_presence[i].firstChild.nodeValue);
                    obj.style.backgroundColor = "#a8e2f8";
                }

                // obj.CHILD(document.getElementById("etatPresent")).innerHTML = etat_presence[i].firstChild.nodeValue;
            }
        }
    };
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
        document.getElementById("btn_touspresence").style.backgroundColor = "#ffcc99";
        document.getElementById("toustext").innerHTML = "Tous Absent";
        for(let i = 0; i<btEtat.length;i++) {
            // btEtat[i].getElementsByClassName("etatpresent_cl")[0].innerHTML = "Pésent";
            btEtat[i].firstElementChild.innerHTML = "Absent";
            btEtat[i].style.backgroundColor = "#ffcc99";
        }
    }else {
        document.getElementById("btn_touspresence").style.backgroundColor = "#a8e2f8";
        document.getElementById("toustext").innerHTML = "Tous Pésent";
        for(let i = 0; i<btEtat.length;i++) {
            // btEtat[i].getElementsByClassName("etatpresent_cl")[0].innerHTML = "Pésent";
            btEtat[i].firstElementChild.innerHTML = "Pésent";
            btEtat[i].style.backgroundColor = "#a8e2f8";
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
    let param = "search_student=" + encodeURIComponent(search_student.value);
    console.log(param);
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
                    let style = "background-color: mediumpurple;";
                    for (let i = 0; i < students.length; i++){
                        console.log(students[i].firstChild.nodeValue);
                        if (students[i].firstChild.nodeValue.split(", ")[1]=="FA"){
                            style = "background-color: mediumslateblue;"
                        }
                        let idStudent = students[i].firstChild.nodeValue.split(", ")[0];
                        let nom = students[i].firstChild.nodeValue.split(", ")[2];
                        let btn_num = document.getElementsByClassName("btn_etatp_cl").length + 1;
                        let insert = " <tr><td><img src=\"https://github.com/PikaMeoow/Photo-Etudiant/blob/main/" +idStudent + ".png?raw=true\"  alt=\"images\"/></td>" +
                            "                <td class=\"student_info\"><span class=\"formation_color\" style=\""+ style +"\"</span><span class=\""+ idStudent +"\">" + nom + "</span></td>" +
                            "                <td><button id=\"btn_etatP" + btn_num + "\" class=\"btn_etatp_cl\" type=\"button\" name=\"" + idStudent + "\" onclick=\"getNbClick(this);\"><span id=\"etatPresent" + btn_num + "\" class=\"etatpresent_cl\">Présent</span></button></td>" +
                            "                <td><span class=\"btn_delet_one\" id=\"delet_" + btn_num + "\" style=\"pointer-events: none; display: none; \">&circleddash;</span></td></tr>";
                        zone.insertAdjacentHTML("beforeend", "<div class = 'student' id='student" +students[i].firstChild.nodeValue.split(" ")[0] +"'><span>" + students[i].firstChild.nodeValue + "</span></div>");
                        document.getElementById("listEtudiant").firstElementChild.insertAdjacentHTML("beforeend", insert);
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
    window.onclick = function(event) {
        document.querySelectorAll('.btn_delet_one').forEach(item => {
            if (event.target === item) {
                item.style.display = "none";
                item.style.pointerEvents = "none";
            }
        });
    }
}

function deleteOneStudent(id){
    let xhr = new XMLHttpRequest();
    let param = "deletestudent=" + encodeURIComponent(id + "_" + id_seance);
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
                }
            }
        }
    };
    xhr.send(param);
}

function confirmAppel() {
    let presences = document.getElementsByClassName("btn_etatp_cl");
    console.log(presences.item(0).getAttribute("name"));
    console.log(presences.item(0).firstChild.textContent);
    let etats = presences.item(0).getAttribute("name") + " " +presences.item(0).firstChild.textContent.replace("é","e");
    console.log("etat 0: " + etats);
    for (let i = 1; i < presences.length; i++){
        console.log(presences.item(i).getAttribute("name"));
        console.log(presences.item(i).firstChild.textContent);
        if (presences.item(i).firstChild.textContent !== "Signaler"){
            etats = etats + "," + presences.item(i).getAttribute("name") + " " + presences.item(i).firstChild.textContent.replace("é","e");
        }
    }
    console.log(etats);
    let xhr = new XMLHttpRequest();
    let param = "etats=" + encodeURIComponent(etats);
    console.log(param);
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        console.log("xhr.status : " + xhr.status);
        if (xhr.status === 200) {
            let update_status = xhr.responseXML.getElementsByTagName("student");
            let update_ok = true;
            let check_not_ok = true;
            for (let i = 0; i < update_status.length; i++){
                if (update_status[i].firstChild.nodeValue !== "update"){
                    update_ok = false;
                    check_not_ok = false;
                }else {
                    presences.item(i).disabled = true;
                }
            }
            if (check_not_ok !== false){
                document.getElementById("valideEtat").style.color = "green";
                document.getElementById("valideEtat").innerHTML = "Valider!";
                document.getElementById("valider").disabled = true;
            }else {
                document.getElementById("valideEtat").style.color = "red";
                document.getElementById("valideEtat").innerHTML = "Erreur!";
            }
        }

    };
    xhr.send(param);
}

// function strNoAccent(a) {
//     var b="áàâäãåçéèêëíïîìñóòôöõúùûüýÁÀÂÄÃÅÇÉÈÊËÍÏÎÌÑÓÒÔÖÕÚÙÛÜÝ",
//         c="aaaaaaceeeeiiiinooooouuuuyAAAAAACEEEEIIIINOOOOOUUUUY",
//         d = "";
//     for(var i = 0, j = a.length; i < j; i++) {
//         var e = a.substr(i, 1);
//         d += (b.indexOf(e) !== -1) ? c.substr(b.indexOf(e), 1) : e;
//     }
//     return d;
// }




//----------------Listener-------------------------
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btn_touspresence").addEventListener("click", getNbClickALL);
    document.getElementById("btn_add").addEventListener("click", addStudent);
    document.getElementById("search_student").addEventListener("keyup",showStudents);
    document.getElementById("delete_search").addEventListener("click", deleteSearchInfo);
    document.getElementById("btn_delete").addEventListener("click", deleteStudent);
    document.getElementById("valider").addEventListener("click", confirmAppel);
    document.getElementsByClassName("close")[0].addEventListener("click", closePOP);
    document.querySelectorAll('.btn_delet_one').forEach(item => {
        item.addEventListener('click', event => {
            const id_delete = item.id.split("_")[1];
            deleteOneStudent(id_delete);
        })
    })
});