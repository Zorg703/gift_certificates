import angular from 'angular';
import Certificates from './certificates/certificates/certificates.module';
import signUp from './auth/signup/signup.module';
import login from './auth/login/login.module';
import header from './header/header.module';
import addForm from './certificates/form/add/add.module';
import editForm from './certificates/form/edit/edit.module';
import foot from './footer/foot.module';
import cart from './certificates/shopping-cart/shopping-cart.module'
import 'angular-resource';


const module = angular.module('app.components', [Certificates, signUp, login, header, addForm,cart, editForm, foot, 'ngResource']);

export default module.name;
