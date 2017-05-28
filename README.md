# spring-base

Ce projet est un exemple pour construire des services WEB avec le puissant framework Spring Boot


Vous devez utiliser STS (Spring Tools Suite) pour l'importer après l'avoir cloné.
Avant de l'importer dans STS vous devez créer la base de données en local.
Pour celà allez dans le dossier sr/main/resources puis exécutez le script du fichier spring-base.sql
avec un utilitaire comme phpMyAdmin ou Navicat.

1. Faites un clic droit étant dans l'éditeur puis cliquez sur => import.
2. RechercheZ dans la zone de texte => Maven.
3. Choisissez  => Existing Maven projects.
4. Allez dans le dossier dans lequel vous avez cloné le projet.
5. Cliquez sur le projet puis faites => Ouvrir puis validez .
6. Spring va importer le projet puis télécharger toutes les dépendances maven (soyez connecter à Internet la première fois).

Pour tester toutes les fonctionnalités vous devez utiliser un client comme Postman.
Télécharger le  sur le site officiel (https://www.getpostman.com/) puis importez la collection (fichier .json) présente dans le dossier src/main/resources

Utilisation de la base de données

  Le dossier src/main/resources contient trois fichiers .properties (application-dev, application-prod, application-local).
  En fonction de votre zone de travail (local, production ou developpement) vous précisez le profil actif à l'aide
  de la ligne spring.profiles.active=local de application.properties
  
Envoi de mail

Pour envoyer des mails aux utilisateurs vous devez ajouter des paramètres de connexion gmail 
au fichier application.properties.

Activer less secure apps avec https://myaccount.google.com/lesssecureapps?pli=1
  #serveur de mail  
  smtp.mail.host=smtp.gmail.com 
  smtp.mail.port=587 
  smtp.mail.username=your_gmail_username 
  smtp.mail.password=your_gmail_password 

Modifier le username et le password de ce fichier .
