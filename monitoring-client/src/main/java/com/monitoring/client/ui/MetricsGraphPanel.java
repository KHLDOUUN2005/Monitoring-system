package com.monitoring.client.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import pourelle.SystemMetrics;

public class MetricsGraphPanel extends JPanel {
    private List<Double> cpuData = new ArrayList<>();
    private List<Double> ramData = new ArrayList<>();
    private static final int MAX_POINTS = 10;

    public MetricsGraphPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 250));
        setBorder(BorderFactory.createTitledBorder(" Évolution CPU/RAM (20 dernières secondes)"));
    }

    public void updateData(List<SystemMetrics> history) {
        int start = Math.max(0, history.size() - MAX_POINTS);
        cpuData.clear();
        ramData.clear();
        
        for (int i = start; i < history.size(); i++) {
            SystemMetrics m = history.get(i);
            cpuData.add(m.getCpuUsage());
            ramData.add(m.getMemoryUsage());
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight() - 40; 
        int padding = 30;

        
        g2d.setColor(new Color(220, 220, 220)); 
        g2d.setStroke(new BasicStroke(1));
        for (int i = 1; i <= 4; i++) {
            int y = height - (i * height / 4) + padding;
            g2d.drawLine(padding, y, width - padding, y);
            g2d.setColor(Color.GRAY);
            g2d.drawString(i * 25 + "%", 5, y + 5);
        }

        if (cpuData.isEmpty()) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("Aucune donnée disponible", width/2 - 100, height/2);
            return;
        }

        
        g2d.setColor(new Color(220, 30, 30)); // Bright red
        g2d.setStroke(new BasicStroke(2.5f));
        drawLine(g2d, cpuData, width, height, padding, new Color(255, 180, 180, 120));

        g2d.setColor(new Color(30, 100, 220)); // Bright blue
        g2d.setStroke(new BasicStroke(2.5f));
        drawLine(g2d, ramData, width, height, padding, new Color(180, 220, 255, 120));

        int legendX = width - 100;
        int legendY = 15;
    
        g2d.setColor(new Color(220, 30, 30));
        g2d.fillRect(legendX, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("CPU", legendX + 20, legendY + 12);
    
        g2d.setColor(new Color(30, 100, 220));
        g2d.fillRect(legendX, legendY + 20, 15, 15);
        g2d.drawString("RAM", legendX + 20, legendY + 32);
    }

    private void drawLine(Graphics2D g2d, List<Double> data, int width, int height, int padding, Color fillColor) {
        if (data.size() < 2) return;

        int stepX = (width - 2 * padding) / Math.max(1, data.size() - 1);
        
        g2d.setColor(fillColor);
        Polygon area = new Polygon();
        area.addPoint(padding, height + padding);
        for (int i = 0; i < data.size(); i++) {
            int x = padding + i * stepX;
            int y = (int)(padding + (1 - data.get(i) / 100.0) * height);
            area.addPoint(x, y);
        }
        area.addPoint(padding + (data.size() - 1) * stepX, height + padding);
        g2d.fillPolygon(area);

        g2d.setColor(g2d.getColor().darker().darker()); 
        for (int i = 0; i < data.size() - 1; i++) {
            int x1 = padding + i * stepX;
            int y1 = (int)(padding + (1 - data.get(i) / 100.0) * height);
            int x2 = padding + (i + 1) * stepX;
            int y2 = (int)(padding + (1 - data.get(i + 1) / 100.0) * height);
            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < data.size(); i++) {
            int x = padding + i * stepX;
            int y = (int)(padding + (1 - data.get(i) / 100.0) * height);
            g2d.setColor(g2d.getColor());
            g2d.fillOval(x - 4, y - 4, 8, 8);
            g2d.setColor(Color.WHITE);
            g2d.fillOval(x - 2, y - 2, 4, 4);
        }
    }
}
