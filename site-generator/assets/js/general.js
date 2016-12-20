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


function initMap() {
    var mapOptions = {
        zoom: 10,
        center: new google.maps.LatLng(51.9814708, 5.1163364),
        scrollwheel: false,
        disableDefaultUI: true,
        styles: [
            {
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#1d2c4d"
                    }
                ]
            },
            {
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#8ec3b9"
                    }
                ]
            },
            {
                "elementType": "labels.text.stroke",
                "stylers": [
                    {
                        "color": "#1a3646"
                    }
                ]
            },
            {
                "featureType": "administrative.country",
                "elementType": "geometry.stroke",
                "stylers": [
                    {
                        "color": "#4b6878"
                    }
                ]
            },
            {
                "featureType": "administrative.land_parcel",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "administrative.land_parcel",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#64779e"
                    }
                ]
            },
            {
                "featureType": "administrative.neighborhood",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "administrative.province",
                "elementType": "geometry.stroke",
                "stylers": [
                    {
                        "color": "#4b6878"
                    }
                ]
            },
            {
                "featureType": "landscape.man_made",
                "elementType": "geometry.stroke",
                "stylers": [
                    {
                        "color": "#334e87"
                    }
                ]
            },
            {
                "featureType": "landscape.natural",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#023e58"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#283d6a"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "labels.text",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#6f9ba5"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "labels.text.stroke",
                "stylers": [
                    {
                        "color": "#1d2c4d"
                    }
                ]
            },
            {
                "featureType": "poi.business",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "poi.park",
                "elementType": "geometry.fill",
                "stylers": [
                    {
                        "color": "#023e58"
                    }
                ]
            },
            {
                "featureType": "poi.park",
                "elementType": "labels.text",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "poi.park",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#3C7680"
                    }
                ]
            },
            {
                "featureType": "road",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#304a7d"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "labels",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#98a5be"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "labels.text.stroke",
                "stylers": [
                    {
                        "color": "#1d2c4d"
                    }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#2c6675"
                    }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "geometry.stroke",
                "stylers": [
                    {
                        "color": "#255763"
                    }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#b0d5ce"
                    }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "labels.text.stroke",
                "stylers": [
                    {
                        "color": "#023e58"
                    }
                ]
            },
            {
                "featureType": "transit",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#98a5be"
                    }
                ]
            },
            {
                "featureType": "transit",
                "elementType": "labels.text.stroke",
                "stylers": [
                    {
                        "color": "#1d2c4d"
                    }
                ]
            },
            {
                "featureType": "transit.line",
                "elementType": "geometry.fill",
                "stylers": [
                    {
                        "color": "#283d6a"
                    }
                ]
            },
            {
                "featureType": "transit.station",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#3a4762"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#0e1626"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "labels.text",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#4e6d70"
                    }
                ]
            }
        ]
    };

    var mapElement = document.getElementById('map');

    var map = new google.maps.Map(mapElement, mapOptions);

    var infowindow = new google.maps.InfoWindow();
    infowindow.setContent('<div><b>Dit ben ik</b></div>');


    var marker = new google.maps.Marker({
        position: map.getCenter(),
        map: map,
        icon: 'data:image/png;base64,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',
        title: 'Click to zoom'
    });

    infowindow.open(map, marker);


}

