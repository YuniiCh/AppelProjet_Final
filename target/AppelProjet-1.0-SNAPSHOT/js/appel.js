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
            btEtat[i].firstElementChild.innerHTML = "Absent";
            btEtat[i].style.backgroundColor = "#ffcc99";
        }
    }else {
        document.getElementById("btn_touspresence").style.backgroundColor = "#a8e2f8";
        document.getElementById("toustext").innerHTML = "Tous Présent";
        for(let i = 0; i<btEtat.length;i++) {
            // btEtat[i].getElementsByClassName("etatpresent_cl")[0].innerHTML = "Présent";
            btEtat[i].firstElementChild.innerHTML = "Présent";
            btEtat[i].style.backgroundColor = "#a8e2f8";
        }
    }
}

function addStudent(){
    const pop = document.getElementById('add_student_pop');

    pop.style.display = "block";


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
    document.getElementById("addmsg").innerHTML="";
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
                console.log(search_student.value);
                zone.innerHTML = ""
                zone.style.display = "none";
            } else {
                zone.style.display = "inline";
                let style = "background-color: mediumpurple;";
                for (let i = 0; i < students.length; i++){
                    if (students[i].firstChild.textContent !== "null"){
                        console.log(students[i].firstChild.textContent);
                        let type = students[i].getElementsByTagName("type").item(0).textContent;
                        let id = students[i].getElementsByTagName("id").item(0).textContent;
                        let nom = students[i].getElementsByTagName("nom").item(0).textContent;
                        if (type === "FA"){
                            style = "background-color: mediumslateblue;"
                        }
                        console.log(style);
                        zone.insertAdjacentHTML("beforeend", "<div class = 'student' id='student_"+ id + "'><span>" + id + ", " + type +", " + nom + "</span></div>");
                        console.log(type);
                    }
                    let list_students = document.getElementsByClassName("student");
                    for (let i = 0; i < list_students.length; i++){
                        list_students[i].addEventListener("click", showStrudentsList);
                    }
                }
            }
        }


    };
    xhr.send(param);
}

function showStrudentsList(){
    let student = this.firstChild.firstChild.nodeValue;
    console.log(this.innerText);
    console.log(this.firstChild.firstChild.nodeValue);
    let add_student = document.getElementById("listEtudiant");
    let msg = document.getElementById("addmsg");
    let xhr = new XMLHttpRequest();
    let param = "addstudent=" + encodeURIComponent(student.split(", ")[0]);
    console.log(param);
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.status === 200) {
            let add_status = xhr.responseXML.getElementsByTagName("student");
            let idStudent = student.split(", ")[0];
            let formation = student.split(", ")[1];
            let nom = student.split(", ")[2];
            let style = "background-color: mediumpurple;";
            if (formation =="FA"){
                style = "background-color: mediumslateblue;";
            }
            let btn_num = document.getElementsByClassName("btn_etatp_cl").length + 1;
            let addlist = "<tr><td>" + idStudent + "</td><td>" + formation + "</td><td>" + nom + "</td></tr>";
            let insert = " <tr><td><img style=\"height: 4.5rem; width: 4.5rem;\" src=\"https://github.com/PikaMeoow/Photo-Etudiant/blob/main/" +idStudent + ".png?raw=true\"  alt=\"images\"/><span id='change_group'>&copy;</span></td>" +
                "                <td class=\"student_info\"><a href=\"presenceEtudiantCtrl?idEtudiant="+idStudent+"\"><span class=\"formation_color\" style=\""+ style +"\">" + student.split(", ")[1] + "</span>" + formation + "</span><span class=\""+ idStudent +"\">" + nom + "</span></a></td>" +
                "                <td><button id=\"btn_etatP" + btn_num + "\" class=\"btn_etatp_cl\" type=\"button\" name=\"" + idStudent + "\" onclick=\"getNbClick(this);\"><span id=\"etatPresent" + btn_num + "\" class=\"etatpresent_cl\">Présent</span></button></td>" +
                "                <td><span class=\"btn_delet_one\" id=\"delet_" + btn_num + "\" style=\"pointer-events: none; display: none; \">&circleddash;</span></td></tr>";
            for(let i=0; i< add_status.length; i++){
                // add_student.insertAdjacentHTML("beforeend","<p>"+student+"</p>")
                if(add_status[i].firstChild.nodeValue !== "null"){
                    document.getElementById("tb_choose").insertAdjacentHTML("beforeend", addlist)
                    add_student.firstElementChild.insertAdjacentHTML("beforeend", insert);
                    if (document.getElementById("btn_etatP" + btn_num)===undefined){
                        msg.innerHTML = "Erreur";
                        msg.style.color = "red";
                    }else{
                        msg.innerHTML = "Ajouter "+ student ;
                        msg.style.color = "green";
                        showStudents();
                    }
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
    let param = "deletestudent=" + encodeURIComponent(id );
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
        location.reload();
    };
    xhr.send(param);
}


function confirmAppel() {
    // let presences = document.getElementsByClassName("btn_etatp_cl");
    let presences = document.getElementsByClassName("btn_etatp_cl");
    let btn_name = this.getAttribute("name");
    console.log("this button " + btn_name);
    console.log(presences.item(0).getAttribute("name"));
    console.log(presences.item(0).firstChild.textContent);
    let etatsConfirm = presences.item(0).getAttribute("name") + " " + presences.item(0).firstChild.textContent;
    console.log("etat 0: " + etatsConfirm);
    for (let i = 1; i < presences.length; i++){
        console.log(presences.item(i).getAttribute("name"));
        console.log(presences.item(i).firstChild.textContent);
        if (presences.item(i).firstChild.textContent !== "Signaler"){
            etatsConfirm = etatsConfirm + "," + presences.item(i).getAttribute("name") + " " + presences.item(i).firstChild.textContent;
        }
    }
    // let etatsConfirm = getEtats();
    console.log(etatsConfirm);
    let xhr = new XMLHttpRequest();
    let param = "etats=" + encodeURIComponent(etatsConfirm) + "&action=" + btn_name;
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
            let span = document.getElementById(btn_name+ "Etat");
            if (check_not_ok !== false){
                span.style.color = "green";
                console.log(span.id);
                // this.nextSibling.style.color  = "green";
                if (btn_name === "submit"){
                    span.innerText = "Valider";
                    span.style.disabled = true;
                }else {
                    span.innerText = "Enregistrer";
                }
            }else {
                span.style.color  = "red";
                span.innerText = "Erreur";
            }
        }
        location.reload();
    };
    xhr.send(param);
}




//----------------Listener-------------------------
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btn_touspresence").addEventListener("click", getNbClickALL);
    document.getElementById("btn_add").addEventListener("click", addStudent);
    document.getElementById("search_student").addEventListener("keyup",showStudents);
    document.getElementById("delete_search").addEventListener("click", deleteSearchInfo);
    document.getElementById("btn_delete").addEventListener("click", deleteStudent);
    document.getElementById("valider").addEventListener("click", confirmAppel);
    document.getElementById("save").addEventListener("click", confirmAppel);
    document.getElementsByClassName("close")[0].addEventListener("click", closePOP);
    document.querySelectorAll('.btn_delet_one').forEach(item => {
        item.addEventListener('click', event => {
            const id_delete = item.id.split("_")[1];
            deleteOneStudent(id_delete);
        })
    })
});