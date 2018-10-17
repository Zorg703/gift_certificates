export default class {
  constructor(giftCertificateService, userService, $stateParams, $state, $location, $rootScope, $timeout) {
    'ngInject';

    this.giftCertificateService = giftCertificateService;
    this.userService = userService;
    this.stateParams = $stateParams;
    this.state = $state;
    this.$rootScope = $rootScope;
    this.search = '';
    this.availablePageSize = ['6', '12', '18'];
    this.page = 1;
    this.pageSize = this.availablePageSize[0];
    this.pages = [];
    this.location = $location;
    this.params = {};
    this.sorts = ['date', 'name'];
    this.tags;
    this.sort = 'date';
    this.text;
    this.timeout = $timeout;
  }

  $onInit() {

    if (this.state.current.name === 'certificates') {
      this.findCertificates();
    }
    if (this.state.current.name === 'my') {
      this.setParamsFromURL();
      this.findUserCertificates();
      this.location.path('/certificates/users').search({
        page: this.page, size: this.pageSize,
      });
    }
  }

  isAuthenticated() {
    return this.userService.isAuthenticated();
  }

  isAdmin() {
    return this.userService.isAdmin();
  }

  delete(id) {
      this.deleteMessage();
    this.giftCertificateService.delete(id).then(() => {
      if (this.certificates.length !== 1) {
        this.location.path('/certificates').search({
          page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
        });
        this.findCertificates();
      } else if (this.page !== 1) {
        this.previous();
      } else {
		  this.state.reload();
      }
    }).catch(response => this.message = response.status);
  }

  findUserCertificates() {
    this.params = this.location.search();
    if (!this.params.size) {
      this.params.size = this.pageSize;
    } else {
      this.pageSize = this.params.size;
    }
    if (!this.params.page) {
      this.params.page = this.page;
    } else {
      this.page = this.params.page;
    }
    this.giftCertificateService.getUserCertificates(this.params).then((response) => {
      this.certificates = [];
      this.orders = response.data.list;
      this.orders.forEach(order => this.certificates.push(order.certificate));
      this.size = response.data.size;
      this.pagination(this.size);
    }).catch(response => this.message = response.status);
  }

  searching() {
    this.deleteMessage();
    this.page = 1;
    this.tags = null;
    this.text = null;
    this.parse(this.search);
    this.location.path('/certificates').search({
      page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
    });
    this.findCertificates();
  }

  findCertificates() {
    this.setParamsFromURL();
    this.giftCertificateService.getCertificatesWithParam(this.params).then((response) => {
      this.certificates = response.data.list;
      this.size = response.data.size;
      this.pagination(this.size);
    }).catch(response => this.message = response.status);
  }

  addInSerach(name) {
    this.search = `${this.search}#${name} `;
    return this.search;
  }

  showCountOfCertificates(size) {
      this.deleteMessage();
    if (this.state.current.name == 'certificates') {
      this.page = 1;
      this.location.path('/certificates').search({
        page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
      });
      this.state.params.size = size;
      this.findCertificates();
    }
    if (this.state.current.name == 'my') {
      this.page = 1;
      this.location.path('/certificates/users').search({
        page: this.page, size: this.pageSize,
      });
      this.state.params.size = size;
      this.findUserCertificates();
    }
  }

  parse(searchString) {
    const TAG_REGEX = /#\S+/g;
    const TEXT_REGEX = /\s?#\S+\s?/g;
    const params = {};
    let tags = searchString.split('#').join(' #').match(TAG_REGEX);
    if (tags) {
      tags = tags.map(a => a.replace('#', ''));
      params.tags = tags.join(',');
      this.tags = params.tags;
    }
    const text = searchString.replace(TEXT_REGEX, '').trim();
    if (text.trim()) {
      params.text = text;
      this.text = params.text;
    }
    return params;
  }

  setParamsFromURL() {
    this.search = '';
    this.params = this.location.search();
    if (localStorage.getItem('url')) {
      this.params = JSON.parse(localStorage.getItem('url'));
      localStorage.removeItem('url');
    }
    if (this.params.text) {
      this.text = this.params.text;
      this.search = this.text;
    }
    if (this.params.tags) {
      this.tags = this.params.tags;
      const a = this.tags.split(',');
      const b = a.join(' #');
      this.search = (`${this.search}#${b}`);
    }
    if (!this.params.size || !this.availablePageSize.some(k => k === this.params.size)) {
      this.params.size = this.pageSize;
    } else {
      this.pageSize = this.params.size;
    }
    if (!this.params.page || this.params.page < 0) {
      this.params.page = this.page;
    } else {
      this.page = this.params.page;
    }
    if (!this.params.sort) {
      this.params.sort = this.sort;
    } else {
      this.sort = this.params.sort;
    }
    this.location.path('/certificates').search({
      page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
    });
  }

  pagination(size) {
    const countPage = (size % this.pageSize === 0 ? size / this.pageSize : Math.floor(size / this.pageSize + 1));
    this.maxPage = countPage;
    const current = parseInt(this.page);
    const last = countPage;
    const delta = 2;
    const left = current - delta;
    const right = current + delta + 1;
    const range = [];
    const rangeWithDots = [];
    let l;
    for (let i = 1; i <= last; i++) {
      if (i === 1 || i === last || i >= left && i < right) {
        range.push(i);
      }
    }

    for (const i of range) {
      if (l) {
        if (i - l === 2) {
          rangeWithDots.push(l + 1);
        } else if (i - l !== 1) {
          rangeWithDots.push('...');
        }
      }
      rangeWithDots.push(i);
      l = i;
    }
    this.pages = rangeWithDots;
  }

  changePage(page) {
      this.deleteMessage();
    if (this.state.current.name == 'certificates') {
      if (page !== '...' && this.page !== page) {
        this.page = page;
        this.location.path('/certificates').search({
          page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
        });
        this.state.params.page = this.page;
        this.findCertificates();
      }
    }
    if (this.state.current.name === 'my') {
      if (page !== '...' && this.page !== page) {
        this.page = page;
        this.location.path('/certificates/users').search({
          page: this.page, size: this.pageSize,
        });
        this.state.params.page = this.page;
        this.findUserCertificates();
      }
    }
  }

  next() {
      this.deleteMessage();
    if (this.state.current.name === 'certificates') {
      if (this.page !== this.maxPage) {
        this.page++;
        this.location.path('/certificates').search({
          page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
        });
        this.state.params.page = this.page;
        this.findCertificates();
      }
    }
    if (this.state.current.name === 'my') {
      if (this.page !== this.maxPage) {
        this.page++;
        this.location.path('/certificates/users').search({
          page: this.page, size: this.pageSize,
        });
        this.state.params.page = this.page;
        this.findUserCertificates();
      }
    }
  }

  previous() {
      this.deleteMessage();
    if (this.state.current.name === 'certificates') {
      if (this.page > 1) {
        this.page--;
        this.location.path('/certificates').search({
          page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
        });
        this.state.params.page = this.page;
        this.findCertificates();
      }
    }
    if (this.state.current.name === 'my') {
      if (this.page > 1) {
        this.page--;
        this.location.path('/certificates/users').search({
          page: this.page, size: this.pageSize,
        });
        this.state.params.page = this.page;
        this.findUserCertificates();
      }
    }
  }

  first() {
      this.deleteMessage();
    if (this.state.current.name === 'certificates') {
      if (this.page !== 1) {
        this.page = 1;
        this.location.path('/certificates').search({
          page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
        });
        this.state.params.page = this.page;
        this.findCertificates();
      }
    }
    if (this.state.current.name === 'my') {
      if (this.page !== 1) {
        this.page = 1;
        this.location.path('/certificates/users').search({
          page: this.page, size: this.pageSize,
        });
        this.state.params.page = this.page;
        this.findUserCertificates();
      }
    }
  }

  last() {
      this.deleteMessage();
    if (this.state.current.name === 'certificates') {
      if (this.page !== this.maxPage) {
        this.page = this.maxPage;
        this.location.path('/certificates').search({
          page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
        });
        this.state.params.page = this.page;
        this.findCertificates();
      }
    }
    if (this.state.current.name === 'my') {
      if (this.page !== this.maxPage) {
        this.page = this.maxPage;
        this.location.path('/certificates/users').search({
          page: this.page, size: this.pageSize,
        });
        this.state.params.page = this.page;
        this.findUserCertificates();
      }
    }
  }

  addToCart(certificate) {
      this.deleteMessage();
    const user = this.userService.getUser();
    let purchase = JSON.parse(localStorage.getItem(user));
    if (!purchase) {
      purchase = [];
    }
    certificate.count = 1;
    purchase.push(certificate);
    localStorage.setItem(user, JSON.stringify(purchase));
  }

  getPurchases() {
    const user = this.userService.getUser();
    const purchase = JSON.parse(localStorage.getItem(user));
    return purchase;
  }

  checkCertificateInPurchase(certificate) {
    const purchase = this.getPurchases();
    if (purchase) {
      return purchase.some(e => e.id === certificate.id);
    }
    return false;
  }

  edit(id) {
      this.deleteMessage();
    localStorage.setItem('url', JSON.stringify(this.location.search()));
    this.state.go('edit', { id });
  }

  changeSort(sort) {
      this.deleteMessage();
    this.sort = sort;
    this.location.path('/certificates').search({
      page: this.page, size: this.pageSize, text: this.text, tags: this.tags, sort: this.sort,
    });
    this.state.params.sort = this.sort;
    this.findCertificates();
  }

  deleteMessage(){
      this.$rootScope.message=null;
      this.message=null;
  }
}
