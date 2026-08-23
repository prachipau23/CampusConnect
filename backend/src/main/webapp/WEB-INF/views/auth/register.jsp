<c:set var="pageTitle" value="Register Account – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div style="max-width: 520px; margin: 2rem auto;">
  <div class="card">
    <div style="text-align: center; margin-bottom: 1.5rem;">
      <h2 style="font-size: 1.75rem; margin-bottom: 0.25rem;">Create Student Account</h2>
      <p style="color: var(--stone-brown); font-size: 0.9rem;">Join the university innovation ecosystem</p>
    </div>

    <form action="<c:url value='/register'/>" method="post">
      <div class="form-group">
        <label class="form-label">Full Name *</label>
        <input type="text" name="fullName" class="form-control" placeholder="e.g. Alex Rivera" required>
      </div>

      <div class="form-group">
        <label class="form-label">Campus Email Address *</label>
        <input type="email" name="email" class="form-control" placeholder="student@university.edu" required>
      </div>

      <div class="grid grid-cols-2">
        <div class="form-group">
          <label class="form-label">Department *</label>
          <select name="department" class="form-select" required>
            <option value="Computer Science & Engineering">Computer Science & Engineering</option>
            <option value="Electronics & Communication">Electronics & Communication</option>
            <option value="Information Technology">Information Technology</option>
            <option value="Mechanical Engineering">Mechanical Engineering</option>
            <option value="Data Science & AI">Data Science & AI</option>
          </select>
        </div>

        <div class="form-group">
          <label class="form-label">Academic Year *</label>
          <select name="academicYear" class="form-select" required>
            <option value="1st Year (Freshman)">1st Year (Freshman)</option>
            <option value="2nd Year (Sophomore)">2nd Year (Sophomore)</option>
            <option value="3rd Year (Junior)">3rd Year (Junior)</option>
            <option value="4th Year (Senior)">4th Year (Senior)</option>
            <option value="Postgraduate">Postgraduate</option>
          </select>
        </div>
      </div>

      <div class="form-group">
        <label class="form-label">Password *</label>
        <input type="password" name="password" class="form-control" placeholder="Create strong password" required minlength="6">
      </div>

      <div style="background-color: var(--white-smoke); padding: 1rem; border-radius: var(--radius-sm); margin-bottom: 1.25rem; border: 1px solid var(--dusty-taupe);">
        <div style="font-size: 0.85rem; font-weight: 700; margin-bottom: 0.5rem; color: var(--black);">Password Recovery Setup</div>
        
        <div class="form-group">
          <label class="form-label">Security Question *</label>
          <select name="securityQuestion" class="form-select" required>
            <option value="What was your first pet's name?">What was your first pet's name?</option>
            <option value="What is your mother's maiden name?">What is your mother's maiden name?</option>
            <option value="What high school did you attend?">What high school did you attend?</option>
            <option value="What is your favorite book?">What is your favorite book?</option>
          </select>
        </div>

        <div class="form-group" style="margin-bottom: 0;">
          <label class="form-label">Security Answer *</label>
          <input type="text" name="securityAnswer" class="form-control" placeholder="Your secret answer" required>
        </div>
      </div>

      <button type="submit" class="btn btn-primary" style="width: 100%; padding: 0.75rem;">
        Complete Registration &rarr;
      </button>
    </form>

    <div style="text-align: center; margin-top: 1.5rem; padding-top: 1rem; border-top: 1px solid var(--border-color); font-size: 0.875rem; color: var(--stone-brown);">
      Already have an account? <a href="<c:url value='/login'/>" style="font-weight: 700;">Log In</a>
    </div>
  </div>
</div>

<jsp:include page="../includes/footer.jsp" />
