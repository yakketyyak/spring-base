# spring-base

Ce projet est un exemple pour construire des services WEB avec le puissant framework Spring Boot

Vous devez installer jdk8, maven et ajouter maven à votre variable d'environnement en fonction de votre plateforme.
Installez et configuer maven (https://maven.apache.org/install.html).

Vous devez utiliser STS (Spring Tools Suite https://spring.io/tools/sts/all) pour l'importer après l'avoir cloné.
Avant de l'importer dans STS vous devez créer la base de données en local.<br />
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

# Utilisation de la base de données

  Le dossier src/main/resources contient trois fichiers .properties (application-dev, application-prod, application-local).
  En fonction de votre zone de travail (local, production ou developpement) vous précisez le profil actif à l'aide
  de la ligne spring.profiles.active=local de application.properties
  
# Envoie de mail

Pour envoyer des mails aux utilisateurs vous devez ajouter des paramètres de connexion gmail 
au fichier application.properties.

Activer less secure apps avec https://myaccount.google.com/lesssecureapps?pli=1. <br />
  #serveur de mail <br /> 
  smtp.mail.host=smtp.gmail.com <br />
  smtp.mail.port=587  <br />
  smtp.mail.username=your_gmail_username  <br />
  smtp.mail.password=your_gmail_password <br />

Modifier le username et le password de ce fichier .

Nous avons inclus l'internalisation des messages d'erreurs (Anglais, Français).

Pour celà vous verrez trois fichiers errorMessage.properties, errorMessage_en.properties et errorMessage_fr.properties.

La langue par défaut est le français (errorMessage.properties).<br />
Comme nous l'avons fait vous pouvez ajouter d'autres langues.<br />
Il suffit de créer un fichier errorMessage_(code_langue).properties (https://fr.wikipedia.org/wiki/Liste_des_codes_ISO_639-1) puis vous traduisez les messages dans la langue choisie.

Pour changer de langue avec la collection Postman , mettez dans le header le mot lang comme valeur le code de langue.

Exemple lang en pour anglais .
Vous trouverez la capture d'une image illustrant mes dires dans src/main/resources.

# Comment lancez le projet ?

Allez à la racine du dossier du projet .

Rassurez vous que les deux commandes fonctionnent correctement. <br />
  mvn clean install -U (à la fin :  BUILD SUCCESS).<br />
  mvn spring-boot:run (Tomcat started on port(s): 8080 (http)).<br /><br />
  
En cas d'erreur, vérifier la connexion à la base de données (j'ai utilisé le port par défaut de MAMP 8889, changez le si vous utilisez XAMPP ou WAMP en 3306)
  
