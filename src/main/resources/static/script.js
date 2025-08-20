const inputEl = document.getElementById("chat-input");
const sendBtn = document.getElementById("send-btn");
const chatEl  = document.getElementById("chat-messages");

sendBtn.addEventListener("click", sendMessage);
inputEl.addEventListener("keydown", e => {
  if (e.key === "Enter") sendMessage();
});

function appendBubble(text, type = "bot") {
  const div = document.createElement("div");
  div.className = `message ${type}-message`;
  div.textContent = text || "";
  chatEl.appendChild(div);
  chatEl.scrollTop = chatEl.scrollHeight;
  return div;
}

function showTypingIndicator() {
  const wrapper = document.createElement("div");
  wrapper.className = "message bot-message";
  const typing = document.createElement("div");
  typing.className = "typing";
  typing.innerHTML = '<span class="dot"></span><span class="dot"></span><span class="dot"></span>';
  wrapper.appendChild(typing);
  chatEl.appendChild(wrapper);
  chatEl.scrollTop = chatEl.scrollHeight;
  return wrapper; // return the element so we can remove it later
}

function typeWriter(element, fullText, speed = 22) {
  // If server returns \n, we rely on CSS white-space: pre-wrap to render lines
  let i = 0;
  return new Promise(resolve => {
    (function tick() {
      if (i < fullText.length) {
        element.textContent += fullText.charAt(i++);
        chatEl.scrollTop = chatEl.scrollHeight;
        setTimeout(tick, speed);
      } else {
        resolve();
      }
    })();
  });
}

async function sendMessage() {
  const text = inputEl.value.trim();
  if (!text) return;

  // User bubble
  appendBubble(text, "user");
  inputEl.value = "";

  // Typing dots while waiting for backend
  const typingEl = showTypingIndicator();

  try {
    // Call your Spring Boot endpoint
    const res = await fetch("/api/chat", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        sessionId: "default-session",
        // IMPORTANT: matches your corrected ChatController expecting "message"
        message: text
      })
    });

    const data = await res.json();
    const botText = (data && data.botMessage) ? data.botMessage : "Sorry, I couldn’t respond.";

    // Remove typing dots and type letter-by-letter
    typingEl.remove();
    const botBubble = appendBubble("", "bot");
    await typeWriter(botBubble, botText, 22); // adjust speed if you like
  } catch (e) {
    // Remove typing dots and show error
    typingEl.remove();
    appendBubble("⚠️ Network error. Please try again.", "bot");
  }
}
