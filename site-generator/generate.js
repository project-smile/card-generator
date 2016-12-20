#!/usr/bin/env node
var Mustache = require('mustache');
var fs = require('fs');
var messages = require('./messages');
var csvParse = require('csv-parse');

const OUTPUT_DIR = process.env.OUTPUT_DIR || 'generated';

var defaultTemplates = {
    cardPage: '',
    registerPage: null,
    homepage: null
};

function generateCardPages(card, outputDir) {

    if (!card.cardId) {
        console.error('Missing card.id. Not generating card', card);
        return;
    }

    // make the folder we are going to write in
    var cardFolder = outputDir + '/' + card.cardId;
    fs.mkdirSync(cardFolder);


    // make the card meta data
    var viewData = {
        cardId: card.cardId,
        message: card.message,
        templateId: card.templateId
    };

    var viewDataJsonFile = cardFolder + '/card.json';
    fs.writeFileSync(viewDataJsonFile, JSON.stringify(viewData));


    // Mustache.render("{{title}} spends {{calc}}", viewData);
}


var parser = csvParse({delimiter: ','}, function (err, data) {
    if (err) {
        console.error('ERROR: ' + err);
        process.exit(-1);
    }

    data.forEach(function (cardEntry) {

        generateCardPages({
            cardId: cardEntry[0],
            templateId: cardEntry[1],
            message: messages[cardEntry[1]]
        }, OUTPUT_DIR);
    });

});


fs.createReadStream(__dirname + '/cards.csv').pipe(parser);
