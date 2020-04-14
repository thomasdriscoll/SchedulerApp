import React from 'react';
import {View,Text, StyleSheet} from 'react-native';
import Button1 from '../atoms/Button1';

export default function Registration({history}){
    return(
        <View style={styles.container}>
            <View style={styles.subCon1}>
                <Text style={styles.header}>Welcome to Schedelta</Text>
                <Text style={styles.text}>A new way of scheduling</Text>
                <Button1 text={`Let's go!`} onPress={()=>console.log('button hit')}/>
            </View>
            <View style={styles.subCon2}>
                <Text style={styles.signInRedirect} onPress={()=>console.log('sign redirect hit')}>Already have a Schedelta account? Sign in</Text>
            </View>
        </View>
    )
}

const styles=StyleSheet.create({
    container:{
        flex:1,
        backgroundColor:'black',
        justifyContent:'center',
        alignItems:'center'
    },
    subCon2:{
        position:"absolute",
        bottom:60
    },
    header:{
        color:'white',
        fontSize:25,
        textAlign:'center',
    },
    text:{
        color:'#cfcfcf',
        fontSize:18,
        textAlign:'center',
        marginTop:20,
        marginBottom:20,
    },
    signInRedirect:{
        fontSize:14,
        color:'#bdbdbd'
    }
})
