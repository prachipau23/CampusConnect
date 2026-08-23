<c:set var="pageTitle" value="Notifications – CampusConnect" scope="request" />
<jsp:include page="../includes/header.jsp" />

<div class="page-header">
  <div>
    <h1 class="page-title">Notifications Center</h1>
    <p class="page-subtitle">Team application updates, campus announcements, and activity alerts</p>
  </div>
  <c:if test="${unreadCount > 0}">
    <form action="<c:url value='/notifications/mark-all-read'/>" method="post">
      <button type="submit" class="btn btn-outline">
        Mark All as Read (${unreadCount})
      </button>
    </form>
  </c:if>
</div>

<div class="card" style="padding: 0; overflow: hidden;">
  <c:choose>
    <c:when test="${not empty notifications}">
      <div style="display: flex; flex-direction: column;">
        <c:forEach var="n" items="${notifications}">
          <div style="padding: 1.25rem; border-bottom: 1px solid var(--border-color); display: flex; justify-content: space-between; align-items: flex-start; ${n.unread ? 'background-color: #fbf7f6;' : ''}">
            <div style="display: flex; gap: 1rem; align-items: flex-start;">
              <span style="font-size: 1.5rem; background-color: var(--white-smoke); padding: 0.5rem; border-radius: var(--radius-sm);">${n.icon}</span>
              <div>
                <div style="display: flex; gap: 0.5rem; align-items: center; margin-bottom: 0.2rem;">
                  <h4 style="font-size: 1rem;">${n.title}</h4>
                  <c:if test="${n.unread}">
                    <span class="badge badge-bordeaux" style="font-size: 0.65rem;">NEW</span>
                  </c:if>
                </div>
                <p style="color: var(--stone-brown); font-size: 0.9rem;">${n.message}</p>
                <span style="font-size: 0.75rem; color: var(--stone-brown); margin-top: 0.25rem; display: block;">${n.createdAt}</span>
              </div>
            </div>

            <form action="<c:url value='/notifications/${n.id}/toggle-read'/>" method="post">
              <button type="submit" class="btn btn-sm btn-outline">
                ${n.unread ? 'Mark Read' : 'Unread'}
              </button>
            </form>
          </div>
        </c:forEach>
      </div>
    </c:when>
    <c:otherwise>
      <div style="padding: 3rem; text-align: center; color: var(--stone-brown);">
        <p style="font-size: 1.1rem;">You have no notifications at this time.</p>
      </div>
    </c:otherwise>
  </c:choose>
</div>

<jsp:include page="../includes/footer.jsp" />
