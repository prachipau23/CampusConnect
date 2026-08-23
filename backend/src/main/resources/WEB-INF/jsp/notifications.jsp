<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>Notifications</title>
<style>body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;margin:0}.header{background:#1e8449;color:white;padding:1rem 2rem}.container{max-width:900px;margin:2rem auto;padding:0 1rem}.item{background:white;border-radius:8px;padding:1rem;margin-bottom:.5rem;box-shadow:0 1px 4px rgba(0,0,0,.08)}.unread{border-left:4px solid #1e8449}</style>
</head><body>
<div class="header"><h1>🔔 Notifications</h1></div>
<div class="container">
<c:forEach var="n" items="${notifications}">
<div class="item ${n.read ? '' : 'unread'}">
  <strong>${n.targetEntityType}</strong> — ${n.message}
  <small style="float:right;color:#888">${n.createdAt}</small>
</div>
</c:forEach>
</div>
</body></html>
