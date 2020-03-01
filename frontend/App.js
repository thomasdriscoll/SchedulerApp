import * as React from 'react';
import 'react-native-gesture-handler';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import { GetTask} from './components/molecules/getTask';
import { CreateTask } from './components/molecules/createTask';

const Stack = createStackNavigator();

global.position = 0;

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="GetTask">
        <Stack.Screen 
          name = "GetTask"
          component = {GetTask}
          options = {{}}
        />
        <Stack.Screen
          name = "CreateTask"
          component = {CreateTask}
          options = {{}}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}