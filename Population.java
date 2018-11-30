package org.cloudbus.cloudsim.examples;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Vm;

public class Population
{
	
	    final static int ELITISM_K = 4;
	    final static int POP_SIZE = 96 + ELITISM_K;  // population size
	    final static int MAX_ITER = 100;             // max number of iterations
	    final static double MUTATION_RATE = 0.1;     // probability of mutation
	    final static double CROSSOVER_RATE = 0.8;     // probability of crossover

	    private static Random m_rand = new Random();  // random-number generator
	    private Individual[] m_population;
	    //=new Individual[200];
	    private double totalFitness;
		//private String[] population;
		private String[] population;
		List<? extends Vm> vmList=new ArrayList();
		List<? extends Cloudlet> CloudletList=new ArrayList();
		//DatacenterBroker db = null;
		
	    
		public Population()
		{
			
		}
		
		public Population(List<String> population2, List<? extends Vm> vmlist, List<? extends Cloudlet> cloudletList, Datacenter datacenter2) throws Exception 
	    {
		/*	this.vmList=vmlist;
			this.CloudletList=cloudletList;*/
			
	        m_population = new Individual[POP_SIZE+1];
	        
	       // String temp;
	        
	        
	        for (int i = 0; i < POP_SIZE; i++) 
	        {
	        	m_population[i] = new Individual();
	        	m_population[i].gene=population2.get(i);
	        	for(int j=0;j<((population2.get(i)).length());j++)
	        	{
	        		m_population[i].genes[j]=Integer.parseInt(String.valueOf((m_population[i].gene).charAt(j)));
	        	//	System.out.println("Check  :"+m_population[i].genes[j]);
	        	} 
	        //	System.out.println("\n");
	      //      m_population[i].randGenes();
	        }
	        
	        

	        // evaluate current population
	        this.evaluate(population2,vmlist,cloudletList,datacenter2);
	    }
	    public void setPopulation(Individual[] newPop) 
	    {
	    	
	       // this.m_population = population;
	        System.arraycopy(newPop, 0, this.m_population, 0, POP_SIZE);
	    }

	    public Individual[] getPopulation() 
	    {
	        return this.m_population;
	    }

	    //Evaluate Fitness
	    public double evaluate(List<String> population2, List<? extends Vm> vmlist, List<? extends Cloudlet> cloudletList, Datacenter datacenter2) throws Exception 
	    {
	    	//              DatacenterBroker db;      // = null;
	    	Individual id=new Individual();
	      //  this.totalFitness = 0.0;
	        for (int i = 0; i < POP_SIZE; i++) 
	        {
	        	// Evaluate using power function or try to implement by calculating power 
	        	//if vm is assigned to that datacentre or host
	        	//db.evaluatePower(m_population[idx].gene,vmlist,cloudletList,datacenter2);
	        	
	        	String str=population2.get(i);
	        	float power=id.evaluate(str,vmlist,cloudletList,datacenter2);
	            this.totalFitness +=power;
	        }
	        return this.totalFitness;
	    }

	    //Select Parents
	    public Individual rouletteWheelSelection() 
	    {
	    //	System.out.println("Inside Roulette ");
	        double randNum = m_rand.nextDouble() * this.totalFitness;
	        
	        
	      //  System.out.println("Rnd num "+randNum+" Fitness "+this.totalFitness);
	        int idx;
	        for (idx=0; idx<POP_SIZE && randNum>0; ++idx) //randNum
	        {
	        //	System.out.println("Fitness value :"+ m_population[idx].getFitnessValue());
	            randNum -= m_population[idx].getFitnessValue();
	        //    System.out.println("Fitnes in roulette idx & randnum "+idx+"    "+randNum);
	        }
	        return m_population[idx-1];
	      //  return m_population[idx];
	    }

	    //Find Fittest
	    public Individual findBestIndividual(Population pop, List<? extends Vm> vmlist, List<? extends Cloudlet> cloudletList, Datacenter datacenter2) throws Exception 
	    {
	        int idxMax = 0, idxMin = 0;
	        double currentMax = 0.0;
	        double currentMin = 1.0;
	        double currentVal;

	        DatacenterBroker db = new DatacenterBroker("Db0");
	        for (int idx=0; idx<POP_SIZE; ++idx) 
	        {
	        	
	          //  currentVal = m_population[idx].getFitnessValue();
	            
	            currentVal=db.evaluatePower(m_population[idx].gene,vmlist,cloudletList,datacenter2);
	         //   System.out.println("Current val :"+currentVal);
	            
	            if (currentMax < currentMin) 
	            {
	                currentMax = currentMin = currentVal;
	                idxMax = idxMin = idx;
	            }
	            if (currentVal > currentMax) 
	            {
	                currentMax = currentVal;
	                idxMax = idx;
	            }
	            if (currentVal < currentMin) 
	            {
	                currentMin = currentVal;
	                idxMin = idx;
	            }
	        }

	        return m_population[idxMin];      // minimization
	       // return m_population[idxMax];        // maximization
	    }

	    //Crossover Parents
	    public static Individual[] crossover(Individual indiv1,Individual indiv2) 
	    {
	    	//System.out.println("Indiv1 :"+indiv1.gene);
	    	//System.out.println("Indiv2 :"+indiv2.gene);
	        Individual[] newIndiv = new Individual[2];
	        newIndiv[0] = new Individual();
	        newIndiv[1] = new Individual();

	        int randPoint = m_rand.nextInt(Individual.SIZE);
	        int i;
	        for (i=0; i<randPoint; ++i) 
	        	
	        {
	        	//System.out.println("Error :"+i);
	            newIndiv[0].setGene(i, indiv1.getGenes(i));
	            newIndiv[1].setGene(i, indiv2.getGenes(i));
	        }
	        for (; i<Individual.SIZE; ++i) 
	        {
	            newIndiv[0].setGene(i, indiv2.getGenes(i));
	            newIndiv[1].setGene(i, indiv1.getGenes(i));
	        }
	        

	        return newIndiv;
	    }


	    public Individual popmain(List<String> population2, List<? extends Vm> vmlist, List<? extends Cloudlet> cloudletList, Datacenter datacenter2) throws Exception 
	    {
	        Population pop = new Population(population2,vmlist,cloudletList,datacenter2);
	       // pop.evaluate(pop);
	        Individual[] newPop = new Individual[POP_SIZE+1];
	        Individual[] indiv = new Individual[2];
	   //     List<String> chromosome=new ArrayList();
	        Individual ind=new Individual();
		    ind.gene="61854372";
	        // current population
	      System.out.println("GENETIC ALGORITHM");
	        System.out.println("Using Roulette Wheel Selection");
	        System.out.println();
	        System.out.println("Total Population Fitness = " + pop.totalFitness);
	        System.out.println(" ; Best Fitness = " + 
	            (pop.findBestIndividual(pop,vmlist,cloudletList,datacenter2)).getFitnessValue());

	        
	       
	        
	        // main loop2][/*
	        int count;
	        for (int iter = 0; iter < MAX_ITER; iter++) 
	        {
	            count = 0;

	            // Elitism
	            for (int i=0; i<ELITISM_K; ++i) 
	            {
	            	//System.out.println("Elitism"+i);
	               newPop[count] = pop.findBestIndividual(pop,vmlist,cloudletList,datacenter2);
	             //  System.out.println("Eli String"+newPop[count].gene);
	                count++;
	            }

	            
	            
	            // build new Population
	            while (count < POP_SIZE) 
	            {
	                // Selection
	            	//System.out.println("Count of pop_size"+count);
	                indiv[0] = pop.rouletteWheelSelection();
	                indiv[1] = pop.rouletteWheelSelection();

	                // Crossover
	                if ( m_rand.nextDouble() < CROSSOVER_RATE ) 
	                {
	                    indiv = crossover(indiv[0], indiv[1]);
	                }

	                // Mutation
	                if ( m_rand.nextDouble() < MUTATION_RATE )
	                 {
	                    indiv[0].mutate();
	                }
	                if ( m_rand.nextDouble() < MUTATION_RATE ) 
	                {
	                    indiv[1].mutate();
	                }

	                // add to new population
	                newPop[count] = indiv[0];
	                newPop[count+1] = indiv[1];
	                count += 2;
	            }
	            List<String> newPopString=new ArrayList();
	            
	            for(int i=0;i<(newPop.length)-1;i++)
	            {
	            	//System.out.println("New Pop length"+newPop.length+"  i  "+i);
	            	newPopString.add(newPop[i].gene);
	            }
	            
	            
	            
	            pop.setPopulation(newPop);
                
	            // reevaluate current population
	            
	            
	            pop.evaluate(newPopString,vmlist,cloudletList,datacenter2);
	            System.out.print("Total Fitness = " + pop.totalFitness);
	           // System.out.println(" ; Best Fitness = " +
	             //   pop.findBestIndividual(pop,vmlist,cloudletList,datacenter2).getFitnessValue()); 
	        }
	        

	        // best indiv
	        Individual bestIndiv = pop.findBestIndividual(pop,vmlist,cloudletList,datacenter2);
	       // return bestIndiv;
	        System.out.println("Best Individual is"+bestIndiv);
	      
	       	return ind;
	        
	    
	    }

	    
}
