import angular from 'angular';
import signupComponent from './signup.component';

import userService from '../../user.service';

const module = angular.module('app.components.signup', [])
  .component('signup', signupComponent)
  .service('userService', userService);

export default module.name;
