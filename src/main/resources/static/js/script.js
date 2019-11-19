const form = document.getElementById('phoneNumberForm');
const input = document.getElementById('phoneNumberInput');

form.addEventListener("submit", getCountry)

function getCountry() {
  fetch('https://localhost:8080/phoneNumber')
      // + new URLSearchParams({
  // phoneNumber: input.value
// }))
  .then((res) => {
    console.log(res.json())
    return res.json()
  })
  .then((data) => {
  console.log(data)
  })
}
