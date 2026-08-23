<c:set var="pageTitle" value="Collaboration Circles – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Collaboration Circles</h1>
    <p class="page-subtitle">Domain-focused interest groups and tech communities across campus</p>
  </div>
</div>

<div class="grid grid-cols-3">
  <c:forEach var="circle" items="${circles}">
    <c:set var="isJoined" value="${joinedCircleIds.contains(circle.id)}" />
    
    <div class="card" style="display: flex; flex-direction: column; height: 100%; margin-bottom: 0;">
      <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.75rem;">
        <span style="font-size: 2rem;">${circle.icon}</span>
        <span class="badge badge-taupe">👥 ${circle.memberCount} Members</span>
      </div>

      <h3 style="font-size: 1.25rem; margin-bottom: 0.3rem;">${circle.name}</h3>
      <span class="badge badge-bordeaux" style="width: fit-content; margin-bottom: 0.75rem;">${circle.category}</span>

      <p style="color: var(--stone-brown); font-size: 0.9rem; margin-bottom: 1.25rem; flex: 1; line-height: 1.6;">
        ${circle.description}
      </p>

      <div style="margin-top: auto; padding-top: 0.75rem; border-top: 1px solid var(--border-color);">
        <sec:authorize access="isAuthenticated()">
          <c:choose>
            <c:when test="${isJoined}">
              <form action="<c:url value='/circles/${circle.id}/leave'/>" method="post">
                <button type="submit" class="btn btn-sm btn-outline" style="width: 100%; color: var(--stone-brown);">
                  Leave Circle
                </button>
              </form>
            </c:when>
            <c:otherwise>
              <form action="<c:url value='/circles/${circle.id}/join'/>" method="post">
                <button type="submit" class="btn btn-sm btn-primary" style="width: 100%;">
                  Join Circle +
                </button>
              </form>
            </c:otherwise>
          </c:choose>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
          <a href="<c:url value='/login'/>" class="btn btn-sm btn-outline" style="width: 100%;">Log In to Join</a>
        </sec:authorize>
      </div>
    </div>
  </c:forEach>
</div>

<jsp:include page="../includes/footer.jsp" />
