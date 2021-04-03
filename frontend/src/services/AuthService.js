import Keycloak from "keycloak-js";
import keycloakConfig from "../keycloak.json";

const keycloak = new Keycloak(keycloakConfig);

const initKeycloak = (onAuthenticatedCallback) => {
    keycloak.init({
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
        pkceMethod: 'S256'
    })
        .then((authenticated) => {
            if (onAuthenticatedCallback) onAuthenticatedCallback();
        })
};

const login = keycloak.login;

const logout = keycloak.logout;

const getToken = () => keycloak.token;

const isAuthenticated = () => keycloak.authenticated;

const updateToken = (successCallback) =>
    keycloak.updateToken(5)
        .then(successCallback)
        .catch(login);

const getUsername = () => keycloak.tokenParsed.preferred_username;

const getId = () => keycloak.tokenParsed.sub;

const getFirstName = () => keycloak.tokenParsed.given_name;

const getLastName = () => keycloak.tokenParsed.family_name;

const getFullName = () => keycloak.tokenParsed.name;

const Role = {
    USER: 'USER',
    ADMIN: 'ADMIN'
}

const hasRole = (roles) => {
    if (roles === undefined || roles === null) return true;
    return roles.some((role) => keycloak.hasRealmRole(role));
}

const getRealm = keycloak.realm;

const AuthService = {
    initKeycloak,
    login,
    logout,
    isAuthenticated,
    getToken,
    updateToken,
    getUsername,
    getId,
    getFirstName,
    getLastName,
    getFullName,
    hasRole,
    Role,
    getRealm
};

export default AuthService;