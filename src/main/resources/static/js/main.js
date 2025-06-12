function loadMain() {
    const content = document.getElementById("content");
    content.innerHTML = `
    <H1>Main Page</H1>
    <div>
      <label for="fireName">Navn på ny brand:</label>
      <input type="text" id="fireName" placeholder="Indtast navn">
      <label for="latitude">Latitude:</label>
      <input type="number" id="latitude" placeholder="Indtast latitude">
      <label for="longitude">Longitude:</label>
      <input type="number" id="longitude" placeholder="Indtast longitude">
      <button onclick="createFire()">Opret ny brand</button>
    </div>
    <button onclick="startAlarm()">Start alarm!</button>
  `;
}

function createFire() {
    const fireNameInput = document.getElementById("fireName");
    const latitudeInput = document.getElementById("latitude");
    const longitudeInput = document.getElementById("longitude");

    const fireName = fireNameInput.value.trim();
    const latitude = parseFloat(latitudeInput.value);
    const longitude = parseFloat(longitudeInput.value);

    if (fireName && !isNaN(latitude) && !isNaN(longitude)) {
        const newFire = {
            name: fireName,
            latitude: latitude,
            longitude: longitude,
            active: true
        };

        fetch("/api/fires", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newFire)
        })
            .then(response => {
                if (response.ok) {
                    alert("Ny brand oprettet!");
                    fireNameInput.value = "";
                    latitudeInput.value = "";
                    longitudeInput.value = "";
                } else {
                    alert("Fejl ved oprettelse af ny brand.");
                }
            })
            .catch(error => {
                console.error("Fejl ved oprettelse af ny brand:", error);
                alert("Fejl ved oprettelse af ny brand.");
            });
    } else {
        alert("Indtast venligst et navn, latitude og longitude for den nye brand.");
    }
}

function loadMain() {
    fetch("/api/fires?active=true")
        .then(response => response.json())
        .then(fires => {
            const content = document.getElementById("content");

            if (fires.length === 0) {
                content.innerHTML = "<p>Ingen aktive brande.</p>";
                return;
            }

            let html = `<h2>Aktive Brande</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Navn</th>
                                    <th>Latitude</th>
                                    <th>Longitude</th>
                                    <th>Handlinger</th>
                                </tr>
                            </thead>
                            <tbody>`;

            fires.forEach(fire => {
                html += `
                    <tr>
                        <td>${fire.id}</td>
                        <td>${fire.name}</td>
                        <td>${fire.latitude}</td>
                        <td>${fire.longitude}</td>
                        <td>
                            <button onclick="startAlarmsForFire(${fire.id})">Start alarmer</button>
                            <button onclick="stopAlarmsForFire(${fire.id})">Stop alarmer</button>
                        </td>
                    </tr>`;
            });

            html += `</tbody></table>`;
            content.innerHTML = html;
        })
        .catch(error => {
            console.error("Fejl ved hentning af aktive brande:", error);
            document.getElementById("content").innerHTML = "<p>Fejl ved hentning af brande.</p>";
        });
}

function startAlarmsForFire(fireId) {
    fetch(`/api/alarms/start/fire/${fireId}`, {
        method: 'PUT'
    })
        .then(response => {
            if (response.ok) {
                alert(`Alarmer startet for brand #${fireId}`);
                loadMain();
            } else {
                alert('Kunne ikke starte alarmer.');
            }
        })
        .catch(error => {
            console.error('Fejl ved start af alarmer:', error);
            alert('Kunne ikke starte alarmer.');
        });
}

function stopAlarmsForFire(fireId) {
    fetch(`/api/alarms/stop/fire/${fireId}`, {
        method: 'PUT'
    })
        .then(response => {
            if (response.ok) {
                alert(`Alarmer stoppet for brand #${fireId}`);
                loadMain();
            } else {
                alert('Kunne ikke stoppe alarmer.');
            }
        })
        .catch(error => {
            console.error('Fejl ved stop af alarmer:', error);
            alert('Kunne ikke stoppe alarmer.');
        });
}





function loadFires() {
    const content = document.getElementById("content");

    // Hent alle aktive fires fra backend
    fetch("/api/fires?active=true")
        .then(response => response.json())
        .then(fires => {
            // Opret en HTML-tabel
            let table = document.createElement("table");
            let thead = document.createElement("thead");
            let tbody = document.createElement("tbody");

            // Opret tabeloverskrifter
            let tr = document.createElement("tr");
            let th1 = document.createElement("th");
            th1.textContent = "ID";
            let th2 = document.createElement("th");
            th2.textContent = "Navn";
            let th3 = document.createElement("th");
            th3.textContent = "Latitude";
            let th4 = document.createElement("th");
            th4.textContent = "Longitude";
            let th5 = document.createElement("th");
            th5.textContent = "Aktiv";
            tr.appendChild(th1);
            tr.appendChild(th2);
            tr.appendChild(th3);
            tr.appendChild(th4);
            tr.appendChild(th5);
            thead.appendChild(tr);

            // Tilføj aktive fires til tabellen
            fires.forEach(fire => {
                if (fire.active) {
                    let tr = document.createElement("tr");
                    let td1 = document.createElement("td");
                    td1.textContent = fire.id;
                    let td2 = document.createElement("td");
                    td2.textContent = fire.name;
                    let td3 = document.createElement("td");
                    td3.textContent = fire.latitude;
                    let td4 = document.createElement("td");
                    td4.textContent = fire.longitude;
                    let td5 = document.createElement("td");
                    td5.textContent = fire.active;
                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tr.appendChild(td4);
                    tr.appendChild(td5);
                    tbody.appendChild(tr);
                }
            });

            table.appendChild(thead);
            table.appendChild(tbody);
            content.innerHTML = "";
            content.appendChild(table);
        })
        .catch(error => {
            console.error("Fejl ved indlæsning af fires:", error);
        });
}


function loadSirens() {
    const content = document.getElementById("content");

    // Hent alle sirener fra backend
    fetch("/api/sirens")
        .then(response => response.json())
        .then(sirens => {
            // Opret en HTML-tabel
            let table = document.createElement("table");
            let thead = document.createElement("thead");
            let tbody = document.createElement("tbody");

            // Opret tabeloverskrifter
            let tr = document.createElement("tr");
            let th1 = document.createElement("th");
            th1.textContent = "ID";
            let th2 = document.createElement("th");
            th2.textContent = "Navn";
            let th3 = document.createElement("th");
            th3.textContent = "Latitude";
            let th4 = document.createElement("th");
            th4.textContent = "Longitude";
            let th5 = document.createElement("th");
            th5.textContent = "Aktiv";
            let th6 = document.createElement("th");
            th6.textContent = "Funktionel";
            tr.appendChild(th1);
            tr.appendChild(th2);
            tr.appendChild(th3);
            tr.appendChild(th4);
            tr.appendChild(th5);
            tr.appendChild(th6);
            thead.appendChild(tr);

            // Tilføj sirener til tabellen
            sirens.forEach(siren => {
                let tr = document.createElement("tr");
                let td1 = document.createElement("td");
                td1.textContent = siren.id;
                let td2 = document.createElement("td");
                td2.textContent = siren.name;
                let td3 = document.createElement("td");
                td3.textContent = siren.latitude;
                let td4 = document.createElement("td");
                td4.textContent = siren.longitude;
                let td5 = document.createElement("td");
                td5.textContent = siren.active;
                let td6 = document.createElement("td");
                td6.textContent = siren.functional;
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tbody.appendChild(tr);
            });

            table.appendChild(thead);
            table.appendChild(tbody);
            content.innerHTML = "";
            content.appendChild(table);
        })
        .catch(error => {
            console.error("Fejl ved indlæsning af sirener:", error);
        });
}

function startAlarm(){
        console.log("Starting Alarm");
}

window.onload = loadMain;

