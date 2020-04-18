// require.('dotenv').config()

import React, { useState } from 'react';
import { View, Text, StyleSheet, TextInput, KeyboardAvoidingView, Button, Dimensions } from 'react-native';
import { MaterialIcons, MaterialCommunityIcons } from '@expo/vector-icons';
import axios from 'axios';
// import { Permissions, Location } from 'expo';
import MapView from 'react-native-maps';
import Geocoder from 'react-native-geocoding';
import GooglePlacesInput from './GooglePlacesInput';
import {LinearGradient} from 'expo-linear-gradient';
// import { GooglePlacesAutocomplete } from 'react-native-google-places-autocomplete';

export default function RegistrationForm4({ setForm, form }) {
    // const [inProgress, setInProgress] = useState(false)
    const [address, setAddress] = useState('Eiffel Tower')
    const [geocode, setGeocode] = useState({})
    // const [error, setError] = useState('')

    Geocoder.init()

    // Permissions.askAsync(Permissions.LOCATION);

    const attemptGeocode = async () => {
        Geocoder.from(address)
            .then(json => {
                const location = json.results[0].geometry.location;
                console.log(location)
                setGeocode(location)
            })
            .catch(error => console.warn(error));
        // https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY
    }

    return (
        <View style={styles.mainCon}>
            <MapView
                provider={MapView.PROVIDER_GOOGLE}
                initialRegion={{
                    latitude: 51.524300,
                    longitude: -0.037790,
                    latitudeDelta: 0.01,
                    longitudeDelta: 0.01,

                }}
                customMapStyle={customMapStyle}
                showCompass={true}
                rotateEnabled={true}
                showUserLocation={false}
                style={styles.mapStyle} >

                <MapView.Marker
                    coordinate={{
                        latitude: 51.524300,
                        longitude: -0.037790,
                    }}
                />
            </MapView>

            <KeyboardAvoidingView style={styles.container} behavior="padding" enabled>
                <View style={styles.subCon}>
                    <View style={styles.prevIcon}>
                        <Text onPress={() => setForm(form - 1)}><MaterialIcons size={35} color="white" name="navigate-next" /></Text>
                    </View>
                    <View style={styles.input}>
                        <MaterialCommunityIcons size={20} color="rgba(255,255,255,0.9)" name="magnify" />
                        <TextInput style={styles.inputText} placeholder="Set your home address" placeholderTextColor="rgba(255,255,255,0.9)" />
                        {/* <GooglePlacesInput/> */}
                    </View>
                </View>
                {/* <Button title="Geocode" onPress={attemptGeocode} /> */}
                <LinearGradient style={styles.nextIcon} colors={['#A12CF5','#8E29FA', '#8327FC']} disabled><Text onPress={() => setForm(form + 1)}><MaterialIcons size={40} color="white" name="navigate-next" /></Text></LinearGradient>
            </KeyboardAvoidingView>
        </View>
    )
}

const styles = StyleSheet.create({
    mainCon: {
        flex: 1
    },
    mapStyle: {
        flex:1,
        width: Dimensions.get('window').width,
        height: Dimensions.get('window').height,
        position: "absolute",
        // top:0,
    },
    container: {
        flex: 1,
        backgroundColor: 'white',
        justifyContent: 'space-between',
        paddingHorizontal: '8%',
        paddingVertical: '15%',
        zIndex: 2,
        backgroundColor: 'rgba(0,0,0,0)'
    },
    subCon: {
        flexDirection: 'row',
        width: '100%',
        justifyContent: 'center'
        // backgroundColor:'red'
    },
    text: {
        fontSize: 28,
        color: 'white',
        fontWeight: 'bold',
        width: '100%',
        // backgroundColor:'red'
    },
    input: {
        backgroundColor: 'rgba(255,255,255,0.5)',
        width: '90%',
        borderRadius: 30,
        height: 35,
        fontSize: 24,
        color: 'white',
        // justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: '4%',
        paddingVertical: '0.5%',
        fontSize: 14,
        flexDirection: 'row'
    },
    inputText: {
        color: 'white',
        fontSize: 16,
        paddingHorizontal: '5%'
    },
    nextIcon: {
        // backgroundColor: 'linear-gradient(to right, #8e2de2, #4a00e0);',
        width: 60,
        height: 60,
        borderRadius: 30,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 0,
        position: 'relative',
        left: '80%'
    },
    prevIcon: {
        justifyContent: 'center',
        alignItems: 'center',
        transform: [{ rotate: '180deg' }],
        width: '10%'
    }
})

const customMapStyle= [
    {
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#212121"
        }
      ]
    },
    {
      "elementType": "labels.icon",
      "stylers": [
        {
          "visibility": "off"
        }
      ]
    },
    {
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#757575"
        }
      ]
    },
    {
      "elementType": "labels.text.stroke",
      "stylers": [
        {
          "color": "#212121"
        }
      ]
    },
    {
      "featureType": "administrative",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#757575"
        }
      ]
    },
    {
      "featureType": "administrative.country",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#9e9e9e"
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
      "featureType": "administrative.locality",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#bdbdbd"
        }
      ]
    },
    {
      "featureType": "poi",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#757575"
        }
      ]
    },
    {
      "featureType": "poi.park",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#181818"
        }
      ]
    },
    {
      "featureType": "poi.park",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#616161"
        }
      ]
    },
    {
      "featureType": "poi.park",
      "elementType": "labels.text.stroke",
      "stylers": [
        {
          "color": "#1b1b1b"
        }
      ]
    },
    {
      "featureType": "road",
      "elementType": "geometry.fill",
      "stylers": [
        {
          "color": "#2c2c2c"
        }
      ]
    },
    {
      "featureType": "road",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#8a8a8a"
        }
      ]
    },
    {
      "featureType": "road.arterial",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#373737"
        }
      ]
    },
    {
      "featureType": "road.highway",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#3c3c3c"
        }
      ]
    },
    {
      "featureType": "road.highway.controlled_access",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#4e4e4e"
        }
      ]
    },
    {
      "featureType": "road.local",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#616161"
        }
      ]
    },
    {
      "featureType": "transit",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#757575"
        }
      ]
    },
    {
      "featureType": "water",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#000000"
        }
      ]
    },
    {
      "featureType": "water",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#3d3d3d"
        }
      ]
    }
  ]

