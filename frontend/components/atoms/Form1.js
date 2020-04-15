import React from 'react';
import { View, Text, StyleSheet, TextInput,Keyboard, KeyboardAvoidingView } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons';

export default function Form1({setForm,form}) {
    // Keyboard.show()
    return (
        <KeyboardAvoidingView style={styles.container} behavior="padding" enabled>
            <Text style={styles.text}>What's your name?</Text>
            <TextInput style={styles.input} keyboardType="name" placeholder="Your name" selectionColor="white" placeholderTextColor="rgba(255,255,255,0.5)"/>
            <Text style={styles.nextIcon} onPress={()=>setForm(form+1)}><MaterialIcons size="40" color="white" name="navigate-next"/></Text>
        </KeyboardAvoidingView>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'black',
        justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal:'10%'
    },
    text:{
        fontSize:28,
        color:'white',
        fontWeight:'bold',
        width:'100%',
        // backgroundColor:'red'
    },
    input: {
        marginTop:20,
        height:40,
        width:'100%',
        height:45,
        borderBottomColor:'rgba(100,100,100,0.8)',
        borderBottomWidth:0.3,
        fontSize:28,
        color:'white',
        paddingRight:'13%'        
    },
    nextIcon:{
        backgroundColor:'rgba(100,100,100,0.5)',
        position:"relative",
        bottom:45,
        left:'44%'
    }
})