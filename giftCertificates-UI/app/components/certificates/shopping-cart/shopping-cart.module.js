import angular from 'angular';
import cartComponent from './shopping-cart.component';
import userService from '../../user.service';
import certificateService from '../certificates.service';


const module = angular.module('app.components.certificates.shopping-cart', [])
  .component('cart', cartComponent)
  .service('userService', userService)
  .service(certificateService, certificateService);

export default module.name;
