<c:set var="pageTitle" value="${team.name} Workspace – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<!-- Workspace Header -->
<div style="background-color: var(--white); border-radius: var(--radius-md); border: 1px solid var(--dusty-taupe); padding: 1.5rem; margin-bottom: 2rem; box-shadow: var(--card-shadow);">
  <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.5rem;">
    <div>
      <span class="badge badge-bordeaux" style="margin-bottom: 0.5rem;">🚀 Team Workspace</span>
      <h1 style="font-size: 2rem; margin-bottom: 0.2rem;">${team.name}</h1>
      <p style="color: var(--stone-brown); font-weight: 600;">Project: ${team.projectTitle}</p>
    </div>
    <div style="text-align: right;">
      <span class="badge badge-taupe" style="font-size: 0.9rem;">👥 ${team.currentMemberCount} / ${team.targetMemberCount} Members</span>
    </div>
  </div>
</div>

<!-- Pending Join Requests (Leader Only) -->
<c:if test="${isLeader && not empty pendingRequests}">
  <div class="card" style="border-color: var(--night-bordeaux); background-color: #fcf8f8;">
    <h3 style="font-size: 1.15rem; color: var(--night-bordeaux); margin-bottom: 0.75rem;">
      📬 Pending Join Applications (${pendingRequests.size()})
    </h3>
    <div style="display: flex; flex-direction: column; gap: 0.75rem;">
      <c:forEach var="req" items="${pendingRequests}">
        <div style="display: flex; justify-content: space-between; align-items: center; padding: 0.85rem; background-color: var(--white); border-radius: var(--radius-sm); border: 1px solid var(--dusty-taupe);">
          <div>
            <a href="<c:url value='/profile/${req.applicant.profile.id}'/>" style="font-weight: 700; color: var(--black);">
              👤 ${not empty req.applicant.profile.fullName ? req.applicant.profile.fullName : req.applicant.email}
            </a>
            <p style="font-size: 0.85rem; color: var(--stone-brown); margin-top: 0.2rem;">"${req.message}"</p>
          </div>
          <div style="display: flex; gap: 0.5rem;">
            <form action="<c:url value='/teams/request/${req.id}/respond'/>" method="post">
              <input type="hidden" name="teamId" value="${team.id}">
              <input type="hidden" name="accept" value="true">
              <button type="submit" class="btn btn-sm btn-primary">Accept +</button>
            </form>
            <form action="<c:url value='/teams/request/${req.id}/respond'/>" method="post">
              <input type="hidden" name="teamId" value="${team.id}">
              <input type="hidden" name="accept" value="false">
              <button type="submit" class="btn btn-sm btn-outline">Decline</button>
            </form>
          </div>
        </div>
      </c:forEach>
    </div>
  </div>
</c:if>

<div style="display: grid; grid-template-columns: 280px 1fr; gap: 2rem; align-items: start;">

  <!-- Left Sidebar: Team Roster -->
  <div class="card">
    <h3 style="font-size: 1.1rem; margin-bottom: 0.85rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.5rem;">
      👥 Team Roster
    </h3>
    <div style="display: flex; flex-direction: column; gap: 0.75rem;">
      <c:forEach var="m" items="${members}">
        <div style="display: flex; align-items: center; justify-content: space-between; font-size: 0.9rem;">
          <div>
            <a href="<c:url value='/profile/${m.user.profile.id}'/>" style="font-weight: 600; color: var(--black);">
              ${not empty m.user.profile.fullName ? m.user.profile.fullName : 'Member'}
            </a>
          </div>
          <span class="badge ${m.role == 'Leader' ? 'badge-bordeaux' : 'badge-taupe'}">${m.role}</span>
        </div>
      </c:forEach>
    </div>
  </div>

  <!-- Right Area: Discussion & Task Board -->
  <div style="display: flex; flex-direction: column; gap: 1.5rem;">

    <!-- Task Board -->
    <div class="card">
      <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
        <h3 style="font-size: 1.15rem;">📋 Team Task Progress</h3>
      </div>

      <!-- Add Task Form -->
      <form action="<c:url value='/workspace/${team.id}/task'/>" method="post" style="display: flex; gap: 0.5rem; margin-bottom: 1.25rem;">
        <input type="text" name="title" class="form-control" placeholder="New task item..." required style="flex: 2;">
        <input type="text" name="dueDate" class="form-control" placeholder="Due date (e.g. Fri)" required style="flex: 1;">
        <button type="submit" class="btn btn-sm btn-primary">Add Task</button>
      </form>

      <!-- Task List -->
      <div style="display: flex; flex-direction: column; gap: 0.5rem;">
        <c:forEach var="task" items="${tasks}">
          <div style="display: flex; align-items: center; justify-content: space-between; padding: 0.65rem 0.85rem; background-color: var(--white-smoke); border-radius: var(--radius-sm); border: 1px solid var(--dusty-taupe);">
            <div>
              <span style="font-weight: 600; font-size: 0.9rem; ${task.status == 'DONE' ? 'text-decoration: line-through; opacity: 0.6;' : ''}">${task.title}</span>
              <span style="font-size: 0.75rem; color: var(--stone-brown); margin-left: 0.5rem;">(Due: ${task.dueDate})</span>
            </div>
            <form action="<c:url value='/workspace/task/${task.id}/status'/>" method="post" style="display: flex; gap: 0.3rem;">
              <input type="hidden" name="teamId" value="${team.id}">
              <select name="status" onchange="this.form.submit()" class="form-select" style="padding: 0.2rem 0.5rem; font-size: 0.75rem;">
                <option value="TODO" ${task.status == 'TODO' ? 'selected' : ''}>To Do</option>
                <option value="IN_PROGRESS" ${task.status == 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                <option value="DONE" ${task.status == 'DONE' ? 'selected' : ''}>Done ✅</option>
              </select>
            </form>
          </div>
        </c:forEach>
      </div>
    </div>

    <!-- Team Discussion Stream -->
    <div class="card">
      <h3 style="font-size: 1.15rem; margin-bottom: 1rem;">💬 Workspace Discussion Stream</h3>

      <form action="<c:url value='/workspace/${team.id}/discussion'/>" method="post" style="margin-bottom: 1.5rem;">
        <textarea name="content" class="form-control" rows="3" placeholder="Share updates, links, code snippets with your team..." required style="margin-bottom: 0.5rem;"></textarea>
        <div style="text-align: right;">
          <button type="submit" class="btn btn-sm btn-primary">Post Message &rarr;</button>
        </div>
      </form>

      <div style="display: flex; flex-direction: column; gap: 1rem;">
        <c:forEach var="disc" items="${discussions}">
          <div style="padding: 0.85rem; background-color: var(--white-smoke); border-radius: var(--radius-sm); border: 1px solid var(--dusty-taupe);">
            <div style="display: flex; justify-content: space-between; font-size: 0.8rem; margin-bottom: 0.3rem; color: var(--stone-brown);">
              <strong>👤 ${not empty disc.author.profile.fullName ? disc.author.profile.fullName : disc.author.email}</strong>
              <span>${disc.createdAt}</span>
            </div>
            <p style="font-size: 0.9rem; color: var(--black);">${disc.content}</p>
          </div>
        </c:forEach>
      </div>
    </div>

  </div>

</div>

<jsp:include page="../includes/footer.jsp" />
