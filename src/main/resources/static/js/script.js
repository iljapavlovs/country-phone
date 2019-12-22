const countries_endpoint = 'http://localhost:8080/countries';
const form = document.getElementById('phoneNumberForm');
const input = document.getElementById('phoneNumberInput');
const ul = document.querySelector('ul');

form.addEventListener("submit", getCountry)

function getCountry(e) {
  e.preventDefault();
  fetch(`${countries_endpoint}?` + new URLSearchParams({
    phoneNumber: `${input.value}`
  }))
  .then((res) => {
    // console.log(res.json())
    if (res.status >= 200 && res.status < 400) {
      return res.json()
    } else {
      console.log(
          `ERROR occurred while communicating with BE. Response status: ${res.status}`)
      alert(
          `ERROR occurred while communicating with BE. Response status: ${res.status}`)
    }
  })
  .then((data) => {
    console.log(data)
    displayCountries(data.countries)
  }).catch(err => {
    console.log(err)
  })
}

function displayCountries(countries) {
  clearCountries();
  for (const country of countries) {
    const li = document.createElement('li');
    li.textContent = country;
    ul.appendChild(li);
  }

}

function clearCountries() {


  while (ul.firstChild) {
    ul.removeChild(ul.firstChild);
  }
}
