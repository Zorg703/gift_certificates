
export default class {
  constructor(userService, $translate, $state, $location) {
    'ngInject';

    this.location = $location;
    this.state = $state;
    this.userService = userService;
    this.translate = $translate;
  }

  $onInit() {
    this.user = this.userService.getUser();
  }

  isAuthenticated() {
    return this.userService.isAuthenticated();
  }

  logout() {
    this.userService.logout();
  }

  isAdmin() {
    return this.userService.isAdmin();
  }

  changeLanguage(langKey) {
    this.translate.use(langKey);
  }

  add() {
    localStorage.setItem('url', JSON.stringify(this.location.search()));
    this.state.go('add');
  }
}
