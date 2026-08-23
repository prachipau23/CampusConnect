<c:set var="pageTitle" value="Hackathons & Events – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Hackathons & Innovation Events</h1>
    <p class="page-subtitle">Participate in campus hackathons, technical workshops, and coding challenges</p>
  </div>
</div>

<!-- Search & Filter Bar -->
<div class="card" style="padding: 1rem; margin-bottom: 2rem;">
  <form action="<c:url value='/hackathons'/>" method="get" style="display: flex; gap: 1rem; flex-wrap: wrap;">
    <input type="text" name="query" class="form-control" placeholder="Search hackathons, tracks, workshops..." value="${query}" style="flex: 1; min-width: 240px;">
    
    <select name="status" class="form-select" style="width: 180px;">
      <option value="">All Statuses</option>
      <option value="Registration Open" ${status == 'Registration Open' ? 'selected' : ''}>Registration Open</option>
      <option value="Upcoming" ${status == 'Upcoming' ? 'selected' : ''}>Upcoming</option>
      <option value="In Progress" ${status == 'In Progress' ? 'selected' : ''}>In Progress</option>
    </select>

    <button type="submit" class="btn btn-primary">Filter Events</button>
  </form>
</div>

<!-- Hackathon Cards Grid -->
<div class="grid grid-cols-2">
  <c:forEach var="h" items="${hackathons}">
    <c:set var="isInterested" value="${interestedIds.contains(h.id)}" />

    <div class="card" style="display: flex; flex-direction: column; justify-content: space-between;">
      <div>
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.75rem;">
          <h3 style="font-size: 1.35rem;">🏆 ${h.title}</h3>
          <span class="badge badge-bordeaux">${h.status}</span>
        </div>

        <div style="font-size: 0.85rem; color: var(--stone-brown); margin-bottom: 0.75rem;">
          🏢 Organizer: <strong>${h.organizer}</strong> | 📅 Event Date: <strong>${h.eventDate}</strong>
        </div>

        <p style="color: var(--stone-brown); font-size: 0.9rem; margin-bottom: 1rem; line-height: 1.6;">
          ${h.description}
        </p>

        <div style="background-color: var(--white-smoke); padding: 0.85rem; border-radius: var(--radius-sm); margin-bottom: 1rem; border: 1px solid var(--dusty-taupe); display: flex; justify-content: space-between;">
          <div>
            <div style="font-size: 0.75rem; color: var(--stone-brown); font-weight: 700;">PRIZE POOL</div>
            <div style="font-size: 1.1rem; font-weight: 800; color: var(--night-bordeaux);">${h.prizePool}</div>
          </div>
          <div style="text-align: right;">
            <div style="font-size: 0.75rem; color: var(--stone-brown); font-weight: 700;">TRACKS</div>
            <div style="font-size: 0.85rem; font-weight: 600;">${h.tracks}</div>
          </div>
        </div>
      </div>

      <div style="border-top: 1px solid var(--border-color); padding-top: 0.85rem;">
        <div style="display: flex; justify-content: space-between; align-items: center; font-size: 0.85rem; margin-bottom: 0.85rem;">
          <span style="color: var(--stone-brown);">👥 Registered: <strong>${h.registeredCount} Students</strong></span>
          <span style="color: var(--stone-brown);">⏳ Deadline: <strong>${h.deadline}</strong></span>
        </div>

        <sec:authorize access="isAuthenticated()">
          <c:choose>
            <c:when test="${isInterested}">
              <button class="btn btn-outline" disabled style="width: 100%;">Registered Interest ✅</button>
            </c:when>
            <c:otherwise>
              <form action="<c:url value='/hackathons/${h.id}/register'/>" method="post" style="display: flex; gap: 0.5rem;">
                <input type="text" name="track" class="form-control" placeholder="Track interest (e.g. AI / Web3)..." required style="flex: 1;">
                <button type="submit" class="btn btn-primary">Register Interest &rarr;</button>
              </form>
            </c:otherwise>
          </c:choose>
        </sec:authorize>
      </div>
    </div>
  </c:forEach>
</div>

<jsp:include page="../includes/footer.jsp" />
