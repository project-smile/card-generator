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

// generic error handling, and informing the user about this error.
window.addEventListener('error', function (ev) {
    // log this error to GA
    ga('send', {
        hitType: 'event',
        eventCategory: 'error',
        eventAction: ev.message,
        eventLabel: JSON.stringify(ev)
    });

    var snackbarContainer = document.getElementById('snackbar');
    if (snackbarContainer) {
        var data = {
            message: 'Er is een fout opgetreden. Mogelijk dat je hier last van ondervindt.',
            timeout: 2000,
            actionHandler: function(ev) {},
            actionText: 'OK'
        };
        snackbarContainer.MaterialSnackbar.showSnackbar(data);
    }

});


/**
 * Polyfills
 */


if (!Array.prototype.forEach) {

    Array.prototype.forEach = function(callback, thisArg) {

        var T, k;

        if (this === null) {
            throw new TypeError(' this is null or not defined');
        }

        // 1. Let O be the result of calling toObject() passing the
        // |this| value as the argument.
        var O = Object(this);

        // 2. Let lenValue be the result of calling the Get() internal
        // method of O with the argument "length".
        // 3. Let len be toUint32(lenValue).
        var len = O.length >>> 0;

        // 4. If isCallable(callback) is false, throw a TypeError exception.
        // See: http://es5.github.com/#x9.11
        if (typeof callback !== "function") {
            throw new TypeError(callback + ' is not a function');
        }

        // 5. If thisArg was supplied, let T be thisArg; else let
        // T be undefined.
        if (arguments.length > 1) {
            T = thisArg;
        }

        // 6. Let k be 0
        k = 0;

        // 7. Repeat, while k < len
        while (k < len) {

            var kValue;

            // a. Let Pk be ToString(k).
            //    This is implicit for LHS operands of the in operator
            // b. Let kPresent be the result of calling the HasProperty
            //    internal method of O with argument Pk.
            //    This step can be combined with c
            // c. If kPresent is true, then
            if (k in O) {

                // i. Let kValue be the result of calling the Get internal
                // method of O with argument Pk.
                kValue = O[k];

                // ii. Call the Call internal method of callback with T as
                // the this value and argument list containing kValue, k, and O.
                callback.call(T, kValue, k, O);
            }
            // d. Increase k by 1.
            k++;
        }
        // 8. return undefined
    };
}

if (!NodeList.prototype.forEach) {
    NodeList.prototype.forEach = Array.prototype.forEach;
}