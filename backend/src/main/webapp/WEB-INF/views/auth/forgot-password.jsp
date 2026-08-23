<c:set var="pageTitle" value="Password Recovery – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div style="max-width: 440px; margin: 3rem auto;">
  <div class="card">
    <div style="text-align: center; margin-bottom: 1.5rem;">
      <h2 style="font-size: 1.75rem; margin-bottom: 0.25rem;">Password Recovery</h2>
      <p style="color: var(--stone-brown); font-size: 0.9rem;">Reset your password using your security question</p>
    </div>

    <form action="<c:url value='/forgot-password'/>" method="post">
      <div class="form-group">
        <label class="form-label">Registered Campus Email *</label>
        <input type="email" name="email" class="form-control" placeholder="student@university.edu" required autofocus>
      </div>

      <div class="form-group">
        <label class="form-label">Security Answer *</label>
        <input type="text" name="securityAnswer" class="form-control" placeholder="Enter your secret answer" required>
      </div>

      <div class="form-group">
        <label class="form-label">New Password *</label>
        <input type="password" name="newPassword" class="form-control" placeholder="Enter new strong password" required minlength="6">
      </div>

      <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 0.5rem; padding: 0.75rem;">
        Reset Password &rarr;
      </button>
    </form>

    <div style="text-align: center; margin-top: 1.5rem; padding-top: 1rem; border-top: 1px solid var(--border-color); font-size: 0.875rem;">
      <a href="<c:url value='/login'/>" style="font-weight: 700;">&larr; Back to Login</a>
    </div>
  </div>
</div>

<jsp:include page="../includes/footer.jsp" />
