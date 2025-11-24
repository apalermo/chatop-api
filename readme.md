# Chatop API

API Back-end de mise en relation entre locataires et propriétaires pour la plateforme **Chatop**.
Application développée avec **Java**, **Spring Boot** et **MySQL**.

Ce projet respecte une architecture en couches (Controller, Service, Repository, DTO) et implémente une sécurité sans
état (Stateless) via JWT.

## 📋 Pré-requis

Avant de commencer, assurez-vous d'avoir installé :

* **Java 21** ou supérieur (Le projet est configuré pour **Java 25**).
* **MySQL 8.0**.
* **Maven** (Optionnel, le wrapper `mvnw` est inclus dans le projet).

## ⚙️ Configuration de la Base de Données

### 1. Création de la base

Connectez-vous à votre serveur MySQL et créez une base de données vide :

```sql
CREATE DATABASE chatop_db;

-- Création de l'utilisateur (à adapter avec vos informations)
CREATE USER 'chatop_user'@'localhost' IDENTIFIED BY 'LeMdpDeVotreChoix!';

-- Attribution des droits sur la base
GRANT ALL PRIVILEGES ON chatop_db.* TO 'chatop_user'@'localhost';
FLUSH PRIVILEGES;
```

> **Note :** L'application est configurée avec `ddl-auto=update`, elle créera automatiquement les tables (`users`,
`rentals`, `messages`) au premier démarrage.

### 2. Variables d'Environnement

Pour des raisons de sécurité, les identifiants ne sont pas stockés dans le code.
Créez un fichier **`.env`** à la racine du projet (au même niveau que le `pom.xml`) avec le contenu suivant :

```properties
# Configuration MySQL
DB_URL=jdbc:mysql://localhost:3306/chatop_db?serverTimezone=UTC
DB_USERNAME=votre_utilisateur_mysql (ex: root)
DB_PASSWORD=votre_mot_de_passe_mysql
# Sécurité JWT (Clé de signature - 32 caractères min)
JWT_SECRET=VotreSuperCleSecretePourLeProjetChatopTresLongue
```

## 🚀 Installation et Lancement

1. **Cloner le projet**
   ```bash
   git clone https://github.com/apalermo/chatop-api.git
   cd chatop-api
   ```

2. **Lancer l'application**
   Utilisez le wrapper Maven inclus pour télécharger les dépendances et démarrer le serveur :

    * **Sous Windows :**
        ```cmd
        mvnw.cmd spring-boot:run
        ```
    * **Sous Mac/Linux :**
        ```bash
        ./mvnw spring-boot:run
        ```

L'API démarrera sur le port **3001**.

## 📚 Documentation de l'API (Swagger)

Une documentation interactive complète (OpenAPI / Swagger UI) est disponible une fois l'application lancée. Elle permet
de :

* Visualiser toutes les routes.
* Tester les endpoints directement (Bouton "Try it out").
* S'authentifier via le bouton **Authorize** (Bearer Token).

👉 **Accéder à la
documentation : [http://localhost:3001/swagger-ui/index.html](http://localhost:3001/swagger-ui/index.html)**

## 🏗️ Architecture Technique

Ce projet suit les standards de développement Spring Boot :

* **Security** : Authentification JWT via `Spring Security`, cryptage des mots de passe avec `BCrypt`.
* **Data** : Communication avec MySQL via `Spring Data JPA`.
* **DTO Pattern** : Utilisation de DTOs (Data Transfer Objects) pour sécuriser et formater les données entrantes et
  sortantes.
* **Validation** : Vérification des données (`@Valid`, `@NotBlank`) avant traitement.
* **Documentation** : Génération automatique via `SpringDoc OpenAPI`.

## 👤 Auteur

**Anthony PALERMO**

* 🐱 GitHub : https://github.com/apalermo

---
*Projet réalisé dans le cadre du parcours "Développeur Full-Stack Java & Angular" chez OpenClassrooms (2025).*