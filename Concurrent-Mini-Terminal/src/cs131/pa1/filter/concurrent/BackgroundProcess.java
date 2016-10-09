package cs131.pa1.filter.concurrent;

import java.util.List;

public class BackgroundProcess {
    // This is just a data structure that will store the List of concurrent filters for a command and a boolean to keep
    // track of when the command has already been sent to standard output
    private List<ConcurrentFilter> filters;
    private boolean printed = false;

    public BackgroundProcess(List<ConcurrentFilter> filters, Boolean printed) {
        this.printed = printed;
        this.filters = filters;
    }

    public Boolean getPrinted() {
        return this.printed;
    }

    public List<ConcurrentFilter> getFilters() {
        return this.filters;
    }

    public void setTrue() {
        this.printed = true;
    }

    public void setFalse() {
        this.printed = false;
    }
}
