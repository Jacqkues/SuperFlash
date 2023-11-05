# SuperFlash
SuperFlash est une application de flashcards intelligente qui vous aide à progresser dans vos révisions.

## Installation

* Dans un premier temps , il faut éditer le fichier application.properties et modifier les informations de connexion à la base de données (remplacer user et password par les identifiant de votre bdd mysql). Le fichier se trouve dans src/main/ressources.

```

spring.datasource.url=jdbc:mysql://localhost:3306/superflash?useSSL=false&serverTimezone=UTC

spring.datasource.username = user
spring.datasource.password = password

```

* Ensuite, il faut créer la base de données superflash à l'aide du script sql qui se trouve dans le dossier sql à la racine du projet.

```
mysql -u user -p 

source superflash.sql
```

* Enfin, vous pouvez lancer l'application depuis eclipse ou en ligne de commande avec la commande suivante :

```
mvn spring-boot:run
```

## Utilisation

* Pour utiliser l'application, il faut se rendre sur la page d'accueil à l'adresse suivante : http://localhost:8080/. Vous serez redirigé vers la page de connexion. Si vous n'avez pas de compte, vous pouvez vous inscrire en cliquant sur le lien "Register Here" en bas de la page.

* Une fois connecté, vous arrivez sur votre dashboard. Ici, vous pouvez voir votre progression selon les decks créés, accéder aux révisions en cours et également accéder à la révision du jour créée intelligemment selon vos précédents scores.

* Votre progression est visible sous forme de graphique qui représente le pourcentage de bonnes réponses pour chaque deck et à chaque révision.

* Le bouton + situé dans la barre de navigation vous permet de créer rapidement un nouveau deck depuis n'importe quelle page du site.

* Vous avez la possibilité de modifier un deck en y ajoutant des flashcards et en le rendant public ; vous pouvez également le supprimer.

* Les flashcards présentes dans les decks peuvent également être modifiées et supprimées.

* Vous disposez d'un espace "MY ACCOUNT" dans lequel vous pouvez voir vos informations, et également votre score qui est calculé en fonction de vos bonnes et mauvaises réponses au cours des révisions.

* Vous avez également accès à la page "explore" qui vous permet de voir et d'utiliser les decks créés par d'autres utilisateurs et rendus publics par la suite. Ici, vous pouvez filtrer les decks en fonction de la matière qui vous intéresse.







## Organisation du projet :

### Liste des dossiers : 
* **config** : contient la configuration pour spring security
* **controller** : contient les controlleurs de l'application
* **dto** : contient les objets de transfert de données
* **entity** : contient les entités de l'application
* **JavaClass** : contient des classes java utiles pour l'application
* **repository** : contient les repositories de l'application
* **service** : contient les services de l'application
* **service/impl** : contient les implémentations des services de l'application
* **security** : contient la class custom user details
* **sql** : contient le script sql pour la création de la base de données
