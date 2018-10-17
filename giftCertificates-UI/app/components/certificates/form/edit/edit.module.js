import angular from 'angular';
import editComponent from './edit.component';
import certificateService from '../../certificates.service';
import userService from '../../../user.service';

const module = angular.module('app.components.certificates.form.edit', [])
  .component('edit', editComponent)
  .service('certificateService', certificateService)
  .service('userService', userService);

export default module.name;
