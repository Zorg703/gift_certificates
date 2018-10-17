export default class {
  constructor(certificateService, userService,	$stateParams, $state, $rootScope) {
    'ngInject';

    this.stateParams = $stateParams;
    this.certificateService = certificateService;
    this.state = $state;
    this.userService = userService;
    this.$rootScope = $rootScope;
  }

  $onInit() {
    if (this.userService.isAdmin()) {
      this.certificateService.getCertificateById(this.stateParams.id)
        .then(response => this.certificate = response);
    } else {
      this.state.go('certificates');
    }
  }

  update() {
    this.certificateService.update(this.certificate)
      .then(() => {
        this.$rootScope.message = 'operation';
        this.state.go('certificates');
      }).catch(response => this.message = response.status);
  }
}
