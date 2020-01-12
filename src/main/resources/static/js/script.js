const countries_endpoint = '/countries';
const form = document.getElementById('phoneNumberForm');
const input = document.getElementById('phoneNumberInput');
const countries_ul = document.querySelector('.countries');
const error = document.getElementById('error');

form.addEventListener("submit", getCountry)

function getCountry(e) {
  e.preventDefault();

  get(countries_endpoint, {
    phoneNumber: input.value
  })
  .then((data) => {
    displayCountries(data.countries)
  }).catch(error => {
    displayError(error)
  })
}

function get(url, params) {
  return fetch(`${url}?` + new URLSearchParams(params))
  .then((res) => {
    let promise = res.json();
    if (res.status >= 200 && res.status < 400) {
      return promise
    } else {
      return promise
      .then((response) => {
        throw response.errorMessage
      })
    }
  });
}


function displayCountries(countries) {
  clearCountries();
  input.classList.remove("is-invalid");
  input.classList.add("is-valid");
  for (const country of countries) {
    const li = document.createElement('li');
    li.textContent = country;
    countries_ul.appendChild(li);
  }

}

function displayError(errorText) {
  clearCountries();
  input.classList.remove("is-valid");
  input.classList.add("is-invalid");
  error.textContent = errorText;
  error.classList.remove("valid-feedback");
  error.classList.add("invalid-feedback");
}

function clearCountries() {
  while (countries_ul.firstChild) {
    countries_ul.removeChild(countries_ul.firstChild);
  }
}
