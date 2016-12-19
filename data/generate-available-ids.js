#!/usr/bin/env node
var fs = require('fs');

var illegalValues = fs.readFileSync('./blacklist', {encoding:'utf-8'}).toLowerCase();
function legalValue(value) {
   return illegalValues.indexOf(value.toLowerCase()) == -1;
}

var generated = generate(3);
for (var id in generated) {
  console.log(generated[id]);
}

function generate(number) {
  var values = [];

  deeper("", number);

  function deeper(value, iterate) {
    for ( var i = 0; i < 36; i++ ) {
      var newValue = value + "" + decode(i);
      if (iterate - 1> 0) {
        deeper(newValue, iterate - 1);
      } else {
        if (legalValue(newValue)) {
          values.push(newValue);
        }
      }
    }
  }

  function decode(value) {
     value = parseInt(value);
     if (value > 9) {
       return String.fromCharCode(value + 55);
     } else {
       return value;
     }
  }

  function shuffle(array) {
    var currentIndex = array.length, temporaryValue, randomIndex;

    // While there remain elements to shuffle...
    while (0 !== currentIndex) {

      // Pick a remaining element...
      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex -= 1;

      // And swap it with the current element.
      temporaryValue = array[currentIndex];
      array[currentIndex] = array[randomIndex];
      array[randomIndex] = temporaryValue;
    }

    return array;
  }

  return shuffle(values);
}
