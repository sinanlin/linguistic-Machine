import com.mikelab.ExperimentMachine;

public class lingExperiment{

  public static void main(String[] args){

    ExperimentMachine machine = new ExperimentMachine();
    machine.importFrom("data.txt");
    machine.run();
    System.out.println("Saving data...");
    machine.exportTo("data.txt");
  }


}
