
---

## README pour `monitoring-agent`

```markdown
#  monitoring-agent

> Agent de collecte des métriques système (CPU, RAM, disque) pour le système de surveillance distribué.

---

## Fonctionnalités

-  Collecte périodique des métriques système (CPU, RAM)
-  Envoi des données au serveur central via **UDP**
-  Envoi d’alertes critiques via **TCP** quand les seuils sont dépassés
-  Identification unique de l’agent
-  Exécution multi-threadée

---

##  Technologies

- Java 17+
- `java.lang.management.OperatingSystemMXBean` pour les métriques système
- `DatagramSocket` pour UDP
- `Socket` pour TCP

---

##  Structure du Projet
src/main/java/
├── com.monitoring.agent/
│ ├── collector/ # Collecte des métriques
│ │ └── MetricsCollector.java
│ ├── model/ # Configuration de l’agent
│ │ └── AgentConfig.java
│ ├── sender/ # Envoi des données
│ │ ├── TCPAlertSender.java
│ │ └── UDPSender.java
│ └── AgentMain.java # Point d’entrée
└── pourelle/ # Classes partagées (DTOs)
└── SystemMetrics.java # Métriques système (sérialisable)
