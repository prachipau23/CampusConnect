<c:set var="pageTitle" value="Create Team Recruitment – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div style="max-width: 680px; margin: 1rem auto;">
  <div class="card">
    <div style="margin-bottom: 1.5rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.75rem;">
      <h2 style="font-size: 1.5rem;">Create Team Recruitment Post</h2>
      <p style="color: var(--stone-brown); font-size: 0.9rem;">Define team goals, skill requirements, and member capacity</p>
    </div>

    <form action="<c:url value='/teams/create'/>" method="post">
      
      <div class="form-group">
        <label class="form-label">Team / Initiative Name *</label>
        <input type="text" name="name" class="form-control" placeholder="e.g. Nexus AI Research Squad" required>
      </div>

      <div class="form-group">
        <label class="form-label">Target Project / Event Title *</label>
        <input type="text" name="projectTitle" class="form-control" placeholder="e.g. Smart Campus Energy Dashboard for Hackathon 2026" required>
      </div>

      <div class="form-group">
        <label class="form-label">Project Vision & Overview *</label>
        <textarea name="description" class="form-control" rows="4" placeholder="Detail the project scope, technical roadmap, and team goals..." required></textarea>
      </div>

      <div class="form-group">
        <label class="form-label">Required Skills & Roles *</label>
        <input type="text" name="requiredSkills" class="form-control" placeholder="e.g. 1x Frontend (React), 1x Backend (Spring Boot), 1x UI/UX Designer" required>
      </div>

      <div class="grid grid-cols-2">
        <div class="form-group">
          <label class="form-label">Target Total Team Size *</label>
          <input type="number" name="targetMemberCount" class="form-control" value="4" min="2" max="10" required>
        </div>

        <div class="form-group">
          <label class="form-label">Application Deadline *</label>
          <input type="text" name="deadline" class="form-control" placeholder="e.g. Oct 15, 2026" required>
        </div>
      </div>

      <div style="display: flex; gap: 1rem; margin-top: 1.5rem; justify-content: flex-end;">
        <a href="<c:url value='/teams'/>" class="btn btn-outline">Cancel</a>
        <button type="submit" class="btn btn-primary">Publish Team Post &rarr;</button>
      </div>

    </form>
  </div>
</div>

<jsp:include page="../includes/footer.jsp" />
