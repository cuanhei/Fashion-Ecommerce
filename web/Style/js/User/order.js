/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function showOnly(className, id) {
  classes = document.querySelectorAll('.'+className);
  classes.forEach(div => {
        if(div.id !== id){
            div.style.display = 'none'; // hide all forms
        }
  });

  const selectedtDiv = document.getElementById(id);
  if (selectedtDiv) {
      if(selectedtDiv.style.display === 'block'){
          selectedtDiv.style.display = 'none';
      }else{
          selectedtDiv.style.display = 'block';
      }
  }
}

function animateStaggeredList(selector, delay = 200) {
  const items = document.querySelectorAll(selector);
  items.forEach((item, index) => {
    setTimeout(() => {
      item.classList.add('orderAnimation');
    }, index * delay);
  });
}

// Trigger animation on page load
window.addEventListener('DOMContentLoaded', () => {
  animateStaggeredList('.order', 100);
});