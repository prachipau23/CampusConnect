<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Internships - CampusConnect</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f5f5f5; margin: 0; }
        .header { background: #1a5276; color: white; padding: 1rem 2rem; }
        .container { max-width: 1000px; margin: 2rem auto; padding: 0 1rem; }
        .card { background: white; border-radius: 10px; padding: 1.5rem; margin-bottom: 1rem; box-shadow: 0 2px 8px rgba(0,0,0,0.08); }
        .stipend { color: #27ae60; font-weight: bold; font-size: 1.1rem; }
        .badge { background: #d6eaf8; color: #1a5276; padding: 3px 10px; border-radius: 12px; font-size: 0.8rem; }
        .deadline { color: #e74c3c; font-weight: bold; }
    </style>
</head>
<body>
<div class="header"><h1>💼 Internship Board</h1></div>
<div class="container">
    <c:forEach var="i" items="${internships}">
        <div class="card">
            <div style="display:flex; justify-content:space-between; align-items:start;">
                <div>
                    <h2 style="margin:0 0 0.25rem;">${i.title}</h2>
                    <h3 style="margin:0 0 0.5rem; color:#1a5276;">${i.company}</h3>
                    <span class="badge">${i.mode}</span> &nbsp;
                    <span class="badge">${i.duration}</span>
                    <p>${i.description}</p>
                    <p><strong>Location:</strong> ${i.location}</p>
                    <p class="deadline">⏰ Apply by: ${i.deadline}</p>
                </div>
                <div style="text-align:right;">
                    <p class="stipend">₹<fmt:formatNumber value="${i.stipend}" type="number" maxFractionDigits="0"/>/month</p>
                    <c:if test="${not empty i.applyUrl}">
                        <a href="${i.applyUrl}" target="_blank" style="background:#1a5276;color:white;padding:8px 16px;border-radius:6px;text-decoration:none;">Apply Now →</a>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
