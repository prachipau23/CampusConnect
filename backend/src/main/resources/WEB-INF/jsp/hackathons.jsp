<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hackathons - CampusConnect</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f5f5f5; margin: 0; }
        .header { background: #6c3483; color: white; padding: 1rem 2rem; }
        .container { max-width: 1000px; margin: 2rem auto; padding: 0 1rem; }
        .card { background: white; border-radius: 10px; padding: 1.5rem; margin-bottom: 1rem; box-shadow: 0 2px 8px rgba(0,0,0,0.08); }
        .prize { color: #27ae60; font-weight: bold; font-size: 1.2rem; }
        .badge { background: #f0e6ff; color: #6c3483; padding: 3px 10px; border-radius: 12px; font-size: 0.8rem; }
        .dates { color: #888; font-size: 0.9rem; }
    </style>
</head>
<body>
<div class="header"><h1>🏆 Hackathon Board</h1></div>
<div class="container">
    <c:forEach var="h" items="${hackathons}">
        <div class="card">
            <div style="display:flex; justify-content:space-between; align-items:start;">
                <div>
                    <h2 style="margin:0 0 0.5rem;">${h.name}</h2>
                    <span class="badge">${h.mode}</span>
                    <p>${h.description}</p>
                    <p><strong>Organizer:</strong> ${h.organizer} | <strong>Location:</strong> ${h.location}</p>
                    <p class="dates">📅 ${h.startDate} → ${h.endDate}</p>
                </div>
                <div style="text-align:right;">
                    <p class="prize">₹<fmt:formatNumber value="${h.prizeAmount}" type="number" maxFractionDigits="0"/></p>
                    <p style="color:#888; font-size:0.8rem;">Prize Pool</p>
                    <c:if test="${not empty h.registrationUrl}">
                        <a href="${h.registrationUrl}" target="_blank" style="background:#6c3483;color:white;padding:8px 16px;border-radius:6px;text-decoration:none;">Register →</a>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
