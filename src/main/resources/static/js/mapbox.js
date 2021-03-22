// mapboxgl.accessToken = mapboxToken;

let mapOptions = {
    accessToken: mapboxToken,
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11', // stylesheet location
    center: [-98.4916, 29.4252], // starting position [lng, lat]
    zoom: 10 // starting zoom
}

let map = new mapboxgl.Map(mapOptions);



//GEOLOCATE
let geolocate = new mapboxgl.GeolocateControl({
    positionOptions: {
        enableHighAccuracy: true
    },
    trackUserLocation: false
});
map.addControl(geolocate);
map.on('load', function() {
    geolocate.trigger();
});

//GEOCODE
var geocoder = new MapboxGeocoder({
    accessToken: mapboxToken,
    marker: {
        color: 'blue',
        draggable: 'false'
    },
    mapboxgl: mapboxgl
});

document.getElementById('geocoder').appendChild(geocoder.onAdd(map));

geocoder.on("result", e => {
    console.log(e.result);
    console.log(e.result.place_name);
    $("#burialaddress").val(e.result.place_name);
})

// let map2 = new mapboxgl.Map(map2Options);
//
// let burialMarkerOptions = {
//     color: "blue",
//     draggable: false
// }
//
// let burialMarker = new mapboxgl.Marker(burialMarkerOptions)
//     .setLngLat(geocode($("#burialaddress").innerText ,mapboxToken))
//     .addTo(map2);

let path = 'https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/' + geocode($("#burialaddress").val() ,mapboxToken) + '/500x300?access_token=' + mapboxToken  + 'alt=""';

$("#staticmap").attr("src", path);

//Pass in an address, returns coordinates
function geocode(search, token) {
    let baseUrl = 'https://api.mapbox.com';
    let endPoint = '/geocoding/v5/mapbox.places/';
    return fetch(baseUrl + endPoint + encodeURIComponent(search) + '.json' + "?" + 'access_token=' + token)
        .then(function(res) {
            return res.json();
            // to get all the data from the request, comment out the following three lines...
        }).then(function(data) {
            console.log(data.features[0])
            return data.features[0].center;
        });
}

//Pass in coordinates, returns address
function reverseGeocode(coordinates, token) {
    var baseUrl = 'https://api.mapbox.com';
    var endPoint = '/geocoding/v5/mapbox.places/';
    return fetch(baseUrl + endPoint + coordinates.lng + "," + coordinates.lat + '.json' + "?" + 'access_token=' + token)
        .then(function(res) {
            return res.json();
        })
        // to get all the data from the request, comment out the following three lines...
        .then(function(data) {
            return data.features[0].place_name;
        });
}

// geocode(echo.restingPlace, mapboxToken).then(function(coordinates){
//     new mapboxgl.Marker(burialMarkerOptions)
//         .setLngLat(coordinates)
//         .addTo(map2);
// })
//
// reverseGeocode(whateverTheCoordinatesAre, mapboxToken).then(function(address){
//     $(".burial-address-display").text(address);
// })



