import angular from 'angular';
import addComponent from './add.component';
import certificateService from '../../certificates.service';

import './add.component.scss';
import userService from '../../../user.service';

const module = angular.module('app.components.certificates.form.add', [])
  .component('add', addComponent)
  .service('certificateService', certificateService)
  .service('userService', userService);

export default module.name;
