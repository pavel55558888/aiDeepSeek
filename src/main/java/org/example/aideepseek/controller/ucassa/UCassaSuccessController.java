package org.example.aideepseek.controller.ucassa;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
public class UCassaSuccessController {

    @GetMapping("/success")
    public ResponseEntity<String> handleSuccessGet() {
        return buildSuccessResponse();
    }

    private ResponseEntity<String> buildSuccessResponse() {

        String html = """
        <!DOCTYPE html>
        <html lang="ru">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>✅ Оплата прошла успешно</title>
          <style>
            body {
              font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
              background: #0a0a0a;
              margin: 0;
              padding: 0;
              display: flex;
              justify-content: center;
              align-items: center;
              min-height: 100vh;
              color: #ffffff;
              overflow: hidden;
              position: relative;
            }
            .container {
              background: rgba(20, 20, 20, 0.85);
              padding: 40px;
              border-radius: 16px;
              box-shadow: 0 0 30px rgba(255, 215, 0, 0.4);
              text-align: center;
              max-width: 500px;
              width: 90%;
              z-index: 10;
              backdrop-filter: blur(10px);
              border: 1px solid rgba(255, 255, 255, 0.1);
            }
            .icon {
              font-size: 64px;
              color: #ffdd00;
              margin-bottom: 24px;
              text-shadow: 0 0 10px rgba(255, 221, 0, 0.7);
            }
            h1 {
              font-size: 28px;
              font-weight: 600;
              margin: 0 0 16px 0;
              color: #ffffff;
              text-shadow: 0 0 5px rgba(255, 255, 255, 0.5);
            }
            p {
              font-size: 16px;
              line-height: 1.6;
              color: #cccccc;
              margin: 0 0 24px 0;
            }
            .btn {
              background: linear-gradient(45deg, #ff6b00, #ffdd00);
              color: white;
              border: none;
              padding: 16px 32px;
              font-size: 18px;
              border-radius: 50px;
              cursor: pointer;
              font-weight: 700;
              transition: all 0.3s ease;
              box-shadow: 0 4px 15px rgba(255, 107, 0, 0.4);
              letter-spacing: 1px;
            }
            .btn:hover {
              transform: scale(1.05) rotate(2deg);
              box-shadow: 0 6px 20px rgba(255, 221, 0, 0.6);
              background: linear-gradient(45deg, #ff8c00, #ffff00);
            }
            .btn:active {
              transform: scale(0.98);
            }
        
            /* Стили для фейерверков */
            .firework {
              position: absolute;
              width: 5px;
              height: 5px;
              border-radius: 50%;
              pointer-events: none;
              z-index: 5;
            }
          </style>
        </head>
        <body>
          <div class="container">
            <div class="icon">🎉</div>
            <h1>Оплата прошла успешно!</h1>
            <p>Ваша подписка активирована. Наслаждайтесь!</p>
            <button class="btn" id="celebrate-btn">Юхууу!!!</button>
          </div>
        
          <script>
            document.getElementById('celebrate-btn').addEventListener('click', () => {
              // Звук фейерверка (опционально)
              const audio = new Audio("data:audio/wav;base64,UklGRl9vT19XQVZFZm10IBAAAAABAAEAQB8AAEAfAAABAAgAZGF0YU..."); // можно удалить, если не нужен
              audio.volume = 0.2;
              audio.play().catch(e => console.log("Audio play failed:", e));
        
              // Анимация фейерверков
              for (let i = 0; i < 5; i++) {
                setTimeout(createFirework, 300 * i);
              }
        
              // Через 3 секунды закрываем окно
              setTimeout(() => {
                if (window.opener) {
                  window.opener.focus();
                }
                window.close();
              }, 3000);
            });
        
            function createFirework() {
              const firework = document.createElement('div');
              firework.classList.add('firework');
              
              // Случайная позиция
              const x = Math.random() * window.innerWidth;
              const y = Math.random() * window.innerHeight / 2;
              
              firework.style.left = x + 'px';
              firework.style.top = y + 'px';
              
              // Случайный цвет
              const hue = Math.floor(Math.random() * 360);
              firework.style.backgroundColor = `hsl(${hue}, 100%, 60%)`;
              
              document.body.appendChild(firework);
        
              // Анимация взрыва
              const particles = 20;
              for (let i = 0; i < particles; i++) {
                setTimeout(() => {
                  const particle = document.createElement('div');
                  particle.classList.add('firework');
                  particle.style.left = x + 'px';
                  particle.style.top = y + 'px';
                  particle.style.backgroundColor = `hsl(${hue + Math.random() * 30}, 100%, 60%)`;
                  document.body.appendChild(particle);
        
                  const angle = Math.random() * Math.PI * 2;
                  const speed = 2 + Math.random() * 3;
                  const vx = Math.cos(angle) * speed;
                  const vy = Math.sin(angle) * speed;
        
                  let posX = x;
                  let posY = y;
        
                  const animate = () => {
                    posX += vx;
                    posY += vy + 0.1; // гравитация
        
                    particle.style.transform = `scale(${1 - (Date.now() - startTime) / 1000})`;
                    particle.style.opacity = 1 - (Date.now() - startTime) / 1000;
                    particle.style.left = posX + 'px';
                    particle.style.top = posY + 'px';
        
                    if (Date.now() - startTime < 1000) {
                      requestAnimationFrame(animate);
                    } else {
                      particle.remove();
                    }
                  };
        
                  const startTime = Date.now();
                  requestAnimationFrame(animate);
                }, Math.random() * 100);
              }
        
              // Удаляем центральную точку через 200мс
              setTimeout(() => {
                firework.remove();
              }, 200);
            }
          </script>
        </body>
        </html>
        """;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8")
                .body(html);
    }
}
