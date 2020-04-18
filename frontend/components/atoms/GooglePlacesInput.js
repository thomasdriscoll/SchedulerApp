import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { GoogleAutoComplete } from 'react-native-google-autocomplete';

import API_KEY from './../../key';
import { TextInput, ScrollView } from 'react-native-gesture-handler';
import LocationItem from './LocationItem';
import { MaterialIcons } from '@expo/vector-icons';


export default function GooglePlacesInput({ form, setForm }) {
    return (
        <View style={styles.container}>
            <View style={styles.subCon}>
                <View style={styles.prevIcon}><Text onPress={() => setForm(form - 1)}><MaterialIcons size={35} color="white" name="navigate-next" /></Text></View>
                <Text style={styles.text}>Set your Home Address</Text>
            </View>
            <GoogleAutoComplete apiKey={API_KEY} debounce={200} minLength={2}>
                {({ handleTextChange, locationResults }) =>
                    <React.Fragment>
                        {/* {console.log(locationResults)} */}
                        <View style={styles.input}>
                            <TextInput
                                placeholder="Set your home address"
                                style={styles.inputText}
                                onChangeText={handleTextChange}
                                placeholderTextColor='rgba(255,255,255,0.8)'
                                autoFocus={true}
                            />
                        </View>
                        <ScrollView style={styles.resultCon}>
                            {locationResults.map(el => (
                                <LocationItem
                                    description={el.description}
                                    el={el}
                                    key={el.id} />
                            )

                            )}
                        </ScrollView>
                    </React.Fragment>

                }
            </GoogleAutoComplete>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'black',
        alignItems: 'flex-start',
        justifyContent: 'center',
        paddingVertical: '20%',
        paddingHorizontal: '10%'
    },
    subCon:{
        flexDirection:'row',
        width:'100%',
    },
    input: {
        backgroundColor: 'rgba(255,255,255,0.5)',
        width: '100%',
        borderRadius: 30,
        height: 35,
        fontSize: 24,
        // color: 'black',
        // justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: '4%',
        paddingVertical: '0.5%',
        fontSize: 14,
        flexDirection: 'row',
        marginTop: 20,
        marginBottom: 20
    },
    inputText: {
        color: 'rgba(255,255,255,0.8)',
        fontSize: 16,
        paddingHorizontal: '5%'
    },
    text: {
        fontSize: 25,
        color: 'white',
        fontWeight: 'bold',
        width: '100%',
        // backgroundColor:'red'
    },
    prevIcon: {
        justifyContent: 'center',
        alignItems: 'center',
        transform: [{ rotate: '180deg' }],
        position:'relative',
        right:'15%'
    },
    resultCon:{
        width:'100%',
    }
})