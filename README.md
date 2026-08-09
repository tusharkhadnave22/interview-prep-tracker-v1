# Interview Prep Tracker — V1

A deliberately simple React + Spring Boot application for tracking interview-preparation questions.

## Features

- Add multiple candidates/users
- Open a candidate's question dashboard
- Add unlimited questions
- Status per question:
  - NOT_STARTED
  - DONE
  - REVISED
  - MOCK_INTERVIEW
- Filter questions by status
- Delete users/questions
- Export questions to `.xlsx`
- Import questions from `.xlsx`
- PostgreSQL database
- CORS configurable with environment variables
- Dockerfiles included for frontend and backend

## Architecture

React (Vite) -> REST API -> Spring Boot -> PostgreSQL

## Database

The application creates/updates tables automatically using:

`spring.jpa.hibernate.ddl-auto=update`

For production, once the schema becomes stable, change this to migrations (Flyway/Liquibase) in a future version.

## Run backend locally

Requirements:
- Java 17+
- Maven 3.9+
- PostgreSQL

Create a database:

`interview_prep`

Then:

```bash
cd backend
mvn spring-boot:run
```

Default database settings:

- URL: `jdbc:postgresql://localhost:5432/interview_prep`
- Username: `postgres`
- Password: `postgres`

Override with:

```bash
DB_URL=jdbc:postgresql://host:5432/db
DB_USERNAME=...
DB_PASSWORD=...
```

Backend starts on:

`http://localhost:8080`

## Run frontend locally

Requirements:
- Node 20+

```bash
cd frontend
npm install
npm run dev
```

Frontend starts on:

`http://localhost:5173`

For another backend URL, create `.env`:

```text
VITE_API_URL=https://your-backend-domain/api
```

## Excel format

The first worksheet should have:

| Question | Status |
|---|---|
| Explain HashMap internally | DONE |
| Explain dependency injection | REVISED |
| Explain CAP theorem | MOCK_INTERVIEW |

If Status is blank or invalid, the importer uses `NOT_STARTED`.

## Production deployment

Deploy PostgreSQL separately and set the backend environment variables:

```text
DB_URL=jdbc:postgresql://...
DB_USERNAME=...
DB_PASSWORD=...
FRONTEND_URL=https://your-frontend-domain
PORT=8080
```

For the frontend:

```text
VITE_API_URL=https://your-backend-domain/api
```

The included Dockerfiles can be used on platforms that support Docker deployments.

## Important V1 limitation

There is intentionally no authentication. Anyone who can reach the app can see and modify the users/questions. Add authentication/authorization before using it with sensitive or private information.

## Suggested V2

- Login/signup
- Categories (Java, Spring, SQL, System Design, etc.)
- Difficulty
- Priority
- Notes/answers
- Search
- Better daily revision logic
- Proper database migrations
- Tests
- CI/CD
