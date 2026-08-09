# Quick Deployment Checklist for Render + Vercel

## BEFORE YOU DEPLOY

- [ ] Push all changes to GitHub (main branch)
- [ ] Test backend locally: `mvn spring-boot:run`
- [ ] Test frontend locally: `npm run dev`
- [ ] Database created: `CREATE DATABASE interview_prep;`

---

## STEP 1: Deploy Backend on Render (5-10 minutes)

### 1a. Create Render Account
- [ ] Go to https://render.com
- [ ] Sign up with GitHub
- [ ] Authorize GitHub access

### 1b. Create Backend Service
- [ ] Click "New" → "Web Service"
- [ ] Select your GitHub repo: `interview-prep-tracker-v1`
- [ ] Fill in:
  - **Name**: interview-prep-backend
  - **Language**: Docker
  - **Root Directory**: backend
  - **Dockerfile Path**: backend/Dockerfile

### 1c. Create Database
- [ ] In Render dashboard: "New" → "PostgreSQL"
- [ ] Name: interview-prep-db
- [ ] Copy connection string
- [ ] Add to backend environment variables

### 1d. Set Environment Variables
In Render Service Settings, add:
```
DB_URL = <your-postgres-url>
DB_USERNAME = <username>
DB_PASSWORD = <password>
FRONTEND_URL = https://your-frontend.vercel.app
PORT = 8080
```

### 1e. Deploy
- [ ] Click "Deploy"
- [ ] Wait for build to complete (3-5 minutes)
- [ ] Copy your backend URL: `https://interview-prep-backend.onrender.com`

---

## STEP 2: Deploy Frontend on Vercel (5 minutes)

### 2a. Create Vercel Account
- [ ] Go to https://vercel.com
- [ ] Sign up with GitHub
- [ ] Authorize GitHub access

### 2b. Create Frontend Project
- [ ] Click "New Project"
- [ ] Select repo: `interview-prep-tracker-v1`
- [ ] Fill in:
  - **Framework**: Vite
  - **Root Directory**: frontend
  - **Build Command**: `npm run build`
  - **Output Directory**: `dist`

### 2c. Set Environment Variables
Add in Vercel:
```
VITE_API_URL = https://interview-prep-backend.onrender.com/api
```

### 2d. Deploy
- [ ] Click "Deploy"
- [ ] Wait for deployment (1-2 minutes)
- [ ] Your frontend URL: `https://your-app.vercel.app`

---

## STEP 3: Verify Deployment

### 3a. Test Backend
```bash
curl https://interview-prep-backend.onrender.com/api/users
```
Expected: JSON response or empty array

### 3b. Test Frontend
- [ ] Open https://your-app.vercel.app
- [ ] Check browser console for errors
- [ ] Try creating a user
- [ ] Verify data is saved

### 3c. Check Logs
**Render Backend Logs**:
- Service → Logs tab
- Look for "Application started" message

**Vercel Frontend Logs**:
- Deployments tab → Click latest deployment
- Check build logs for errors

---

## COMMON ISSUES & FIXES

| Issue | Solution |
|-------|----------|
| Backend won't start | Check DB credentials in env vars |
| Frontend can't reach backend | Update VITE_API_URL in Vercel |
| Database connection error | Verify database service is active |
| CORS errors | Check FRONTEND_URL in backend env vars |
| Dockerfile build fails | Run `mvn clean install` locally first |

---

## AFTER DEPLOYMENT

- [ ] Share your deployed URL
- [ ] Auto-deploy is now enabled
  - Any push to `main` → Auto redeploy
  - No need to manually redeploy!

---

## Cost Summary
- Render Backend: FREE (or $7/mo paid)
- Render Database: ~$3.50-7/mo
- Vercel Frontend: FREE
- **Total**: $0-14/month

---

## Need Help?
- Render Docs: https://render.com/docs
- Vercel Docs: https://vercel.com/docs
- Check logs in both dashboards for detailed errors

