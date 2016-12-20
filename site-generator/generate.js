#!/usr/bin/env node
var Mustache = require('mustache');
var fs = require('fs');
var messages = require('./messages');
var csvParse = require('csv-parse');

const OUTPUT_DIR = process.env.OUTPUT_DIR || 'generated';

var defaultTemplates = {
    cardPage: fs.readFileSync('./templates/card.mustache', 'utf8'),
    registerPage: fs.readFileSync('./templates/register.mustache', 'utf8'),
    moreinfoPage: fs.readFileSync('./templates/moreinfo.mustache', 'utf8'),
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
        templateId: card.templateId,
        permalink: 'https://projectsmile.nl/' + card.cardId
    };

    var viewDataJsonFile = cardFolder + '/card.json';
    fs.writeFileSync(viewDataJsonFile, JSON.stringify(viewData));

    var indexFile = cardFolder + '/index.html';
    fs.writeFileSync(indexFile, Mustache.render(defaultTemplates.cardPage, viewData));

    var moreinfoFile = cardFolder + '/moreinfo.html';
    fs.writeFileSync(moreinfoFile, Mustache.render(defaultTemplates.moreinfoPage, viewData));

    var registerPage = cardFolder + '/register.html';
    fs.writeFileSync(registerPage, Mustache.render(defaultTemplates.registerPage, viewData));
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
