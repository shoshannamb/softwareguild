/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$('#unitOptions').change(function () {
    var unitType = $('#unitOptions').val();
    if (unitType !== "no-selection") {
        $('#conversionOptions').show();
        populateForm($('#startingUnits'), unitType);
        populateForm($('#endingUnits'), unitType);
        $('#inputBox').attr("placeholder",unitType + " to convert");
    } else {
        $('#conversionOptions').hide();
    }
});

function populateForm(form, unit) {
    var timeOptions = ["Seconds", "Minutes", "Hours", "Days", "Weeks", "Years"];
    var volumeOptions = ["Milliliters", "Liters", "Teaspoons", "Tablespoons", "Cups", "Quarts", "Pints", "Gallons"];
    var massOptions = ["Ounces", "Pounds", "Tons", "Grams", "Kilograms"];
    var htmlString = "";
    var options;

    switch (unit) {
        case "Time":
            options = timeOptions;
            break;
        case "Volume":
            options = volumeOptions;
            break;
        case "Mass":
            options = massOptions;
            break;
    }
    ;

    for (var i = 0; i < options.length; i++) {
        htmlString += "<option value=\"" + options[i] + "\">" + options[i] + "</option>";
    }
    
    form.html(htmlString);
}