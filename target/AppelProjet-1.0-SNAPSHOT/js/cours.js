function showListAppel() {
    let xhr = new XMLHttpRequest();
    let select_cours = document.getElementById("select_cours");
    console.log(select_cours.value);
    let param = "select_cours=" + encodeURIComponent(select_cours.value);
    console.log(param);
    xhr.open("POST", url)
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.status === 200) {

        }
    }
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("select_cours").addEventListener("change", showListAppel);
});
