import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { FontAwesome } from '@expo/vector-icons';
import { TouchableOpacity } from 'react-native-gesture-handler';


export default function LocationItem({ el, address, setAddress, form, setForm  }) {

    const selectAddress = () => {
        setAddress(el.description)
        setForm(form+1)
    }

    return (
        <TouchableOpacity onPress={selectAddress}>
        <View style={styles.root}>
            <FontAwesome name='map-marker' size={25} color='white' />
            <View style={styles.textCon}>
                <Text style={styles.mainText}>{el.structured_formatting.main_text}</Text>
                <Text style={styles.subText}>{el.structured_formatting.secondary_text}</Text>
            </View>
        </View>
        </TouchableOpacity>
    )
}

const styles = StyleSheet.create({
    root: {
        height: 50,
        borderBottomWidth: StyleSheet.hairlineWidth,
        justifyContent: 'flex-start',
        backgroundColor: 'rgba(255,255,255,0.2)',
        marginBottom: 20,
        flexDirection: 'row',
        paddingHorizontal:'7%',
        alignItems:'center',
        borderRadius:25,
        width:'100%',
    },
    textCon:{
        marginLeft:25,
    },
    mainText: {
        color: 'white'
    },
    subText: {
        color: 'rgba(255,255,255,0.5)'
    }
})