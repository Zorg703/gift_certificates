import angular from 'angular';
import footerComponent from './foot.component';

const module = angular.module('app.components.foot', [])
  .component('foot', footerComponent);

export default module.name;
