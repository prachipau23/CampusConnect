<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - CampusConnect</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f5f5f5; margin: 0; }
        .header { background: #2c3e50; color: white; padding: 1rem 2rem; }
        .header h1 { margin: 0; font-size: 1.5rem; }
        .container { max-width: 1200px; margin: 2rem auto; padding: 0 1rem; }
        table { width: 100%; border-collapse: collapse; background: white; box-shadow: 0 2px 8px rgba(0,0,0,0.1); border-radius: 8px; overflow: hidden; }
        th { background: #34495e; color: white; padding: 12px 16px; text-align: left; }
        td { padding: 10px 16px; border-bottom: 1px solid #eee; }
        tr:last-child td { border-bottom: none; }
        tr:hover { background: #f9f9f9; }
        .gpa-high { color: #27ae60; font-weight: bold; }
        .gpa-mid  { color: #e67e22; font-weight: bold; }
        .gpa-low  { color: #e74c3c; font-weight: bold; }
        .badge { padding: 3px 8px; border-radius: 12px; font-size: 0.75rem; }
        .badge-student { background: #d5e8d4; color: #2c7a4f; }
    </style>
</head>
<body>
<div class="header">
    <h1>🎓 CampusConnect — Admin / Teacher Dashboard</h1>
    <p style="margin:0.25rem 0 0; font-size:0.9rem; opacity:0.8;">Student Performance Overview</p>
</div>
<div class="container">
    <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; margin-bottom: 1.5rem;">
        <h2 style="margin: 0;">Students Overview (${profiles.size()} total)</h2>
        <form action="<c:url value='/views/admin/dashboard'/>" method="get" style="display: flex; gap: 0.75rem; align-items: center; background: white; padding: 0.5rem 1rem; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.08);">
            <label style="font-size: 0.85rem; font-weight: bold; color: #555;">Filter GPA:</label>
            <input type="number" name="minGpa" step="0.1" min="0" max="10" placeholder="Min" value="${minGpa}" style="width: 70px; padding: 4px 8px; border: 1px solid #ccc; border-radius: 4px;">
            <span>-</span>
            <input type="number" name="maxGpa" step="0.1" min="0" max="10" placeholder="Max" value="${maxGpa}" style="width: 70px; padding: 4px 8px; border: 1px solid #ccc; border-radius: 4px;">
            <button type="submit" style="background: #2c3e50; color: white; border: none; padding: 5px 12px; border-radius: 4px; cursor: pointer; font-size: 0.85rem;">Filter</button>
            <a href="<c:url value='/views/admin/dashboard'/>" style="color: #666; font-size: 0.85rem; text-decoration: none; margin-left: 0.25rem;">Clear</a>
        </form>
    </div>
    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Department</th>
                <th>Year</th>
                <th>GPA</th>
                <th>Resume</th>
                <th>Performance Notes</th>
                <th>Skills</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="profile" items="${profiles}" varStatus="loop">
            <tr>
                <td>${loop.count}</td>
                <td><strong>${profile.fullName}</strong></td>
                <td>${profile.department}</td>
                <td>${profile.yearOfStudy}</td>
                <td>
                    <c:choose>
                        <c:when test="${profile.gpa != null and profile.gpa >= 9.0}">
                            <span class="gpa-high">${profile.gpa}</span>
                        </c:when>
                        <c:when test="${profile.gpa != null and profile.gpa >= 7.5}">
                            <span class="gpa-mid">${profile.gpa}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="gpa-low">${profile.gpa != null ? profile.gpa : 'N/A'}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty profile.resumeFileName}">
                            <a href="<c:url value='/api/admin/students/${profile.id}/resume/download'/>" style="display: inline-flex; align-items: center; gap: 4px; color: #2980b9; text-decoration: none; font-weight: 600; font-size: 0.85rem;" title="${profile.resumeFileName}">
                                📄 Download
                            </a>
                        </c:when>
                        <c:otherwise>
                            <span style="color: #bbb; font-size: 0.8rem;">None</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${profile.performanceNotes}</td>
                <td style="max-width:200px; font-size:0.85rem;">${profile.skills}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
