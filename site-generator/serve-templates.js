#!/usr/bin/env node
var mustacheExpress = require('mustache-express');
var express = require('express');
var multer = require('multer');
var bodyParser = require('body-parser')
var fs = require('fs');

var app = express();

app.use(bodyParser.json());


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
        message: 'Welkom',
        permalink: 'https://projectsmile.nl/'+req.params.cardId
        };
	res.render(req.params.page, card);
});

app.post('/submit', multer({dest:'./'}).single('selfie'), function(req, res) {
    console.log(req.file);
    res.contentType(req.file.mimetype);
    fs.createReadStream(__dirname + "/" + req.file.filename, 'base64').pipe(res);
});

app.get('/status', function(req, res) {
   res.status(200).send({status:'ok'});
});

app.use('/assets', express.static('./assets'));

app.use('/', express.static('./root-assets'));

console.log('listening on 3000');
app.listen(3000);
