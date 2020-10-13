function gebruikersnaam() {
  var naam = "";
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      var obj = JSON.parse(this.responseText);
      voornaam = "Hallo " + obj.voornaam + "!";
      naam = obj.voornaam + " " + obj.achternaam;
      document.getElementById("gebruikersnaam").innerHTML = naam;
      document.getElementById("gebruikersnaam2").innerHTML = "Welkom "+ naam;
    }
  };
  xhr.open(
    "GET",
    "http://173.212.208.199:1337/api/users/" + localStorage.getItem("userId"),
    true
  );
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
}

function bedrijfsnaam() {
  var naam = "";
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      var obj = JSON.parse(this.responseText);
      naam = obj.bedrijfsNaam;
    }

    document.getElementById("bedrijfsnaam").innerHTML = naam;
  };
  xhr.open(
    "GET",
    "http://173.212.208.199:1337/api/users/bedrijf/" +
      localStorage.getItem("userId"),
    true
  );
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
}

const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("userId");
  localStorage.removeItem("userRole");
  window.location.href = "../inlogscherm.html";
};

const deleteGebruiker = () => {
  var xhr = new XMLHttpRequest();
  xhr.open(
    "DELETE",
    "http://173.212.208.199:1337/api/users/" +
      document.getElementById("selectVerwijderGebruiker").value,
    true
  );
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
  document.getElementById("modal-aanpassen").innerHTML = "Gebruiker verwijdert";
  $("#myModal-aanpassen").modal("show");
  setTimeout(paginaReload, 2000);
};

const deleteBedrijf = () => {
  let xhr = new XMLHttpRequest();
  console.log(document.getElementById("selectBedrijf").value);
  xhr.open(
    "DELETE",
    "http://173.212.208.199:1337/api/bedrijf/delete/" +
      document.getElementById("selectBedrijf").value,
    true
  );
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
  document.getElementById("modal-aanpassen").innerHTML = "Bedrijf verwijdert";
  $("#myModal-aanpassen").modal("show");
  setTimeout(paginaReload, 2000);
};

const loginAuthAdmin = () => {
  if (localStorage.getItem("userRole") != "[ROLE_ADMIN]")
    window.location.href = "../inlogscherm.html";
};

const alleBedrijvenModal = () => {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      var bedrijven = JSON.parse(this.responseText);
      var x;
      var selectVerwijderBedrijf = document.getElementById("selectBedrijf");
      for (x = 0; x < bedrijven.length; x++) {
        var el = document.createElement("option");
        el.textContent = bedrijven[x].bedrijfsNaam;
        el.value = bedrijven[x].id;
        selectVerwijderBedrijf.appendChild(el);
      }
    }
  };
  xhr.open("GET", "http://173.212.208.199:1337/api/bedrijf/all", true);
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
};

const alleBedrijvenWijzigModal = () => {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      var bedrijven = JSON.parse(this.responseText);
      var x;
      var selectWijzigBedrijf = document.getElementById("selectNumber");
      for (x = 0; x < bedrijven.length; x++) {
        var el = document.createElement("option");
        el.textContent = bedrijven[x].bedrijfsNaam;
        el.value = bedrijven[x].id;
        selectWijzigBedrijf.appendChild(el);
      }
    }
  };
  xhr.open("GET", "http://173.212.208.199:1337/api/bedrijf/all", true);
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
};

const GebruikersVerwijderenModal = () => {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      var databaseContents = JSON.parse(this.responseText);
      var x;
      var c;
      var select = document.getElementById("selectVerwijderGebruikermodal");
      for (x = 0; x < databaseContents.length; x++) {
        var el = document.createElement("option");
        for (c = 0; c < databaseContents[x].roles.length; c++) {
          if (
            databaseContents[x].roles[c].name !== "ROLE_ADMIN" &&
            databaseContents[x].roles[c].name !== "ROLE_BEDRIJF"
          ) {
            el.textContent =
              databaseContents[x].voornaam + " " + databaseContents[x].achternaam;
            el.value = databaseContents[x].userId;
            select.appendChild(el);
          }
        }
      }
    }
  };
  xhr.open("GET", "http://173.212.208.199:1337/api/admin/all-users", true);
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
};

const alleGebruikersModal = () => {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      var databaseContents = JSON.parse(this.responseText);

      var x;
      var c;
      var select = document.getElementById("selectGebruiker");
      var selectverw = document.getElementById("selectVerwijderGebruiker");

      for (x = 0; x < databaseContents.length; x++) {
        for (c = 0; c < databaseContents[x].roles.length; c++) {
          if (
            databaseContents[x].roles[c].name == "ROLE_ADMIN" ||
            databaseContents[x].roles[c].name == "ROLE_BEDRIJF"
          ) {
          } else {
            var el = document.createElement("option");
            el.textContent =
              databaseContents[x].voornaam +
              " " +
              databaseContents[x].achternaam;
            el.value = databaseContents[x].id;
            select.appendChild(el);
          }
        }
      }
    }
  };
  xhr.open("GET", "http://173.212.208.199:1337/api/admin/all-users", true);
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
};

const updateBedrijftoGebruiker = () => {
  var xhr = new XMLHttpRequest();
  xhr.open(
    "PUT",
    "http://173.212.208.199:1337/api/bedrijf/" +
      document.getElementById("selectBedrijf").value +
      "/" +
      document.getElementById("selectGebruiker").value,
    true
  );
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.setRequestHeader("Authorization", localStorage.getItem("token"));
  xhr.send();
  document.getElementById("modal-aanpassen").innerHTML =
    "Gebruiker gekoppeld aan bedrijf";
  $("#myModal-aanpassen").modal("show");
};

function paginaTerug() {
  window.location.replace("gebruikeroverzicht.html");
}

function paginaTerugBedrijf() {
  window.location.replace("bedrijfoverzicht.html");
}

function paginaReload() {
  location.reload();
}

function vandaag(){
  var months = ["Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "October", "November", "December"];
  var n = new Date();
  var y = n.getFullYear();
  var m = n.getMonth();
  var d = n.getDate();
  document.getElementById("vandaag").innerHTML = d + " " + months[m] + " " + y;
  document.getElementById("vandaag2").innerHTML = d + " " + months[m] + " " + y;
}