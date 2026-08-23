<c:set var="pageTitle" value="Resource Library – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Resource & Study Materials Library</h1>
    <p class="page-subtitle">Access peer-contributed study guides, lab manuals, lecture slides, and past exam papers</p>
  </div>
</div>

<!-- Upload Box -->
<sec:authorize access="isAuthenticated()">
  <div class="card" style="background-color: var(--white-smoke); border-color: var(--dusty-taupe); margin-bottom: 2rem;">
    <h3 style="font-size: 1.1rem; margin-bottom: 0.75rem;">📤 Share Academic Resource or Study Guide</h3>
    
    <form action="<c:url value='/resources/upload'/>" method="post" enctype="multipart/form-data" style="display: flex; gap: 1rem; flex-wrap: wrap;">
      <input type="text" name="title" class="form-control" placeholder="Resource Title (e.g. Operating Systems Semester Notes)" required style="flex: 2; min-width: 220px;">
      
      <select name="category" class="form-select" required style="flex: 1; min-width: 160px;">
        <option value="Lecture Notes">Lecture Notes</option>
        <option value="Lab Manual">Lab Manual</option>
        <option value="Past Exam Papers">Past Exam Papers</option>
        <option value="Project Template">Project Template</option>
      </select>

      <input type="file" name="file" class="form-control" required style="flex: 1; min-width: 200px;">

      <button type="submit" class="btn btn-primary">Upload to Library</button>
    </form>
  </div>
</sec:authorize>

<!-- Filter Bar -->
<div class="card" style="padding: 1rem; margin-bottom: 2rem;">
  <form action="<c:url value='/resources'/>" method="get" style="display: flex; gap: 1rem; flex-wrap: wrap;">
    <input type="text" name="query" class="form-control" placeholder="Search resources..." value="${query}" style="flex: 1; min-width: 240px;">
    
    <select name="category" class="form-select" style="width: 200px;">
      <option value="">All Categories</option>
      <option value="Lecture Notes" ${category == 'Lecture Notes' ? 'selected' : ''}>Lecture Notes</option>
      <option value="Lab Manual" ${category == 'Lab Manual' ? 'selected' : ''}>Lab Manual</option>
      <option value="Past Exam Papers" ${category == 'Past Exam Papers' ? 'selected' : ''}>Past Exam Papers</option>
      <option value="Project Template" ${category == 'Project Template' ? 'selected' : ''}>Project Template</option>
    </select>

    <button type="submit" class="btn btn-primary">Filter Resources</button>
  </form>
</div>

<!-- Resource Items Table/Grid -->
<div class="card" style="padding: 0; overflow: hidden;">
  <table style="width: 100%; border-collapse: collapse; text-align: left; font-size: 0.9rem;">
    <thead>
      <tr style="background-color: var(--white-smoke); border-bottom: 1px solid var(--dusty-taupe);">
        <th style="padding: 1rem 1.25rem;">Resource Title</th>
        <th style="padding: 1rem 1.25rem;">Category</th>
        <th style="padding: 1rem 1.25rem;">Department</th>
        <th style="padding: 1rem 1.25rem;">Format & Size</th>
        <th style="padding: 1rem 1.25rem;">Downloads</th>
        <th style="padding: 1rem 1.25rem; text-align: right;">Action</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="res" items="${resources}">
        <tr style="border-bottom: 1px solid var(--border-color);">
          <td style="padding: 1rem 1.25rem; font-weight: 700;">
            📄 ${res.title}
          </td>
          <td style="padding: 1rem 1.25rem;">
            <span class="badge badge-taupe">${res.category}</span>
          </td>
          <td style="padding: 1rem 1.25rem; color: var(--stone-brown);">${res.dept}</td>
          <td style="padding: 1rem 1.25rem; color: var(--stone-brown);">${res.format} (${res.size})</td>
          <td style="padding: 1rem 1.25rem; font-weight: 600;">📥 ${res.downloadCount}</td>
          <td style="padding: 1rem 1.25rem; text-align: right;">
            <form action="<c:url value='/resources/${res.id}/download'/>" method="post">
              <button type="submit" class="btn btn-sm btn-primary">Download File &rarr;</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<jsp:include page="../includes/footer.jsp" />
