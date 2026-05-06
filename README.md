# Animal Shelter Management System

Projet Java de gestion d’un refuge animalier reposant sur une base de données relationnelle PostgreSQL.

Ce projet a été réalisé dans le cadre d’un travail universitaire afin de modéliser, centraliser et gérer les informations liées aux animaux, aux familles d’accueil, aux soins vétérinaires et aux activités du refuge.

---

## Fonctionnalités

- Gestion des animaux du refuge
- Gestion des familles d’accueil et d’adoption
- Suivi des soins et de l’historique médical
- Gestion des box et des placements
- Gestion des bénévoles et des activités
- Persistance des données avec PostgreSQL
- Gestion des exceptions métier et des erreurs de connexion

---

## Technologies utilisées

- Java
- PostgreSQL
- JDBC
- SQL
- Javadoc

---

## Structure du projet

```txt
src/
├── application/
├── connexion/
├── data/
├── exceptions/
├── gestion/
└── Serial/

database/
└── script_creation.sql

docs/
└── javadoc/
```

---

## Base de données

Le script SQL de création de la base est disponible dans :

```txt
database/script_creation.sql
```

Le projet utilise PostgreSQL pour stocker les données du refuge animalier.

---

## Configuration de la connexion

Les informations sensibles de connexion ne sont pas stockées dans le code source.

Créer un fichier :

```txt
config/database.properties
```

à partir du modèle :

```txt
config/database.example.properties
```

Exemple :

```properties
db.url=jdbc:postgresql://localhost:5432/refuge_animalier
db.user=your_username
db.password=your_password
```

---

## Documentation

La documentation Javadoc est disponible dans :

```txt
docs/javadoc/
```

Ouvrir :

```txt
docs/javadoc/index.html
```

dans un navigateur pour consulter la documentation.

---

## Objectif du projet

L’objectif principal du projet est de concevoir une architecture Java organisée autour d’une base de données relationnelle afin d’assurer :

- la cohérence des données,
- la traçabilité des informations,
- la gestion des relations entre les entités du refuge,
- et la séparation entre logique métier, accès aux données et gestion des exceptions.

---

## Auteur

Projet réalisé par Younes Baziz et Floriant Prevost.
