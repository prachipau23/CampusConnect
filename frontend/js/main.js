/* CampusConnect Main Vanilla JavaScript Logic */

document.addEventListener("DOMContentLoaded", () => {
  renderNavbar();
  renderFooter();
  initNotificationDropdown();
  initMobileNavToggle();
  initGlobalModals();
});

// Render Shared Navbar across all pages
function renderNavbar() {
  const navContainer = document.getElementById("main-nav");
  if (!navContainer) return;

  const currentPath = window.location.pathname.split("/").pop() || "index.html";
  const user = getStoredData("user", MOCK_DATA.currentUser);
  const notifications = getStoredData("notifications", MOCK_DATA.notifications);
  const unreadCount = notifications.filter(n => n.unread).length;

  navContainer.innerHTML = `
    <nav class="navbar">
      <div class="nav-container">
        <a href="index.html" class="brand-logo">
          <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <path d="M22 10v6M2 10l10-5 10 5-10 5z"/>
            <path d="M6 12v5c3 3 9 3 12 0v-5"/>
          </svg>
          CampusConnect
          <span class="brand-badge">Innovation</span>
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

        <div class="nav-actions">
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
                ${notifications.slice(0, 4).map(n => `
                  <div class="notification-item ${n.unread ? 'unread' : ''}" data-id="${n.id}">
                    <div class="notification-icon">${n.icon}</div>
                    <div class="notification-content">
                      <div class="notification-text"><strong>${n.title}:</strong> ${n.message}</div>
                      <div class="notification-time">${n.time}</div>
                    </div>
                  </div>
                `).join('')}
              </div>
            </div>
          </div>

          <!-- User Profile Quick Pill -->
          <a href="profile.html" class="user-pill" title="View Student Profile">
            <div class="user-avatar">${user.avatar}</div>
            <span class="user-name">${user.name.split(' ')[0]}</span>
          </a>
        </div>
      </div>
    </nav>
  `;
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
            <li><a href="profile.html">Student Profile</a></li>
            <li><a href="login.html">Login</a></li>
            <li><a href="register.html">Register Account</a></li>
            <li><a href="forgot-password.html">Forgot Password</a></li>
          </ul>
        </div>
      </div>
      <div class="footer-bottom">
        <div>&copy; 2026 CampusConnect Platform. Designed for Spring Boot / JSP Integration.</div>
        <div>HTML5 • CSS3 Custom Properties • Vanilla JS</div>
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
  toast.className = "toast";
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
