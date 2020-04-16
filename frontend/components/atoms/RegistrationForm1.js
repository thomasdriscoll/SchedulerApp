import React from 'react';
import { View, Text, StyleSheet, TextInput, KeyboardAvoidingView } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons';

export default function RegistrationForm1({ setForm, form }) {
    // Keyboard.show()
    return (
        <KeyboardAvoidingView style={styles.container} behavior="padding" enabled>
            <View style={styles.prevIcon}><Text onPress={() => setForm(form - 1)}><MaterialIcons size="35" color="white" name="navigate-next" /></Text></View>
            <View style={styles.subCon}>
                <Text style={styles.text}>What's your name?</Text>
                <TextInput style={styles.input} autoFocus={true} keyboardType='default' placeholder="Your name" selectionColor="white" placeholderTextColor="rgba(255,255,255,0.5)" />
            </View>
            <View style={styles.nextIcon}><Text onPress={() => setForm(form + 1)}><MaterialIcons size="40" color="white" name="navigate-next" /></Text></View>
        </KeyboardAvoidingView>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'black',
        justifyContent: 'space-between',
        alignItems: 'flex-start',
        paddingHorizontal: '10%',
        paddingVertical:'10%'
    },
    subCon: {
        // backgroundColor: 'red',
        width: '100%',
    },
    text: {
        fontSize: 28,
        color: 'white',
        fontWeight: 'bold',
        width: '100%',
        // backgroundColor:'red'
    },
    input: {
        marginTop: 20,
        height: 40,
        width: '100%',
        height: 45,
        borderBottomColor: 'rgba(100,100,100,0.8)',
        borderBottomWidth: 0.3,
        fontSize: 28,
        color: 'white',
        paddingRight: '13%'
    },
    nextIcon: {
        backgroundColor: 'rgba(100,100,100,0.9)',
        width: 60,
        height: 60,
        borderRadius: 30,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 0,
        position:'relative',
        left:'80%'
    },
    prevIcon: {
        justifyContent: 'center',
        alignItems: 'center',
        transform: [{ rotate: '180deg' }],
        
    }
})