<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>Team Workspace</title>
<style>body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;margin:0}.header{background:#1f618d;color:white;padding:1rem 2rem}.container{max-width:800px;margin:2rem auto;padding:0 1rem}.post{background:white;border-radius:8px;padding:1rem;margin-bottom:.75rem;box-shadow:0 1px 4px rgba(0,0,0,.08)}.author{font-weight:bold;color:#1f618d}.timestamp{font-size:.8rem;color:#aaa;float:right}</style>
</head><body>
<div class="header"><h1>🖥 Team Workspace — ${team.name}</h1></div>
<div class="container">
<c:forEach var="p" items="${posts}">
<div class="post">
  <span class="author">${p.author.username}</span>
  <span class="timestamp">${p.createdAt}</span>
  <p style="margin:.5rem 0 0">${p.content}</p>
</div>
</c:forEach>
<c:if test="${empty posts}"><p style="color:#888">No posts yet. Start the conversation!</p></c:if>
</div>
</body></html>
