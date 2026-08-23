<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><title>Circles - CampusConnect</title>
<style>body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;margin:0}.header{background:#884ea0;color:white;padding:1rem 2rem}.container{max-width:1000px;margin:2rem auto;padding:0 1rem}.card{background:white;border-radius:10px;padding:1.5rem;margin-bottom:1rem;box-shadow:0 2px 8px rgba(0,0,0,.08)}</style>
</head>
<body>
<div class="header"><h1>⭕ Circles</h1></div>
<div class="container">
<c:forEach var="c" items="${circles}">
<div class="card">
  <h2 style="margin:0 0 .5rem">${c.iconEmoji} ${c.name}</h2>
  <span style="background:#f0e6ff;color:#884ea0;padding:3px 8px;border-radius:12px;font-size:.8rem">${c.category}</span>
  <p>${c.description}</p>
  <p><strong>${c.memberCount}</strong> members</p>
</div>
</c:forEach>
</div>
</body>
</html>
