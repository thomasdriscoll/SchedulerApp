import React from 'react';
import { StyleSheet, TouchableOpacity, Text, View } from 'react-native';

export default function Button1({ text, onPress }) {
    return (
        <TouchableOpacity onPress={onPress}>
            <View style={styles.button}>
                <Text style={styles.buttonText}>{text}</Text>
            </View>
        </TouchableOpacity>
    )
}

const styles=StyleSheet.create({
    button:{
        backgroundColor:'#A12BF6',
        paddingHorizontal:10,
        paddingVertical:14,        
    },
    buttonText:{
        color:'white',
        fontSize:16,
        textAlign:'center',
        fontWeight:'bold'
    }
})