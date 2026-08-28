/**
 * CampusConnect Frontend Configuration & API Wrapper
 *
 * Points directly at the live Spring Boot backend.
 */

const API_BASE_URL = 'https://campusconnect-backend-m7y6.onrender.com';

// Stored auth token (JWT)
function getAuthToken() {
  return localStorage.getItem('campusconnect_jwt');
}

function setAuthToken(token) {
  localStorage.setItem('campusconnect_jwt', token);
}

// Stored current user info
function getStoredUser() {
  try {
    const raw = localStorage.getItem('campusconnect_user');
    return raw ? JSON.parse(raw) : null;
  } catch (e) {
    return null;
  }
}

function setStoredUser(user) {
  try {
    localStorage.setItem('campusconnect_user', JSON.stringify(user));
  } catch (e) {
    console.error('Error saving user to storage', e);
  }
}

// Clear all auth on logout
function clearAuth() {
  localStorage.removeItem('campusconnect_jwt');
  localStorage.removeItem('campusconnect_user');
}

// Build headers with optional Bearer auth
function authHeaders(extraHeaders = {}) {
  const token = getAuthToken();
  return {
    'Content-Type': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...extraHeaders
  };
}

// Unified API caller
async function apiCall(path, options = {}) {
  const url = path.startsWith('http') ? path : `${API_BASE_URL}${path}`;
  const response = await fetch(url, {
    ...options,
    headers: options.isFormData ? (getAuthToken() ? { Authorization: `Bearer ${getAuthToken()}` } : {}) : authHeaders(options.headers || {})
  });
  if (!response.ok) {
    const err = await response.json().catch(() => ({ error: response.statusText }));
    throw new Error(err.error || err.message || `API error ${response.status}`);
  }
  return response.json();
}

async function apiGet(path) {
  return apiCall(path, { method: 'GET' });
}

async function apiPost(path, data) {
  return apiCall(path, {
    method: 'POST',
    body: JSON.stringify(data)
  });
}

async function apiPut(path, data) {
  return apiCall(path, {
    method: 'PUT',
    body: JSON.stringify(data)
  });
}

async function apiDelete(path) {
  return apiCall(path, { method: 'DELETE' });
}

async function apiPatch(path, data = null) {
  return apiCall(path, {
    method: 'PATCH',
    body: data ? JSON.stringify(data) : undefined
  });
}
