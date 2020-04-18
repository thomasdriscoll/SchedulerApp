import React,{useState} from 'react';
import {View,Text, StyleSheet} from 'react-native';
import Button1 from '../atoms/Button1';
import RegistrationForm1 from '../atoms/RegistrationForm1';
import RegistrationForm2 from '../atoms/RegistrationForm2';
import RegistrationForm3 from '../atoms/RegistrationForm3';
import RegistrationForm4 from '../atoms/RegistrationForm4';

export default function Registration({history}){
    const [form,setForm] = useState(0);
    // Keyboard.show()

    if(form==1){
        return <RegistrationForm1 form={form} setForm={setForm}/>
    }
    else if(form==2){
        return <RegistrationForm2 form={form} setForm={setForm}/>
    }
    else if(form==3){
        return <RegistrationForm3 form={form} setForm={setForm}/>
    }
    else if(form==4){
        return <RegistrationForm4 form={form} setForm={setForm}/>
    }
    return( 
        <View style={styles.container}>
            <View style={styles.subCon1}>
                <Text style={styles.header}>Welcome to Schedelta</Text>
                <Text style={styles.text}>A new way of scheduling</Text>
                <Button1 text={`Let's go!`} onPress={()=>setForm(form+1)}/>
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
