
---

##  README pour `monitoring-client`

```markdown
#  monitoring-client

> Interface graphique desktop (Swing) pour le système de surveillance distribué — visualisation des données en temps réel.

---

##  Fonctionnalités

-  Tableau des agents avec statut (ONLINE/ALERT/OFFLINE)
-  Graphiques en temps réel (CPU/RAM) avec historique de 20 secondes
-  Liste des alertes critiques
-  Sélection d’agent pour suivi détaillé
-  Rafraîchissement automatique toutes les 2 secondes

---

##  Technologies

- Java 17+
- Swing (JFrame, JTable, JComboBox, JPanel)
- RMI pour la communication avec le serveur
- Custom painting pour les graphiques

---

##  Structure du Projet
src/main/java/
├── com.monitoring.client/
│ ├── ui/ # Interface graphique
│ │ ├── MainFrame.java
│ │ └── MetricsGraphPanel.java
│ └── ClientMain.java # Point d’entrée
└── pourelle/ # Classes partagées (DTOs)
├── Agent.java
├── Alert.java
├── SystemMetrics.java
└── MonitoringService.java
