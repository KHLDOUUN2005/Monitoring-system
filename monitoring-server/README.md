
---

##  README pour `monitoring-server`

```markdown
#  monitoring-server

> Serveur central du système de surveillance distribué — réception, stockage et exposition des données via RMI.

---

##  Fonctionnalités

-  Réception des métriques via **UDP** (multi-agents)
-  Réception des alertes via **TCP**
-  Gestion concurrente des agents
-  Services RMI pour l’accès aux données
-  Détection des agents hors ligne (timeout de 12s)
-  Stockage des métriques et alertes

---

##  Technologies

- Java 17+
- `DatagramSocket` / `ServerSocket` pour UDP/TCP
- `UnicastRemoteObject` pour RMI
- `ConcurrentHashMap` pour la gestion thread-safe

---

##  Structure du Projet
src/main/java/
├── com.monitoring.server/
│ ├── receiver/ # Récepteurs UDP/TCP
│ │ ├── UDPReceiver.java
│ │ └── TCPAlertReceiver.java
│ ├── rmi/ # Interface RMI
│ │ ├── MonitoringService.java
│ │ └── MonitoringServiceImpl.java
│ ├── storage/ # Stockage et gestion
│ │ ├── DataManager.java
│ │ └── AgentHealthChecker.java
│ └── ServerMain.java # Point d’entrée
└── pourelle/ # Classes partagées (DTOs)
├── Agent.java
├── Alert.java
├── SystemMetrics.java
└── MonitoringService.java


