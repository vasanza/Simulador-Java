/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaJava;

public class DiscreteEventSimulation {

	public static void main(String[] args) {
		
		Simulation s = new Simulation();
		
		//s.setup(); // setup simulation;
                s.leerTxt();
		
		s.run(10000); // run of simulation
		
	}

}