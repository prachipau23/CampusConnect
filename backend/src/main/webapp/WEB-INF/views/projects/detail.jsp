<c:set var="pageTitle" value="${project.title} – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div style="max-width: 800px; margin: 0 auto;">
  <div style="margin-bottom: 1rem;">
    <a href="<c:url value='/projects'/>" style="font-weight: 600; font-size: 0.9rem;">&larr; Back to Project Showcase</a>
  </div>

  <div class="card">
    <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 1rem;">
      <div>
        <h1 style="font-size: 2rem; margin-bottom: 0.5rem;">${project.title}</h1>
        <div style="display: flex; gap: 0.5rem; align-items: center;">
          <span class="badge badge-taupe">${project.status}</span>
          <c:if test="${project.verified}">
            <span class="badge badge-bordeaux">Faculty Verified Project ✅</span>
          </c:if>
        </div>
      </div>

      <c:if test="${isOwner}">
        <form action="<c:url value='/projects/${project.id}/delete'/>" method="post" onsubmit="return confirm('Are you sure you want to delete this project?');">
          <button type="submit" class="btn btn-sm btn-outline" style="color: #c5221f; border-color: #fad2cf;">
            🗑️ Delete Project
          </button>
        </form>
      </c:if>
    </div>

    <c:if test="${not empty project.screenshotPath}">
      <div style="margin-bottom: 1.5rem; border-radius: var(--radius-md); overflow: hidden; max-height: 400px; border: 1px solid var(--dusty-taupe);">
        <img src="<c:url value='${project.screenshotPath}'/>" alt="Project Screenshot" style="width: 100%; height: 100%; object-fit: cover;">
      </div>
    </c:if>

    <div style="margin-bottom: 1.5rem;">
      <h3 style="font-size: 1.1rem; margin-bottom: 0.5rem;">Project Overview</h3>
      <p style="color: var(--stone-brown); line-height: 1.7; font-size: 1rem;">
        ${project.description}
      </p>
    </div>

    <div style="margin-bottom: 1.5rem;">
      <h3 style="font-size: 1.1rem; margin-bottom: 0.5rem;">Technologies Used</h3>
      <div style="display: flex; gap: 0.5rem; flex-wrap: wrap;">
        <c:forEach var="tech" items="${project.techUsed.split(',')}">
          <span class="badge badge-taupe" style="padding: 0.35rem 0.75rem;">${tech.trim()}</span>
        </c:forEach>
      </div>
    </div>

    <div style="display: flex; gap: 1rem; margin-bottom: 1.5rem; flex-wrap: wrap; padding: 1rem; background-color: var(--white-smoke); border-radius: var(--radius-sm); border: 1px solid var(--dusty-taupe);">
      <c:if test="${not empty project.githubRepo}">
        <a href="${project.githubRepo}" target="_blank" class="btn btn-outline">
          🌐 View GitHub Repository &rarr;
        </a>
      </c:if>
      <c:if test="${not empty project.demoUrl}">
        <a href="${project.demoUrl}" target="_blank" class="btn btn-primary">
          🚀 Live Demo / URL &rarr;
        </a>
      </c:if>
    </div>

    <div style="border-top: 1px solid var(--border-color); padding-top: 1rem; display: flex; align-items: center; justify-content: space-between;">
      <div>
        <div style="font-size: 0.8rem; color: var(--stone-brown);">Project Creator</div>
        <div style="font-weight: 700;">
          <a href="<c:url value='/profile/${project.owner.profile.id}'/>">
            👤 ${not empty project.owner.profile.fullName ? project.owner.profile.fullName : 'Student Owner'}
          </a>
        </div>
      </div>
      <div style="font-size: 0.8rem; color: var(--stone-brown);">
        Posted on: ${project.createdAt}
      </div>
    </div>
  </div>
</div>

<jsp:include page="../includes/footer.jsp" />
