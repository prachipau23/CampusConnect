<c:set var="pageTitle" value="Internship Board – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Internship & Research Opportunities</h1>
    <p class="page-subtitle">Handpicked technical internships, lab assistantships, and campus roles</p>
  </div>
</div>

<!-- Search & Filter Bar -->
<div class="card" style="padding: 1rem; margin-bottom: 2rem;">
  <form action="<c:url value='/internships'/>" method="get" style="display: flex; gap: 1rem; flex-wrap: wrap;">
    <input type="text" name="query" class="form-control" placeholder="Search by role title, company, technology..." value="${query}" style="flex: 1; min-width: 240px;">
    
    <select name="type" class="form-select" style="width: 180px;">
      <option value="">All Types</option>
      <option value="Full-time" ${type == 'Full-time' ? 'selected' : ''}>Full-time</option>
      <option value="Part-time" ${type == 'Part-time' ? 'selected' : ''}>Part-time</option>
      <option value="Research" ${type == 'Research' ? 'selected' : ''}>Research</option>
    </select>

    <button type="submit" class="btn btn-primary">Filter Internships</button>
  </form>
</div>

<!-- Internship Postings -->
<div class="grid grid-cols-2">
  <c:forEach var="item" items="${internships}">
    <c:set var="isBookmarked" value="${bookmarkedIds.contains(item.id)}" />

    <div class="card" style="display: flex; flex-direction: column; justify-content: space-between;">
      <div>
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.5rem;">
          <div>
            <h3 style="font-size: 1.25rem;">${item.title}</h3>
            <p style="color: var(--night-bordeaux); font-weight: 700; font-size: 0.95rem;">🏢 ${item.company}</p>
          </div>
          <span class="badge badge-taupe">${item.type}</span>
        </div>

        <p style="color: var(--stone-brown); font-size: 0.9rem; margin: 0.75rem 0 1rem; line-height: 1.6;">
          ${item.description}
        </p>

        <div style="display: flex; gap: 1.5rem; font-size: 0.85rem; color: var(--stone-brown); margin-bottom: 1rem;">
          <span>📍 Location: <strong>${item.location}</strong></span>
          <span>💰 Stipend: <strong>${item.stipend}</strong></span>
        </div>
      </div>

      <div style="border-top: 1px solid var(--border-color); padding-top: 0.85rem; display: flex; justify-content: space-between; align-items: center;">
        <span style="font-size: 0.8rem; color: var(--stone-brown);">⏳ Deadline: ${item.deadline}</span>

        <div style="display: flex; gap: 0.5rem;">
          <sec:authorize access="isAuthenticated()">
            <form action="<c:url value='/internships/${item.id}/bookmark'/>" method="post">
              <button type="submit" class="btn btn-sm btn-outline">
                ${isBookmarked ? '⭐ Bookmarked' : '☆ Bookmark'}
              </button>
            </form>
          </sec:authorize>
          <a href="${item.applyUrl}" target="_blank" class="btn btn-sm btn-primary">
            Apply Externally &rarr;
          </a>
        </div>
      </div>
    </div>
  </c:forEach>
</div>

<jsp:include page="../includes/footer.jsp" />
