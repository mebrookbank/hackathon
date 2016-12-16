(function () {
    'use strict';

    angular
        .module('mredApp')
        .controller('DeconflictionController', DeconflictionController);

    DeconflictionController.$inject = ['$scope', 'leafletData', '$http'];

    function DeconflictionController($scope, leafletData, $http) {


        $scope.geojson = {};
        $scope.geojson.aircraft = {};
        $scope.geojson.aahorse = {};

        var localAircraft = 'content/data/flights_uk.json';
        var snowflakeAircraft = 'https://api.laminardata.aero/v1/tiles/3/3/1/flights?user_key=';


        var localHorse = 'content/data/horse.json';
        var geoserverHorse = 'http://localhost:8099/geoserver/mred/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=mred:horse_view&maxFeatures=50&outputFormat=application%2Fjson';

        // Get the countries geojson data from a JSON
        $http.get(localAircraft).then(function (aircResp) {
                $http.get(localHorse).then(function (horseResp) {
                    angular.extend($scope.geojson, {
                        aircraft: {
                            data: aircResp.data,
                            resetStyleOnMouseout: true,
                            style: {
                                fillColor: "green",
                                weight: 2,
                                opacity: 1,
                                color: 'white',
                                dashArray: '3',
                                fillOpacity: 0.7
                            },
                            pointToLayer: function (feature, latlng) {
                                return L.circleMarker(latlng);
                            },
                            onEachFeature: function (feature, layer) {
                                // Create get the view template
                                var callsign = feature.properties.callsign == null ? '' : feature.properties.callsign;
                                var flightStatus = feature.properties.flightStatus == null ? '' : feature.properties.flightStatus;
                                var airline = feature.properties.airline == null ? '' : feature.properties.airline;

                                var popupContent = "Callsign: " + callsign + '<br/>Status: ' + flightStatus + '<br/>Airline: ' + airline;

                                // Bind the popup
                                // HACK: I have added the stream in the popup options
                                layer.bindPopup(popupContent, {
                                    closeButton: false,
                                    minWidth: 200,
                                    feature: feature
                                });
                            }
                        },
                        horse: {
                            data: horseResp.data,
                            resetStyleOnMouseout: true,
                            style: {
                                fillColor: "red",
                                weight: 2,
                                opacity: 1,
                                color: 'white',
                                dashArray: '3',
                                fillOpacity: 0.7
                            },
                            pointToLayer: function (feature, latlng) {
                                return L.circleMarker(latlng);
                            },
                            onEachFeature: function (feature, layer) {
                                // Create get the view template
                                var horsename = feature.properties.horsename == null ? '' : feature.properties.horsename;
                                var ownername = feature.properties.ownername == null ? '' : feature.properties.ownername;
                                var phone = feature.properties.phone == null ? '' : feature.properties.phone;

                                var popupContent = "Horse Name: " + horsename + '<br/>Ownername: ' + ownername + '<br/>Phone: ' + phone;

                                // Bind the popup
                                // HACK: I have added the stream in the popup options
                                layer.bindPopup(popupContent, {
                                    closeButton: false,
                                    minWidth: 200,
                                    feature: feature
                                });
                            }
                        }
                    });
                },
                function (response) {
                    console.warn("Failed to get data");
                });
            },
            function (response) {
                console.warn("Failed to get data");
            }
        );
    }
    }

    )();
