import React, { useState } from 'react';
import { View, Text } from 'react-native';
import { NativeRouter } from 'react-router-native';
import Registration from './components/molecules/Registration';
import routes from './routes';
import Navbar from './components/molecules/Navbar';
import Login from './components/molecules/Login';

export default function App() {
  const [loggedIn, setLoggedIn] = useState(false)
  const [renderLogin,setRenderLogin] = useState(false)

  if (!loggedIn) {
    if(renderLogin){
      return <Login setRenderLogin={setRenderLogin}/>
    }
    return (
      <Registration setRenderLogin={setRenderLogin}/>
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
