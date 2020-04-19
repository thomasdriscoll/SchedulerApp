import React, { useState } from 'react';
import {View,Text} from 'react-native';
import RegistrationForm2 from './../atoms/RegistrationForm2';
import PasswordForm2 from './../atoms/PasswordForm2';

export default function Login({setRenderLogin}){

    const [form,setForm] = useState(1);
    const [email,setEmail] = useState('');
    const [password,setPassword] = useState('');

    if(form==0){
        setRenderLogin(false)
    }
    else if(form==1){
        return <RegistrationForm2 form={form} setForm={setForm} email={email} setEmail={setEmail}/>
    }
    else if(form==2){
        return <PasswordForm2 form={form} setForm={setForm} password={password} setPassword={setPassword}/>
    }

    return(
        <View>
            <Text>Login</Text>
        </View>
    )
}