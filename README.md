# 🎓 Student Support and Campus information AI-ChatBot

An AI-powered chatbot built with **Spring Boot** that helps students with quick answers about college facilities, admissions, exams, library timings, hostel rules, etc.  
It uses a **knowledge base (YAML)** for fixed data, an **opinion engine**, and **Gemini AI API** for intelligent fallback answers.  

---

## ✨ Features

- 📚 **Knowledge Base Support** – Answers common college-related queries (exams, library, mess, hostel).  
- 🤖 **AI-Powered Fallback** – Uses Google’s **Gemini API** when knowledge base doesn’t have the answer.  
- 💬 **Conversation Flow** – Maintains context for smooth replies.  
- 🧠 **Opinion Service** – Provides friendly “senior student” style responses.  
- 🌐 **Web Frontend** – Simple UI to chat with the bot.  
- 🔧 **Extensible** – Easily add new knowledge in `knowledge.yml`.  

---

## 🛠 Tools & Technologies Used

- **Java 17**  
- **Spring Boot 3**  
- **Maven** (dependency management)  
- **OkHttp** (for Gemini API requests)  
- **YAML** (knowledge base)  
- **HTML / CSS / JavaScript** (frontend)  

---

## ⚙️ Setup Instructions

### 1. Clone this repo
```bash
git clone https://github.com/your-username/college-chatbot.git
cd college-chatbot
```

### 2. Get a Gemini API Key
- Go to [Google AI Studio](https://makersuite.google.com/app/apikey).  
- Generate a new key.  

### 3. Add API Key
#### Option 1: (Recommended) Use `application.properties`
Open `src/main/resources/application.properties` and add:
```properties
gemini.api.key=YOUR_API_KEY_HERE
```

#### Option 2: Use Environment Variable
Set the variable before running:
```bash
export GEMINI_API_KEY="YOUR_API_KEY_HERE"    # Linux/Mac
setx GEMINI_API_KEY "YOUR_API_KEY_HERE"      # Windows
```

### 4. Run the Project
```bash
mvn spring-boot:run
```

Open in browser: 👉 [http://localhost:8080]

---

## 💡 Usage Example

### Sample Knowledge Query:
**User:** When are exams?  
**Bot:**  
```
Internal exams will start from 1st September 2025 till 8th September 2025.
```

### AI Fallback Query:
**User:** Who is Virat Kohli?  
**Bot (Gemini):**  
```
Virat Kohli is an Indian cricketer known for his aggressive batting style...
```

---

## 📂 Project Structure

```
src/main/java/com/example/bot/
├── ai/              # AIService (Gemini API calls)
├── knowledge/       # KnowledgeService (loads knowledge.yml)
├── opinions/        # OpinionService (friendly replies)
├── service/         # PipelineService (main message handler)
├── state/           # Conversation store
└── nlp/             # IntentClassifier
```

Knowledge base file:  
```yaml
exams: "Internal exams will start from 1st September 2025 till 8th September 2025."
library: "Library is open Mon–Sat, 9:20 AM – 4:00 PM."
admissions: "Please contact the college administration."
mess: "Hostel mess timings: Breakfast 8–9AM, Lunch 12:50–1:30PM, Snacks 4–5PM, Dinner 7–9PM."
hostel: "Hostel timings are till 7:00 PM."
```

---

## 🚀 Future Upgrades

- 🔒 **User Authentication** – Add login for personalized responses.  
- 📱 **Mobile App** – Build React Native / Flutter frontend.  
- 📊 **Analytics Dashboard** – Track common queries.  
- 🗣 **Voice Input/Output** – Integrate with Speech-to-Text APIs.  
- 📌 **Database Storage** – Save conversation history.  
- 🌍 **Multi-language Support** – Add Hindi, French, etc.  

---

## 👨‍💻 Author
Developed by ## 🧑‍💻 Author

Developed by :
**Saurish Dobhal**
[![GitHub Profile](https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png)](https://github.com/dobhalsaurish)
[![LinkedIn Profile](https://cdn-icons-png.flaticon.com/512/174/174857.png)](https://www.linkedin.com/in/dobhalsaurish/)

**Gaurav Soni**
[![GitHub Profile](https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png)](https://github.com/gauravsoni02)
[![LinkedIn Profile](https://cdn-icons-png.flaticon.com/512/174/174857.png)](https://www.linkedin.com/in/gauravsoni02/)
