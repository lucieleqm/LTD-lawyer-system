const TOKEN_KEY = '@AppJuridico:token';
const TOKEN_TIPO_KEY = '@juridico:tokenTipo';
const EXPIRA_EM_KEY = '@juridico:expiraEm';

export function setToken(token: string, tipo: string, expiraEm: string) {
  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(TOKEN_TIPO_KEY, tipo);
  localStorage.setItem(EXPIRA_EM_KEY, expiraEm);
}

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function getTokenTipo() {
  return localStorage.getItem(TOKEN_TIPO_KEY) ?? 'Bearer';
}

export function getExpiraEm() {
  return localStorage.getItem(EXPIRA_EM_KEY);
}

export function clearToken() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(TOKEN_TIPO_KEY);
  localStorage.removeItem(EXPIRA_EM_KEY);
}

export function isTokenExpirado(): boolean {
  const expiraEm = getExpiraEm();
  if (!expiraEm) return true;
  return new Date(expiraEm).getTime() < Date.now();
}
