package codeTest.v1.single;

import java.util.concurrent.atomic.AtomicReference;

public class CasSingle {
    private static AtomicReference<CasSingle> instance = null;

    private CasSingle() {

    }

    public static CasSingle getInstance() {
        if (instance.get() == null) {
            instance.compareAndSet(null, new CasSingle());
        }
        return instance.get();
    }

}
