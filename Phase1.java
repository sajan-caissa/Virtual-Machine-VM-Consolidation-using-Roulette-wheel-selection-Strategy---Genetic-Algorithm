
	

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
	import org.cloudbus.cloudsim.VmSchedulerTimeShared;
	import org.cloudbus.cloudsim.core.CloudSim;
	import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
	import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
	import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

	
	public class Phase1  {

		
		 final static int ELITISM_K = 4;
		    final static int POP_SIZE = 96 + ELITISM_K;  // population size
		 //   final static int MAX_ITER = 2000;             // max number of iterations
		//    final static double MUTATION_RATE = 0.05;     // probability of mutation
		 //   final static double CROSSOVER_RATE = 0.7;     // probability of crossover 
		    
		/** The cloudlet list. */
		public static List<Cloudlet> cloudletList;

		/** The vmlist. */
		public static List<Vm> vmlist;

		private static List<Vm> createVM(int userId, int vms, int idShift)
		{
			
			//Creates a container to store VMs. This list is passed to the broker later
			LinkedList<Vm> list = new LinkedList<Vm>();	       
			//VM Parameters
			long size = 10000; //image size (MB)
			int ram = 512; //vm memory (MB)
			int mips = 250;
			long bw = 1000;
			int pesNumber = 1; //number of cpu
			String vmm = "Xen"; //VMM name

			//create VMs
			Vm[] vm = new Vm[vms];
		/*	int[] ii=new int[10];
			Individual inte=pop.popmain();  
			for(int i=0;i<inte.SIZE;i++)
			{
				ii[i]=inte.getGene(i);
				System.out.println(ii[i]);
			}*/
			
			for(int i=0;i<vms;i++){
				
			//	idShift=ii[i];
				System.out.println(); 
				vm[i] = new Vm(idShift+i, userId, mips+(i*mips), pesNumber, ram+(i*256), bw, size, vmm, new CloudletSchedulerTimeShared());
				list.add(vm[i]);   //vm[i] = new Vm(idShift + i, userId, mips
			}
			
		/*	vm[0] = new Vm(0, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			vm[1] = new Vm(1, userId+1, mips+250, pesNumber+2, ram+512, bw+1000, size, vmm, new CloudletSchedulerTimeShared());
			vm[2] = new Vm(2, userId+2, mips, pesNumber+4, ram+256, bw+1500, size, vmm, new CloudletSchedulerTimeShared());
			
			list.add(vm[0]);
			list.add(vm[1]);
			list.add(vm[2]);*/
			
			return list;
		}


		private static List<Cloudlet> createCloudlet(int userId, int cloudlets, int idShift)
		{
			// Creates a container to store Cloudlets
			LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

			//cloudlet parameters
			long length = 40000;
			long fileSize = 300;
			long outputSize = 300;
			int pesNumber = 1;
			UtilizationModel utilizationModel = new UtilizationModelFull();

			Cloudlet[] cloudlet = new Cloudlet[cloudlets];

			for(int i=0;i<cloudlets;i++){
				cloudlet[i] = new Cloudlet(idShift+i , (length+(i*10000)), pesNumber+(i*2), (fileSize-(i*50)), outputSize, utilizationModel, utilizationModel, utilizationModel);
				
				// setting the owner of these Cloudlets
				cloudlet[i].setUserId(userId);
				list.add(cloudlet[i]);
			}

			return list;
		}


		

		
		 //Creates main() to run this example
		 
		public static void main(String[] args)
		{
			Log.printLine("Welcome to Roulette Whell allocation Strategy");

			try {
				// First step: Initialize the CloudSim package. It should be called
				// before creating any entities.
				int num_user = 3;   // number of grid users
				Calendar calendar = Calendar.getInstance();
				boolean trace_flag = false;  // mean trace events

				// Initialize the CloudSim library
				CloudSim.init(num_user, calendar, trace_flag);

				// Second step: Create Datacenters
				//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
				@SuppressWarnings("unused")
				Datacenter datacenter0 = createDatacenter("Datacenter_0");
				@SuppressWarnings("unused")
				
				Datacenter datacenter1 = createDatacenter("Datacenter_1");
				@SuppressWarnings("unused")
				Datacenter datacenter2 = createDatacenter("Datacenter_2");

				//Third step: Create Broker
				DatacenterBroker broker = createBroker("Broker_0");
				int brokerId = broker.getId();

				//Fourth step: Create VMs and Cloudlets and send them to broker
				vmlist = createVM(brokerId, 4, 0); //creating 6 vms
				cloudletList = createCloudlet(brokerId, 4, 0); // creating 3 cloudlets
				
			

				broker.submitVmList(vmlist);
				broker.submitCloudletList(cloudletList);
				/*for(Cloudlet c:cloudletList)
				{
					int vmId=assignCloudletToVM(vmlist,c);
				}*/
				
				//System.out.println("vmlist"+vmlist.size());
				
				//30-Sep-Sun -- start
//$#@!
				int tot=(vmlist.size()+cloudletList.size());
				//System.out.println("cloudletList"+cloudletList.size());
				int a[][]=new int[tot+1][tot+1];
				
				//intialization of decision matrix...
				for(int i=0;i<tot+1;i++)
				{
					for(int j=0;j<tot+1;j++)
					{
						a[i][j]=0;
					}
				}
				
				a=broker.assignCloudletToVM(vmlist,cloudletList,datacenter2);//new method for assignment
				//assigning as per decision matrix...
				for(int i=0;i<cloudletList.size();i++)
				{
					for(int j=0;j<vmlist.size();j++)
					{
						if(a[i][j]==1)
						{
							System.out.println("Inside binding.........");

							broker.bindCloudletToVm(i,j);
						}
					}
				}
				
				
			//	broker.bindCloudletToVm(1,0);

				// 30-Sep-Sun end
			
				// A thread that will create a new broker at 200 clock time
				Runnable monitor = new Runnable() 
				{
					@Override
					public void run() 
					{
						CloudSim.pauseSimulation(200);
						while (true)
						{
							if (CloudSim.isPaused()) 
							{
								break;
							}
							try 
							{
								Thread.sleep(100);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}

						Log.printLine("\n\n\n" + CloudSim.clock() + ": The simulation is paused for 5 sec \n\n");

						try
						{
							Thread.sleep(5000);
						}
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}

				/*		DatacenterBroker broker = createBroker("Broker_1");
						int brokerId = broker.getId();

						//Create VMs and Cloudlets and send them to broker
						vmlist = createVM(brokerId, 6, 100); //creating 6 vms
						cloudletList = createCloudlet(brokerId, 3, 100); // creating 3 cloudlets

						broker.submitVmList(vmlist);
						broker.submitCloudletList(cloudletList);*/

						CloudSim.resumeSimulation();
					}
				};

				new Thread(monitor).start();
				Thread.sleep(1000);

				// Fifth step: Starts the simulation
				CloudSim.startSimulation();

				// Final step: Print results when simulation is over
				List<Cloudlet> newList = broker.getCloudletReceivedList();
				
				
				CloudSim.stopSimulation();

				printCloudletList(newList);

				Log.printLine("Roulette Wheel Successfully finished!");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				Log.printLine("The simulation has been terminated due to an unexpected error");
			}
		}

		private static Datacenter createDatacenter(String name)
		{

			// Here are the steps needed to create a PowerDatacenter:
			// 1. We need to create a list to store one or more
			//    Machines
			List<Host> hostList = new ArrayList<Host>();

			// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
			//    create a list to store these PEs before creating
			//    a Machine.
			List<Pe> peList1 = new ArrayList<Pe>();

			int mips = 1000;

			// 3. Create PEs and add these into the list.
			//for a quad-core machine, a list of 4 PEs is required:
			peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
			peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
			peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
			peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

			//Another list, for a dual-core machine
			List<Pe> peList2 = new ArrayList<Pe>();

			peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
			peList2.add(new Pe(1, new PeProvisionerSimple(mips)));

			//4. Create Hosts with its id and list of PEs and add them to the list of machines
			int hostId=0;
			int ram = 200000; //host memory (MB)
			long storage = 1000000; //host storage
			int bw = 10000;

			hostList.add(
	    			new Host(
	    				hostId,
	    				new RamProvisionerSimple(ram),
	    				new BwProvisionerSimple(bw),
	    				storage,
	    				peList1,
	    				new VmSchedulerTimeShared(peList1)
	    			)
	    		); // This is our first machine

			hostId++;

			hostList.add(
	    			new Host(
	    				hostId,
	    				new RamProvisionerSimple(ram),
	    				new BwProvisionerSimple(bw),
	    				storage,
	    				peList2,
	    				new VmSchedulerTimeShared(peList2)
	    			)
	    		); // Second machine

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
			double costPerStorage = 0.1;	// the cost of using storage in this resource
			double costPerBw = 0.1;			// the cost of using bw in this resource
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
        // IMPORTANT NOTE REGARDING BROKER POLICY
		//We  should strongly encourage users to develop our own broker policies, to submit vms and cloudlets according
		//to the specific rules of the simulated scenario
		public static DatacenterBroker createBroker(String name){

			DatacenterBroker broker = null;
			try {
				broker = new DatacenterBroker(name);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return broker;
		}

		
		 /* Prints the Cloudlet objects
		    param list  list of Cloudlets*/
		
	
		private static void printCloudletList(List<Cloudlet> list) {
			int size = list.size();
			Cloudlet cloudlet;

			String indent = "    ";
			Log.printLine();
			Log.printLine("========== OUTPUT ==========");
			Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
					"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

			DecimalFormat dft = new DecimalFormat("###.##");
			for (int i = 0; i < size; i++) {
				cloudlet = list.get(i);
				Log.print(indent + cloudlet.getCloudletId() + indent + indent);

				if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
					Log.print("SUCCESS");
					
				
					Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
							indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
							indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
				
					
				}
			}

		}
	}

	
	
