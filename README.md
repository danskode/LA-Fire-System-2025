# 🔥 LA Wild Fire
*Digital sirenealarm og brandovervågning for Los Angeles Fire Department*

LA Wild Fire er et full-stack REST webapplikationsprojekt udviklet som eksamensopgave. Projektet simulerer et intelligent varslingssystem, der hjælper brandvæsnet i Los Angeles med at overvåge og håndtere brande digitalt – i realtid.

[Demo af LA Wild Fire](docs/LA Wild Fires.gif)

---

Clon projektet og start det op. Besøg så localhost:8080/index.html ...

## 🚨 Formål
Systemet er udviklet til at understøtte Los Angeles brandvæsens behov for:
- **Overvågning af sirener** i byen og nærliggende skove
- **Digital registrering af brande** med tid og sted
- **Automatisk aktivering af sirener** inden for 10 km af en brand
- **Statusvisning** på sirener (aktiv/fred) og brande (aktiv/lukket)
- **Effektiv afmelding**, der slukker brande digitalt og opdaterer sirenernes status

---

## 🛠️ Teknologier
- **Backend**: Java, Spring Boot, REST API, JPA, H2
- **Frontend**: HTML, CSS, JavaScript, Leaflet.js (kortvisning)
- **Test**: JUnit, Mockito
- **Database**: Relationsmodel med entiteter for Sirens, Fires og Alarms

---

## ✨ Funktioner
### 👩‍🚒 Sirenehåndtering
- CRUD på sirener: opret, læs, opdater og slet
- Se status (aktiv/fred) og funktionalitet (virker/ikke)

### 🔥 Brandregistrering
- Opret brand via koordinater
- Aktiver automatisk sirener indenfor 10 km radius
- Vis brand og sirener på interaktivt kort

### 🧯 Alarmfunktion
- Automatisk alarmoprettelse og tidsstempling
- Lukning af brand deaktiverer tilhørende sirener

---

## 🗺️ Frontend Highlights
- Interaktive kort med ikoner for brande og sirener
- Visuelle indikatorer på status og placering
- Modal forms til redigering og oprettelse

---

## 📦 API Endpoints
```http
GET    /api/sirens                  // Hent alle sirener  
POST   /api/sirens                  // Opret ny sirene  
PUT    /api/sirens/{id}             // Rediger sirene  
DELETE /api/sirens/{id}             // Slet sirene  

POST   /api/fires                   // Opret ny brand og aktiver sirener  
GET    /api/fires/active            // Hent aktive brande  
PUT    /api/fires/deactivate/{id}   // Luk brand og deaktiver sirener
