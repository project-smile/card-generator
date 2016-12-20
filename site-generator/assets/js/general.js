// Disclaimer
// Please note that all the code in this project is not written with a lot of love
// It was written two days prior to issuing the actual cards.
// It was also purposely written with as much vanilla javascript as possible (to practise this).


var isProduction = function() { return window.location.hostname.lastIndexOf('projectsmile.nl') > -1 };
// config per env
if (isProduction()) {
    window.config = {
        env: 'prd',
        uaCode: 'UA-85533742-1'
    };
} else {
    window.config = {
        env: 'dev',
        uaCode: 'UA-85533742-2'
    };
}

// google analytics code
(function (i, s, o, g, r, a, m) {
    i['GoogleAnalyticsObject'] = r;
    i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
    a = s.createElement(o),
        m = s.getElementsByTagName(o)[0];
    a.async = 1;
    a.src = g;
    m.parentNode.insertBefore(a, m)
})(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');
ga('create', window.config.uaCode, 'auto');
ga('send', 'pageview');


document.addEventListener('DOMContentLoaded', function () {
    ga('set', 'dimension1', 'unknown'); // dimension1 = card


    document.querySelectorAll('[track-event]').forEach(function (elem) {
        elem.addEventListener('click', function () {
            ga('send', {
                hitType: 'event',
                eventCategory: 'button',
                eventAction: 'click',
                eventLabel: elem.getAttribute('track-event')
            });
        });
    });



    (function() {
        document.querySelectorAll('input').forEach(function (input) {
            input.classList.add('pristine');
            input.addEventListener('focus', function() {
                input.classList.remove('pristine');
            });
        });
    })();


    window.setTimeout(function () {
        document.body.classList.add('fade-in');
    });


});
