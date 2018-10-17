import jwtDecode from 'jwt-decode';

export default class {
  constructor($httpParamSerializer,$http) {
    'ngInject';

    this.httpParamSerializer=$httpParamSerializer;
    this.http=$http;
  }

  isAdmin() {
    if (localStorage.getItem('access_token')) {
      const data = jwtDecode(localStorage.getItem('access_token'));
      return data.authorities.some(elem => elem === 'admin');
    }

    return false;
  }

  isAuthenticated() {
    return !!localStorage.getItem('access_token');
  }

  logout() {
    localStorage.removeItem(this.getUser());
    localStorage.removeItem('access_token');
    localStorage.removeItem('user');

  }

  getUser() {
      localStorage.getItem('user');
  }

	registration(username,password) {
		let url = 'http://localhost:8110/rest/users/signUp';
		let data = {
			"username": username,
			"password": password
		};
		return this.http.post(url,data);
	}

	authenticate(username, password) {
		const url = 'http://localhost:8110/oauth/token';
		const data = {
			grant_type: 'password',
			username,
			password,

		};
		return this.http({
			method: 'POST',
			url,
			headers: {
				Authorization: `Basic ${btoa('admin:admin')}`,
				'content-type': 'application/x-www-form-urlencoded;',
				Accept: 'application/json',
			},
			data: this.httpParamSerializer(data),
		})
			.then((response) => {
				const token = response.data.access_token;
				const tokens = jwtDecode(token);
				const username = tokens.user_name;
				localStorage.setItem('user', username);
				localStorage.setItem('access_token', token);
			});
	}
}
