<c:set var="pageTitle" value="Login – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div style="max-width: 440px; margin: 3rem auto;">
  <div class="card">
    <div style="text-align: center; margin-bottom: 1.5rem;">
      <h2 style="font-size: 1.75rem; margin-bottom: 0.25rem;">Welcome Back</h2>
      <p style="color: var(--stone-brown); font-size: 0.9rem;">Sign in to your student collaboration account</p>
    </div>

    <form action="<c:url value='/login'/>" method="post">
      <div class="form-group">
        <label class="form-label">Campus Email Address *</label>
        <input type="email" name="username" class="form-control" placeholder="student@university.edu" required autofocus>
      </div>

      <div class="form-group">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.4rem;">
          <label class="form-label" style="margin-bottom: 0;">Password *</label>
          <a href="<c:url value='/forgot-password'/>" style="font-size: 0.8rem;">Forgot Password?</a>
        </div>
        <input type="password" name="password" class="form-control" placeholder="••••••••" required>
      </div>

      <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 0.5rem; padding: 0.75rem;">
        Sign In to Platform &rarr;
      </button>
    </form>

    <div style="text-align: center; margin-top: 1.5rem; padding-top: 1rem; border-top: 1px solid var(--border-color); font-size: 0.875rem; color: var(--stone-brown);">
      Don't have a student account? <a href="<c:url value='/register'/>" style="font-weight: 700;">Register Here</a>
    </div>
  </div>
</div>

<jsp:include page="../includes/footer.jsp" />
