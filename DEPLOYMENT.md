# Deployment Guide - Interview Prep Tracker V1

## Monorepo Structure
```
interview-prep-tracker-v1/
├── backend/          (Spring Boot + MySQL)
├── frontend/         (React + Vite)
└── README.md
```

## Deployment Strategy

### STEP 1: Push Code to GitHub
```bash
git add .
git commit -m "Ready for deployment"
git push origin main
```

---

## STEP 2: Deploy Backend on Render.com

### 2a. Create Web Service
1. Go to [render.com](https://render.com)
2. Click **"New +"** → **"Web Service"**
3. Connect your GitHub repo (interview-prep-tracker-v1)

### 2b. Configure Backend Service
Fill in the following on Render dashboard:

| Field | Value |
|-------|-------|
| **Name** | interview-prep-backend |
| **Language** | Docker |
| **Branch** | main |
| **Root Directory** | backend |
| **Dockerfile Path** | backend/Dockerfile |
| **Port** | 8080 |

### 2c. Environment Variables
Add these in Render dashboard (Services → Environment):

```
DB_URL=postgresql://user:password@your-db-host:5432/interview_prep
DB_USERNAME=your_username
DB_PASSWORD=your_password
FRONTEND_URL=https://your-frontend.vercel.app
```

### 2d. Add MySQL/PostgreSQL Database
1. In Render dashboard, click **"New +"** → **"PostgreSQL"**
2. Name: `interview-prep-db`
3. Copy the connection string
4. Use it in your backend environment variables

---

## STEP 3: Deploy Frontend on Vercel

### 3a. Connect Repository
1. Go to [vercel.com](https://vercel.com)
2. Click **"New Project"**
3. Select your GitHub repo (interview-prep-tracker-v1)

### 3b. Configure Frontend Project
- **Framework**: Vite
- **Root Directory**: `frontend`
- **Build Command**: `npm run build`
- **Output Directory**: `dist`

### 3c. Environment Variables
Add in Vercel dashboard:

```
VITE_REACT_APP_API_URL=https://your-backend.onrender.com
```

### 3d. Deploy
Click **"Deploy"** - Vercel will automatically build and deploy!

---

## Backend Environment Variables Reference

```env
# Database Configuration
DB_URL=jdbc:mysql://host:3306/interview_prep
DB_USERNAME=root
DB_PASSWORD=admin

# Frontend CORS (allow Vercel domain)
FRONTEND_URL=https://your-frontend.vercel.app

# Server Port (Render sets this automatically)
PORT=8080
```

---

## Testing After Deployment

### Test Backend
```bash
curl https://your-backend.onrender.com/api/users
```

### Test Frontend
Visit: `https://your-frontend.vercel.app`

---

## Troubleshooting

### Backend won't start
- Check environment variables in Render dashboard
- Verify database connection in logs
- Run `mvn clean install` locally first

### Frontend can't reach backend
- Update `VITE_REACT_APP_API_URL` in Vercel
- Check CORS settings in backend application.properties
- Verify database is running

### Database connection error
- Verify DB_URL, DB_USERNAME, DB_PASSWORD
- Check database service is active in Render
- Test connection locally first

---

## Cost Estimate

| Service | Tier | Cost |
|---------|------|------|
| Render Backend | Free | $0 (or $7/mo paid) |
| Render Database | PostgreSQL | $3.50-7/mo |
| Vercel Frontend | Hobby | $0 (free) |
| **Total** | | **$0-14/mo** |

---

## Auto-Deploy Settings

Both Render and Vercel watch your GitHub repo:
- Any push to `main` branch → Auto redeploy
- No manual deployment needed after initial setup!


