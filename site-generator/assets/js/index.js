(function () {


    var map = null;
    var registrations = null;

    var req = new XMLHttpRequest();
    req.open("GET", window.config.apiBaseUrl + "/card/-/registration", true);
    req.onload = function () {
        if (req.status == 200) {
            registrations = JSON.parse(req.responseText);
            addRegistrations();
        } else {
            // an error occurred
            window.snackbar('Er is iets fout gegaan. Sorry');
        }
    };
    req.send();

    function onRegistrationClick(reg, marker) {
        var content = '<h3>'+(reg.firstName?reg.firstName:'Anoniem')+'</h3>';
        if (reg.selfieUri) {
            content += '<p><img src="' + reg.selfieUri + '" alt="Selfie" /></p>';
        }

        var infowindow = new google.maps.InfoWindow({
            content: content
        });
        infowindow.open(map, marker);
    }


    function addRegistrations() {
        if (map != null && registrations != null) {
            var markers = [];
            var bounds = new google.maps.LatLngBounds();
            registrations.forEach(function (reg) {
                if (reg.location_latitude && reg.location_longitude) {
                    var marker = new google.maps.Marker({
                        position: {lat: reg.location_latitude, lng: reg.location_longitude},
                        title: reg.firstName ? reg.firstName : 'Anoniem'
                    });
                    markers.push(marker);
                    marker.addListener('click', function() {
                        onRegistrationClick(reg, marker);
                    });
                    bounds.extend(marker.position);

                }
            });

            var markerCluster = new MarkerClusterer(map, markers,
                {
                    maxZoom: 10,
                    // gridSize: 10,
                    zoomOnClick: true,
                    minimumClusterSize: 2,
                    imagePath: 'https://raw.githubusercontent.com/googlemaps/js-marker-clusterer/gh-pages/images/m'
                });

            google.maps.event.addListener(map, 'zoom_changed', function() {
                var zoomChangeBoundsListener =
                    google.maps.event.addListener(map, 'bounds_changed', function() {
                        if (this.getZoom() > 13 && this.initialZoom == true) {
                            // Change max/min zoom here
                            this.setZoom(13);
                            this.initialZoom = false;
                        }
                        google.maps.event.removeListener(zoomChangeBoundsListener);
                    });
            });
            map.initialZoom = true;
            map.fitBounds(bounds);
        }
    }

    this.initMap = function() {
        var mapOptions = {
            zoom: 8,
            center: new google.maps.LatLng(51.9814708, 5.1163364),
            scrollwheel: true,
            disableDefaultUI: true,
            styles: [
                {
                    "featureType": "administrative",
                    "elementType": "labels.text.fill",
                    "stylers": [
                        {
                            "color": "#444444"
                        }
                    ]
                },
                {
                    "featureType": "administrative.country",
                    "elementType": "geometry.stroke",
                    "stylers": [
                        {
                            "visibility": "on"
                        },
                        {
                            "saturation": "-1"
                        },
                        {
                            "weight": "1.00"
                        }
                    ]
                },
                {
                    "featureType": "administrative.country",
                    "elementType": "labels",
                    "stylers": [
                        {
                            "visibility": "on"
                        },
                        {
                            "saturation": "0"
                        },
                        {
                            "lightness": "21"
                        },
                        {
                            "gamma": "1.28"
                        },
                        {
                            "weight": "0.95"
                        }
                    ]
                },
                {
                    "featureType": "administrative.country",
                    "elementType": "labels.icon",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "administrative.locality",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "landscape",
                    "elementType": "all",
                    "stylers": [
                        {
                            "color": "#f2f2f2"
                        },
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "landscape",
                    "elementType": "geometry",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "landscape.man_made",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "landscape.natural",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "landscape.natural",
                    "elementType": "geometry",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "landscape.natural",
                    "elementType": "geometry.fill",
                    "stylers": [
                        {
                            "visibility": "on"
                        },
                        {
                            "hue": "#baff00"
                        }
                    ]
                },
                {
                    "featureType": "landscape.natural.landcover",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        },
                        {
                            "weight": "1.17"
                        }
                    ]
                },
                {
                    "featureType": "poi",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "poi.attraction",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "poi.attraction",
                    "elementType": "geometry.fill",
                    "stylers": [
                        {
                            "visibility": "off"
                        },
                        {
                            "hue": "#3500ff"
                        }
                    ]
                },
                {
                    "featureType": "poi.attraction",
                    "elementType": "geometry.stroke",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "poi.business",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "poi.medical",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "poi.park",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "poi.place_of_worship",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "poi.school",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "poi.sports_complex",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "road",
                    "elementType": "all",
                    "stylers": [
                        {
                            "saturation": -100
                        },
                        {
                            "lightness": 45
                        }
                    ]
                },
                {
                    "featureType": "road.highway",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "simplified"
                        }
                    ]
                },
                {
                    "featureType": "road.arterial",
                    "elementType": "labels.icon",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "transit",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "transit",
                    "elementType": "geometry",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "transit.station",
                    "elementType": "all",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "water",
                    "elementType": "all",
                    "stylers": [
                        {
                            "color": "#46bcec"
                        },
                        {
                            "visibility": "on"
                        }
                    ]
                },
                {
                    "featureType": "water",
                    "elementType": "geometry.fill",
                    "stylers": [
                        {
                            "visibility": "on"
                        }
                    ]
                }
            ]
        };

        var mapElement = document.getElementById('map');
        map = new google.maps.Map(mapElement, mapOptions);
    };

    return this;
})();

// for async callback of google
function initMap() {
    map.initMap();
}