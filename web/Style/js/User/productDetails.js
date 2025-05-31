/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


const buttons = document.querySelectorAll('.sizeBtn');
const forms = document.querySelectorAll('.purcahseForm');

buttons.forEach(button => {
  button.addEventListener('click', () => {
    // Update button styling
    buttons.forEach(btn => {
      btn.classList.remove('btn-dark');
      btn.classList.add('btn-light');
    });
    button.classList.remove('btn-light');
    button.classList.add('btn-dark');
  });
});

function showPurchaseForm(formID) {
  forms.forEach(form => {
    form.style.display = 'none'; // hide all forms
  });

  const selectedForm = document.getElementById(formID);
  if (selectedForm) {
    selectedForm.style.display = 'block'; // show the selected form
  }
}


function toggleShowReplies(element) {
  element.textContent = (element.textContent.trim() === 'Show Replies') ? 'Hide Replies' : 'Show Replies';
}

