import angular from 'angular';
import giftCertificatesComponent from './certificates.component';
import giftCertificateService from '../certificates.service';
import userService from '../../user.service';

const module = angular.module('app.components.certificates.certificate-list', [])
  .component('certificates', giftCertificatesComponent)
  .service('giftCertificateService', giftCertificateService)
  .service('userService', userService);
export default module.name;
