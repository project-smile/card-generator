#!/usr/bin/env node
var mustacheExpress = require('mustache-express');
var express = require('express');

var app = express();

// Register '.html' extension with The Mustache Express
app.engine('mustache', mustacheExpress());

app.set('view engine', 'mustache');
app.disable('view cache');
app.set('views', __dirname + '/templates'); // you can change '/views' to '/public',
    // but I recommend moving your templates to a directory
    // with no outside access for security reasons

app.get('/:cardId/:page', function (req, res) {
  var card = {
        cardId: req.params.cardId,
        templateId: 'MSG1',
        message: 'Welkom'
        };
	res.render(req.params.page, card);
});

app.use('/assets', express.static('../../project-smile.github.io/assets'));

console.log('listening on 8080');
app.listen(8080);
