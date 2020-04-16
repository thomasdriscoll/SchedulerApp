import React from 'react';
import { View, Text, StyleSheet, TextInput, KeyboardAvoidingView } from 'react-native';
import { MaterialIcons, MaterialCommunityIcons } from '@expo/vector-icons';

// home

// Entypo

export default function RegistrationForm4({ setForm, form }) {
    // const [homeInput,] = useState(null);
    // const [errorMessage,setErrorMessage] = useState(null); 

    return (
        <View style={styles.mainCon}>
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
                <View style={styles.nextIcon}><Text onPress={() => setForm(form + 1)}><MaterialIcons size="40" color="white" name="navigate-next" /></Text></View>
            </KeyboardAvoidingView>
        </View>
    )
}

const styles = StyleSheet.create({
    mainCon:{
        flex:1
    },
    container: {
        flex: 1,
        backgroundColor: 'white',
        justifyContent: 'space-between',
        paddingHorizontal: '8%',
        paddingVertical: '15%',
        zIndex:2,
        backgroundColor:'red'
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

