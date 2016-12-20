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

if (window.location.hostname.lastIndexOf('projectsmile.nl') > -1) {
    // prd profile
    ga('create', 'UA-85533742-1', 'auto');
} else {
    //dev profile
    ga('create', 'UA-85533742-2', 'auto');
}
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


    window.setTimeout(function () {
        document.body.classList.add('fade-in');
    });


});
