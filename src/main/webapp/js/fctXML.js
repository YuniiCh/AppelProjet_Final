/**
 * Cette méthode "Ajax" affiche le XML.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */
let nbClick = 0;
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
    let url = "appelCtrl";
    xhr.open("GET", url+"?"+param);
    // xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function (){
        if (xhr.status===200){
            console.log("click");
            let etat_presence = xhr.responseXML.getElementsByTagName("etatpresence");
            // let etat = document.getElementById("etatPresent" + numbt_etatp);
            for (let i = 0; i<etat_presence.length; i++){
                console.log(etat_presence[i].firstChild.nodeValue);
                obj.innerHTML = etat_presence[i].firstChild.nodeValue;
                // obj.CHILD(document.getElementById("etatPresent")).innerHTML = etat_presence[i].firstChild.nodeValue;
            }
        }
    }
    xhr.send();
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
    let btEtat = document.getElementsByClassName("bt_etatp_cl");
    if (nb === 1) {
        document.getElementById("toustext").innerHTML = "Tous Absent";
        for(let i = 0; i<btEtat.length;i++) {
            btEtat[i].firstElementChild.innerHTML = "Absent";
        }

    }else {
        document.getElementById("toustext").innerHTML = "Tous Pésent";
        for(let i = 0; i<btEtat.length;i++) {
            btEtat[i].firstElementChild.innerHTML = "Pésent";
        }
    }
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
    document.getElementById("touspresence").addEventListener("click", getNbClickALL);
});