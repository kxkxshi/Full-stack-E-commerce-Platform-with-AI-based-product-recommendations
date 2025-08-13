# Full Stack E‑Commerce Platform with AI-based Product Recommendations

A complete semester-ready project including:
- **Backend:** Spring Boot (Java 17), REST API, JPA, H2 (dev) with seed data, simple AI recommendations (tag similarity).
- **Frontend:** React + Vite (JavaScript), product catalogue, cart, checkout stub, recommendations widget.
- **Docs:** API overview, setup steps, sample data.

## Quick Start (Dev)
### 1) Backend
```bash
cd backend
./mvnw spring-boot:run
```
The backend starts at `http://localhost:8080`.

H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:ecomdb`, user: `sa`, password blank)

### 2) Frontend
```bash
cd frontend
npm install
npm run dev
```
The frontend starts at `http://localhost:5173`.

---

## API Overview
- `GET /api/products` – list products
- `GET /api/products/{id}` – get product
- `GET /api/recommendations/{id}` – recommended products for a given product id
- `POST /api/cart` – add to cart (in-memory session per demo)
- `GET /api/cart` – get cart
- `POST /api/orders/checkout` – fake checkout creates an order

> NOTE: Auth is simplified for semester scope. You can extend with JWT if required.

## Tech
- Spring Boot 3.x, Java 17, JPA/Hibernate, H2 (dev)
- React + Vite + Fetch API

## How the AI Recommender Works
We store `tags` for each product (e.g., "phone,android,5g,midrange").
We compute Jaccard similarity between the target product's tag set and others and return top‑N similar products.

You can upgrade to TF‑IDF cosine similarity or collaborative filtering later.

## Switch to MySQL (Optional)
Replace H2 with MySQL by adding a `mysql` Spring profile and properties (see `application-mysql.properties`), create schema using `schema.sql`, and run the app with:
```bash
SPRING_PROFILES_ACTIVE=mysql ./mvnw spring-boot:run
```

---

## Semester Report Hints
Include: problem statement, system architecture (include the class diagram here), data model (ERD), algorithms (recommendation), screenshots, test cases, conclusion & future work.
