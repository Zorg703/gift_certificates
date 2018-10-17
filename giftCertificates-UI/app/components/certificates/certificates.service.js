export default class {
  constructor($http, $httpParamSerializer) {
    'ngInject';

    this.http = $http;
    this.httpParamSerializer = $httpParamSerializer;
  }

  qetAllCertificates(page, size) {
    const params = {};
    params.page = page;
    params.size = size;
    return this.http.get('http://localhost:8109/rest/gift_certificates', { params });
  }

  getUserCertificates(params) {
    const token = localStorage.getItem('access_token');
    const url = 'http://localhost:8109/rest/users/orders';
    return this.http({
      method: 'GET',
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params,
    });
  }

  save(certificate) {
    const token = localStorage.getItem('access_token');
    const url = 'http://localhost:8109/rest/gift_certificates';
    return this.http({
      method: 'POST',
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
      data: JSON.stringify(certificate),
    });
  }

  update(certificate) {
    const token = localStorage.getItem('access_token');
    const certificat = JSON.stringify(certificate);
    const url = `${'http://localhost:8109/rest/gift_certificates' + '/'}${certificate.id}`;
    return this.http({
      method: 'PUT',
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
      data: certificat,
    });
  }

  delete(id) {
    const token = localStorage.getItem('access_token');
    const url = `${'http://localhost:8109/rest/gift_certificates' + '/'}${id}`;
    return this.http({
      method: 'DELETE',
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  buy(id,count) {
    const token = localStorage.getItem('access_token');
    const url = `${'http://localhost:8109/rest/gift_certificates' + '/'}${id}/orders`;
    return this.http({
      method: 'POST',
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
        data: count,
    });
  }

  showCountOfCertificates(size, page) {
    const data = {
      size,
      page,
    };
    return this.http.get('http://localhost:8109/rest/gift_certificates',
      { params: data });
  }

  getCertificatesWithParam(params) {
    return this.http.get('http://localhost:8109/rest/gift_certificates',
      { params });
  }

  getCertificateById(id) {
    return this.http({
      url: `${'http://localhost:8109/rest/gift_certificates' + '/'}${id}`,
      method: 'GET',
    }).then(response => response.data);
  }

  checkCertificates(list) {
    const url = `${'http://localhost:8109/rest/gift_certificates/check'}`;
    return this.http({
      method: 'POST',
      url,
      data: list,
    });
  }
}
