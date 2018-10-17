
export default class {
  constructor(userService, $state, $rootScope) {
    'ngInject';

    this.state = $state;
    this.userService = userService;
    this.rootScope = $rootScope;
  }

  $onInit() {
    if (this.userService.isAuthenticated()) {
      this.state.go('certificates');
    }
  }

  signup($scope) {
    const username = $scope.username;
    const password = $scope.password;
    this.userService.registration(username, password).then(() => {
      this.userService.authenticate(username, password);
    }).then(() => {
      this.rootScope.message = 'hello';
      this.state.go('certificates');
    })
      .catch(response => this.message = response.status);
  }
}
