/**
 * CampusConnect Frontend Configuration
 *
 * This is the ONLY file you need to edit to point the frontend
 * at a different backend URL.
 *
 * When the Render backend is deployed, this value is updated
 * automatically to the live Render service URL.
 */

const API_BASE_URL = 'https://campusconnect-backend-m7y6.onrender.com';

// Helper: Get stored auth token
function getAuthToken() {
  return localStorage.getItem('campusconnect_jwt');
}

// Helper: Set auth token after login
function setAuthToken(token) {
  localStorage.setItem('campusconnect_jwt', token);
}

// Helper: Clear auth on logout
function clearAuth() {
  localStorage.removeItem('campusconnect_jwt');
  localStorage.removeItem('campusconnect_user');
}

// Helper: Build authenticated fetch options
function authHeaders(extraHeaders = {}) {
  const token = getAuthToken();
  return {
    'Content-Type': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...extraHeaders
  };
}

// Helper: Unified API call wrapper
async function apiCall(path, options = {}) {
  const url = `${API_BASE_URL}${path}`;
  const response = await fetch(url, {
    ...options,
    headers: authHeaders(options.headers || {})
  });
  if (!response.ok) {
    const err = await response.json().catch(() => ({ error: response.statusText }));
    throw new Error(err.error || `API error ${response.status}`);
  }
  return response.json();
}
