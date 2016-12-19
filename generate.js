#!/usr/bin/env node
var exec = require('child_process').execSync;


var cards = [
	{message: 'Ik wens jou alvast hele fijne feestdagen en een geweldig nieuwjaar!', codes: [
        'BGD', '4WK', 'NVS', 'SD5', '2TI', 'UL2', 'HZ7', '3DJ', 'PBL', 'QRI', 'TT4', 'VQ9', 'A0I', 'MJ7', 'HIO', 'XRT', '67D', 'B9C', 'NVJ', '1V8']},
	{message: 'Geniet elke dag alsof het je laatste is. Lach wanneer je kan!', codes: ['KEH', '0OD', '4BR', '4N9', '35O', 'UJ1', '1WU', 'YMZ', 'XGX', 'MQF']},
	{message: 'A smile everyday,<br/>keeps the darkness away', codes: ['DK1', 'L3C', 'OF8', 'RXL', 'BG5', '3GN', 'IP3', 'MVY', '92T', 'A1D']},
	{message: 'Hoe vaak heb jij al gelachen vandaag?', codes: ['2RF', 'TYN', 'LXO', 'M8J', 'NII', '5QF', 'F7F', '54O', 'K3A', '317']},
	{message: 'Wanneer was jouw laatste goede daad?', codes: ['K9M', '7FR', 'U8Q', 'G6Z', 'K1H', 'HDS', 'L21', 'HSO', '6SX', 'GII']},
	{message: 'Als de trein op tijd rijdt, <br />dan is dat perrongeluk...', codes: ['NGV', 'KTM', 'UNN', 'LCH', 'K46', '8NP', 'HSG', 'RLI', '9AQ', 'HJ9']},
	{message: 'Wat maakt iemand zijn dag? <br />Juist, dat is een welgemeende glimlach!', codes: ['1RE', 'BQD', '6AS', 'AWE', 'ITS', 'QTF', '1UP', '7WW', '0U5', 'U2U']},
	{message: 'Ik wens jou een fijne dag. <br />Geef het door!', codes: ['KBH', 'I6V', 'QUP', '8DQ', 'GDA', 'DTR', '6RO', 'EZ4', 'BS1', 'SOY']},
	{message: 'Je mag trots zijn op wie je bent', codes: ['V39', '1S3', 'Y3C', '1JA', 'J7K', 'YDX', 'XT4', 'U4F', '3RB', 'VSU']}
];


for (var i = 0; i < cards.length; i++) {
	var cardDetails = cards[i];

	for (var c = 0; c < cardDetails.codes.length; c++) {
		var cardCode = cardDetails.codes[c];
		createCard(cardCode, cardDetails.message);
	}
}


function createCard(cardCode, message) {
    console.log('Starting to generated card ' + cardCode);

	var cmd = 'wkhtmltopdf --page-height 61mm --page-width 91mm -T 0 -B 0 -L 0 -R 0 --no-pdf-compression -d 600' +
        ' "file:///Users/sdegroot/scm/github/project-smile.github.io/card/card.html?code='+cardCode + '&message='+ message +'" tmp/'+cardCode+'.pdf';
	exec(cmd, function(error, stdout, stderr) {
        console.log('Finished card ' + cardCode);
		// command output is in stdout
        if (error || stderr) {
            console.log('A problem occurred with card ' + cardCode);
        }
	});
}