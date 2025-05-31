/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

const buttons = document.querySelectorAll('.paymentMethodBtn');
buttons.forEach(button => {
  button.addEventListener('click', () => {
    // Update button styling
    buttons.forEach(btn => {
      btn.classList.remove('btn-primary');
      btn.classList.add('btn-light');
    });
    button.classList.remove('btn-light');
    button.classList.add('btn-primary');
  });
});

function selectPaymentMethod(method) {
    document.getElementById('paymentMethod').value = method;

    // Show/hide payment forms
    document.getElementById('card').style.display = (method === 'card') ? 'block' : 'none';
    document.getElementById('cash').style.display = (method === 'cash') ? 'block' : 'none';

    // Toggle required fields
    const cardFields = document.querySelectorAll('[name="cardNo"], [name="name"], [name="expDate"], [name="cvv"]');
    cardFields.forEach(field => {
        field.required = (method === 'card');
    });
}

function validatePaymentSelection() {
    const selected = document.getElementById('paymentMethod').value;
    if (!selected) {
        alert('Please select a payment method.');
        return false;
    }
    return true;
}