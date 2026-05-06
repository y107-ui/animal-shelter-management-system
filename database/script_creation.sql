--BAZIZ YOUNES , FLORIANT PREVOST 
-- Création de la table annimal

CREATE TABLE Animal(
    id_animal INTEGER PRIMARY KEY,
    puce VARCHAR(50),
    nom VARCHAR(255),
    type  VARCHAR(100),
    race VARCHAR(255),
    date_naissance DATE,
    date_arrivee DATE,
    statut VARCHAR(50),
    compatible_humains BOOLEAN,
    compatible_chiens BOOLEAN,
    compatible_chats BOOLEAN,
    compatible_bebes BOOLEAN,
    nourriture VARCHAR(500)
    
);
-- Création de la table soin

CREATE TABLE Soin(
    id_soin INTEGER PRIMARY KEY,
    libelle VARCHAR(255),
    description TEXT,
    duree_minute INTEGER,
    veterinaire VARCHAR(255)
);
-- Création de la table animal_soin(liaison)
CREATE TABLE Animal_Soin(
    id_animal INTEGER,
    id_soin INTEGER,
    date_soin DATE,
    commentaire TEXT,
    PRIMARY KEY(id_animal, id_soin, date_soin),
    CONSTRAINT fk_animal_soin_animal FOREIGN KEY(id_animal) REFERENCES Animal(id_animal),
    CONSTRAINT fk_animal_soin_soin FOREIGN KEY(id_soin) REFERENCES Soin(id_soin)
);
-- Création de la table famille

CREATE TABLE Famille(
    id_famille INTEGER PRIMARY KEY,
    nom VARCHAR(255),
    adresse VARCHAR(255),
    telephone VARCHAR(50),
    capacite INTEGER
);
-- Création de la table animal_famille(liaison)
CREATE TABLE Animal_Famille(
    id_animal INTEGER,
    id_famille INTEGER,
    type_famille VARCHAR(20) CHECK(type_famille IN ('Accueil', 'Adoption')),
    date_debut_famille DATE,
    date_fin_famille DATE,
    raison_retour TEXT,
    PRIMARY KEY(id_animal, id_famille, type_famille, date_debut_famille),
    CONSTRAINT fk_animal_famille_animal FOREIGN KEY(id_animal) REFERENCES Animal(id_animal),
    CONSTRAINT fk_animal_famille_famille FOREIGN KEY(id_famille) REFERENCES Famille(id_famille)
);
-- Création de la table box
CREATE TABLE Box(
    id_box INTEGER PRIMARY KEY,
    num INTEGER,
    type_autorise VARCHAR(255),
    capacite_max INTEGER
   
);
-- Création de la table animal_box(liaison)
CREATE TABLE Animal_Box(
    id_animal INTEGER,
    id_box INTEGER,
    date_debut_box DATE,
    date_fin_box DATE,
    commentaire TEXT,
    PRIMARY KEY(id_animal, id_box, date_debut_box),
    CONSTRAINT fk_animal_box_animal FOREIGN KEY(id_animal) REFERENCES Animal(id_animal),
    CONSTRAINT fk_animal_box_box FOREIGN KEY(id_box) REFERENCES Box(id_box)
);
-- Création de la table activité
CREATE TABLE Activite(
    id_activite INTEGER PRIMARY KEY,
    type_activite VARCHAR(255),
    description TEXT,
    nombre_min_animaux INTEGER
);
-- Création de la table créneau
CREATE TABLE Creneau(
    id_creneau INTEGER PRIMARY KEY,
    date_creneau DATE,
    heure_debut TIME,
    heure_fin TIME,
    nombre_min_benevoles INTEGER
);
-- Création de la table personne
CREATE TABLE Benevole(
    id_benevole INTEGER PRIMARY KEY,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    telephone VARCHAR(50),
    role VARCHAR(255)
);
-- Création de la table partictipation_activité 
CREATE TABLE Participation_Activite(
    id_animal INTEGER,
    id_creneau INTEGER,
    id_activite INTEGER,
    id_benevole INTEGER,
    commentaire TEXT,
    PRIMARY KEY(id_animal, id_creneau, id_activite, id_benevole),
    CONSTRAINT fk_participation_animal FOREIGN KEY(id_animal) REFERENCES Animal(id_animal),
    CONSTRAINT fk_participation_creneau FOREIGN KEY(id_creneau) REFERENCES Creneau(id_creneau),
    CONSTRAINT fk_participation_activite FOREIGN KEY(id_activite) REFERENCES Activite(id_activite),
    CONSTRAINT fk_participation_benevole FOREIGN KEY(id_benevole) REFERENCES Benevole(id_benevole)
);
