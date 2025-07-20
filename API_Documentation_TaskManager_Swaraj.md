# Task Manager API Documentation

Base URL: `http://localhost:8080/api/tasks`

---

## Endpoints Overview

| Method | Endpoint      | Description          |
| ------ | ------------- | -------------------- |
| GET    | `/tasks`      | Get all tasks        |
| GET    | `/tasks/{id}` | Get task by ID       |
| POST   | `/tasks`      | Create new task      |
| PUT    | `/tasks/{id}` | Update existing task |
| DELETE | `/tasks/{id}` | Delete task          |

---

## POST `/tasks` - Create Task

### Request Body

```json
{
  "title": "Submit Project",
  "description": "Finalize and submit",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-07-20T18:00:00"
}
```

### Success Response

**Status:** `201 Created`

```json
{
  "id": 1,
  "title": "Submit Project",
  "description": "Finalize and submit",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-07-20T18:00:00"
}
```

### Validation Error

**Status:** `400 Bad Request`

```json
{
  "errors": [
    "Title must not be blank",
    "DueDate must be present"
  ]
}
```

---

## GET `/tasks` - Get All Tasks

### Success Response

**Status:** `200 OK`

```json
[
  {
    "id": 1,
    "title": "Submit Project",
    "description": "Finalize and submit",
    "status": "PENDING",
    "priority": "HIGH",
    "dueDate": "2025-07-20T18:00:00"
  },
  {
    "id": 2,
    "title": "Deploy App",
    "description": "Deploy to production",
    "status": "DONE",
    "priority": "MEDIUM",
    "dueDate": "2025-07-15T12:00:00"
  }
]
```

---

## GET `/tasks/{id}` - Get Task by ID

### Success Response

**Status:** `200 OK`

```json
{
  "id": 1,
  "title": "Submit Project",
  "description": "Finalize and submit",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-07-20T18:00:00"
}
```

### Not Found

**Status:** `404 Not Found`

```json
{
  "message": "Task not found with id: 99"
}
```

---

## PUT `/tasks/{id}` - Update Task

### Request Body

```json
{
  "title": "Submit Final Report",
  "description": "Updated description",
  "status": "IN_PROGRESS",
  "priority": "LOW",
  "dueDate": "2025-07-25T17:00:00"
}
```

### Success Response

**Status:** `200 OK`

```json
{
  "id": 1,
  "title": "Submit Final Report",
  "description": "Updated description",
  "status": "IN_PROGRESS",
  "priority": "LOW",
  "dueDate": "2025-07-25T17:00:00"
}
```

### Validation Error

**Status:** `400 Bad Request`

```json
{
  "errors": [
    "Title must not be blank"
  ]
}
```

---

## DELETE `/tasks/{id}` - Delete Task

### Success Response

**Status:** `204 No Content`

### Not Found

**Status:** `404 Not Found`

```json
{
  "message": "Task not found with id: 999"
}
```


## Sample curl commands or Postman collection

{
	"info": {
		"_postman_id": "68ea30a2-012c-4b30-83a2-526c74f717bf",
		"name": "Task Manager API",
		"description": "Test collection for Task Manager Spring Boot API",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "46098510",
		"_collection_link": "https://swaraj-6562739.postman.co/workspace/SWARAJ's-Workspace~bd10f22b-e75a-4299-8992-b385cfb7b946/collection/46098510-68ea30a2-012c-4b30-83a2-526c74f717bf?action=share&source=collection_link&creator=46098510"
	},
	"item": [
		{
			"name": "Create Task - Valid",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"title\": \"Complete Spring Boot Assignment\",\n    \"description\": \"Build a task management API\",\n    \"status\": \"PENDING\",\n    \"priority\": \"HIGH\",\n    \"dueDate\": \"2024-02-15T00:00:00\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/api/tasks",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"tasks"
					]
				}
			},
			"response": []
		},
		{
			"name": "Create Task - Invalid",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"title\": \"Hi\",\n    \"description\": \"\",\n    \"status\": \"INVALID_STATUS\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/api/tasks",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"tasks"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get All Tasks",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/tasks",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"tasks"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get Task by Id",
			"request": {
				"method": "GET",
				"header": []
			},
			"response": []
		},
		{
			"name": "Get Non-existent Task",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/tasks/999",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"tasks",
						"999"
					]
				}
			},
			"response": []
		},
		{
			"name": "Update Task (ID: 1)",
			"request": {
				"method": "PUT",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"title\": \"Updated Spring Boot Assignment\",\n    \"description\": \"Update the task API logic\",\n    \"status\": \"IN_PROGRESS\",\n    \"priority\": \"MEDIUM\",\n    \"dueDate\": \"2024-03-01T00:00:00\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/api/tasks/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"tasks",
						"1"
					]
				}
			},
			"response": []
		},
		{
			"name": "Delete Task (ID: 1)",
			"request": {
				"method": "DELETE",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/tasks/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"tasks",
						"1"
					]
				}
			},
			"response": []
		}
	]
}