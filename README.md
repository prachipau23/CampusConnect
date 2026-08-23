# CampusConnect — Backend

> **Enterprise Java** · Spring Boot 3.2.5 · Jakarta EE · JSP/JSTL · Spring Data JPA · Spring Security (JWT) · PostgreSQL · Maven · Docker

---

## 🌐 Live URLs

| Service | URL |
|---|---|
| **Backend API (Render)** | `https://campusconnect-backend-m7y6.onrender.com` |
| **Health check** | `https://campusconnect-backend-m7y6.onrender.com/actuator/health` |
| **Admin JSP Dashboard** | `https://campusconnect-backend-m7y6.onrender.com/views/admin/dashboard` |
| **Frontend (GitHub Pages / Vercel)** | See `frontend/` folder → deployed separately |

> **Note**: If the Render service name was different when created via API, update the URL above.
> The frontend `frontend/js/config.js` always holds the authoritative API base URL.

---

## 🏗 Architecture

```
monorepo/
├── backend/          ← Spring Boot 3.x Java app (Render web service)
│   ├── Dockerfile    ← Multi-stage: eclipse-temurin:21 build → slim JRE
│   ├── pom.xml
│   └── src/main/java/com/campusconnect/
│       ├── entity/         12 JPA entities
│       ├── repository/     12 Spring Data repos
│       ├── service/        12 service classes
│       ├── controller/     REST + JSP MVC controllers
│       ├── security/       JWT filter, UserDetailsService
│       ├── config/         SecurityConfig, CorsConfig, GlobalExceptionHandler
│       └── seed/           DataSeeder CommandLineRunner
└── frontend/         ← Plain HTML/CSS/JS (GitHub Pages / Vercel)
    └── js/config.js  ← ONLY file edited to point at backend URL
```

---

## 🔑 Modules (all 12)

| # | Module | REST Base | JSP View |
|---|---|---|---|
| 1 | **Auth** | `/api/auth/register`, `/api/auth/login` | — |
| 2 | **Profile** | `/api/profiles/me`, `/api/profiles/{userId}` | `/views/profile` |
| 3 | **Project Showcase** | `/api/projects` | `/views/projects` |
| 4 | **Directory** | `/api/directory?q=...` | `/views/directory` |
| 5 | **Circles** | `/api/circles` + join/leave | `/views/circles` |
| 6 | **Team Formation** | `/api/teams` + join/leave | `/views/teams` |
| 7 | **Team Workspace** | `/api/teams/{id}/workspace` | `/views/teams/{id}/workspace` |
| 8 | **Hackathon Board** | `/api/hackathons` | `/views/hackathons` |
| 9 | **Internship Board** | `/api/internships` | `/views/internships` |
| 10 | **Resource Library** | `/api/resources` + `/{id}/download` | `/views/resources` |
| 11 | **Notifications** | `/api/notifications` + `/{id}` (redirect URL) | `/views/notifications` |
| 12 | **Admin Dashboard** | `/api/admin/dashboard` (ADMIN/TEACHER only) | `/views/admin/dashboard` |

---

## ⚙️ Required Environment Variables (Render)

Set these in the Render web service's **Environment** tab (they are never committed to git):

| Variable | Description |
|---|---|
| `DATABASE_URL` | PostgreSQL JDBC URL — `jdbc:postgresql://...` |
| `DB_USERNAME` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |
| `JWT_SECRET` | Secret key for HMAC-SHA256 JWT signing (min 32 chars) |
| `FRONTEND_ORIGIN` | Frontend Vercel/GitHub Pages URL, e.g. `https://campusconnect-frontend.vercel.app` |
| `PORT` | Auto-set by Render (do not override) |
| `SEED_ENABLED` | Set `true` for first deploy to seed data; flip to `false` after |

> ⚠️ **`FRONTEND_ORIGIN`**: If you don't know the exact Vercel URL yet, set a placeholder now and update it after the frontend deploys. No code change needed — just update the env var and redeploy.

---

## 🌱 Seed Data

The seed inserts **on first run** (when DB is empty) if `seed.enabled=true`:

- **20 students** — varied GPAs (6.5–9.8), 4 departments, years 1–4, with realistic bio/skills
- **2 admin/teacher** accounts (admin@campus.edu / teacher.cs@campus.edu)
- **10 projects** — varied tech stacks and statuses
- **6 circles** with ~35 circle memberships spread across students
- **8 teams** (OPEN/CLOSED mix) with team leaders and members
- **6 hackathons** — real Indian competitions with ₹ prize amounts
- **6 internships** — real companies with ₹ stipends and deadlines
- **10 resource files** — real PDF/text content stored in DB, all downloadable
- **15 notifications** spread across PROJECT, TEAM, HACKATHON, INTERNSHIP, CIRCLE, RESOURCE, WORKSPACE, PROFILE types

### Toggle seed data

```bash
# To seed on first deploy: add env var SEED_ENABLED=true on Render
# After seeding (next deploy): remove/set to false
# The seeder checks if any users exist and skips if DB is already populated
```

**Default seed credentials:**
- Students: `aarav.sharma@campus.edu` / `Campus@123` (and 19 others)
- Admin: `admin@campus.edu` / `Admin@123`
- Teacher: `teacher.cs@campus.edu` / `Admin@123`

---

## 🏃 Running Locally (in Codespace — no local install required)

```bash
# Prerequisite: Codespace has Java 21 and Maven pre-installed
cd backend

# Set env vars (or export in terminal)
export DATABASE_URL=jdbc:postgresql://localhost:5432/campusconnect
export DB_USERNAME=campusconnect
export DB_PASSWORD=campusconnect
export JWT_SECRET=LocalDevelopmentSecretKeyAtLeast32Characters
export FRONTEND_ORIGIN=http://localhost:3000
export SEED_ENABLED=true   # only for first run

# Run
mvn spring-boot:run

# Backend starts at http://localhost:8080
# JSP admin dashboard: http://localhost:8080/views/admin/dashboard
# Health: http://localhost:8080/actuator/health
```

---

## 🐳 Docker (local)

```bash
cd backend
docker build -t campusconnect-backend .
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/campusconnect \
  -e DB_USERNAME=campusconnect \
  -e DB_PASSWORD=campusconnect \
  -e JWT_SECRET=LocalDevelopmentSecretKeyAtLeast32Characters \
  -e FRONTEND_ORIGIN=http://localhost:3000 \
  campusconnect-backend
```

---

## 📡 Key API Calls

```bash
# Register
curl -X POST https://campusconnect-backend.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@test.com","password":"Test@123","fullName":"Test User"}'

# Login (get JWT)
curl -X POST https://campusconnect-backend.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@campus.edu","password":"Admin@123"}'

# Admin dashboard (JWT required)
curl -H "Authorization: Bearer <token>" \
  https://campusconnect-backend.onrender.com/api/admin/dashboard

# Download a resource file
curl -O -J https://campusconnect-backend.onrender.com/api/resources/1/download

# Get notification with redirect URL
curl -H "Authorization: Bearer <token>" \
  https://campusconnect-backend.onrender.com/api/notifications/1
```

---

## 🎨 Currency / Monetary Fields

All monetary fields (hackathon prize amounts, internship stipends) are stored as `BigDecimal` in PostgreSQL.

- **JSP views**: Format with `₹` using `<fmt:formatNumber>` — never hardcodes `$`
- **Frontend**: The frontend JS should use `₹` prefix when displaying these fields. See `frontend/js/config.js` for the API wiring and `frontend/hackathons.html` / `frontend/internships.html` for display.

---

## 🔒 Security

- Passwords: BCrypt hashing via Spring Security (`BCryptPasswordEncoder`)
- Auth: Stateless JWT (HMAC-SHA256, 24h expiry)
- Roles: `STUDENT`, `ADMIN`, `TEACHER` (stored as enum in DB)
- Admin endpoints: `@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")` — returns 403 for STUDENT
- CORS: Configurable via `FRONTEND_ORIGIN` env var, never hardcoded

---

## ✅ Design Decisions (Made Without Asking)

| Decision | Choice | Reason |
|---|---|---|
| Packaging | `jar` (not `war`) | Docker + embedded Tomcat; simpler Render deploy |
| Auth | Stateless JWT | Works with plain-JS SPA frontend (no server sessions) |
| File storage | PostgreSQL `BYTEA` | No extra cloud storage config; files are small |
| JSP packaging | Under `src/main/resources/WEB-INF/jsp/` | Works with embedded Tomcat + Jasper |
| JSTL version | `jakarta.servlet.jsp.jstl` 3.0 | Required for Jakarta EE / Spring Boot 3.x |
| Render region | Default (Oregon) | Free tier default |
| Frontend API hook | New `frontend/js/config.js` file | Single-point change, no existing files modified |
| Monetary format | `BigDecimal` in DB, `₹` in views | As specified; `$` never hardcoded |
| Seed idempotency | Check `userRepository.count() > 0` | Safe to restart without re-seeding |
| JWT key derivation | Pad string to 32 bytes for HMAC-SHA256 | Handles short secret without crashing startup |
