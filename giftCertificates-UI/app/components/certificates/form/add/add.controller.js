export default class {
  constructor(certificateService, userService, $state, $rootScope) {
    'ngInject';

    this.state = $state;
    this.certificateService = certificateService;
    this.userService = userService;
    this.$rootScope = $rootScope;
  }

  $onInit() {
    if (this.userService.isAdmin()) {
      this.certificate = {};
    } else {
      this.state.go('certificates');
    }
  }

  save() {
    this.certificateService.save(this.certificate)
      .then(() => {
        this.$rootScope.message = 'operation';
        this.state.go('certificates');
      })
      .catch(response => this.message = response.status);
  }
}
