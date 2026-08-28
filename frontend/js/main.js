/* CampusConnect Main Vanilla JavaScript Logic */

document.addEventListener("DOMContentLoaded", () => {
  renderNavbar();
  renderFooter();
  initNotificationDropdown();
  initMobileNavToggle();
  initGlobalModals();
});

// Render Shared Navbar across all pages
async function renderNavbar() {
  const navContainer = document.getElementById("main-nav");
  if (!navContainer) return;

  const currentPath = window.location.pathname.split("/").pop() || "index.html";
  const user = getStoredUser();
  const token = getAuthToken();
  const isLoggedIn = !!(token && user);

  let unreadCount = 0;
  let notifications = [];

  if (isLoggedIn) {
    try {
      notifications = await apiGet("/api/notifications");
      unreadCount = notifications.filter(n => !n.read).length;
    } catch (e) {
      notifications = [];
    }
  }

  const avatarText = user && user.avatarInitials ? user.avatarInitials : (user && user.fullName ? user.fullName.substring(0, 2).toUpperCase() : (user && user.username ? user.username.substring(0, 2).toUpperCase() : "CC"));
  const displayName = user && user.fullName ? user.fullName.split(" ")[0] : (user && user.username ? user.username : "Account");

  navContainer.innerHTML = `
    <nav class="navbar">
      <div class="nav-container">
        <a href="index.html" class="brand-logo">
          <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <path d="M22 10v6M2 10l10-5 10 5-10 5z"/>
            <path d="M6 12v5c3 3 9 3 12 0v-5"/>
          </svg>
          CampusConnect
          <span class="brand-badge">Live</span>
        </a>

        <button class="mobile-toggle" id="mobile-nav-toggle" aria-label="Toggle Navigation">
          ☰
        </button>

        <ul class="nav-menu" id="nav-menu-links">
          <li><a href="index.html" class="nav-link ${currentPath === 'index.html' ? 'active' : ''}">Home</a></li>
          <li><a href="projects.html" class="nav-link ${currentPath.includes('project') ? 'active' : ''}">Projects</a></li>
          <li><a href="directory.html" class="nav-link ${currentPath === 'directory.html' ? 'active' : ''}">Directory</a></li>
          <li><a href="circles.html" class="nav-link ${currentPath === 'circles.html' ? 'active' : ''}">Circles</a></li>
          <li><a href="teams.html" class="nav-link ${currentPath.includes('team') ? 'active' : ''}">Teams</a></li>
          <li><a href="hackathons.html" class="nav-link ${currentPath === 'hackathons.html' ? 'active' : ''}">Hackathons</a></li>
          <li><a href="internships.html" class="nav-link ${currentPath === 'internships.html' ? 'active' : ''}">Internships</a></li>
          <li><a href="resources.html" class="nav-link ${currentPath === 'resources.html' ? 'active' : ''}">Resources</a></li>
        </ul>

        <div class="nav-actions" style="display: flex; align-items: center; gap: 0.75rem;">
          ${isLoggedIn ? `
            <!-- Notification Bell Dropdown Container -->
            <div style="position: relative;">
              <button class="notification-trigger" id="notif-btn" title="Notifications">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/>
                  <path d="M13.73 21a2 2 0 0 1-3.46 0"/>
                </svg>
                ${unreadCount > 0 ? `<span class="badge-dot" id="notif-unread-count">${unreadCount}</span>` : ''}
              </button>

              <!-- Dropdown Menu -->
              <div class="dropdown-menu" id="notif-dropdown">
                <div class="dropdown-header">
                  <h4>Notifications (${unreadCount} unread)</h4>
                  <a href="notifications.html" style="font-size: 0.8rem; font-weight: 600;">View All</a>
                </div>
                <div class="dropdown-body" id="notif-dropdown-list">
                  ${notifications.length === 0 ? '<div style="padding: 1rem; text-align: center; color: var(--stone-brown); font-size: 0.85rem;">No new notifications</div>' :
                    notifications.slice(0, 4).map(n => `
                    <div class="notification-item ${!n.read ? 'unread' : ''}" style="cursor: pointer;" onclick="handleNotificationClick('${n.id}', '${n.targetEntityType || ''}', '${n.targetEntityId || ''}')">
                      <div class="notification-icon">🔔</div>
                      <div class="notification-content">
                        <div class="notification-text">${n.message}</div>
                        <div class="notification-time">${formatTimeAgo(n.createdAt)}</div>
                      </div>
                    </div>
                  `).join('')}
                </div>
              </div>
            </div>

            <!-- User Profile Quick Pill -->
            <a href="profile.html" class="user-pill" title="View Profile">
              <div class="user-avatar">${avatarText}</div>
              <span class="user-name">${displayName}</span>
            </a>

            <!-- Logout Button (Issue 1) -->
            <button class="btn btn-sm btn-ghost" id="logout-btn" onclick="handleLogout()" title="Log out of account" style="color: var(--stone-brown); border: 1px solid var(--border-color); padding: 0.35rem 0.75rem;">
              Logout
            </button>
          ` : `
            <!-- Non-authenticated actions -->
            <a href="login.html" class="btn btn-sm btn-ghost">Sign In</a>
            <a href="register.html" class="btn btn-sm btn-primary">Register</a>
          `}
        </div>
      </div>
    </nav>
  `;
}

// Format relative time helper
function formatTimeAgo(dateStr) {
  if (!dateStr) return "recently";
  try {
    const d = new Date(dateStr);
    const now = new Date();
    const diffSec = Math.floor((now - d) / 1000);
    if (diffSec < 60) return "just now";
    if (diffSec < 3600) return `${Math.floor(diffSec / 60)}m ago`;
    if (diffSec < 86400) return `${Math.floor(diffSec / 3600)}h ago`;
    return `${Math.floor(diffSec / 86400)}d ago`;
  } catch (e) {
    return "recently";
  }
}

// Issue 1: Logout Handler
function handleLogout() {
  clearAuth();
  showToast("Logged out successfully.", "info");
  setTimeout(() => {
    window.location.href = "login.html";
  }, 400);
}

// Issue 2: Clickable Notification Redirect Handler
async function handleNotificationClick(id, entityType, entityId) {
  try {
    if (id) {
      await apiPatch(`/api/notifications/${id}/read`).catch(() => {});
    }
  } catch (e) {
    console.error("Error marking notification read", e);
  }

  // Navigate to resolved entity page
  let targetUrl = "notifications.html";
  const type = (entityType || "").toUpperCase();
  const eid = entityId && entityId !== "null" && entityId !== "" ? entityId : "";

  if (type === "PROJECT") {
    targetUrl = eid ? `project-detail.html?id=${eid}` : "projects.html";
  } else if (type === "TEAM" || type === "WORKSPACE") {
    targetUrl = eid ? `team-workspace.html?teamId=${eid}` : "teams.html";
  } else if (type === "HACKATHON") {
    targetUrl = eid ? `hackathons.html#hackathon-${eid}` : "hackathons.html";
  } else if (type === "INTERNSHIP") {
    targetUrl = eid ? `internships.html#internship-${eid}` : "internships.html";
  } else if (type === "CIRCLE") {
    targetUrl = eid ? `circles.html#circle-${eid}` : "circles.html";
  } else if (type === "RESOURCE") {
    targetUrl = eid ? `resources.html#resource-${eid}` : "resources.html";
  } else if (type === "PROFILE") {
    targetUrl = eid ? `profile.html?userId=${eid}` : "profile.html";
  }

  window.location.href = targetUrl;
}

// Render Shared Footer
function renderFooter() {
  const footerContainer = document.getElementById("main-footer");
  if (!footerContainer) return;

  footerContainer.innerHTML = `
    <footer class="footer">
      <div class="footer-container">
        <div class="footer-brand">
          <div class="footer-title">CampusConnect</div>
          <p class="footer-desc">
            Student Collaboration & Innovation Platform enabling students to showcase projects, join domain circles, form teams, and discover hackathons.
          </p>
        </div>
        <div class="footer-col">
          <h5>Platform</h5>
          <ul class="footer-links">
            <li><a href="projects.html">Project Showcase</a></li>
            <li><a href="directory.html">Student Directory</a></li>
            <li><a href="circles.html">Collaboration Circles</a></li>
            <li><a href="teams.html">Team Workspace</a></li>
          </ul>
        </div>
        <div class="footer-col">
          <h5>Opportunities</h5>
          <ul class="footer-links">
            <li><a href="hackathons.html">Hackathons</a></li>
            <li><a href="internships.html">Internship Board</a></li>
            <li><a href="resources.html">Resource Hub</a></li>
          </ul>
        </div>
        <div class="footer-col">
          <h5>Account & Auth</h5>
          <ul class="footer-links">
            <li><a href="profile.html">Profile</a></li>
            <li><a href="login.html">Login</a></li>
            <li><a href="register.html">Register Account</a></li>
            <li><a href="forgot-password.html">Forgot Password</a></li>
          </ul>
        </div>
      </div>
      <div class="footer-bottom">
        <div>&copy; 2026 CampusConnect Platform. Pure Java Spring Boot 3 Backend.</div>
        <div>Connected to Live Render Database</div>
      </div>
    </footer>
  `;
}

// Notification Dropdown Toggle
function initNotificationDropdown() {
  document.addEventListener("click", (e) => {
    const trigger = e.target.closest("#notif-btn");
    const dropdown = document.getElementById("notif-dropdown");

    if (trigger) {
      e.stopPropagation();
      dropdown?.classList.toggle("show");
    } else if (dropdown && !e.target.closest("#notif-dropdown")) {
      dropdown.classList.remove("show");
    }
  });
}

// Mobile Nav Menu Toggle
function initMobileNavToggle() {
  document.addEventListener("click", (e) => {
    if (e.target.closest("#mobile-nav-toggle")) {
      const links = document.getElementById("nav-menu-links");
      links?.classList.toggle("show");
    }
  });
}

// Modal Toggle Utility
function initGlobalModals() {
  document.addEventListener("click", (e) => {
    // Open Modal
    const modalTrigger = e.target.closest("[data-modal-target]");
    if (modalTrigger) {
      const modalId = modalTrigger.getAttribute("data-modal-target");
      const modal = document.getElementById(modalId);
      if (modal) modal.classList.add("show");
    }

    // Close Modal
    if (e.target.closest("[data-modal-close]") || e.target.classList.contains("modal-backdrop")) {
      const modals = document.querySelectorAll(".modal-backdrop");
      modals.forEach(m => m.classList.remove("show"));
    }
  });
}

// Toast Notification System
function showToast(message, type = "info") {
  let container = document.getElementById("toast-container");
  if (!container) {
    container = document.createElement("div");
    container.id = "toast-container";
    container.className = "toast-container";
    document.body.appendChild(container);
  }

  const toast = document.createElement("div");
  toast.className = `toast toast-${type}`;
  toast.innerHTML = `
    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
      <polyline points="22 4 12 14.01 9 11.01"/>
    </svg>
    <span>${message}</span>
  `;

  container.appendChild(toast);
  setTimeout(() => {
    toast.remove();
  }, 3500);
}
