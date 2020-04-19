import React from 'react';
import { View, Text, StyleSheet, TextInput, KeyboardAvoidingView } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons';
import { LinearGradient } from 'expo-linear-gradient';


export default function RegistrationForm3({ setForm, form, password, setPassword }) {
    // Keyboard.show()
    const passwordValidator = () => {
        if(password.length>0){
            return <LinearGradient style={styles.nextIcon} colors={['#A12CF5', '#8E29FA', '#8327FC']}><View ><Text onPress={() => setForm(form + 1)}><MaterialIcons size={40} color="white" name="navigate-next" /></Text></View></LinearGradient>
        }
        else{
            return <View style={styles.nextIcon}><Text><MaterialIcons size={40} color="white" name="navigate-next" /></Text></View>
        }
    }

    return (
        <KeyboardAvoidingView style={styles.container} behavior="padding" enabled>
            <View style={styles.prevIcon}><Text onPress={() => setForm(form - 1)}><MaterialIcons size={35} color="white" name="navigate-next" /></Text></View>
            <View style={styles.subCon}>
                <Text style={styles.text}>Choose your password</Text>
                <TextInput 
                style={styles.input} 
                autoFocus={true} 
                keyboardType='default' 
                secureTextEntry={true} 
                placeholder="Your password" 
                selectionColor="white" 
                placeholderTextColor="rgba(255,255,255,0.5)" 
                onChangeText={setPassword}
                value={password}
                />
            </View>
            {passwordValidator()}
        </KeyboardAvoidingView>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'black',
        justifyContent: 'space-between',
        alignItems: 'flex-start',
        paddingHorizontal: '8%',
        paddingVertical:'15%'
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