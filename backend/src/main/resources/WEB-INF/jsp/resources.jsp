<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><title>Resources - CampusConnect</title>
<style>body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;margin:0}.header{background:#117a65;color:white;padding:1rem 2rem}.container{max-width:1000px;margin:2rem auto;padding:0 1rem}.card{background:white;border-radius:10px;padding:1.5rem;margin-bottom:1rem;box-shadow:0 2px 8px rgba(0,0,0,.08)}.badge{background:#d1f2eb;color:#117a65;padding:3px 8px;border-radius:12px;font-size:.8rem}</style>
</head>
<body>
<div class="header"><h1>📚 Resource Library</h1></div>
<div class="container">
<c:forEach var="r" items="${resources}">
<div class="card">
  <div style="display:flex;justify-content:space-between;align-items:center">
    <div>
      <h3 style="margin:0 0 .25rem">${r.title}</h3>
      <span class="badge">${r.category}</span>&nbsp;<span class="badge">${r.fileType}</span>
      <p>${r.description}</p>
    </div>
    <a href="/api/resources/${r.id}/download"
       style="background:#117a65;color:white;padding:8px 16px;border-radius:6px;text-decoration:none;white-space:nowrap">
      ⬇ Download
    </a>
  </div>
</div>
</c:forEach>
</div>
</body>
</html>
