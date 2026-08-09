# Render Configuration Guide - For Your Screenshot

You're currently on the Render configuration page. Here's what to fill in:

## CONFIGURATION SETTINGS

### Source Code Section
✅ **Already Connected**
- Repository: `tusharkhadnave22/interview-prep-tracker-v1`
- You're good here!

### Name Section
**Input**: `interview-prep-backend`
```
interview-prep-backend
```

### Language Section
**Dropdown**: Select `Docker` (already selected in your screenshot)
```
Language: Docker ✓
```

### Branch Section
**Input**: `master` (or `main` - whichever your GitHub uses)
```
Branch: master
```

### Region Section
**Dropdown**: Keep `Oregon (US West)` or choose your preferred region
```
Region: Oregon (US West)
```

### Root Directory (IMPORTANT FOR MONOREPO!)
**Input**: `backend`
```
Root Directory: backend
```
⚠️ This tells Render to run commands from the backend folder!

### Dockerfile Path (IMPORTANT!)
**Input**: `backend/Dockerfile`
```
Dockerfile Path: backend/Dockerfile
```
⚠️ This is the path to your backend Dockerfile!

---

## NEXT PAGE: Environment Variables

After filling the above, click "Continue" or "Deploy" and you'll be asked for:

### Environment Variables to Add:

```
DB_URL = jdbc:postgresql://your-db-url:5432/interview_prep
DB_USERNAME = your_username
DB_PASSWORD = your_password
FRONTEND_URL = https://your-frontend.vercel.app
PORT = 8080
```

**Where to get these:**
- Create a PostgreSQL database first in Render
- Copy the connection string from the database service
- Update with your actual Vercel frontend URL later

---

## STEP-BY-STEP FOR YOUR SCREENSHOT

1. **Name**: Type `interview-prep-backend`
2. **Language**: Keep as `Docker` (shown in dropdown)
3. **Branch**: Type `master` or `main`
4. **Region**: Keep as `Oregon (US West)` (or choose one)
5. **Root Directory**: Type `backend` ⭐ IMPORTANT
6. **Dockerfile Path**: Type `backend/Dockerfile` ⭐ IMPORTANT
7. **Click "Create Web Service"**

---

## AFTER CLICKING "Create Web Service"

1. Render will show you environment variables form
2. Add the database credentials
3. Click "Deploy"
4. Wait 5-10 minutes for build
5. Render gives you a URL like: `https://interview-prep-backend.onrender.com`

---

## THEN: Create Database

1. Go back to Render Dashboard
2. Click "New" → "PostgreSQL"
3. Name it: `interview-prep-db`
4. Choose plan (free tier available)
5. Get connection string
6. Add to your backend's environment variables

---

## QUICK REFERENCE

```
┌─────────────────────────────────────┐
│ FORM FIELDS TO FILL                 │
├─────────────────────────────────────┤
│ Name ..................... backend   │
│ Language ................ Docker ✓  │
│ Branch .................. master     │
│ Region .......... Oregon (US West)  │
│ Root Directory .......... backend   │ ← Important!
│ Dockerfile Path  backend/Dockerfile │ ← Important!
├─────────────────────────────────────┤
│           [Create Web Service]      │
└─────────────────────────────────────┘
```

---

## AFTER DEPLOYMENT

Your backend URL will be something like:
```
https://interview-prep-backend.onrender.com
```

Use this in Vercel for:
```
VITE_API_URL=https://interview-prep-backend.onrender.com/api
```

✅ Done!

