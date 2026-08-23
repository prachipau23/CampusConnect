<c:set var="pageTitle" value="Team Formation – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Team Formation Board</h1>
    <p class="page-subtitle">Find co-founders, hackathon teammates, and research collaborators</p>
  </div>
  <sec:authorize access="isAuthenticated()">
    <a href="<c:url value='/teams/create'/>" class="btn btn-primary">
      🚀 Create Team Recruitment
    </a>
  </sec:authorize>
</div>

<!-- Search & Filter Bar -->
<div class="card" style="padding: 1rem; margin-bottom: 2rem;">
  <form action="<c:url value='/teams'/>" method="get" style="display: flex; gap: 1rem; flex-wrap: wrap;">
    <input type="text" name="query" class="form-control" placeholder="Search team posts by project name, skill requirement..." value="${query}" style="flex: 1; min-width: 240px;">
    
    <select name="status" class="form-select" style="width: 180px;">
      <option value="">All Statuses</option>
      <option value="OPEN" ${status == 'OPEN' ? 'selected' : ''}>Open for Members</option>
      <option value="CLOSED" ${status == 'CLOSED' ? 'selected' : ''}>Recruitment Closed</option>
    </select>

    <button type="submit" class="btn btn-primary">Filter Teams</button>
  </form>
</div>

<!-- Team Recruitment Posts -->
<div class="grid grid-cols-2">
  <c:forEach var="team" items="${teams}">
    <div class="card" style="display: flex; flex-direction: column; justify-content: space-between;">
      <div>
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.75rem;">
          <h3 style="font-size: 1.35rem;">${team.name}</h3>
          <span class="badge ${team.status == 'OPEN' ? 'badge-bordeaux' : 'badge-taupe'}">${team.status}</span>
        </div>

        <div style="font-size: 0.95rem; font-weight: 700; color: var(--stone-brown); margin-bottom: 0.75rem;">
          📌 Project: ${team.projectTitle}
        </div>

        <p style="color: var(--stone-brown); font-size: 0.9rem; margin-bottom: 1rem; line-height: 1.6;">
          ${team.description}
        </p>

        <div style="background-color: var(--white-smoke); padding: 0.85rem; border-radius: var(--radius-sm); margin-bottom: 1rem; border: 1px solid var(--dusty-taupe);">
          <div style="font-size: 0.85rem; font-weight: 700; margin-bottom: 0.3rem;">Required Skills:</div>
          <div style="font-size: 0.85rem; color: var(--black);">${team.requiredSkills}</div>
        </div>
      </div>

      <div style="border-top: 1px solid var(--border-color); padding-top: 0.85rem;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.85rem; font-size: 0.85rem; color: var(--stone-brown);">
          <span>👥 Capacity: <strong>${team.currentMemberCount} / ${team.targetMemberCount}</strong> Members</span>
          <span>⏳ Deadline: ${team.deadline}</span>
        </div>

        <sec:authorize access="isAuthenticated()">
          <c:choose>
            <c:when test="${team.createdBy.id == currentUser.id}">
              <a href="<c:url value='/workspace/${team.id}'/>" class="btn btn-primary" style="width: 100%;">
                ⚡ Manage Workspace & Applicants &rarr;
              </a>
            </c:when>
            <c:otherwise>
              <c:if test="${team.status == 'OPEN'}">
                <form action="<c:url value='/teams/${team.id}/join'/>" method="post" style="display: flex; gap: 0.5rem;">
                  <input type="text" name="message" class="form-control" placeholder="Brief note about your background..." required style="flex: 1;">
                  <button type="submit" class="btn btn-primary">Apply &rarr;</button>
                </form>
              </c:if>
              <c:if test="${team.status != 'OPEN'}">
                <button class="btn btn-outline" disabled style="width: 100%;">Recruitment Closed</button>
              </c:if>
            </c:otherwise>
          </c:choose>
        </sec:authorize>
      </div>
    </div>
  </c:forEach>
</div>

<jsp:include page="../includes/footer.jsp" />
