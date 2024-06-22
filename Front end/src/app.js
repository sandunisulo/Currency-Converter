document.getElementById('currency-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const amount = document.getElementById('amount').value;
    const valueFrom = document.getElementById('valueFrom').value;
    const valueTo = document.getElementById('valueTo').value;

    console.log('Form submitted with values:', amount, valueFrom, valueTo);

    // Construct the API URL
    const apiUrl = `http://localhost:8080/javaImplementation/getValue?amount=${amount}&valueFrom=${valueFrom}&valueTo=${valueTo}`;

    console.log('Fetching data from:', apiUrl);
    // Fetch the converted value from the backend
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Data received:', data);
            // Assuming the API returns a JSON object with a property "convertedValue"
            document.getElementById('converted-value').innerText = data.convertedValue;
        })
        .catch(error => {
            console.error('Error fetching the converted value:', error);
        });
});