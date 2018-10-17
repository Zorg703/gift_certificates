import angular from 'angular';
import headerComponent from './header.component';
import userService from '../user.service';

const module = angular.module('app.components.header', [])
  .component('header', headerComponent)
  .service('userService', userService);

export default module.name;
