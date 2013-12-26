/*
* Copyright (c) Joan-Manuel Marques 2013. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*
* This file is part of the practical assignment of Distributed Systems course.
*
* This code is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
*/

package lsimElement.recipesService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;


import recipesService.ServerData;
import recipesService.activitySimulation.SimulationData;
import recipesService.communication.Host;

import util.Serializer;


/**
 * @author Joan-Manuel Marques
 * July 2013
 *
 */
public class WorkerInitHandler {
	
	private ServerData serverData;
	private Host localHost;
	
	public Object execute(Object obj) {
		List<Object> params = (List<Object>) obj;
				
		int i=0;
		
		// param 0: groupId
		String groupId = ((String)params.get(i++));

		// new serverData + Raft parameters
		serverData = new ServerData(
				groupId,
				Long.parseLong((String)params.get(i++)) // electionTimeout
				);
		
		// simulation parameters
		SimulationData.getInstance().setSimulationStop(Integer.parseInt((String)params.get(i++))*1000);
		SimulationData.getInstance().setExecutionStop(Integer.parseInt((String)params.get(i++))*1000);

		Random rnd = new Random();
		int simulationDelay = (int) (rnd.nextDouble() * (2 * Integer.parseInt((String)params.get(i++)) * 1000));
		SimulationData.getInstance().setSimulationDelay(simulationDelay);
		SimulationData.getInstance().setSimulationPeriod(Integer.parseInt((String)params.get(i++))*1000);

		SimulationData.getInstance().setProbDisconnect(Double.parseDouble((String)params.get(i++)));
		SimulationData.getInstance().setProbReconnect(Double.parseDouble((String)params.get(i++)));
		SimulationData.getInstance().setProbCreate(Double.parseDouble((String)params.get(i++)));
		SimulationData.getInstance().setProbDel(Double.parseDouble((String)params.get(i))); // i not incremented because same parameter used to know if deletion is activated (next sentence)
		SimulationData.getInstance().setDeletion(!(Double.parseDouble((String)params.get(i++)) == 0.0));

		SimulationData.getInstance().setSamplingTime(Integer.parseInt((String)params.get(i++))*1000);
		
		// parameter to indicate if all Servers will run in a single computer
		// or they will run Servers hosted in different computers (or more than one 
		// Server in a single computer but this computer having the same internal and external IP address)
		// * true: all Server run in a single computer
		// * false: Servers running in different computers (or more than one Server in a single computer but
		// 			this computer having the same internal and external IP address)
		SimulationData.getInstance().setLocalExecution(((String)params.get(i++)).equals("localMode"));
		
		
//		//         this computer having the same internal and external IP address) 
//		// publish the service in the first empty port staring on obj.get(0)
//		// (starts a thread to deal with TSAE sessions from partner servers)
//		// set connected state on simulation data
//		ServerPartnerSide serverPartnerSide = new ServerPartnerSide(port, serverData); <---????????????????
//		serverPartnerSide.start();
//		
		String hostAddress = null;
		
		if (SimulationData.getInstance().localExecution()){
			try {
				hostAddress = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			hostAddress = getHostAddress();
		}
//
//		// waits until the serverPartnerSide has published the service in a port
//		serverPartnerSide.waitServicePublished(); <--- Crec que es podria treure
		
		String id = null;
		// create id
		id = groupId+"@"+hostAddress+":"+(System.currentTimeMillis()%100000)+":"+(rnd.nextLong()%100000);
		System.out.println("WorkerInitHandler --> id: "+id);
//		id = groupId+"@"+hostAddress+":"+serverPartnerSide.getPort(); <---- cal comprovar si es pot fer servir com id al nom del servei que es publica al registre

		// createlocal node information to send to coordinator node
		localHost = new Host(id);
		
        // init return value
		Object returnObj = null;
		try {
			returnObj = Serializer.serialize(localHost);
		} catch (IOException e) {
			// TODO Auto-generated catch block		List<Object> params = (List<Object>) obj;

			e.printStackTrace();
		}

		return returnObj;
	}

	public Host getLocalHost(){
		return localHost;
	}
	
	public ServerData getServerData(){
		return serverData; 
	}
	
	/*
	 * Auxiliary methods
	 */
	private String getHostAddress(){
		Socket socket = null;
        ObjectInputStream in = null;
        String testServerAddress = "sd.uoc.edu";
        int port = 54324;
        String hostAddress = null;
        try {
        	socket = new Socket(testServerAddress, port);
        	in = new ObjectInputStream(socket.getInputStream());
        	hostAddress = (String) in.readObject();
        	in.close();
        	socket.close();
        } catch (IOException e) {
        	System.err.println("WorkerInitiHandler -- getHostAddress -- Couldn't get I/O for "
        			+ "the connection to: " + testServerAddress);
        	System.exit(1);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return hostAddress;
	}
}