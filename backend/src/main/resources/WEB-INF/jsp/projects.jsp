<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><title>Projects - CampusConnect</title>
<style>body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;margin:0}.header{background:#2e4057;color:white;padding:1rem 2rem}.container{max-width:1000px;margin:2rem auto;padding:0 1rem}.card{background:white;border-radius:10px;padding:1.5rem;margin-bottom:1rem;box-shadow:0 2px 8px rgba(0,0,0,.08)}.badge{background:#e8f4f8;color:#2e4057;padding:3px 8px;border-radius:12px;font-size:.8rem}.status-ACTIVE{color:#27ae60}.status-COMPLETED{color:#3498db}.status-ARCHIVED{color:#888}</style>
</head>
<body>
<div class="header"><h1>🚀 Project Showcase</h1></div>
<div class="container">
<c:forEach var="p" items="${projects}">
<div class="card">
  <div style="display:flex;justify-content:space-between">
    <h2 style="margin:0 0 .5rem">${p.title}</h2>
    <span class="status-${p.status}">${p.status}</span>
  </div>
  <p>${p.description}</p>
  <p><strong>Tech:</strong> ${p.techStack}</p>
  <p><strong>Owner:</strong> ${p.owner.username}</p>
  <c:if test="${not empty p.githubUrl}"><a href="${p.githubUrl}" target="_blank">GitHub →</a></c:if>
</div>
</c:forEach>
</div>
</body>
</html>
