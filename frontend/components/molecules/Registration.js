import React, { useState } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import Button1 from '../atoms/Button1';
import Geocoder from 'react-native-geocoding';
import API_KEY from './../../key';
import RegistrationForm1 from '../atoms/RegistrationForm1';
import RegistrationForm2 from '../atoms/RegistrationForm2';
import RegistrationForm3 from '../atoms/RegistrationForm3';
import RegistrationForm4 from '../atoms/RegistrationForm4';
import GooglePlacesInput from '../atoms/GooglePlacesInput';

export default function Registration({ setRenderLogin, loggedIn, setLoggedIn, register }) {
    const [form, setForm] = useState(0);
    const [name,setName] = useState('');
    const [email,setEmail] = useState('');
    const [password,setPassword] = useState('');
    const [address, setAddress] = useState('');
    const [geocode, setGeocode] = useState({});
    
    // Keyboard.show()

    if (form == 1) {
        return <RegistrationForm1 form={form} setForm={setForm} name={name} setName={setName}/>
    }
    else if (form == 2) {
        return <RegistrationForm2 form={form} setForm={setForm} email={email} setEmail={setEmail}/>
    }
    else if (form == 3) {
        return <RegistrationForm3 form={form} setForm={setForm} password={password} setPassword={setPassword}/>
    }
    else if (form == 4) {
        return <GooglePlacesInput form={form} setForm={setForm} address={address} setAddress={setAddress} loggedIn={loggedIn} setLoggedIn={setLoggedIn} />
    }
    else if (form == 5) {

        if (geocode.lat) {
            return (
                <RegistrationForm4 form={form} setForm={setForm} address={address} geocode={geocode} name={name} email={email} password={password}/>
            )
        }

        else {
            Geocoder.init(API_KEY);
            Geocoder.from(address)
                .then(json => {
                    setGeocode(json.results[0].geometry.location);
                })
                .catch(error => console.warn(error));
            return <View></View>
        }


    }
    return (
        <View style={styles.container}>
            <View style={styles.subCon1}>
                <Text style={styles.header}>Welcome to Schedelta</Text>
                <Text style={styles.text}>A new way of scheduling</Text>
                <Button1 text={`Let's go!`} onPress={() => setForm(form + 1)} />
            </View>
            <View style={styles.subCon2}>
                <Text style={styles.signInRedirect} onPress={() => setRenderLogin(true)}>Already have a Schedelta account? Sign in</Text>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'black',
        justifyContent: 'center',
        alignItems: 'center',
    },
    subCon2: {
        position: "absolute",
        bottom: 60
    },
    header: {
        color: 'white',
        fontSize: 25,
        textAlign: 'center',
    },
    text: {
        color: '#cfcfcf',
        fontSize: 18,
        textAlign: 'center',
        marginTop: 20,
        marginBottom: 20,
    },
    signInRedirect: {
        fontSize: 14,
        color: '#bdbdbd'
    }
})
