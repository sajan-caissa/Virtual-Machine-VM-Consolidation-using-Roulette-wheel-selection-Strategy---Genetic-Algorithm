package org.cloudbus.cloudsim.examples;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class GeneticAlgo {
	
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;

	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {

		Log.printLine("VM Consolidation using Roulette Wheel Strategy");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the GridSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
			@SuppressWarnings("unused")
			Datacenter datacenter1 = createDatacenter("Datacenter_1");
			
			// Datacenter datacenter2 = createDatacenter("Datacenter_2");


			//Third step: Create Broker
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();

			//Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			//VM description
			int vmid1 = 0;
			int mips1 = 250;
			long size1 = 10000; //image size (MB)
			int ram1 = 512; //vm memory (MB)
			long bw1 = 1000;
			int pesNumber1 = 1; //number of cpus
			String vmm1 = "Xen"; //VMM name
			
			int vmid2 = 0;
			int mips2 = 500;
			long size2 = 10000; //image size (MB)
			int ram2 = 512; //vm memory (MB)
			long bw2 = 1000;
			int pesNumber2 = 1; //number of cpus
			String vmm2 = "Xen"; //VMM name
			
			int vmid3 = 0;
			int mips3 = 50;
			long size3 = 10000; //image size (MB)
			int ram3 = 512; //vm memory (MB)
			long bw3 = 1000;
			int pesNumber3 = 1; //number of cpus
			String vmm3 = "Xen"; //VMM name
			
			int vmid4 = 0;
			int mips4 = 250;
			long size4 = 10000; //image size (MB)
			int ram4 = 512; //vm memory (MB)
			long bw4 = 1000;
			int pesNumber4 = 1; //number of cpus
			String vmm4 = "Xen"; //VMM name
			
			int vmid5 = 0;
			int mips5 = 250;
			long size5 = 10000; //image size (MB)
			int ram5 = 256; //vm memory (MB)
			long bw5 = 1000;
			int pesNumber5 = 1; //number of cpus
			String vmm5 = "Xen"; //VMM name
			
			int vmid6 = 0;
			int mips6 = 250;
			long size6 = 10000; //image size (MB)
			int ram6 = 512; //vm memory (MB)
			long bw6 = 1000;
			int pesNumber6 = 1; //number of cpus
			String vmm6 = "Xen"; //VMM name

			//create two VMs
			Vm vm1 = new Vm(vmid1, brokerId, mips1, pesNumber1, ram1, bw1, size1, vmm1, new CloudletSchedulerTimeShared());

			// vmid++; (I Changed it)
			
			Vm vm2 = new Vm(vmid2, brokerId, mips2, pesNumber2, ram2, bw2, size2, vmm2, new CloudletSchedulerTimeShared());

	
			Vm vm3 = new Vm(vmid3, brokerId, mips3, pesNumber3, ram3, bw3, size3, vmm3, new CloudletSchedulerTimeShared());

			Vm vm4 = new Vm(vmid4, brokerId, mips4, pesNumber4, ram4, bw4, size4, vmm4, new CloudletSchedulerTimeShared());

			Vm vm5 = new Vm(vmid5, brokerId, mips5, pesNumber5, ram5, bw5, size5, vmm5, new CloudletSchedulerTimeShared());

			Vm vm6 = new Vm(vmid6, brokerId, mips6, pesNumber6, ram6, bw6, size6, vmm6, new CloudletSchedulerTimeShared());

			
			
			
			//add the VMs to the vmList
			vmlist.add(vm1);
			vmlist.add(vm2);
			vmlist.add(vm3);
			vmlist.add(vm4);
			vmlist.add(vm5);
			vmlist.add(vm6);



			//submit vm list to the broker
			broker.submitVmList(vmlist);


			//Fifth step: Create two Cloudlets
			cloudletList = new ArrayList<Cloudlet>();

			//Cloudlet properties
			int id = 0;
			long length = 40000;
			long fileSize = 300;
			long outputSize = 300;
			UtilizationModel utilizationModel = new UtilizationModelFull();

			Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber1, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet1.setUserId(brokerId);

			id++;
			Cloudlet cloudlet2 = new Cloudlet(id, length, pesNumber3, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet2.setUserId(brokerId);

			//add the cloudlets to the list
			cloudletList.add(cloudlet1);
			cloudletList.add(cloudlet2);

			//submit cloudlet list to the broker
			broker.submitCloudletList(cloudletList);
			
			//NOTE HERE WRITE GENETIC ALGORITHM CODE FOR SEGMENT 1 CLOUDLET TO VM ALLOCATION


			//bind the cloudlets to the vms. This way, the broker
			// will submit the bound cloudlets only to the specific VM
			broker.bindCloudletToVm(cloudlet1.getCloudletId(),vm1.getId());
			broker.bindCloudletToVm(cloudlet2.getCloudletId(),vm2.getId());

			// Sixth step: Starts the simulation
			CloudSim.startSimulation();


			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();

			CloudSim.stopSimulation();

        	printCloudletList(newList);

			Log.printLine("CloudSim finished!");
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	private static Datacenter createDatacenter(String name){

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		//    our machine
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		List<Pe> peList = new ArrayList<Pe>();

		int mips = 1000;

		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		//4. Create Host with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 2048; //host memory (MB)
		long storage = 1000000; //host storage
		int bw = 10000;


		//in this example, the VMAllocatonPolicy in use is SpaceShared. It means that only one VM
		//is allowed to run on each Pe. As each Host has only one Pe, only one VM can run on each Host.
		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerSpaceShared(peList)
    			)
    		); // This is our first machine

		// 5. Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.001;	// the cost of using storage in this resource
		double costPerBw = 0.0;			// the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

	       DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
	                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	//to the specific rules of the simulated scenario
	private static DatacenterBroker createBroker(){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	/**
	 * Prints the Cloudlet objects
	 * @param list  list of Cloudlets
	 */
	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
						indent + indent + dft.format(cloudlet.getFinishTime()));
			}
		}

	}
}
	


