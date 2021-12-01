package org.iesfm.rest.swing;

import org.iesfm.rest.Flight;
import org.iesfm.rest.FlightAPI;
import org.iesfm.rest.clients.FlightClient;
import org.iesfm.rest.exceptions.FlightNotFoundException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.List;

public class FlightSwing {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aerolinea");
        JPanel panel = new JPanel();
        JTextArea label = new JTextArea();

        FlightAPI flightAPI = new FlightClient(new RestTemplateBuilder().rootUri("http://localhost:8080").build());

        List<Flight> flights = flightAPI.list(null);

        frame.add(panel);
        frame.setBounds(675, 300, 600, 600);

        for (Flight flight : flights) {
            panel.add(new JLabel(flight.toString()));
        }

        try {
            Flight flight = flightAPI.getFlight("2");
            panel.add(new JLabel(flight.toString()));
        } catch (HttpClientErrorException.NotFound e) {
            JOptionPane.showMessageDialog(null, "No existe el vuelo");
        }

        try {
            flightAPI.createFlight(new Flight("2", "Barcelona", "Madrid"));
            JOptionPane.showMessageDialog(null, "Vuelo creado");
        } catch (HttpClientErrorException.Conflict e) {
            JOptionPane.showMessageDialog(null, "Ya existe el vuelo");
        }

        try {
            flightAPI.updateFlight("2", new Flight("2", "Barcelona", "Madrid"));
            JOptionPane.showMessageDialog(null, "Vuelo actualizado");
        } catch (HttpClientErrorException.NotFound e) {
            JOptionPane.showMessageDialog(null, "No existe el vuelo");
        }

        try {
            flightAPI.deleteFlight("2");
            JOptionPane.showMessageDialog(null, "Vuelo eliminado");
        } catch (HttpClientErrorException.NotFound e) {
            JOptionPane.showMessageDialog(null, "No existe el vuelo");
        }

        frame.setVisible(true);
    }
}
