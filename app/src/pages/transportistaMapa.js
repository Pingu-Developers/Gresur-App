import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import Topbar from '../components/Topbar';

import tt from '@tomtom-international/web-sdk-maps';
import { isMobileOrTablet } from '../maps/mobile-or-tablet';
import { Foldable } from '../maps/foldable';
import { InfoHint } from '../maps/info-hint';
import { handleEnterSubmit } from '../maps/searchbox-enter-submit';



export class transportistaMapa extends Component {
    render() {
        return (
            <div id='map' class='map'>
                <div id='foldable' class='tt-overlay-panel -left-top -medium js-foldable'>
                    <div id='form'>
                        <div id='searchBoxesPlaceholder' class='tt-form-label'></div>
                    </div>
                </div>
            </div>
        )
    }


    componentDidMount() {
        var MAX_ROUTING_INPUTS = 7;
        var map = tt.map({
            key: '941pdxoaNpqiTQwN3TPfKUveJoHkA1sg',
            container: 'map',
            style: 'tomtom://vector/1/basic-main',
            dragPan: !isMobileOrTablet
        });
        map.addControl(new tt.FullscreenControl());
        map.addControl(new tt.NavigationControl());
        new Foldable('#foldable', 'top-right');
        var routingInputs = [];
        var route;
        var markers = [];
        var errorHint = new InfoHint('error', 'bottom-center', 5000).addTo(document.getElementById('map'));
       var loadingHint = new InfoHint('info', 'bottom-center').addTo(document.getElementById('map'));
        function RoutingService() { }
        RoutingService.prototype.calculateRoute = function () {
            this.clearMarkers();
            if (route) {
                map.removeLayer('route');
                map.removeSource('route');
                route = undefined;
            }
            var locations = this.getLocations();
            this.drawMarkers(locations.arr);
            if (locations.count < 2) {
                return;
            }
            loadingHint.setMessage('Loading...');
            tt.services.calculateRoute({
                key: '941pdxoaNpqiTQwN3TPfKUveJoHkA1sg',
                traffic: false,
                locations: locations.str
            })
                .go()
                .then(function (response) {
                    loadingHint.hide();
                    this.clearMarkers();
                    var geojson = response.toGeoJson();
                    route = map.addLayer({
                        'id': 'route',
                        'type': 'line',
                        'source': {
                            'type': 'geojson',
                            'data': geojson
                        },
                        'paint': {
                            'line-color': '#2faaff',
                            'line-width': 6
                        }
                    }, this.findFirstBuildingLayerId());
                    var coordinates = geojson.features[0].geometry.coordinates;
                    if (geojson.features[0].properties.segmentSummary.length > 1) {
                        coordinates = [].concat.apply([], coordinates);
                    }
                    this.updateRoutesBounds(coordinates);
                    this.drawMarkers(locations.arr);
                }.bind(this))
                .catch(function (error) {
                    loadingHint.hide();
                    errorHint.setErrorMessage(error);
                });
        };
        RoutingService.prototype.clearMarkers = function () {
            markers.forEach(function (marker) {
                marker.remove();
            });
        };
        RoutingService.prototype.drawMarkers = function (locations) {
            var bounds = new tt.LngLatBounds();
            var maxIndex = routingInputs.length - 1;
            if (locations.length === 0) {
                return;
            }
            routingInputs.forEach(function (input, index) {
                if (input.position) {
                    var marker = new tt.Marker(this.waypointMarker(index, maxIndex)).setLngLat(input.position).addTo(map);
                    markers.push(marker);
                    bounds.extend(tt.LngLat.convert(input.position));
                    if (input.viewport) {
                        input.viewport.forEach(function (viewport) {
                            bounds.extend(tt.LngLat.convert(viewport));
                        });
                    }
                }
            }, this);
            map.fitBounds(bounds, { duration: 0, padding: 100 });
        };
        RoutingService.prototype.findFirstBuildingLayerId = function () {
            var layers = map.getStyle().layers;
            for (var index in layers) {
                if (layers[index].type === 'fill-extrusion') {
                    return layers[index].id;
                }
            }
            throw new Error('Map style does not contain any layer with fill-extrusion type.');
        };
        RoutingService.prototype.getLocations = function () {
            var resultStr = '';
            var resultArr = [];
            var count = 0;
            routingInputs.forEach(function (routingInput) {
                if (routingInput.position) {
                    count += 1;
                    resultArr.push(routingInput.position);
                    resultStr += routingInput.position.lng + ',' + routingInput.position.lat + ':';
                }
            });
            resultStr = resultStr.substring(0, resultStr.length - 1);
            return {
                str: resultStr,
                count: count,
                arr: resultArr
            };
        };
        RoutingService.prototype.waypointMarker = function (index, total) {
            var container = document.createElement('div');
            container.className = 'waypoint-marker';
            if (index === 0) {
                container.className += ' tt-icon -start -white';
            } else if (index === total) {
                container.className += ' tt-icon -finish -white';
            } else {
                var number = document.createElement('div');
                number.innerText = index;
                container.appendChild(number);
            }
            return container;
        };
        RoutingService.prototype.updateRoutesBounds = function (coordinates) {
            var bounds = new tt.LngLatBounds();
            coordinates.forEach(function (point) {
                bounds.extend(tt.LngLat.convert(point));
            });
            if (!bounds.isEmpty()) {
                map.fitBounds(bounds, { duration: 0, padding: 100 });
            }
        };
        function RoutingInput(options) {
            this.index = options.index;
            this.routingService = options.routingService;
            this.onRemoveBtnClick = options.onRemove.bind(this);
            this.container = this.createContainer();
            this.searchBox = this.createSearchBox();
            this.icon = this.createIconContainer();
            this.removeButton = this.createRemoveButton();
            this.container.appendChild(this.icon);
            this.container.appendChild(this.searchBox);
            this.container.appendChild(this.removeButton);
        }
        RoutingInput.prototype.createContainer = function () {
            var container = document.createElement('div');
            container.className = 'route-input-container';
            return container;
        };
        RoutingInput.prototype.createSearchBox = function () {
            var searchBox = new tt.plugins.SearchBox(tt.services, {
                showSearchButton: false,
                searchOptions: {
                    key: '941pdxoaNpqiTQwN3TPfKUveJoHkA1sg'
                },
                labels: {
                    placeholder: 'Query e.g. Washington'
                }
            });
            var htmlSearchBox = searchBox.getSearchBoxHTML();
            document.getElementById('searchBoxesPlaceholder').appendChild(htmlSearchBox);
            searchBox.on('tomtom.searchbox.resultscleared', this.onResultCleared.bind(this));
            searchBox.on('tomtom.searchbox.resultsfound', function (event) {
                handleEnterSubmit(event, this.onResultSelected.bind(this), errorHint);
            }.bind(this));
            searchBox.on('tomtom.searchbox.resultselected', function (event) {
                if (event.data && event.data.result) {
                    this.onResultSelected(event.data.result);
                }
            }.bind(this));
            return htmlSearchBox;
        };
        RoutingInput.prototype.getIconType = function () {
            var lastIdx = routingInputs.length - 1;
            switch (this.index) {
                case 0:
                    return 'start';
                case lastIdx:
                    return 'finish';
                default:
                    return 'number';
            }
        };
        RoutingInput.prototype.getIconClassName = function (iconType) {
            switch (iconType) {
                case 'start':
                    return 'tt-icon tt-icon-size icon-spacing-right -start';
                case 'finish':
                    return 'tt-icon tt-icon-size icon-spacing-right -finish';
                case 'number':
                    return 'tt-icon-number tt-icon-size icon-spacing-right icon-number';
            }
        };
        RoutingInput.prototype.createRemoveButton = function () {
            var button = document.createElement('button');
            button.className = 'tt-icon icon-spacing-left remove-btn -trash';
            button.onclick = function (event) {
                event.preventDefault();
                this.container.parentNode.removeChild(this.container);
                routingInputs.splice(this.index, 1);
                this.onRemoveBtnClick();
            }.bind(this);
            return button;
        };
        RoutingInput.prototype.createIconContainer = function () {
            var icon = document.createElement('div');
            return icon;
        };
        RoutingInput.prototype.updateIcons = function () {
            var icon = document.createElement('div');
            var iconType = this.getIconType();
            icon.className = this.getIconClassName(iconType);
            if (iconType === 'number') {
                var number = document.createElement('div');
                number.innerText = this.index;
                icon.appendChild(number);
            }
            this.container.replaceChild(icon, this.icon);
            this.icon = icon;
            if (routingInputs.length <= 2) {
                this.removeButton.classList.add('hidden');
            } else {
                this.removeButton.classList.remove('hidden');
            }
        };
        RoutingInput.prototype.onResultCleared = function () {
            this.position = undefined;
            this.viewport = undefined;
            this.routingService.calculateRoute();
        };
        RoutingInput.prototype.onResultSelected = function (result) {
            this.position = result.position;
            this.viewport = [result.viewport.topLeftPoint, result.viewport.btmRightPoint];
            this.routingService.calculateRoute();
        };
        function Panel(routingService) {
            this.routingService = routingService;
            this.container = document.getElementById('form');
            this.createInput();
            this.createInput();
            this.createAddButton();
        }
        Panel.prototype.createWaypoint = function () {
            var length = routingInputs.length;
            if (length === MAX_ROUTING_INPUTS) {
                errorHint.setMessage('You cannot add more waypoints in this example, but ' +
                    'the Routing service supports up to 150 waypoints.');
                return;
            }
            var index = length - 1;
            var routingInput = this.createRoutingInput(index);
            this.container.insertBefore(routingInput.container, routingInputs[length - 1].container);
            routingInputs.splice(index, 0, routingInput);
            this.updateRoutingInputIndexes();
            this.updateRoutingInputIcons();
        };
        Panel.prototype.createRoutingInput = function (index) {
            return new RoutingInput({
                index: index,
                onRemove: this.onRemoveBtnClick.bind(this),
                routingService: this.routingService
            });
        };
        Panel.prototype.createInput = function () {
            var index = routingInputs.length;
            var routingInput = this.createRoutingInput(index);
            this.container.appendChild(routingInput.container);
            routingInputs.push(routingInput);
            routingInput.updateIcons();
        };
        Panel.prototype.createAddButton = function () {
            var button = document.createElement('button');
            button.appendChild(document.createTextNode('ADD STOP'));
            button.className = 'tt-button -primary add-stop-btn';
            button.onclick = function (event) {
                event.preventDefault();
                this.createWaypoint();
            }.bind(this);
            this.container.appendChild(button);
        };
        Panel.prototype.onRemoveBtnClick = function () {
            this.updateRoutingInputIndexes();
            this.updateRoutingInputIcons();
            this.routingService.calculateRoute();
        };
        Panel.prototype.updateRoutingInputIndexes = function () {
            routingInputs.forEach(function (routingInput, index) {
                routingInput.index = index;
            });
        };
        Panel.prototype.updateRoutingInputIcons = function () {
            routingInputs.forEach(function (routingInput) {
                routingInput.updateIcons();
            });
        };
        var routingService = new RoutingService();
        map.on('load', function () {
            routingService.calculateRoute();
        });
        new Panel(routingService);


    }
}


export default transportistaMapa



