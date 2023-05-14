package digital.paynetics.phos.kernel.common.emv.kernel.common;


public interface Kernel {
    KernelType getKernelType();

    int getAppKernelId();

    /**
     * This is Application Version Number (Tag 9F09) ("Version number assigned by the payment system for the
     * application", i.e. the terminal/reader/kernel
     *
     * @return kernel app version number
     */
    int getKernelApplicationVersion();

    boolean stopSignal();
}
