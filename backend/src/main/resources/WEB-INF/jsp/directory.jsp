<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>Directory</title>
<style>body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;margin:0}.header{background:#2c3e50;color:white;padding:1rem 2rem}.container{max-width:1000px;margin:2rem auto;padding:0 1rem}.card{background:white;border-radius:8px;padding:1rem;margin-bottom:.5rem;box-shadow:0 1px 4px rgba(0,0,0,.08);display:flex;align-items:center;gap:1rem}.avatar{width:48px;height:48px;border-radius:50%;background:#2c3e50;color:white;display:flex;align-items:center;justify-content:center;font-weight:bold}</style>
</head><body>
<div class="header"><h1>📋 Student Directory</h1></div>
<div class="container">
<form method="get" style="margin-bottom:1rem"><input name="q" placeholder="Search students..." style="padding:8px 16px;width:300px;border-radius:6px;border:1px solid #ccc"> <button type="submit" style="padding:8px 16px;background:#2c3e50;color:white;border:none;border-radius:6px;cursor:pointer">Search</button></form>
<c:forEach var="p" items="${profiles}">
<div class="card">
  <div class="avatar">${p.avatarInitials}</div>
  <div>
    <strong>${p.fullName}</strong><br>
    <small>${p.department} | ${p.yearOfStudy}</small><br>
    <small>${p.skills}</small>
  </div>
</div>
</c:forEach>
</div>
</body></html>
