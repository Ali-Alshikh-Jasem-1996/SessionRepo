package js.technology.session.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
