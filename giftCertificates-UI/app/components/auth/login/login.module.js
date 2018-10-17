import angular from 'angular';
import loginComponent from './login.component';
import userService from '../../user.service';

const module = angular.module('app.components.login', [])
  .component('login', loginComponent)
  .service('userservice', userService);

export default module.name;
