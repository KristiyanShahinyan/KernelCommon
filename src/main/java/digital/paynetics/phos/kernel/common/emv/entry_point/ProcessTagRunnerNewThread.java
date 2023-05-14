package digital.paynetics.phos.kernel.common.emv.entry_point;

import javax.inject.Inject;


public class ProcessTagRunnerNewThread implements ProcessTagRunner {
    @Inject
    public ProcessTagRunnerNewThread() {
    }


    @Override
    public void run(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.start();
    }
}
