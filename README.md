# 🔥 Body Sport Zone

> Push harder. Train smarter. No excuses — just results.

A JavaFX desktop application for sport and wellness management.
Features user authentication, password reset via email, and a
cinematic animated UI with a real gym background.

---

## Requirements

| Tool | Version |
|------|---------|
| Java JDK | 17 or higher |
| JavaFX SDK | 17 or higher |
| MySQL | 8.x |
| Eclipse IDE | 2022+ (recommended) |

---

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/body-sport-zone.git
```

### 2. Import into Eclipse
- File → Import → General → **Existing Projects into Workspace**
- Browse to the cloned folder → Finish

### 3. Add JavaFX to the build path
- Right-click the project → **Build Path** → Configure Build Path
- Libraries tab → Add External JARs
- Navigate to your JavaFX SDK `lib/` folder and select **all `.jar` files**

### 4. Add the JavaFX VM argument
- Right-click project → Run As → **Run Configurations**
- Arguments tab → VM arguments — add:
```
--module-path "C:\path\to\your\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
```
> Replace the path with where your JavaFX SDK is actually installed.

### 5. Set up the database
- Create a MySQL database (check `DataSource.java` for the database name)
- Update `src-core/tn/edu/esprit/tools/DataSource.java` with your local MySQL credentials:
  - host, port, database name, username, password

### 6. Set environment variables (optional — for email reset)
The password reset feature sends a 6-digit code by email.
Set these two environment variables on your machine:

| Variable | Value |
|----------|-------|
| `GMAIL_SMTP_USER` | your Gmail address |
| `GMAIL_SMTP_APP_PASSWORD` | your Gmail App Password |

> If these are not set, reset codes will print to the Eclipse console instead — the app still works normally.

**How to set env vars on Windows:**
- Search → "Edit the system environment variables" → Environment Variables
- Under User variables → New → add each one

---

## Project Structure

```
body-sport-zone/
├── src-design/
│   └── greenmindtechfx/
│       ├── Main.fxml              # Full-page layout
│       ├── Login.fxml             # Login form
│       ├── Signup.fxml            # Register form
│       ├── Reset.fxml             # Password reset form
│       ├── Session.fxml           # Logged-in view
│       ├── theme.css              # All styles
│       ├── MainController.java    # Navigation + animations
│       ├── LoginController.java
│       ├── SignupController.java
│       ├── ResetController.java
│       ├── SessionController.java
│       └── ...
└── src-core/
    └── tn/edu/esprit/
        ├── entities/              # User, PasswordReset
        ├── services/              # ServiceUser, ServicePasswordReset
        └── tools/                 # DataSource, EmailSender, ValidationUtil
```

---

## Features

- 🔥 **Animated background** — Ken Burns effect on a real gym photo
- ⚡ **Login / Signup / Logout** with database validation
- 🔑 **Password reset** via 6-digit email code (Gmail SMTP)
- 🎬 **Cinematic animations** — staggered header reveal, form transitions, login push animation

---

## Notes for collaborators

- **Do not** commit `DataSource.java` if it contains your personal MySQL password — each person configures it locally
- **Do not** hardcode Gmail credentials anywhere — use environment variables
- Run `git pull` before starting work each session to get the latest changes
