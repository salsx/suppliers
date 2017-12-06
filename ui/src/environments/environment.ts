// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `angular-cli.json`.

export const environment = {
  production: false,
  serviceUrl: 'http://localhost:9000/api/v1',
  auth_clientID: 'XYQEF1J7DXFJYD2gOkpjz5ZsuNAbZ0o7',
  auth_domain: 'pasquydomain.eu.auth0.com',
  auth_apiUrl: 'https://pasquydomain.eu.auth0.com/api/v2/',
  auth_callbackURL: 'http://localhost:4200/'
};
