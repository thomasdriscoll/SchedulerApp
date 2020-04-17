import React, {useState} from 'react';
import { View, Text, StyleSheet, TextInput, KeyboardAvoidingView, Button } from 'react-native';
import { MaterialIcons, MaterialCommunityIcons } from '@expo/vector-icons';
import axios from 'axios';
// import { Permissions, Location } from 'expo';
// import MapView from 'react-native-maps';
// import Geocoder from 'react-native-geocoding';



export default function RegistrationForm4({ setForm, form }) {
    // const [inProgress, setInProgress] = useState(false)
    const [address, setAddress] = useState('Eiffel Tower')
    const [geocode, setGeocode] = useState({})
    // const [error, setError] = useState('')

    // Geocoder.init("")

    // Permissions.askAsync(Permissions.LOCATION);

    const attemptGeocode = async() => {
        // Geocoder.from(address)
        // .then(json => {
        //     const location = json.results[0].geometry.location;
        //     console.log(location)
        //     setGeocode(location)
        // })
        // .catch(error => console.warn(error));

        let res = await axios.get('https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key='')

        console.log(res);
        // https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY
    }

    return (
        <View style={styles.mainCon}>
            {/* <MapView style={styles.mapStyle} /> */}
            <KeyboardAvoidingView style={styles.container} behavior="padding" enabled>
                <View style={styles.subCon}>
                    <View style={styles.prevIcon}>
                        <Text onPress={() => setForm(form - 1)}><MaterialIcons size="35" color="black" name="navigate-next" /></Text>
                    </View>
                    <View style={styles.input}>
                        <MaterialCommunityIcons size="20" color="rgba(255,255,255,0.9)" name="magnify" />
                        <TextInput style={styles.inputText} placeholder="Set your home address" placeholderTextColor="rgba(255,255,255,0.9)" />
                    </View>
                </View>
                <Button title="Geocode" onPress={attemptGeocode}/>
                <View style={styles.nextIcon}><Text onPress={() => setForm(form + 1)}><MaterialIcons size="40" color="white" name="navigate-next" /></Text></View>
            </KeyboardAvoidingView>
        </View>
    )
}

const styles = StyleSheet.create({
    mainCon: {
        flex: 1
    },
    mapStyle: {
        // width: Dimensions.get('window').width,
        // height: Dimensions.get('window').height,
    },
    container: {
        flex: 1,
        backgroundColor: 'white',
        justifyContent: 'space-between',
        paddingHorizontal: '8%',
        paddingVertical: '15%',
        zIndex: 2,
        backgroundColor: 'blue'
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
        backgroundColor: 'rgba(0,0,0,0.2)',
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
        backgroundColor: 'rgba(100,100,100,0.9)',
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

// _getLocationAsync = async () => {
//     let { status } = await Permissions.askAsync(Permissions.LOCATION);
//     if (status !== 'granted') {
//         this.setState({
//             errorMessage: 'Permission to access location was denied',
//         });
//     }
//     let location = await Location.getCurrentPositionAsync({});
//     this.setState({ location });
// };

