export const environment = {
  production: true,
  api : {
    url : location.protocol === 'https:' ? 'https://localhost:8443/api/' : 'http://localhost:8080/api/',
    token: ''
  }
};
