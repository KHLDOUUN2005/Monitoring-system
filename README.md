# Système de Surveillance Distribué

> Projet de la Filière DATA — Année Universitaire 2025-2026  
> Architecture distribuée pour la surveillance en temps réel des ressources système (CPU, RAM) sur plusieurs machines distantes.

---

## Objectifs Pédagogiques

-  Implémenter une architecture client/serveur complète avec threads, UDP, TCP et RMI.
-  Concevoir une interface graphique moderne (Swing).
-  Comprendre la séparation Backend/Frontend et les principes MVC.
-  Appliquer les concepts de systèmes distribués et de gestion d’alertes.

---

##  Architecture Globale

Le système est composé de trois composants principaux :

1. **Agent de Surveillance** (`monitoring-agent`)  
   - Collecte les métriques système et envoie les données au serveur.

2. **Serveur Central** (`monitoring-server`)  
   - Réception des données, gestion des agents, exposition des services via RMI.

3. **Interface Utilisateur** (`monitoring-client`)  
   - Visualisation des données en temps réel : tableau, graphiques, alertes.

---

##  Fonctionnalités Clés

-  **Tableau dynamique** des agents avec statut (ONLINE/ALERT/OFFLINE)
-  **Graphiques en temps réel** (CPU/RAM) avec historique de 20 secondes
-  **Alertes critiques** envoyées via TCP quand les seuils sont dépassés
-  **Détection automatique des agents hors ligne** (timeout de 12s)
-  **Rafraîchissement automatique** toutes les 2 secondes
-  **Interface Swing moderne** avec onglets et sélection d’agent

---

##  Technologies Utilisées

| Composant | Technologie |
|-----------|-----------|
| Backend | Java 17+, Threads, UDP, TCP, RMI |
| Frontend | Swing (Java Desktop) |
| Build | Maven |

---

##  Structure du Dépôt
monitoring-system/

├── monitoring-agent/ # Agent de collecte des métriques

├── monitoring-server/ # Serveur central (RMI + stockage)

├── monitoring-client/ # Interface graphique (Swing)

└── README.md # Ce fichier
