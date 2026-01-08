package com.monitoring.client.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import pourelle.Agent;
import pourelle.Alert;
import pourelle.MonitoringService;
import pourelle.SystemMetrics;

public class MainFrame extends JFrame {

    private JTable agentsTable;
    private DefaultTableModel tableModel;
    private JTable alertsTable;
    private DefaultTableModel alertsModel;
    private String selectedAgentId = null;
    private JComboBox<String> agentSelector;
    private MetricsGraphPanel graphPanel;

    public MainFrame() {
        setTitle(" Système de Surveillance Distribué");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] agentColumns = {"Agent ID", "IP", "CPU %", "RAM %", "Statut", "Dernière mise à jour"};
        tableModel = new DefaultTableModel(agentColumns, 0);
        agentsTable = new JTable(tableModel);
        agentsTable.setAutoCreateRowSorter(true);
        agentsTable.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());
        agentsTable.setDefaultRenderer(Object.class, new StatusCellRenderer());

        agentsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int viewRow = agentsTable.getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = agentsTable.convertRowIndexToModel(viewRow);
                    String agentId = (String) tableModel.getValueAt(modelRow, 0);
                    selectedAgentId = agentId;
                    agentSelector.setSelectedItem(agentId);
                    updateGraphs();
                }
            }
        });
        JScrollPane agentsScrollPane = new JScrollPane(agentsTable);

        
        String[] alertColumns = {"Agent ID", "Message", "Timestamp"};
        alertsModel = new DefaultTableModel(alertColumns, 0);
        alertsTable = new JTable(alertsModel);
        alertsTable.setAutoCreateRowSorter(true);
        JScrollPane alertsScrollPane = new JScrollPane(alertsTable);

        
        JPanel graphsPanel = new JPanel(new BorderLayout());

       
        JPanel selectorPanel = new JPanel(new FlowLayout());
        selectorPanel.setBorder(BorderFactory.createTitledBorder("📌 Surveillance ciblée"));
        selectorPanel.add(new JLabel("Agent :"));
        
        agentSelector = new JComboBox<>();
        agentSelector.setPreferredSize(new Dimension(150, 25));
        agentSelector.addActionListener(e -> {
        	selectedAgentId = (String) agentSelector.getSelectedItem();
        	updateGraphs();
        });
        selectorPanel.add(agentSelector);
        graphsPanel.add(selectorPanel, BorderLayout.NORTH);

        
        graphPanel = new MetricsGraphPanel();
        graphsPanel.add(graphPanel, BorderLayout.CENTER);

        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(" Agents", agentsScrollPane);
        tabbedPane.addTab(" Alertes", alertsScrollPane);
        tabbedPane.addTab(" Graphiques", graphsPanel);
        add(tabbedPane);

        
        refreshData();

        
        if (agentSelector.getItemCount() > 0) {
            selectedAgentId = (String) agentSelector.getItemAt(0);
            agentSelector.setSelectedItem(selectedAgentId);
            updateGraphs();
        }

        
        Timer timer = new Timer(2000, e -> {
            refreshData();
            if (selectedAgentId != null) {
                updateGraphs();
            }
        });
        timer.start();
    }

    private void setSelectedAgentId(String agentId) {
        
        SwingUtilities.invokeLater(() -> {
            selectedAgentId = agentId;
            agentSelector.setSelectedItem(agentId);
            updateGraphs();
        });
    }

    private void refreshData() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 7000);
            MonitoringService service = (MonitoringService) registry.lookup("MonitoringService");

            
            List<Agent> agents = service.getAllAgents();
            tableModel.setRowCount(0);
            for (Agent agent : agents) {
                String cpu = String.format("%.1f", agent.getLastMetrics().getCpuUsage());
                String ram = String.format("%.1f", agent.getLastMetrics().getMemoryUsage());
                String lastUpdate = Instant.ofEpochMilli(agent.getLastUpdate())
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                
                tableModel.addRow(new Object[]{
                    agent.getAgentId(),
                    agent.getIpAddress(),
                    cpu + "%",
                    ram + "%",
                    agent.getStatus(),
                    lastUpdate
                });
            }

            
            List<Alert> alerts = service.getAllAlerts();
            alertsModel.setRowCount(0);
            for (Alert alert : alerts) {
                String timestamp = Instant.ofEpochMilli(alert.getTimestamp())
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                alertsModel.addRow(new Object[]{
                    alert.getAgentId(),
                    alert.getMessage(),
                    timestamp
                });
            }

            
            String currentlySelected = selectedAgentId; 
            
            agentSelector.removeAllItems();
            for (Agent agent : agents) {
                agentSelector.addItem(agent.getAgentId());
            }
            
            
            if (currentlySelected != null && agentSelector.getItemCount() > 0) {
                boolean found = false;
                for (int i = 0; i < agentSelector.getItemCount(); i++) {
                    if (currentlySelected.equals(agentSelector.getItemAt(i))) {
                        agentSelector.setSelectedIndex(i);
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    selectedAgentId = (String) agentSelector.getItemAt(0);
                    agentSelector.setSelectedIndex(0);
                } else {
                    selectedAgentId = currentlySelected;
                }
            } else if (agentSelector.getItemCount() > 0) {
               
                selectedAgentId = (String) agentSelector.getItemAt(0);
                agentSelector.setSelectedIndex(0);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                " Erreur de connexion au serveur :\n" + e.getMessage(), 
                "Connexion RMI", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateGraphs() {
        if (selectedAgentId == null) {
            if (graphPanel != null) graphPanel.updateData(new ArrayList<>());
            return;
        }

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 7000);
            MonitoringService service = (MonitoringService) registry.lookup("MonitoringService");

            List<SystemMetrics> history = service.getMetricsHistory(selectedAgentId);
            
            
            if (graphPanel != null) {
                graphPanel.updateData(history);
            }

        } catch (Exception e) {
            if (graphPanel != null) graphPanel.updateData(new ArrayList<>());
        }
    }

    
    private class StatusCellRenderer extends JLabel implements javax.swing.table.TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            setOpaque(true);

            String status = (String) table.getValueAt(row, 4);
            if ("ONLINE".equals(status)) {
                setBackground(new Color(200, 255, 200)); // vert
                setForeground(new Color(0, 100, 0));
            } else if ("ALERT".equals(status)) {
                setBackground(new Color(255, 230, 200)); // orange
                setForeground(new Color(180, 80, 0));
            } else if ("OFFLINE".equals(status)) {
                setBackground(new Color(255, 200, 200)); // rouge
                setForeground(new Color(150, 0, 0));
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }

            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }

            return this;
        }
    }
}
