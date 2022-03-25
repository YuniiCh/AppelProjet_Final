/**
 * Cette méthode "Ajax" affiche le XML.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */
let nbClick = 0;
let numbt_etatp = 0;
function getNbClick(obj){
    if (nbClick>2){
        nbClick = 1;
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

// window.onload=function (){
//
// }
document.addEventListener("DOMContentLoaded", () => {
    // document.getElementsByClassName("bt_etatp_cl").addEventListener("click", getNbClick);
    // var bt_etatP = document.getElementsByClassName("bt_etatp_cl");
    // for (let i = 0; i < bt_etatP.length; i++){
    //     let e = "bt_etatP"+i;
    //     numbt_etatp++;
    //     document.getElementById(e).addEventListener("click", getNbClick);
    // }
});