
export default class {
  constructor(userService, $state, $timeout, $rootScope) {
    'ngInject';

    this.userService = userService;
    this.$state = $state;
    this.timeout = $timeout;
    this.$rootScope = $rootScope;
  }

  $onInit() {
  	if (this.userService.isAuthenticated()) {
  		this.$state.go('certificates');
    }
  }

  login($scope) {
    const username = $scope.username;
    const password = $scope.password;
    this.userService.authenticate(username, password)
      .then(() => {
        this.$rootScope.message = 'hello';
        this.$state.go('certificates');
      }).catch((response) => { this.message = response.status; });
  }
}
