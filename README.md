# 🏋️‍♂️ Motiv – Personalized Fitness Goal Tracker

**Motiv** is a fitness-focused web app designed to help users achieve their physical goals — whether that's aesthetics, strength, or athleticism — by providing weekly workout plans, tracking progress through logged workouts and photos, and adjusting routines based on their performance.

---

## 🚀 MVP Features

- ✅ User authentication and profile creation
- 🎯 Goal selection (aesthetic, strength, athleticism, etc.)
- 📅 Workout plan generation based on selected goal
- 📝 Manual workout logging (exercises, sets, reps, weight)
- 📊 Progress tracking via charts
- 📸 Progress photo uploads (stored in cloud bucket)

---

## 🧠 Core Concepts

- **User Goals:** Users can define their primary fitness goal, which is used to tailor their weekly plan.
- **Adaptive Workouts:** The app can recommend changes in reps/sets based on recent performance.
- **Tracking:** Users can view progress over time in both visual and data formats.

---

## 🧱 Database Overview

- **Users** – Basic profile info, linked to goals and workouts
- **Goals** – Each user can define multiple goals over time
- **Workouts** – Each session is tied to a user and a goal
- **Exercises** – Global list of supported exercises
- **Workout_Exercises** – Junction table for exercises performed during a workout
- **Progress Photos** – Images uploaded to track physical changes

---

## 🛠️ Tech Stack (Planned)

- **Backend:** Spring Boot (Java)
- **Frontend:** React / Next.js
- **Auth:** JWT or OAuth (TBD)
- **Database:** PostgreSQL
- **Storage:** Cloud storage bucket (e.g., Supabase / AWS S3)

---

## 📌 Roadmap

1. Set up database schema
2. Build user auth and profile system
3. Implement goal selection and plan generation
4. Build workout logging and tracking
5. Add progress charting and photo upload
6. Polish UI/UX for MVP launch

---

## ✨ Future Ideas

- AI-generated plan suggestions
- Nutrition tracking
- Social feed for workout sharing
- Coach and client mode

---

## 📄 License

MIT – Feel free to use and contribute!

