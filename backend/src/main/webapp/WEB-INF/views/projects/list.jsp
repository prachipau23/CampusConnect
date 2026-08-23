<c:set var="pageTitle" value="Project Showcase – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Project Showcase</h1>
    <p class="page-subtitle">Discover innovative student projects, research work, and open-source contributions across campus</p>
  </div>
  <sec:authorize access="isAuthenticated()">
    <a href="<c:url value='/projects/new'/>" class="btn btn-primary">
      ➕ Publish Project
    </a>
  </sec:authorize>
</div>

<!-- Search & Filter Bar -->
<div class="card" style="padding: 1rem; margin-bottom: 2rem;">
  <form action="<c:url value='/projects'/>" method="get" style="display: flex; gap: 1rem; flex-wrap: wrap;">
    <input type="text" name="query" class="form-control" placeholder="Search by title, technology, keyword..." value="${query}" style="flex: 1; min-width: 240px;">
    
    <select name="status" class="form-select" style="width: 180px;">
      <option value="">All Statuses</option>
      <option value="Completed" ${status == 'Completed' ? 'selected' : ''}>Completed</option>
      <option value="In Development" ${status == 'In Development' ? 'selected' : ''}>In Development</option>
      <option value="Research Phase" ${status == 'Research Phase' ? 'selected' : ''}>Research Phase</option>
    </select>

    <button type="submit" class="btn btn-primary">Filter Projects</button>
  </form>
</div>

<!-- Project Cards Grid -->
<div class="grid grid-cols-3">
  <c:forEach var="project" items="${projects}">
    <div class="card" style="display: flex; flex-direction: column; height: 100%; margin-bottom: 0;">
      <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.75rem;">
        <span class="badge badge-taupe">${project.status}</span>
        <c:if test="${project.verified}">
          <span class="badge badge-bordeaux">Verified ✅</span>
        </c:if>
      </div>

      <h3 style="font-size: 1.2rem; margin-bottom: 0.5rem; line-height: 1.3;">
        <a href="<c:url value='/projects/${project.id}'/>">${project.title}</a>
      </h3>

      <p style="color: var(--stone-brown); font-size: 0.875rem; margin-bottom: 1rem; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden; flex: 1;">
        ${project.description}
      </p>

      <div style="margin-top: auto; padding-top: 0.75rem; border-top: 1px solid var(--border-color);">
        <div style="font-size: 0.8rem; color: var(--stone-brown); margin-bottom: 0.5rem;">
          <strong>Tech:</strong> ${project.techUsed}
        </div>
        
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <a href="<c:url value='/profile/${project.owner.profile.id}'/>" style="font-size: 0.8rem; font-weight: 600; color: var(--black);">
            👤 ${not empty project.owner.profile.fullName ? project.owner.profile.fullName : 'Student'}
          </a>
          
          <a href="<c:url value='/projects/${project.id}'/>" class="btn btn-sm btn-outline">
            View Details &rarr;
          </a>
        </div>
      </div>
    </div>
  </c:forEach>
</div>

<jsp:include page="../includes/footer.jsp" />
