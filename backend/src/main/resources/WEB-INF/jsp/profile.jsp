<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>Profile</title>
<style>body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;margin:0}.header{background:#2c3e50;color:white;padding:1rem 2rem}.container{max-width:700px;margin:2rem auto;padding:0 1rem}.card{background:white;border-radius:10px;padding:2rem;box-shadow:0 2px 8px rgba(0,0,0,.1)}.gpa{color:#27ae60;font-size:1.5rem;font-weight:bold}</style>
</head><body>
<div class="header"><h1>👤 Student Profile</h1></div>
<div class="container">
<c:if test="${not empty profile}">
<div class="card">
  <h2>${profile.fullName}</h2>
  <p>${profile.department} | ${profile.yearOfStudy}</p>
  <p><strong>GPA:</strong> <span class="gpa">${profile.gpa}</span></p>
  <p>${profile.bio}</p>
  <p><strong>Skills:</strong> ${profile.skills}</p>
  <p><strong>Performance:</strong> ${profile.performanceNotes}</p>
  <c:if test="${not empty profile.githubUrl}"><a href="${profile.githubUrl}" target="_blank">GitHub</a>&nbsp;</c:if>
  <c:if test="${not empty profile.linkedinUrl}"><a href="${profile.linkedinUrl}" target="_blank">LinkedIn</a></c:if>
</div>
</c:if>
</div>
</body></html>
