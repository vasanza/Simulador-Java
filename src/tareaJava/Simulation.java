package tareaJava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    public Random generator = new Random(); // random number generator
    public EventHeap h;
    double now;

    public Machine m = new Machine();
    public Repairman r = new Repairman();
    public User u = new User();

    public Simulation() {
        generator = new Random();
        h = new EventHeap(10000);
        now = 0;
    }

    public void scheduleEvent(Event e) {
        h.insert(e);
    }

    public void setup() {
        Event machineEvent = new Event();
        machineEvent.setHandler(m);
        machineEvent.setType(working);
        machineEvent.setTime(0);
        scheduleEvent(machineEvent);

        Event userEvent = new Event();
        userEvent.setHandler(u);
        //userEvent.setType(userCheck);
        userEvent.setType(userCheck);
        userEvent.setTime(60);
        scheduleEvent(userEvent);
        return;
    }

    public void leerTxt() {

        ArrayList<String> listaEventos = new ArrayList<String>();
        try (BufferedReader bufr = new BufferedReader(new FileReader("/home/usuario/eventos.txt"))) {

            String linea;
            while ((linea = bufr.readLine()) != null) {
                listaEventos.add(linea);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Event arregloObjetosEventos[] = new Event[listaEventos.size()];
        for (int i = 0; i < arregloObjetosEventos.length; i++) {
            arregloObjetosEventos[i] = new Event();
            String cadena = listaEventos.get(i).toString();
            String d1 = cadena.substring(0, cadena.indexOf(","));
            String d2 = cadena.substring(cadena.indexOf(",") + 1, cadena.lastIndexOf(","));
            String d3 = cadena.substring(cadena.lastIndexOf(",") + 1, cadena.length());

            //mru,15,tiempo
            if (d1.equals("m")) {
                arregloObjetosEventos[i].setHandler(m);
            } else if (d1.equals("u")) {
                arregloObjetosEventos[i].setHandler(u);
            } else if (d1.equals("r")) {
                arregloObjetosEventos[i].setHandler(r);
            }

            arregloObjetosEventos[i].setType(Integer.valueOf(d2));
            arregloObjetosEventos[i].setTime(Double.parseDouble(d3));
            scheduleEvent(arregloObjetosEventos[i]);

        }

      
        return;
    }

    public void run(double maxTime) {
        while (!h.isEmpty() && h.peek().getTime() <= maxTime) {
            Event nextEvent = h.remove();
            now = nextEvent.getTime();
            nextEvent.getHandler().respondToEvent(nextEvent, this);
        }
    }
    // events
    public final int working = 1;
    public final int failure = 2;
    public final int startRepair = 3;
    public final int finishRepair = 4;
    public final int userCheck = 5;

}
