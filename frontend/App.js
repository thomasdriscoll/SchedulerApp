import React, { useState } from 'react';
import { View, Text } from 'react-native';
import { NativeRouter } from 'react-router-native';
import Registration from './components/molecules/Registration';
import routes from './routes';
import Navbar from './components/molecules/Navbar';

export default function App() {
  const [loggedIn, setLoggedIn] = useState(false)
  if (!loggedIn) {
    return (
      <Registration/>
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
