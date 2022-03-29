document.onload = function getTimeTable() {

    var xhr = new XMLHttpRequest();
    var date = new date();
    alert(date);
    xhr.open("GET", "calendarServlet?date=" + date);
    xhr.responseType = 'json';

    const semaine = ["lundi", "mardi", "mercredi", "jeudi", "vendredi"]
    xhr.onload = function () {
        if (xhr.status === 200) {
            var jSon = xhr.response;
            var elt = document.getElementById("cd-schedule__events")
            var String = "<ul>"
            for (let jourSemaine = 0; jourSemaine < jSon.timeTable.length; jourSemaine++) {
                timeTable += "<li id='cd-schedule__group' class='cd-schedule__group'> " +
                    "<div class='cd-schedule__top-info'><span> " + semaine[jourSemaine] + "</span></div> <ul>"
                for (let cours = 0; cours < jSon.timeTable[jourSemaine].length; cours++) {
                    var event = jSon.timeTable[jourSemaine][cours].event;
                    var dataStart = jSon['timeTable'][jourSemaine][cours]['dataStart'];
                    var dataEnd = jSon['timeTable'][jourSemaine][cours]['dataEnd'];
                    String += "<li class='cd-schedule__event'> <a data-start='" + dataStart +
                        "' data-end='" + dataEnd + "' data-content='event-rowing-workout' " +
                        "data-event='event-2' href='#0'> <em class='cd-schedule__name'>" + event + "</em> </a> </li>"
                }
                String += "</ul></li>"
            }
            String += "</ul>"
            elt.innerHTML = String;

        }
    };
    xhr.send();

}

document.addEventListener("DOMContentLoaded", () => {
    document.addEventListener("load", getTimeTable);

});
