<c:set var="pageTitle" value="Publish New Project – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div style="max-width: 680px; margin: 1rem auto;">
  <div class="card">
    <div style="margin-bottom: 1.5rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.75rem;">
      <h2 style="font-size: 1.5rem;">Publish Student Project</h2>
      <p style="color: var(--stone-brown); font-size: 0.9rem;">Share your work with campus recruiters, faculty, and peer collaborators</p>
    </div>

    <form action="<c:url value='/projects/new'/>" method="post" enctype="multipart/form-data">
      
      <div class="form-group">
        <label class="form-label">Project Title *</label>
        <input type="text" name="title" class="form-control" placeholder="e.g. AI-Powered Campus Transit Tracker" required>
      </div>

      <div class="form-group">
        <label class="form-label">Development Status *</label>
        <select name="status" class="form-select" required>
          <option value="In Development">In Development</option>
          <option value="Completed">Completed</option>
          <option value="Research Phase">Research Phase</option>
        </select>
      </div>

      <div class="form-group">
        <label class="form-label">Technologies Used (Comma separated) *</label>
        <input type="text" name="techUsed" class="form-control" placeholder="e.g. Java, Spring Boot, React, PostgreSQL, Docker" required>
      </div>

      <div class="form-group">
        <label class="form-label">Full Project Description *</label>
        <textarea name="description" class="form-control" rows="5" placeholder="Explain the problem solved, architecture, key features, and accomplishments..." required></textarea>
      </div>

      <div class="grid grid-cols-2">
        <div class="form-group">
          <label class="form-label">GitHub Repository URL</label>
          <input type="url" name="githubRepo" class="form-control" placeholder="https://github.com/org/repo">
        </div>

        <div class="form-group">
          <label class="form-label">Live Demo / Deployed Link</label>
          <input type="url" name="demoUrl" class="form-control" placeholder="https://project.example.com">
        </div>
      </div>

      <div class="form-group">
        <label class="form-label">Project Screenshot / Cover Image</label>
        <input type="file" name="screenshot" class="form-control" accept="image/*">
      </div>

      <div style="display: flex; gap: 1rem; margin-top: 1.5rem; justify-content: flex-end;">
        <a href="<c:url value='/projects'/>" class="btn btn-outline">Cancel</a>
        <button type="submit" class="btn btn-primary">Publish Project Showcase &rarr;</button>
      </div>

    </form>
  </div>
</div>

<jsp:include page="../includes/footer.jsp" />
