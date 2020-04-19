import React, { useState } from 'react';
import { View, Text } from 'react-native';
import { NativeRouter } from 'react-router-native';
import Registration from './components/molecules/Registration';
import routes from './routes';
import Navbar from './components/molecules/Navbar';
import Login from './components/molecules/Login';

export default function App() {
  const [loggedIn, setLoggedIn] = useState(false)
  const [renderLogin, setRenderLogin] = useState(false)

  //add code here to check if the user is logged in and set a boolean value for loggedIn

  const login = (email,password) => {
    //axios post request
    //setLoggedIn 
  }

  const register = (name,email,password,geocode) => {
    //axios post request
    //setLoggedIn 
  }

  const logout = () => {
    //axios post request
    //setLoggedIn 
  }

  if (!loggedIn) {
    if (renderLogin) {
      return <Login setRenderLogin={setRenderLogin} login={login} />
    }
    return (
      <Registration setRenderLogin={setRenderLogin} register={register} />
    )
  }
  return (
    <NativeRouter>
      <View>
        {routes}
        <Navbar />
      </View>
    </NativeRouter>
  )
}
