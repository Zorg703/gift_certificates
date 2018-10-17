import angular from 'angular';
import uiRouter from '@uirouter/angularjs';
import LocalStorageModule from 'angular-local-storage';
import 'ng-tags-input';
import 'ng-tags-input/build/ng-tags-input';
import 'angular-animate';
import 'angular-sanitize';
import 'angular-ui-bootstrap';
import 'bootstrap';
import 'ng-tags-input/build/ng-tags-input.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import translate from 'angular-translate';
import Components from './components/component.module';
import AppComponent from './app.component';
import locale_en from './components/resources/translation/en';
import locale_ru from './components/resources/translation/ru';

angular
  .module('app', [Components, uiRouter, LocalStorageModule, translate, 'ngTagsInput', 'ngAnimate', 'ngSanitize', 'ui.bootstrap'])
  .component('app', AppComponent)
  .config(($stateProvider, $locationProvider, $urlRouterProvider, $translateProvider) => {
    $translateProvider.translations('en', locale_en)
      .translations('ru', locale_ru)
      .preferredLanguage('en')
      .registerAvailableLanguageKeys(['en', 'ru']);
    $stateProvider
      .state('login', {
        url: '/login',
        template: '<header></header><login></login><foot></foot>',
      })
      .state('certificates', {
        url: '/certificates',

        template: '<header></header><certificates></certificates><foot></foot>',
      })
      .state('signup', {
        url: '/signup',
        template: '<header></header><signup></signup><foot></foot>',
      })
      .state('add', {
        url: '/certificates/add',
        template: '<header></header><add></add><foot></foot>',
      })
      .state('my', {
        url: '/certificates/users',
        template: '<header></header><certificates></certificates><foot></foot>',
      })
      .state('edit', {
        url: '/certificates/edit/:id',
        template: '<header></header><edit></edit><foot></foot>',
      })
		.state('cart',{
			url: '/shopping-cart',
			template: '<header></header><cart></cart><foot></foot>',
        });
    $urlRouterProvider.otherwise('/login');
  });
