-- Supprimer la base de données si elle existe
DROP DATABASE IF EXISTS superflash;

-- Créer la base de données
CREATE DATABASE superflash;

-- Utiliser la base de données superflash
USE superflash;

-- Créer la table matieres
CREATE TABLE matieres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

-- Insérer les matières
INSERT INTO matieres (name, description) VALUES
    ('Mathématiques', 'Étude des mathématiques'),
    ('Informatique', 'Informatique et programmation'),
    ('Physique', 'Étude de la physique'),
    ('Chimie', 'Étude de la chimie'),
    ('Français', 'Étude de la langue française'),
    ('Anglais', 'Étude de la langue anglaise');
    