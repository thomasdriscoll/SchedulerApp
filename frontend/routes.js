import React from 'react';
import {Route, Switch} from 'react-router-native';
import GetTask from './components/molecules/GetTask';
import CreateTask from './components/molecules/CreateTask';
import Home from './components/molecules/Home';

export default(
    <Switch>
      <Route path='/getTask' component={GetTask}/>
      <Route path='/createTask' component={CreateTask}/>
      <Route path='/' component={Home}/>
    </Switch>
)