<c:set var="pageTitle" value="Student Profile – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div style="display: grid; grid-template-columns: 320px 1fr; gap: 2rem; align-items: start;">
  
  <!-- Left Column: Profile Card -->
  <div class="card" style="text-align: center;">
    <div style="width: 100px; height: 100px; border-radius: var(--radius-full); background-color: var(--dusty-taupe); margin: 0 auto 1rem; display: flex; align-items: center; justify-content: center; font-size: 2.5rem; color: var(--white); overflow: hidden; border: 3px solid var(--night-bordeaux);">
      <c:choose>
        <c:when test="${not empty profile.profilePicPath}">
          <img src="<c:url value='${profile.profilePicPath}'/>" alt="Profile" style="width: 100%; height: 100%; object-fit: cover;">
        </c:when>
        <c:otherwise>
          👤
        </c:otherwise>
      </c:choose>
    </div>

    <h2 style="font-size: 1.35rem; margin-bottom: 0.2rem;">${not empty profile.fullName ? profile.fullName : 'Student Member'}</h2>
    <p style="color: var(--stone-brown); font-weight: 600; font-size: 0.9rem; margin-bottom: 0.75rem;">
      ${not empty profile.department ? profile.department : 'Department Not Specified'}
    </p>

    <div style="display: flex; gap: 0.5rem; justify-content: center; flex-wrap: wrap; margin-bottom: 1.25rem;">
      <span class="badge badge-bordeaux">${not empty profile.academicYear ? profile.academicYear : 'Year N/A'}</span>
      <span class="badge badge-taupe">${not empty profile.college ? profile.college : 'Main Campus'}</span>
    </div>

    <c:if test="${isOwnProfile}">
      <div style="background-color: var(--white-smoke); padding: 0.85rem; border-radius: var(--radius-sm); margin-bottom: 1.25rem; border: 1px solid var(--dusty-taupe);">
        <div style="display: flex; justify-content: space-between; font-size: 0.8rem; font-weight: 700; margin-bottom: 0.3rem;">
          <span>Profile Strength</span>
          <span style="color: var(--night-bordeaux);">${profile.completionPercentage}%</span>
        </div>
        <div style="width: 100%; height: 8px; background-color: #e2e8f0; border-radius: 4px; overflow: hidden;">
          <div style="width: ${profile.completionPercentage}%; height: 100%; background-color: var(--night-bordeaux);"></div>
        </div>
      </div>

      <a href="<c:url value='/profile/edit'/>" class="btn btn-outline" style="width: 100%;">
        ✏️ Edit Profile Details
      </a>
    </c:if>
  </div>

  <!-- Right Column: Bio, Skills & Resume -->
  <div style="display: flex; flex-direction: column; gap: 1.5rem;">
    
    <div class="card">
      <h3 style="font-size: 1.15rem; margin-bottom: 0.85rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.5rem;">
        About Me & Research Interests
      </h3>
      <p style="color: var(--stone-brown); font-size: 0.95rem; line-height: 1.7;">
        ${not empty profile.aboutMe ? profile.aboutMe : 'No biography provided yet.'}
      </p>
    </div>

    <div class="card">
      <h3 style="font-size: 1.15rem; margin-bottom: 0.85rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.5rem;">
        Technical Skills & Stack
      </h3>
      <div style="display: flex; gap: 0.5rem; flex-wrap: wrap;">
        <c:choose>
          <c:when test="${not empty profile.skills}">
            <c:forEach var="skill" items="${profile.skills.split(',')}">
              <span class="badge badge-taupe" style="padding: 0.4rem 0.85rem; font-size: 0.85rem;">${skill.trim()}</span>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <span style="color: var(--stone-brown); font-size: 0.9rem;">No technical skills listed yet.</span>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <div class="card">
      <h3 style="font-size: 1.15rem; margin-bottom: 0.85rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.5rem;">
        Links & Attachments
      </h3>
      <div style="display: flex; flex-direction: column; gap: 0.75rem;">
        <c:if test="${not empty profile.githubUrl}">
          <div style="display: flex; align-items: center; gap: 0.5rem;">
            <span>🌐 <strong>GitHub:</strong></span>
            <a href="${profile.githubUrl}" target="_blank" rel="noopener">${profile.githubUrl}</a>
          </div>
        </c:if>
        <c:if test="${not empty profile.linkedinUrl}">
          <div style="display: flex; align-items: center; gap: 0.5rem;">
            <span>💼 <strong>LinkedIn:</strong></span>
            <a href="${profile.linkedinUrl}" target="_blank" rel="noopener">${profile.linkedinUrl}</a>
          </div>
        </c:if>
        <c:if test="${not empty profile.resumePath}">
          <div style="display: flex; align-items: center; gap: 0.5rem; margin-top: 0.25rem;">
            <span>📄 <strong>Resume Attachment:</strong></span>
            <a href="<c:url value='${profile.resumePath}'/>" target="_blank" class="btn btn-sm btn-primary">
              Download Resume PDF &rarr;
            </a>
          </div>
        </c:if>
      </div>
    </div>

  </div>

</div>

<jsp:include page="../includes/footer.jsp" />
