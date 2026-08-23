<c:set var="pageTitle" value="Student Directory – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Student Directory</h1>
    <p class="page-subtitle">Connect with peers across departments, find project teammates, and build your campus network</p>
  </div>
</div>

<!-- Filter Bar -->
<div class="card" style="padding: 1rem; margin-bottom: 2rem;">
  <form action="<c:url value='/directory'/>" method="get" style="display: flex; gap: 1rem; flex-wrap: wrap;">
    <input type="text" name="query" class="form-control" placeholder="Search by student name or skills (e.g. React, Java)..." value="${query}" style="flex: 1; min-width: 240px;">
    
    <select name="department" class="form-select" style="width: 220px;">
      <option value="">All Departments</option>
      <option value="Computer Science & Engineering" ${department == 'Computer Science & Engineering' ? 'selected' : ''}>CS & Engineering</option>
      <option value="Electronics & Communication" ${department == 'Electronics & Communication' ? 'selected' : ''}>Electronics & Comm</option>
      <option value="Information Technology" ${department == 'Information Technology' ? 'selected' : ''}>Information Tech</option>
      <option value="Mechanical Engineering" ${department == 'Mechanical Engineering' ? 'selected' : ''}>Mechanical Eng</option>
      <option value="Data Science & AI" ${department == 'Data Science & AI' ? 'selected' : ''}>Data Science & AI</option>
    </select>

    <select name="year" class="form-select" style="width: 180px;">
      <option value="">All Academic Years</option>
      <option value="1st Year (Freshman)" ${year == '1st Year (Freshman)' ? 'selected' : ''}>1st Year</option>
      <option value="2nd Year (Sophomore)" ${year == '2nd Year (Sophomore)' ? 'selected' : ''}>2nd Year</option>
      <option value="3rd Year (Junior)" ${year == '3rd Year (Junior)' ? 'selected' : ''}>3rd Year</option>
      <option value="4th Year (Senior)" ${year == '4th Year (Senior)' ? 'selected' : ''}>4th Year</option>
    </select>

    <button type="submit" class="btn btn-primary">Filter Students</button>
  </form>
</div>

<!-- Student Grid -->
<div class="grid grid-cols-3">
  <c:forEach var="student" items="${students}">
    <div class="card" style="text-align: center; display: flex; flex-direction: column; height: 100%; margin-bottom: 0;">
      <div style="width: 70px; height: 70px; border-radius: var(--radius-full); background-color: var(--dusty-taupe); margin: 0 auto 0.75rem; display: flex; align-items: center; justify-content: center; font-size: 1.8rem; color: var(--white); border: 2px solid var(--night-bordeaux); overflow: hidden;">
        <c:choose>
          <c:when test="${not empty student.profilePicPath}">
            <img src="<c:url value='${student.profilePicPath}'/>" style="width: 100%; height: 100%; object-fit: cover;">
          </c:when>
          <c:otherwise>👤</c:otherwise>
        </c:choose>
      </div>

      <h3 style="font-size: 1.15rem; margin-bottom: 0.2rem;">${student.fullName}</h3>
      <p style="color: var(--stone-brown); font-size: 0.85rem; margin-bottom: 0.5rem; font-weight: 600;">${student.department}</p>

      <div style="margin-bottom: 0.75rem;">
        <span class="badge badge-taupe">${student.academicYear}</span>
      </div>

      <div style="margin-top: auto; padding-top: 0.75rem; border-top: 1px solid var(--border-color);">
        <div style="font-size: 0.8rem; color: var(--stone-brown); margin-bottom: 0.75rem; min-height: 2.2rem; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;">
          <strong>Skills:</strong> ${not empty student.skills ? student.skills : 'Skills pending update'}
        </div>

        <a href="<c:url value='/profile/${student.id}'/>" class="btn btn-sm btn-outline" style="width: 100%;">
          View Full Profile &rarr;
        </a>
      </div>
    </div>
  </c:forEach>
</div>

<jsp:include page="../includes/footer.jsp" />
