# todo-task-management

This is a simple full-stack To-Do application that allows users to:

- Create, update, and delete tasks  
- Change task status (complete/incomplete)  
- Add comments to each task  
- Display a list of all tasks  

---

## Tech Stack

- **Backend**: Java Spring Boot  
- **Frontend**: React (Next.js)  
- **Database**: PostgreSQL  
- **Containerization**: Docker & Docker Compose

---

## 📁 Project Structure
project-root/
│
├── backend/ # Java Spring Boot application
├── frontend/ # React + Next.js application
├── docker-compose.yml
└── README.md

## Prerequisites

Before you begin, make sure you have the following installed:

- Docker
- Docker Compose
- Java 17+ (only required for manual backend builds)
- Node.js 18+ (only required for manual frontend runs)

---

## Step-by-Step Setup

### 1️⃣ Build the Backend (Spring Boot)

Navigate to the backend folder and run: 
./mvnw.cmd clean package -DskipTests

This command:
- Uses Maven wrapper to run Maven tasks
- Cleans previous builds
- Packages the project into a JAR file
- Skips running tests to speed up the build

---

### 2️⃣ Move to Project Root

If you’re in the backend folder, go to the root folder where `docker-compose.yml` is . To go to root folder, run command:
cd ..

### 3️⃣ Start the Application Using Docker

From the root folder, run:
docker-compose up --build


This command:
- Builds Docker images for backend, frontend, and PostgreSQL
- Starts all services as defined in `docker-compose.yml`

---

## Access the App

- Frontend: http://localhost:3000  
- Backend API: http://localhost:8080/api/tasks  
- PostgreSQL: Running inside Docker at localhost:5432  

---

## Features

| Feature            | Description                                |
|--------------------|--------------------------------------------|
| Create Task      | Add a new task with a title and due date   |
| Update Task      | Edit task details                          |
| Delete Task      | Remove a task                              |
| Update Status    | Mark tasks as complete/incomplete          |
| Add Comments     | Add comments to individual tasks           |
| View Tasks       | Display a list of all tasks                |

---

## Stopping the App

To stop the application:

Press `CTRL + C` in the terminal.

To stop and remove containers:
docker-compose down


---

## Configuration (Optional)

You can change database credentials in `docker-compose.yml`:

```yaml
environment:
  POSTGRES_DB: todo-list
  POSTGRES_USER: postgres
  POSTGRES_PASSWORD: root



