<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${pageTitle != null ? pageTitle : 'CampusConnect – Student Collaboration Platform'}</title>
  <link rel="stylesheet" href="<c:url value='/css/styles.css'/>">
</head>
<body>

  <!-- Navigation Bar -->
  <header class="navbar">
    <div class="nav-container">
      <a href="<c:url value='/projects'/>" class="brand-logo">
        <span class="brand-badge">CC</span>
        CampusConnect
      </a>

      <nav>
        <ul class="nav-menu">
          <li><a href="<c:url value='/projects'/>" class="nav-link">💡 Projects</a></li>
          <li><a href="<c:url value='/directory'/>" class="nav-link">👥 Directory</a></li>
          <li><a href="<c:url value='/circles'/>" class="nav-link">⭕ Circles</a></li>
          <li><a href="<c:url value='/teams'/>" class="nav-link">🚀 Teams</a></li>
          <li><a href="<c:url value='/hackathons'/>" class="nav-link">🏆 Hackathons</a></li>
          <li><a href="<c:url value='/internships'/>" class="nav-link">💼 Internships</a></li>
          <li><a href="<c:url value='/resources'/>" class="nav-link">📚 Resources</a></li>
        </ul>
      </nav>

      <div class="nav-actions">
        <sec:authorize access="isAuthenticated()">
          <a href="<c:url value='/notifications'/>" class="btn btn-sm btn-outline">
            🔔 Notifications
          </a>
          <a href="<c:url value='/profile'/>" class="btn btn-sm btn-outline">
            👤 My Profile
          </a>
          <sec:authorize access="hasRole('ADMIN')">
            <a href="<c:url value='/admin/dashboard'/>" class="btn btn-sm btn-primary">
              ⚡ Admin
            </a>
          </sec:authorize>
          <a href="<c:url value='/logout'/>" class="btn btn-sm btn-outline">Logout</a>
        </sec:authorize>

        <sec:authorize access="!isAuthenticated()">
          <a href="<c:url value='/login'/>" class="btn btn-sm btn-outline">Log In</a>
          <a href="<c:url value='/register'/>" class="btn btn-sm btn-primary">Get Started</a>
        </sec:authorize>
      </div>
    </div>
  </header>

  <main class="main-content">
    <c:if test="${not empty successMessage}">
      <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
      <div class="alert alert-error">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty infoMessage}">
      <div class="alert alert-error" style="background-color: #e8f0fe; color: #1a73e8; border-color: #d2e3fc;">${infoMessage}</div>
    </c:if>
