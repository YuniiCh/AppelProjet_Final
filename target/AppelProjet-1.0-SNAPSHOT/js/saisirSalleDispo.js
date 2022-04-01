function l_cours ()
{
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    var dateD = document.getElementById("dateDebut").value;
    var dateF = document.getElementById("dateFin").value;

    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET","saisirNouvSeanceCtrl?dateDebut="+dateD+"&dateFin="+dateF);

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function()
    {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200)
        {
            // Elément html que l'on va mettre à jour.
            var elt = document.getElementById("lSalle");
            console.log("1");

            // Lecture du fichier XML
            var salles = xhr.responseXML.getElementsByTagName("salle");
            console.log("2");

            var res = "";
            for(i=0; i<salles.length; i++) {
                console.log(salles[i].firstChild.nodeValue);

                res += "<option value='" + salles[i].firstChild.nodeValue + "'>" + salles[i].firstChild.nodeValue + "</option>";
            }
            elt.innerHTML = res;
        }
    };

    // Envoie de la requête.
    xhr.send();

}

document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("dateFin").addEventListener("change", l_cours);
});