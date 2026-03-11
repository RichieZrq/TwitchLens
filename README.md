# TwitchLens

A personalized Twitch content recommendation engine that helps users discover streams, videos, and clips based on their favorites. Users can search for games, browse live streams, videos, and clips, save favorites, and receive personalized recommendations powered by their watch history.

URL: https://pkthwaaekp.us-east-2.awsapprunner.com/

## Features

- Browse top Twitch games and search by name
- View live streams, videos, and clips for any Twitch genre
- User registration and authentication
- Save and manage favorite content
- Personalized recommendations based on favorited games
- Caffeine-based caching for fast response times

## Architecture Overview

TwitchLens is a full-stack application with a React frontend served by a Spring Boot backend.

```
Browser (React SPA)
        │
        ▼
Spring Boot (REST API, Port 8080)
        │
   ┌────┴────┐
   │         │
MySQL     Twitch API
(RDS)    (via OpenFeign)
```

### Frontend
- Pre-built React single-page application served as static files from Spring Boot
- Communicates with the backend via REST API calls
- Features game search, tabbed content browsing (Streams / Videos / Clips), favorites management, and recommendations

### Backend
- **Spring Boot 3** — REST API server and static file host
- **Spring Security** — session-based authentication with JDBC user store
- **Spring Data JDBC** — database access via repositories
- **OpenFeign** — declarative HTTP client for Twitch API integration
- **Caffeine Cache** — in-memory caching for Twitch API responses (1 minute TTL)

### Database
- **MySQL** — stores users, favorited items, and favorite records
- Schema auto-initialized on startup via `database-init.sql`

### Infrastructure
- **Docker** — containerized for consistent deployment
- **AWS ECR** — Docker image registry
- **AWS App Runner** — fully managed container hosting with auto-scaling
- **AWS RDS** — managed MySQL database

## Local Setup

### Prerequisites
- Java 17+
- Docker Desktop
- A Twitch developer account with a registered application

### 1. Get Twitch API Credentials
1. Go to [dev.twitch.tv/console](https://dev.twitch.tv/console)
2. Create a new application, then copy your **Client ID** and **Client Secret**

### 2. Configure Environment Variables
Create a `.env` file in the project root:
```
TWITCH_CLIENT_ID=your_client_id
TWITCH_CLIENT_SECRET=your_client_secret
DATABASE_URL=localhost
DATABASE_USERNAME=root
DATABASE_PASSWORD=secret
```

### 3. Start the Database
```bash
docker-compose up -d
```

### 4. Run the Application
```bash
# Load environment variables (PowerShell)
Get-Content .env | ForEach-Object { if ($_ -match '^(.+)=(.+)$') { [System.Environment]::SetEnvironmentVariable($matches[1], $matches[2]) } }

# Start the app
./gradlew bootRun
```

### 5. Open in Browser
Navigate to `http://localhost:8080`
