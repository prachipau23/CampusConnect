<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><title>Teams - CampusConnect</title>
<style>body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;margin:0}.header{background:#1f618d;color:white;padding:1rem 2rem}.container{max-width:1000px;margin:2rem auto;padding:0 1rem}.card{background:white;border-radius:10px;padding:1.5rem;margin-bottom:1rem;box-shadow:0 2px 8px rgba(0,0,0,.08)}.open{color:#27ae60}.closed{color:#e74c3c}</style>
</head>
<body>
<div class="header"><h1>👥 Teams</h1></div>
<div class="container">
<c:forEach var="t" items="${teams}">
<div class="card">
  <div style="display:flex;justify-content:space-between">
    <h2 style="margin:0 0 .5rem">${t.name}</h2>
    <span class="${t.status == 'OPEN' ? 'open' : 'closed'}">${t.status}</span>
  </div>
  <p>${t.description}</p>
  <p>Max size: ${t.maxSize} | <a href="/views/teams/${t.id}/workspace">View Workspace →</a></p>
</div>
</c:forEach>
</div>
</body>
</html>
