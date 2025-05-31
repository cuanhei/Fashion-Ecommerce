/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


 function confirmSignUp() {
        // Get form field values
        var username = document.getElementsByName("username")[0].value;
        var name = document.getElementsByName("name")[0].value;
        var email = document.getElementsByName("email")[0].value;
        var contact = document.getElementsByName("contact")[0].value;
        var password = document.getElementsByName("password")[0].value;
        var conPassword = document.getElementsByName("conPassword")[0].value;

        // Build confirmation message with plain passwords
        var message = "Please confirm your information:\n\n" +
                      "Username: " + username + "\n" +
                      "Name: " + name + "\n" +
                      "Email: " + email + "\n" +
                      "Contact: " + contact + "\n" +
                      "Password: " + password + "\n" +
                      "Confirm Password: " + conPassword + "\n\n" +
                      "Click OK to submit or Cancel to go back.";

        // Show confirmation dialog
        return confirm(message);
    }
