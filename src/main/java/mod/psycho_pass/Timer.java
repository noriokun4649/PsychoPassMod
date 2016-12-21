package mod.psycho_pass;

/**
 * Created by noriokun4649 on 2016/12/21.
 */
public class Timer extends Thread {
    public boolean istime = false;
    public void run(){
        try {
            sleep(3000);
            istime = true;
        }catch (InterruptedException e){

        }
    }
}
