
export default class {
  constructor($state, userService, certificateService, $rootScope, $q) {
    'ngInject';

    this.state = $state;
    this.userService = userService;
    this.certificateService = certificateService;
    this.rootScope = $rootScope;
    this.page = 1;
    this.$q = $q;
    this.pageSize = 4;
    this.pages = [];
    this.message;
  }

  $onInit() {
    if (!this.userService.isAuthenticated()) {
      this.$state.go('certificates');
    }
    this.purchase = JSON.parse(localStorage.getItem(this.userService.getUser()));
  }

  add(certificate) {
    this.deleteMessage();
    certificate.count++;
    localStorage.setItem(this.userService.getUser(), JSON.stringify(this.purchase));
  }

  remove(certificate) {
    this.deleteMessage();
    if (certificate.count !== 1) {
      certificate.count--;
      localStorage.setItem(this.userService.getUser(), JSON.stringify(this.purchase));
    }
  }

  delete(certificate) {
    this.deleteMessage();
    this.purchase = this.purchase.filter(item => item.id !== certificate.id);
    localStorage.setItem(this.userService.getUser(), JSON.stringify(this.purchase));
  }


  totalCost() {
    let total = 0;
    if (this.purchase) {
      this.purchase.forEach(e => total += e.price * e.count);
    }
    return total;
  }

  doOrder() {
    this.deleteMessage();
	  this.purchase = JSON.parse(localStorage.getItem(this.userService.getUser()));
    this.certificateService.checkCertificates(this.purchase)
      .then((response) => {
        this.certificates = response.data;
        if (this.certificates && this.purchase.length !== this.certificates.length) {
          for (let i = 0; i < this.purchase.length; i++) {
            if (i < this.certificates.length) {
              if (this.purchase[i].id === this.certificates[i].id) {
                this.certificates[i].count = this.purchase[i].count;
              }
            }
          }
          localStorage.setItem(this.userService.getUser(), JSON.stringify(this.certificates));
          this.purchase = JSON.parse(localStorage.getItem(this.userService.getUser()));
          this.rootScope.message = 'bad';
        } else {
          this.cleanCart(this.purchase).then(() => {
            localStorage.removeItem(this.userService.getUser());
            this.rootScope.message = 'operation';
            this.state.go('certificates');
          })
            .catch(() => {
              this.rootScope.message = 'error';
              localStorage.removeItem(this.userService.getUser());
            });
        }
      }).catch((response) => {
        this.rootScope.message = 'unknown';
        this.message = response.status;
      });
  }

  cleanCart(arr) {
    const cart = [];
    arr.forEach((e) => {
      cart.push(this.certificateService.buy(e.id, e.count));
    });
    return this.$q.all(cart);
  }

  deleteMessage() {
    this.rootScope.message = null;
    this.message = null;
  }
}
