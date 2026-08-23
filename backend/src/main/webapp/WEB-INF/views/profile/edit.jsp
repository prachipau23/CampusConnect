<c:set var="pageTitle" value="Edit Student Profile – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div style="max-width: 680px; margin: 1rem auto;">
  <div class="card">
    <div style="margin-bottom: 1.5rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.75rem;">
      <h2 style="font-size: 1.5rem;">Edit Student Profile</h2>
      <p style="color: var(--stone-brown); font-size: 0.9rem;">Keep your profile up to date for campus project recruiting and team formation</p>
    </div>

    <form action="<c:url value='/profile/edit'/>" method="post" enctype="multipart/form-data">
      
      <div class="form-group">
        <label class="form-label">Full Name *</label>
        <input type="text" name="fullName" class="form-control" value="${profile.fullName}" required>
      </div>

      <div class="grid grid-cols-2">
        <div class="form-group">
          <label class="form-label">University / College *</label>
          <input type="text" name="college" class="form-control" value="${profile.college}" placeholder="e.g. Campus Connect University" required>
        </div>

        <div class="form-group">
          <label class="form-label">Department *</label>
          <input type="text" name="department" class="form-control" value="${profile.department}" placeholder="e.g. Computer Science" required>
        </div>
      </div>

      <div class="form-group">
        <label class="form-label">Academic Year *</label>
        <select name="academicYear" class="form-select" required>
          <option value="1st Year (Freshman)" ${profile.academicYear == '1st Year (Freshman)' ? 'selected' : ''}>1st Year (Freshman)</option>
          <option value="2nd Year (Sophomore)" ${profile.academicYear == '2nd Year (Sophomore)' ? 'selected' : ''}>2nd Year (Sophomore)</option>
          <option value="3rd Year (Junior)" ${profile.academicYear == '3rd Year (Junior)' ? 'selected' : ''}>3rd Year (Junior)</option>
          <option value="4th Year (Senior)" ${profile.academicYear == '4th Year (Senior)' ? 'selected' : ''}>4th Year (Senior)</option>
          <option value="Postgraduate" ${profile.academicYear == 'Postgraduate' ? 'selected' : ''}>Postgraduate</option>
        </select>
      </div>

      <div class="form-group">
        <label class="form-label">Skills & Tech Stack (Comma separated) *</label>
        <input type="text" name="skills" class="form-control" value="${profile.skills}" placeholder="e.g. Java, Spring Boot, React, Python, MySQL, Docker" required>
      </div>

      <div class="form-group">
        <label class="form-label">About Me & Experience</label>
        <textarea name="aboutMe" class="form-control" rows="4" placeholder="Describe your technical background, interest areas, and past project experience...">${profile.aboutMe}</textarea>
      </div>

      <div class="grid grid-cols-2">
        <div class="form-group">
          <label class="form-label">GitHub URL</label>
          <input type="url" name="githubUrl" class="form-control" value="${profile.githubUrl}" placeholder="https://github.com/username">
        </div>

        <div class="form-group">
          <label class="form-label">LinkedIn URL</label>
          <input type="url" name="linkedinUrl" class="form-control" value="${profile.linkedinUrl}" placeholder="https://linkedin.com/in/username">
        </div>
      </div>

      <div class="grid grid-cols-2">
        <div class="form-group">
          <label class="form-label">Upload Profile Picture</label>
          <input type="file" name="picFile" class="form-control" accept="image/*">
        </div>

        <div class="form-group">
          <label class="form-label">Upload Resume (PDF)</label>
          <input type="file" name="resumeFile" class="form-control" accept=".pdf">
        </div>
      </div>

      <div style="display: flex; gap: 1rem; margin-top: 1.5rem; justify-content: flex-end;">
        <a href="<c:url value='/profile'/>" class="btn btn-outline">Cancel</a>
        <button type="submit" class="btn btn-primary">Save Profile Changes &rarr;</button>
      </div>

    </form>
  </div>
</div>

<jsp:include page="../includes/footer.jsp" />
