<c:set var="pageTitle" value="Admin Dashboard – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Platform Administration</h1>
    <p class="page-subtitle">Manage student accounts, verify research projects, publish campus announcements, and post opportunities</p>
  </div>
</div>

<!-- Platform Stats Grid -->
<div class="grid grid-cols-4" style="margin-bottom: 2rem;">
  <div class="card" style="margin-bottom: 0; text-align: center;">
    <div style="font-size: 0.8rem; font-weight: 700; color: var(--stone-brown);">TOTAL STUDENTS</div>
    <div style="font-size: 2.25rem; font-weight: 800; color: var(--night-bordeaux); margin-top: 0.25rem;">${stats.totalStudents}</div>
  </div>

  <div class="card" style="margin-bottom: 0; text-align: center;">
    <div style="font-size: 0.8rem; font-weight: 700; color: var(--stone-brown);">PROJECTS (VERIFIED)</div>
    <div style="font-size: 2.25rem; font-weight: 800; color: var(--night-bordeaux); margin-top: 0.25rem;">${stats.totalProjects} (${stats.verifiedProjects})</div>
  </div>

  <div class="card" style="margin-bottom: 0; text-align: center;">
    <div style="font-size: 0.8rem; font-weight: 700; color: var(--stone-brown);">ACTIVE TEAMS</div>
    <div style="font-size: 2.25rem; font-weight: 800; color: var(--night-bordeaux); margin-top: 0.25rem;">${stats.activeTeams}</div>
  </div>

  <div class="card" style="margin-bottom: 0; text-align: center;">
    <div style="font-size: 0.8rem; font-weight: 700; color: var(--stone-brown);">HACKATHONS & INTERNSHIPS</div>
    <div style="font-size: 2.25rem; font-weight: 800; color: var(--night-bordeaux); margin-top: 0.25rem;">${stats.totalHackathons + stats.totalInternships}</div>
  </div>
</div>

<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-bottom: 2rem;">

  <!-- Broadcast Announcement -->
  <div class="card">
    <h3 style="font-size: 1.15rem; margin-bottom: 1rem;">📢 Broadcast System Announcement</h3>
    <form action="<c:url value='/admin/announcements/publish'/>" method="post">
      <div class="form-group">
        <label class="form-label">Announcement Title *</label>
        <input type="text" name="title" class="form-control" placeholder="e.g. Annual Campus Innovation Fair Announced!" required>
      </div>

      <div class="form-group">
        <label class="form-label">Announcement Body *</label>
        <textarea name="content" class="form-control" rows="3" placeholder="Full broadcast details for all registered students..." required></textarea>
      </div>

      <div class="form-group">
        <label class="form-label">Priority Level</label>
        <select name="priority" class="form-select">
          <option value="NORMAL">Normal</option>
          <option value="HIGH">High Priority 🔴</option>
        </select>
      </div>

      <button type="submit" class="btn btn-primary" style="width: 100%;">Broadcast to All Students &rarr;</button>
    </form>
  </div>

  <!-- Post Hackathon Event -->
  <div class="card">
    <h3 style="font-size: 1.15rem; margin-bottom: 1rem;">🏆 Post New Hackathon Event</h3>
    <form action="<c:url value='/admin/hackathons/add'/>" method="post">
      <div class="form-group">
        <label class="form-label">Hackathon Title *</label>
        <input type="text" name="title" class="form-control" placeholder="e.g. National Hackathon 2026" required>
      </div>

      <div class="grid grid-cols-2">
        <div class="form-group">
          <label class="form-label">Organizer *</label>
          <input type="text" name="organizer" class="form-control" placeholder="e.g. CS Dept" required>
        </div>
        <div class="form-group">
          <label class="form-label">Event Date *</label>
          <input type="text" name="eventDate" class="form-control" placeholder="e.g. Nov 12-14" required>
        </div>
      </div>

      <div class="grid grid-cols-2">
        <div class="form-group">
          <label class="form-label">Prize Pool *</label>
          <input type="text" name="prizePool" class="form-control" placeholder="e.g. $10,000" required>
        </div>
        <div class="form-group">
          <label class="form-label">Status *</label>
          <select name="status" class="form-select" required>
            <option value="Registration Open">Registration Open</option>
            <option value="Upcoming">Upcoming</option>
          </select>
        </div>
      </div>

      <div class="form-group">
        <label class="form-label">Tracks (Comma separated) *</label>
        <input type="text" name="tracks" class="form-control" placeholder="e.g. AI/ML, Web3, FinTech, GreenTech" required>
      </div>

      <div class="form-group">
        <label class="form-label">Description *</label>
        <textarea name="description" class="form-control" rows="2" placeholder="Hackathon rules, theme, guidelines..." required></textarea>
      </div>

      <div class="form-group">
        <label class="form-label">Registration Deadline *</label>
        <input type="text" name="deadline" class="form-control" placeholder="e.g. Oct 30, 2026" required>
      </div>

      <button type="submit" class="btn btn-primary" style="width: 100%;">Publish Hackathon &rarr;</button>
    </form>
  </div>

</div>

<!-- Student Account Management Table -->
<div class="card" style="margin-bottom: 2rem;">
  <h3 style="font-size: 1.15rem; margin-bottom: 1rem;">👥 Student Account Management</h3>
  <div style="overflow-x: auto;">
    <table style="width: 100%; border-collapse: collapse; font-size: 0.875rem;">
      <thead>
        <tr style="background-color: var(--white-smoke); border-bottom: 1px solid var(--dusty-taupe);">
          <th style="padding: 0.75rem; text-align: left;">Email</th>
          <th style="padding: 0.75rem; text-align: left;">Role</th>
          <th style="padding: 0.75rem; text-align: left;">Status</th>
          <th style="padding: 0.75rem; text-align: right;">Action</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="u" items="${students}">
          <tr style="border-bottom: 1px solid var(--border-color);">
            <td style="padding: 0.75rem; font-weight: 600;">${u.email}</td>
            <td style="padding: 0.75rem;"><span class="badge badge-taupe">${u.role}</span></td>
            <td style="padding: 0.75rem;">
              <span class="badge ${u.active ? 'badge-bordeaux' : 'badge-taupe'}">${u.active ? 'Active' : 'Disabled'}</span>
            </td>
            <td style="padding: 0.75rem; text-align: right;">
              <form action="<c:url value='/admin/users/${u.id}/toggle-active'/>" method="post" style="display: inline;">
                <button type="submit" class="btn btn-sm btn-outline">
                  ${u.active ? 'Disable Account' : 'Enable Account'}
                </button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Project Verification Management Table -->
<div class="card">
  <h3 style="font-size: 1.15rem; margin-bottom: 1rem;">💡 Student Project Verification</h3>
  <div style="overflow-x: auto;">
    <table style="width: 100%; border-collapse: collapse; font-size: 0.875rem;">
      <thead>
        <tr style="background-color: var(--white-smoke); border-bottom: 1px solid var(--dusty-taupe);">
          <th style="padding: 0.75rem; text-align: left;">Project Title</th>
          <th style="padding: 0.75rem; text-align: left;">Student Owner</th>
          <th style="padding: 0.75rem; text-align: left;">Verification</th>
          <th style="padding: 0.75rem; text-align: right;">Faculty Action</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="p" items="${projects}">
          <tr style="border-bottom: 1px solid var(--border-color);">
            <td style="padding: 0.75rem; font-weight: 600;">${p.title}</td>
            <td style="padding: 0.75rem;">${not empty p.owner.profile.fullName ? p.owner.profile.fullName : p.owner.email}</td>
            <td style="padding: 0.75rem;">
              <span class="badge ${p.verified ? 'badge-bordeaux' : 'badge-taupe'}">${p.verified ? 'Faculty Verified ✅' : 'Unverified'}</span>
            </td>
            <td style="padding: 0.75rem; text-align: right;">
              <form action="<c:url value='/admin/projects/${p.id}/verify'/>" method="post" style="display: inline;">
                <button type="submit" class="btn btn-sm btn-outline">
                  ${p.verified ? 'Revoke Verification' : 'Verify Project ✅'}
                </button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<jsp:include page="../includes/footer.jsp" />
