# CHALLENGE - PART 2

`PART 1 OF THE CHALLENGE:` https://github.com/pedroferrarezzo/LocaWeb-LocaMail-Kotlin-Compose-Challenge-Fiap

"The challenge now is to integrate the email app into a robust backend solution. This phase of the project focuses on the integration of an intuitive, efficient, and customizable user interface with a backend architecture capable of supporting advanced user settings, spam control, and email service simulation.

Requirements:
- User Preferences and Settings API:
  - Allow users to adjust settings such as: themes, colors, categories, labels, and other custom preferences;
  - The possibility to migrate settings to another device (store these settings in a database);
  - Choose two themes to treat as the user's preference.

- Email Service Mock:
  - Create endpoints that simulate email listing, opening details, sending emails, and integration with a calendar. (It is not required to integrate with an actual email service).

- Spam Control Mechanism for Sending:
  - Implement logic to detect sending patterns that might be considered Spam;
  - Limit the frequency of email sending per user or per application to prevent abuse.

- Application:
  - Connect the app to the Preferences and Email Service Mock APIs."

# TECHNOLOGIES USED
- Kotlin;
  - Android API Level 27 (Android Oreo);
  - UI Framework: Jetpack Compose;
  - Retrofit and GSON;
  - Google Gemini LLM;
- Java 21;
  - Spring Boot 3;
    - Spring Web;
    - Spring Data JPA.
  - Flyway;
  - Lombok;
  - Resilience4j.
- Oracle Database 21c.

# SPAM MECHANISM
For spam control, an external API call (BlackList Checker) was implemented, where the sender's email domain is validated against over 100 blacklists frequently updated by active providers, preventing the receipt of malicious content and enhancing user security. The Resilience4J library was also implemented, with the use of:
- Ratelimiter: Limits the number of requests within a given period, both for receiving and sending emails, thus preventing resource abuse.
  ![image](https://github.com/user-attachments/assets/79d6f5d0-0209-49f5-ac45-85ac194ea9f7)
  ![image](https://github.com/user-attachments/assets/c61fb943-2694-468c-b90a-ccc2c91cbb38)

# USER PREFERENCES
- Custom Folders:
  - Users can create custom folders to organize their inbox emails;
  - Emails moved to organization folders are automatically removed from the main inbox.
    ![image](https://github.com/user-attachments/assets/359ccf44-1e1b-4aa5-8f77-ab89213c37fe)
    ![image](https://github.com/user-attachments/assets/3e1fcc95-1059-415a-9065-dfdfe453c218)
    ![image](https://github.com/user-attachments/assets/cde34633-e48a-4073-8ea3-c76dfb9f680b)
    ![image](https://github.com/user-attachments/assets/84a35e18-9383-41f0-8374-a9f62d9a5eac)
  - Calendar Integration:
    - The app features an integrated calendar;
    - Users can create tasks and events in the calendar;
    - Tasks and events have automatic day repetition options;
    - The current day is styled in a special way, enhancing user experience;
    - Users can assign a custom color to tasks or events, indicating, for example, their importance level.

      ![image](https://github.com/user-attachments/assets/f1ddcb39-d88c-447e-8045-d8ac9f752bac)
      ![image](https://github.com/user-attachments/assets/dba05c43-760c-4e47-bd65-125dca00412c)
      ![image](https://github.com/user-attachments/assets/511c2a79-fb48-44f0-b3a2-d1a41349053d)
      ![image](https://github.com/user-attachments/assets/e6e6de95-1695-489e-8e5a-498de5068dea)

# API DIFFERENTIATORS
- Circuit Breaker:
  If a service is unavailable or failing consecutively, the circuit breaker is opened, rejecting all further requests for a certain period, saving system resources.
  ![image](https://github.com/user-attachments/assets/b6eee959-6802-40e5-b284-0136bdd0a887)
  ![image](https://github.com/user-attachments/assets/5e99dcf4-243f-415c-b83e-085e6edd805a)

- Actuator:
  API monitoring using the Actuator library.
  ![image](https://github.com/user-attachments/assets/c163c2f1-2a50-45c6-a222-0cf99c618641)

- Design Patterns:
  API developed using MVC, DTO, Services, and Repository patterns.

- Lombok:
  A library used to reduce boilerplate code.

- Bean Validation:
  Validation following best practices in the DTO classes.

# STATISTICAL DATA
- Rate Limiting:
  This article from Cloudflare explains how rate limiting can block users, bots, or apps that abuse a web property. Rate limiting can help stop certain types of malicious bot activities, including brute-force attacks and DoS, which can contribute to spam sending: https://www.cloudflare.com/pt-br/learning/bots/what-is-rate-limiting/

- Importance of Rate Limiting:
  This article from Aiqon highlights the importance of rate limiting as a vital part of modern web security, helping to block malicious activities like unauthorized login attempts, which might be associated with spam sending: https://aiqon.com.br/blog/rate-limit-uma-parte-vital-da-seguranca-web-moderna/
